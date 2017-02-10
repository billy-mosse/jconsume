package ar.uba.dc.analysis.escape.graph.node;

import java.util.Iterator;

import org.apache.commons.lang.NotImplementedException;

import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;
import soot.SootMethod;

public class PaperNodeUtils {
	
	public static PaperNode createPaperNodeFromNormalNode(Node origNode, CircularStack<String> context, String belongsTo)
	{
		if(origNode.getClass() == StmtNode.class)
		{
			StmtNode sNode = (StmtNode) origNode;
			return new PaperStmtNode(sNode, context);
		}
		else if(origNode.getClass() == ParamNode.class)
		{
			return new PaperParamNode(origNode);
		}
		else if(origNode.getClass() == MethodNode.class)
		{
			MethodNode mNode = (MethodNode) origNode;
			return new PaperMethodNode(mNode, context, belongsTo);
		}
		else if(origNode.getClass() == ThisNode.class)
		{
			//ThisNode mNode = (ThisNode) node;
			return new PaperThisNode();
		}
		else if(origNode.getClass() == ContainerNode.class)
		{
			ContainerNode cNode = (ContainerNode) origNode;
			return new PaperContainerNode(cNode, context, belongsTo);
		}
		else if(origNode.getClass() == GlobalNode.class)
		{
			GlobalNode gNode = (GlobalNode) origNode;
			return new PaperGlobalNode();
		}
		else
		{
			throw new NotImplementedException(origNode.getClass());
		}
	}
	
	
	public static CircularStack<String> getIrContext(Node n)
	{
		CircularStack<StatementId> context = n.getContext();
		CircularStack<String> ir_context = new CircularStack<String>();			
		
		if(context != null)
		{
			Iterator<StatementId>ctxtIterator = context.iterator();
			while(ctxtIterator.hasNext())
			{
				StatementId id = ctxtIterator.next();
				
				//SootMethod idMethod = id.getId().getInvokeExpr().getMethod();
				//String methodFullName = idMethod.getDeclaringClass() + "." + idMethod.getName();
				ir_context.push(id.toString());
			}
		}
		return ir_context;
	}

}
