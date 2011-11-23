package ar.uba.dc.analysis.escape.summary.io.xstream;


import java.util.HashSet;
import java.util.Set;

import soot.Local;
import soot.SootMethod;
import soot.Type;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.PointsToGraph;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.ContainerNode;
import ar.uba.dc.analysis.escape.graph.node.GlobalNode;
import ar.uba.dc.analysis.escape.graph.node.MethodNode;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;
import ar.uba.dc.analysis.escape.graph.node.StmtNode;
import ar.uba.dc.analysis.escape.graph.node.ThisNode;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.soot.xstream.MethodConverter;
import ar.uba.dc.soot.xstream.StatementIdConverter;
import ar.uba.dc.soot.xstream.TypeConverter;

import com.thoughtworks.xstream.XStream;

public class XStreamFactory {

	public static XStream getXStream() {
		return getXStream(new StatementIdConverter("method", "value", "line-number", "bytecode-offset"));
	}
	
	public static XStream getXStream(StatementIdConverter statementConverter) {
		XStream xstream = new XStream();
		
		xstream.registerConverter(new EscapeSummaryConverter(xstream.getMapper()));
		xstream.registerConverter(new ParamNodeConverter(xstream.getMapper()));
		xstream.registerConverter(new PointsToGraphConverter(xstream.getMapper()));
		xstream.registerConverter(new ThisNodeConverter(xstream.getMapper()));
		xstream.registerConverter(new GlobalNodeConverter(xstream.getMapper()));
		xstream.registerConverter(new StatementNodeConverter(xstream.getMapper()));
		xstream.registerConverter(new MethodNodeConverter(xstream.getMapper()));
		xstream.registerConverter(new ContainerNodeConverter(xstream.getMapper()));
		xstream.registerConverter(new EdgeConverter(xstream.getMapper()));
		xstream.registerConverter(new TypeConverter());
		xstream.registerConverter(new MethodConverter("class", "signature"));
		xstream.registerConverter(statementConverter);
		
		xstream.alias("summary", EscapeSummary.class);
		xstream.alias("points-to-graph", PointsToGraph.class);
		xstream.alias("this", ThisNode.class);
		xstream.alias("param", ParamNode.class);
		xstream.alias("global", GlobalNode.class);
		xstream.alias("statement", StmtNode.class);
		xstream.alias("method", MethodNode.class);
		xstream.alias("container", ContainerNode.class);
		xstream.alias("edge", Edge.class);
		xstream.alias("source", Node.class);
		xstream.alias("target", Node.class);
		xstream.alias("variable", Local.class);
		xstream.alias("points-to", Set.class);
		xstream.alias("returned", Set.class);
		xstream.alias("escape-globaly", Set.class);
		xstream.alias("fields", Set.class);
		xstream.alias("field", String.class);
		xstream.alias("statement-id", StatementId.class);
		xstream.alias("method-id", SootMethod.class);
		xstream.alias("call", StatementId.class);
		xstream.alias("type", Type.class);
		xstream.alias("belongsTo", SootMethod.class);
		
		xstream.aliasField("index", ParamNode.class, "id");

		xstream.addDefaultImplementation(HashSet.class, Set.class);
		
		xstream.setMode(XStream.ID_REFERENCES);
		
		return xstream;
	}
	
}
