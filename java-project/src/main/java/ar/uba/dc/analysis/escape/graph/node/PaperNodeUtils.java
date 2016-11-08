package ar.uba.dc.analysis.escape.graph.node;

import org.apache.commons.lang.NotImplementedException;

import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.util.collections.CircularStack;

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
		else
		{
			throw new NotImplementedException();
		}
	}

}
