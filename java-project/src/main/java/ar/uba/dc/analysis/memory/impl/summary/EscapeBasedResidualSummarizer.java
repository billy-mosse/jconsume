package ar.uba.dc.analysis.memory.impl.summary;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import soot.SootClass;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.SymbolicCalculator;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.summary.ResidualSummarizer;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;
import ar.uba.dc.util.collections.map.HashMultiMap;
import ar.uba.dc.util.collections.map.MultiMap;

/**
 * Implementa el coloreo para calcular residual. Basicamente devuelve 
 * 
 * sum (X) + sum (W) donde:
 * 
 *  - X son los nodos creados en el metodo
 *  - W es max(Q) donde Q es el residual de c/implementacion de un call. 
 *  
 * Puede verse que esta expresion es recursiva y tiene como caso base a los HeapPartition que se encuentra
 * en X.
 * 
 * @author testis
 */
public class EscapeBasedResidualSummarizer implements ResidualSummarizer<EscapeBasedMemorySummary, ParametricExpression> {

	protected ParametricExpressionFactory expressionFactory;
	
	protected SymbolicCalculator symbolicCalculator;
	
	@Override
	public ParametricExpression getResidual(EscapeBasedMemorySummary summary) {
		// Como el proceso de summarizacion modifica las particiones, trabajamos sobre una copia
		Set<PointsToHeapPartition> heapPartitions = new HashSet<PointsToHeapPartition>();
		Map<PointsToHeapPartition, ParametricExpression> mapping = new HashMap<PointsToHeapPartition, ParametricExpression>();
		for (HeapPartition hp : summary.getResidualPartitions()) {
			PointsToHeapPartition php = (PointsToHeapPartition) hp.clone();
			heapPartitions.add(php);
			mapping.put(php, summary.getResidual(php));
		}
		
		return getResidual(heapPartitions, mapping);
	}
	
	protected ParametricExpression getResidual(Set<PointsToHeapPartition> heapPartitions, Map<PointsToHeapPartition, ParametricExpression> mapping) {
		ParametricExpression residual = expressionFactory.constant(0L);
		
		// Primero separamos las particiones en aquellas que se generaron por calls y 
		// por news del metodo al que pertenece el summary
		LinkedList<PointsToHeapPartition> fromNews = new LinkedList<PointsToHeapPartition>();
		LinkedList<PointsToHeapPartition> fromCalls= new LinkedList<PointsToHeapPartition>();
		for (HeapPartition hp : heapPartitions) {
			PointsToHeapPartition php = (PointsToHeapPartition) hp;
			if (php.getNode().isInside()) {
				if (php.getNode().getContext().size() == 0) {
					fromNews.add(php);
				} else {
					fromCalls.add(php);
				}
			}
		}
		
		// Todos los objetos creados por news aportan al residual su consumo
		ParametricExpression[] expressionsFromNews = new ParametricExpression[fromNews.size()];
		int i = 0;
		for (PointsToHeapPartition hp : fromNews) {
			expressionsFromNews[i] = mapping.get(hp);
			if (expressionsFromNews[i] == null) {
				expressionsFromNews[i] = expressionFactory.constant(0L);
			}
			i++;
		}
		
		if (expressionsFromNews.length > 0) {
			residual = symbolicCalculator.add(expressionsFromNews);
		}
		
		// Agrupamos los nodos por el call del cual provienen
		MultiMap<Stmt, PointsToHeapPartition> hpGroupByCall = new HashMultiMap<Stmt, PointsToHeapPartition>(); // call -> { hp polimorficas }
		for (PointsToHeapPartition hp : fromCalls) {
			Stmt call = hp.getNode().getContext().peek().getId();
			hpGroupByCall.put(call, hp);
		}
		
		// Calculamos el residual que aporta cada call.
		ParametricExpression[] expressionsFromCalls = new ParametricExpression[hpGroupByCall.keySet().size()];
		
		// Dividimos cada call en nodos de acuerdo a la implementacion del call de la cual provienen.
		i = 0;
		for (Stmt call : hpGroupByCall.keySet()) {
			MultiMap<SootClass, PointsToHeapPartition> hpGroupedByImplementation = new HashMultiMap<SootClass, PointsToHeapPartition>(); // impl -> particiones de esa impl
			for (PointsToHeapPartition hp : hpGroupByCall.get(call)) {
				SootClass impl = getImplementation(hp);
				hpGroupedByImplementation.put(impl, hp);
			}
			// Calculamos cuanto residual aporta este call. Esto se hace sacando el maximo de todas las implementaciones
			ParametricExpression expressionForCall = expressionFactory.constant(0L);
			for (SootClass impl : hpGroupedByImplementation.keySet()) {
					// Nos preparamos para obtener el residual haciendo un llamado recurisvo
				Map<PointsToHeapPartition, ParametricExpression> implMapping = new HashMap<PointsToHeapPartition, ParametricExpression>();
				Set<PointsToHeapPartition> implHps = new HashSet<PointsToHeapPartition>();
				for (PointsToHeapPartition hp : hpGroupedByImplementation.get(impl)) {
					PointsToHeapPartition implHp = hp.clone();
					implHp.getNode().popContext();
					implHps.add(implHp);
					implMapping.put(implHp, mapping.get(hp));
				}
					// Obtenemos recursivamente el residual para esta implementacion
				ParametricExpression residualImpl = getResidual(implHps, implMapping);
					// Nos quedamos con el maximo valor calculado hasta ahora (para un call polimorfico, 
					// devolvemos la implementacion que aporta mas residual)
				expressionForCall = symbolicCalculator.supreme(expressionForCall, residualImpl);
			}
			expressionsFromCalls[i] = expressionForCall;
			i++;
		}
		
		// Agregamos al residual el valor obtenido de contar el residual por c/implementacion
		if (expressionsFromCalls.length > 0) {
			residual = symbolicCalculator.add(residual, symbolicCalculator.add(expressionsFromCalls));
		}
		
		return residual;
	}

	protected SootClass getImplementation(PointsToHeapPartition hp) {
		CircularStack<StatementId> ctx = hp.getNode().getContext();
		SootClass impl = null;
		
		// El stack tiene como primer elemento al metodo invocado por el metodo al cual pertenece el summary 
		// que estamos procesando. Si el nodo procede de otro call (el metodo invocado invoco a otro call), la implementacion
		// podemos obtenerla de dicho call. Si no hay mas calls, eso quiere decir que la impl la obtenemos de la clase a la que
		// pertenece el nodo (la que creo el nodo).
		if (ctx.size() > 1) {
			ctx.pop();
			impl = ctx.peek().getMethodOfId().getDeclaringClass();
		} else {
			impl = hp.getNode().belongsTo().getDeclaringClass();
		}
		
		return impl;
	}

	public void setExpressionFactory(ParametricExpressionFactory expressionFactory) {
		this.expressionFactory = expressionFactory;
	}

	public void setSymbolicCalculator(SymbolicCalculator symbolicCalculator) {
		this.symbolicCalculator = symbolicCalculator;
	}

}