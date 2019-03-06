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


public class RichPaperPointsToHeapPartition implements PaperPointsToHeapPartition{
	
	public static Integer counter = 0;

	protected PaperNode node;
	private String belongsTo;
	public Integer number;
	
	//puede ser null si es un NEW
	protected IntStringId comesFrom;
	
	public RichPaperPointsToHeapPartition(Integer number)
	{
		this.number = number;
	}
	
	public PaperNode getNode() {
		return node;
	}


	public void setNode(PaperNode node) {
		this.node = node;
	}
	

	@Override
	public int hashCode() {
		return node.hashCode(); 
	}
	
	
	@Override
	public boolean equals(Object o)
	{
		if(o.getClass() == RichPaperPointsToHeapPartition.class)
		{
			try
			{
				RichPaperPointsToHeapPartition to_compare = (RichPaperPointsToHeapPartition)o;
				return this.getBelongsTo().equals(to_compare.getBelongsTo()) && to_compare.node.equals(this.node) && this.number.equals(to_compare.number);
			}
			catch(Exception e)
			{
				throw new Error("Hola");
			}
		}
		
		return false;
	}
	

	

	public RichPaperPointsToHeapPartition(PaperNode node)
	{
		this.node = node;
	}	
	
	public RichPaperPointsToHeapPartition(PaperNode node, String belongsTo)
	{
		this.node = node;
		this.setBelongsTo(belongsTo);
	}	
	
	public RichPaperPointsToHeapPartition(PaperNode node, String belongsTo, Integer number)
	{
		this.node = node;
		this.setBelongsTo(belongsTo);
		this.number = number;
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

	
	public RichPaperPointsToHeapPartition(Node node, CircularStack<String> context, String belongsTo, Integer number)
	{
		
		this.number = number;
		
		this.setBelongsTo(belongsTo);
		
		this.node = PaperNodeUtils.createPaperNodeFromNormalNode(node, context, belongsTo);
	}	
	
	@Override
	public Integer getNumber() {
		return number;
	}


	public void setNumber(Integer number) {
		this.number = number;
	}

	
	private static Log log = LogFactory.getLog(RichPaperPointsToHeapPartition.class);

	public RichPaperPointsToHeapPartition(HeapPartition heapPartition)
	{
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
			
			this.setBelongsTo(m.getDeclaringClass().toString() + "." +  m.getName());
			
			

			this.node = PaperNodeUtils.createPaperNodeFromNormalNode(origNode, ir_context, getBelongsTo());
			
		}
		else
		{
			throw new NotImplementedException("Not implemented for another type of Heap Partition");
		}		
	}
	
	public RichPaperPointsToHeapPartition(String belongsTo, PaperNode node) {
		this.setBelongsTo(belongsTo);
		this.node = node;
	}


	public RichPaperPointsToHeapPartition() {
		// TODO Auto-generated constructor stub
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
		return this.getBelongsTo() + "." + this.node.toString(); 
	}
	
	public String toSimpleString()
	{
		return this.node.toString(); 
	}


	public String getBelongsTo() {
		return belongsTo;
	}


	public void setBelongsTo(String belongsTo) {
		this.belongsTo = belongsTo;
	}


	@Override
	public int compareTo(PaperPointsToHeapPartition o) {
		return this.number.compareTo(o.getNumber());
	}

	@Override
	public void setNumber(int number) {
		this.number = number;
	}

}
