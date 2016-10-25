package ar.uba.dc.analysis.memory.impl.summary;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethodBuilder;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.analysis.escape.graph.node.GlobalNode;
import ar.uba.dc.analysis.escape.graph.node.PaperGlobalNode;
import ar.uba.dc.analysis.escape.graph.node.PaperParamNode;
import ar.uba.dc.analysis.escape.graph.node.PaperStmtNode;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;
import ar.uba.dc.analysis.escape.graph.node.StmtNode;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.HeapPartitionVisitor;
import soot.SootMethod;

public class PaperPointsToHeapPartition implements HeapPartition {

	
	protected boolean temporal; //creo que lo voy a tirar
	protected PaperNode node;	
	public String belongsTo;
	
	public PaperNode getNode() {
		return node;
	}


	public void setNode(PaperNode node) {
		this.node = node;
	}

	

	public PaperPointsToHeapPartition(PaperNode node, boolean temporal)
	{
		this.temporal = temporal;
		this.node = node;
	}	
	
	
	public PaperPointsToHeapPartition(boolean temporal, Node node, String belongsTo)
	{
		this.temporal = temporal;
		
		
		//this.node = new PaperNode(node);
		
		
		this.belongsTo = belongsTo;
		
		
		if(node.getClass() == StmtNode.class)
		{
			this.node = new PaperStmtNode(node);
		}
		else if(node.getClass() == ParamNode.class)
		{
			this.node = new PaperParamNode(node);
		}
	}	
	
	
	private static Log log = LogFactory.getLog(PaperPointsToHeapPartition.class);

	public PaperPointsToHeapPartition(HeapPartition heapPartition)
	{
		this.temporal = heapPartition.isTemporal();
		
		if(heapPartition.getClass() == PointsToHeapPartition.class)
		{
			PointsToHeapPartition hp = ((PointsToHeapPartition) heapPartition);
			
			Node origNode = hp.getNode();

			
			//TODO: hacer los otros.
			if(origNode.getClass() == StmtNode.class)
			{
				this.node = new PaperStmtNode(origNode);
			}
			else if(origNode.getClass() == ParamNode.class)
			{
				this.node = new PaperParamNode(origNode);
			}
			
			SootMethod m = hp.getNode().belongsTo();
			
			if(hp.getNode().getContext().size() > 0)
			{
				log.debug("Hola");
			}
			
			this.belongsTo = m.getDeclaringClass().toString() + "." +  m.getName();
			
		}
		else
		{
			throw new NotImplementedException("Not implemented for another type of Heap Partition");
		}		
	}
	
	public PaperPointsToHeapPartition(boolean temporal, String belongsTo, PaperNode node) {
		this.temporal = temporal;
		this.belongsTo = belongsTo;
		this.node = node;
	}


	public PaperPointsToHeapPartition() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean isTemporal() {
		// TODO Auto-generated method stub
		return temporal;
	}

	@Override
	public HeapPartition clone() {
		return null;
	}

	@Override
	public <T> T apply(HeapPartitionVisitor<T> visitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toHumanReadableString() {
		// TODO Auto-generated method stub
		return null;
	}

}
