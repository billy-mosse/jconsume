package ar.uba.dc.analysis.memory.impl.summary.io.xstream;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import soot.SootMethod;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpression;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpressionFactory;
import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedMemorySummary;
import ar.uba.dc.analysis.memory.impl.summary.PointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.PointsToHeapPartitionEdge;
import ar.uba.dc.barvinok.BarvinokSyntax;
import ar.uba.dc.soot.SootUtils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class EscapeBasedMemorySummaryConverter extends AbstractCollectionConverter implements Converter {
	
	private BarvinokSyntax syntax;
	
	private BarvinokParametricExpressionFactory expressionFactory;
	
	public EscapeBasedMemorySummaryConverter(Mapper mapper, BarvinokSyntax syntax, BarvinokParametricExpressionFactory expressionFactory) {
		super(mapper);
		this.syntax = syntax;
		this.expressionFactory = expressionFactory;
	}
	
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		EscapeBasedMemorySummary summary = (EscapeBasedMemorySummary) source;
		
		writer.addAttribute("className", summary.getTarget().getDeclaringClass().getName());
		writer.addAttribute("methodSignature", summary.getTarget().getSubSignature());
		
		writer.startNode("heap");
			marshalHeap(writer, context, summary);
		writer.endNode();
		
		writer.startNode("parameters");
			for (String param : summary.getParameters()) {
				writer.startNode("parameter");
					writer.setValue(param);
				writer.endNode();
			}
		writer.endNode();
		
		writer.startNode("temporal");
			writer.setValue(toString(summary.getTemporal()));
		writer.endNode();
		
		writer.startNode("residual");
			for (HeapPartition hp : summary.getResidualPartitions()) {
				PointsToHeapPartition php = (PointsToHeapPartition) hp;
				writer.startNode("node");
					writeItem(php.getNode(), context, writer);
					writer.startNode("value");
						writer.setValue(toString(summary.getResidual(hp)));
					writer.endNode();
				writer.endNode();
			}
		writer.endNode();
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		String className = reader.getAttribute("className");
		String methodSignature = reader.getAttribute("methodSignature");
		
		SootMethod method = SootUtils.getMethod(className, methodSignature);
		
		Set<String> params = new TreeSet<String>();
		EscapeBasedMemorySummary summary = new EscapeBasedMemorySummary(method, params, null, null);
		
		Map<PointsToHeapPartition, PointsToHeapPartition> cachePartition = new HashMap<PointsToHeapPartition, PointsToHeapPartition>();
		
		reader.moveDown();
			// Parseamos el heap
			unmarshalHeap(reader, context, summary, cachePartition);
		reader.moveUp();
		reader.moveDown();
			unmarshalParameters(reader, context, params);
		reader.moveUp();
		reader.moveDown();
			String temporal = reader.getValue();
		reader.moveUp();
		reader.moveDown();
			// Parseamos la informacion de residual
			unmarshalResidual(reader, context, summary, cachePartition);
		reader.moveUp();
		
		summary.setTemporal(expressionFactory.polynomial(syntax.parsePiecewiseQuasipolynomial(temporal.replaceAll("==", "="))));
		return summary;
	}

	private void marshalHeap(HierarchicalStreamWriter writer, MarshallingContext context, EscapeBasedMemorySummary summary) {
		// Grafo de particiones
		writer.startNode("graph");
			// Nodos
			writer.startNode("nodes");
				for (HeapPartition hp : summary.getAllPartitions()) {
					PointsToHeapPartition php = (PointsToHeapPartition) hp;
					writeItem(php.getNode(), context, writer);
				}
			writer.endNode();
			// Ejes
			writer.startNode("edges");
				for (PointsToHeapPartition php : summary.getEdgesSources()) {
					for (PointsToHeapPartitionEdge phpEdge : summary.getEdgesOutOf(php)) {
						Edge edge = new Edge(phpEdge.getSource().getNode(), phpEdge.getField(), phpEdge.getTarget().getNode(), phpEdge.isInside());
						writeItem(edge, context, writer);
					}
				}
			writer.endNode();
		writer.endNode();
		
		// Particiones retornadas
		writer.startNode("returned");
			for (PointsToHeapPartition php : summary.getReturnedPartitions()) {
				writeItem(php.getNode(), context, writer);
			}
		writer.endNode();
		
		// Particiones que escapan globalmente
		writer.startNode("escape-globaly");
			for (PointsToHeapPartition php : summary.getEscapeGlobalyPartitions()) {
				writeItem(php.getNode(), context, writer);
			}
		writer.endNode();
	}
	
	private void unmarshalParameters(HierarchicalStreamReader reader, UnmarshallingContext context, Set<String> params) {
		while (reader.hasMoreChildren()) {
			reader.moveDown();
				String param = reader.getValue();
			reader.moveUp();
			params.add(param);
		}
	}
	
	private void unmarshalHeap(HierarchicalStreamReader reader, UnmarshallingContext context, EscapeBasedMemorySummary summary, Map<PointsToHeapPartition, PointsToHeapPartition> cachePartition) {
		reader.moveDown();
			// Grafo de particiones
			reader.moveDown();
				// Nodos
				while (reader.hasMoreChildren()) {
					reader.moveDown();
						Node node = (Node) readItem(reader, context, summary);
					reader.moveUp();
					summary.add(buildHeapPartition(node, cachePartition));
				}
			reader.moveUp();
			reader.moveDown();
				// Ejes
				while (reader.hasMoreChildren()) {
					reader.moveDown();
						Edge edge = (Edge) readItem(reader, context, summary);
					reader.moveUp();
					summary.relate(buildHeapPartition(edge.getSource(), cachePartition), 
								   edge.getField(), 
								   edge.isInside(), 
								   buildHeapPartition(edge.getTarget(), cachePartition));
				}
			reader.moveUp();
		reader.moveUp();
		reader.moveDown();
			// Particiones retornadas
			while (reader.hasMoreChildren()) {
				reader.moveDown();
					Node node = (Node) readItem(reader, context, summary);
				reader.moveUp();
				summary.returnPartition(buildHeapPartition(node, cachePartition));
			}
		reader.moveUp();
		reader.moveDown();
			// Particiones que escapan globalmente
			while (reader.hasMoreChildren()) {
				reader.moveDown();
					Node node = (Node) readItem(reader, context, summary);
				reader.moveUp();
				summary.partitionEscapeGlobaly(buildHeapPartition(node, cachePartition));
			}
		reader.moveUp();
	}

	private void unmarshalResidual(HierarchicalStreamReader reader, UnmarshallingContext context, EscapeBasedMemorySummary summary, Map<PointsToHeapPartition, PointsToHeapPartition> cachePartition) {
		while (reader.hasMoreChildren()) {
			reader.moveDown();
				reader.moveDown();
					Node target = (Node) readItem(reader, context, summary);
				reader.moveUp();
				reader.moveDown();
					String residual = reader.getValue();
				reader.moveUp();
			reader.moveUp();
			ParametricExpression residualValue = expressionFactory.polynomial(syntax.parsePiecewiseQuasipolynomial(residual.replaceAll("==", "=")));
			summary.setResidual(buildHeapPartition(target, cachePartition), residualValue);
		}
	}
	
	/**
	 * Usamos una cache para poder tener las mismas referencias dentro del objeto para cosas que con equals nos daria igual.
	 * Podriamos haber dejado un new por c/vez qeu creabamos un {@link PointsToHeapPartition} pero asi nos aseguramos que
	 * si dos cosas apuntan a particiones iguales (usando equals) esten apuntando a la misma referencia.  
	 * 
	 * @param target
	 * @param cache
	 * @return
	 */
	protected PointsToHeapPartition buildHeapPartition(Node target, Map<PointsToHeapPartition, PointsToHeapPartition> cache) {
		PointsToHeapPartition hp = new PointsToHeapPartition(target, false);
		if (!cache.containsKey(hp)) {
			cache.put(hp, hp);
		}
		 
		return cache.get(hp);
	}
	
	protected String toString(ParametricExpression expr) {
		return syntax.toString(((BarvinokParametricExpression) expr).getExpression());
	}

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return EscapeBasedMemorySummary.class.isAssignableFrom(clazz);
	}
}
