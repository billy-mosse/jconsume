package ar.uba.dc.analysis.memory.impl.summary;

import java.util.Iterator;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethodBuilder;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.analysis.escape.graph.node.GlobalNode;
import ar.uba.dc.analysis.escape.graph.node.MethodNode;
import ar.uba.dc.analysis.escape.graph.node.PaperGlobalNode;
import ar.uba.dc.analysis.escape.graph.node.PaperMethodNode;
import ar.uba.dc.analysis.escape.graph.node.PaperNodeUtils;
import ar.uba.dc.analysis.escape.graph.node.PaperParamNode;
import ar.uba.dc.analysis.escape.graph.node.PaperStmtNode;
import ar.uba.dc.analysis.escape.graph.node.PaperThisNode;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;
import ar.uba.dc.analysis.escape.graph.node.StmtNode;
import ar.uba.dc.analysis.escape.graph.node.ThisNode;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.HeapPartitionVisitor;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;
import soot.SootMethod;


public class RichPaperPointsToHeapPartition implements PaperPointsToHeapPartition {
	
	public static int counter = 0;

	
	protected boolean temporal; //creo que lo voy a tirar. TODO: tirar, posta.
	protected PaperNode node;	
	public String belongsTo;
	public int number;
	
	//puede ser null si es un NEW
	protected IntStringId comesFrom;
	
	public PaperNode getNode() {
		return node;
	}


	public void setNode(PaperNode node) {
		this.node = node;
	}
	

	@Override
	public int hashCode() {
		return node.hashCode() + (temporal ? 69 : 0); 
	}
	
	
	@Override
	public boolean equals(Object o)
	{
		if(o.getClass() == RichPaperPointsToHeapPartition.class)
		{
			try
			{
			RichPaperPointsToHeapPartition to_compare = (RichPaperPointsToHeapPartition)o;
			return to_compare.equals(this.temporal) == this.temporal && this.belongsTo.equals(to_compare.belongsTo) && to_compare.node.equals(this.node);
			}
			catch(Exception e)
			{
				throw new Error("Hola");
			}
		}
		
		return false;
	}
	

	

	public RichPaperPointsToHeapPartition(PaperNode node, boolean temporal)
	{
		this.temporal = temporal;
		this.node = node;
	}	

	public RichPaperPointsToHeapPartition(PaperNode node)
	{
		this.node = node;
	}	
	
	public RichPaperPointsToHeapPartition(PaperNode node, boolean temporal, String belongsTo)
	{
		this.temporal = temporal;
		this.node = node;
		this.belongsTo = belongsTo;
	}	
	
	
	/*public PaperPointsToHeapPartition(boolean temporal, Node node, CircularStack<String> context, String belongsTo)
	{
		this.temporal = temporal;
		
		this.number = counter;
		counter++;
		
		
		//this.node = new PaperNode(node);
		
		
		this.belongsTo = belongsTo;
		
		this.node = PaperNodeUtils.createPaperNodeFromNormalNode(node, context, belongsTo);
	}	*/
	
	
	public RichPaperPointsToHeapPartition(boolean temporal, Node node, CircularStack<String> context, String belongsTo, int number)
	{
		this.temporal = temporal;
		
		this.number = number;
		
		this.belongsTo = belongsTo;
		
		this.node = PaperNodeUtils.createPaperNodeFromNormalNode(node, context, belongsTo);
	}	
	
	@Override
	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}

	
	private static Log log = LogFactory.getLog(RichPaperPointsToHeapPartition.class);

	public RichPaperPointsToHeapPartition(HeapPartition heapPartition)
	{
		this.temporal = heapPartition.isTemporal();
		
		if(heapPartition.getClass() == PointsToHeapPartition.class)
		{
			PointsToHeapPartition hp = ((PointsToHeapPartition) heapPartition);
			
			Node origNode = hp.getNode();
			
			
			CircularStack<StatementId> context = origNode.getContext();
			CircularStack<String> ir_context = new CircularStack<String>();
			
			if(context != null)
			{
				Iterator<StatementId>ctxtIterator = context.iterator();
				while(ctxtIterator.hasNext())
				{
					StatementId id = ctxtIterator.next();
					String methodFullName = id.getMethodOfId().getDeclaringClass() + "." + id.getMethodOfId().getName();
					ir_context.push(methodFullName);
				}
			}
			
			
			SootMethod m = hp.getNode().belongsTo();
			
			this.belongsTo = m.getDeclaringClass().toString() + "." +  m.getName();
			
			

			this.node = PaperNodeUtils.createPaperNodeFromNormalNode(origNode, ir_context, belongsTo);
			
		}
		else
		{
			throw new NotImplementedException("Not implemented for another type of Heap Partition");
		}		
	}
	
	public RichPaperPointsToHeapPartition(boolean temporal, String belongsTo, PaperNode node) {
		this.temporal = temporal;
		this.belongsTo = belongsTo;
		this.node = node;
	}


	public RichPaperPointsToHeapPartition() {
		// TODO Auto-generated constructor stub
	}


	public boolean isTemporal() {
		// TODO Auto-generated method stub
		return temporal;
	}

	public HeapPartition clone() {
		return null;
	}

	public <T> T apply(HeapPartitionVisitor<T> visitor) {
		// TODO Auto-generated method stub
		return null;
	}

	public String toHumanReadableString() {
		throw new NotImplementedException();
	}
	
	@Override
	public String toString()
	{
		return this.belongsTo + "." + (this.temporal ? "(Temp)" : "") + this.node.toString(); 
	}

}
