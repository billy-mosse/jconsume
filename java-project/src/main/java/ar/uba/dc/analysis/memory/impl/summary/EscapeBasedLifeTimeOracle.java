package ar.uba.dc.analysis.memory.impl.summary;

import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.StmtNode;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.LifeTimeOracle;
import ar.uba.dc.analysis.memory.code.CallStatement;
import ar.uba.dc.analysis.memory.code.NewStatement;
import ar.uba.dc.invariant.InvariantProvider;
import ar.uba.dc.soot.StatementId;

public class EscapeBasedLifeTimeOracle implements LifeTimeOracle {

	private SummaryRepository<EscapeSummary> escapeSummaryProvider;
	
	private InvariantProvider invariantProvider;
	
	private int sensitivity;
	
	@Override
	public HeapPartition bind(HeapPartition calleePartition, CallStatement callStmt) {
		PointsToHeapPartition hpCallee = (PointsToHeapPartition) calleePartition;
		
			// Ignoramos los parametros
		if (hpCallee.getNode().isParam()) {
			return null;
		}
		
			// Construyo el nodo en el caller que basicamente es el mismo nodo pero cambiando el contexto (el escape era k-sensitivo)
		Node escapeNode = hpCallee.getNode().clone();
		escapeNode.changeContext(new StatementId(callStmt.belongsTo(), callStmt.getStatement()));
		
			// Obtengo el escape summary del metodo bajo analisis
		EscapeSummary callerSummary = escapeSummaryProvider.get(callStmt.belongsTo());
		
		// La particion puede estar como nodo o bien contenida dentro del mismo 
		Node nodeToBind = null;
		for (Node callerNode : callerSummary.getNodes()) {
			if (callerNode.accept(escapeNode)) {
				nodeToBind = callerNode;
				break;
			}
		}
		
			// Podria ocurrir que el callstmt sea un virtual call. En este caso el escape podria resolver 
			// el polimorfismo y darse cuenta de que hay una implementacion que no es invocada pero para memoria
			// no darnos cuenta. Si esto ocurre, no consideramos las particiones de la implementacion que escape
			// ha ignorado. Esto es correcto porque si escape ignora implementaciones, en el summary de escape no 
			// figuraran estos nodos y por tanto en el summary de memoria tampoco.
		if (nodeToBind == null) {
			return null;
		}
		
			// A partir del summary me fijo si el nodo escapa o no.
		boolean isTemporal = !callerSummary.getEscaping().contains(nodeToBind);
		isTemporal = isTemporal || invariantProvider.captureAllPartitions(callStmt);

			// Con eso ya puedo armar el heapPartition del caller.
		PointsToHeapPartition hpCaller = new PointsToHeapPartition(nodeToBind, isTemporal);
		
		return hpCaller;
	}

	@Override
	public HeapPartition getPartition(NewStatement newStmt) {
		StmtNode node = new StmtNode(new StatementId(newStmt.belongsTo(), newStmt.getStatement()), true, sensitivity);
		
			// Obtengo el escape summary del metodo bajo analisis
		EscapeSummary escapeSummary = escapeSummaryProvider.get(newStmt.belongsTo());
		
			// Hay nodos que pueden representar a muchos news. Vemos si alguno de ellos escapa
		boolean isTemporal = true;
		Node finalNode = node;
		
		// A partir del summary me fijo si el nodo escapa o no
		for (Node escapeNode : escapeSummary.getEscaping()) {
			if (escapeNode.accept(node)) {
				isTemporal = false;
				finalNode = escapeNode;
				break;
			}
		}
		
		// El invariantProvider puede pisar la decision tomada en base al summary
		isTemporal = isTemporal || invariantProvider.captureAllPartitions(newStmt);
		
		PointsToHeapPartition hp = new PointsToHeapPartition(finalNode, isTemporal);
		
		return hp;
	}

	public void setEscapeSummaryProvider(SummaryRepository<EscapeSummary> escapeSummaryProvider) {
		this.escapeSummaryProvider = escapeSummaryProvider;
	}

	public void setInvariantProvider(InvariantProvider invariantProvider) {
		this.invariantProvider = invariantProvider;
	}

	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}
}
