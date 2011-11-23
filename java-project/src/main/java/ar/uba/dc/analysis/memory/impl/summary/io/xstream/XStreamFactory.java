package ar.uba.dc.analysis.memory.impl.summary.io.xstream;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import soot.SootMethod;

import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.node.ContainerNode;
import ar.uba.dc.analysis.escape.graph.node.GlobalNode;
import ar.uba.dc.analysis.escape.graph.node.MethodNode;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;
import ar.uba.dc.analysis.escape.graph.node.StmtNode;
import ar.uba.dc.analysis.escape.graph.node.ThisNode;
import ar.uba.dc.analysis.escape.summary.io.xstream.ContainerNodeConverter;
import ar.uba.dc.analysis.escape.summary.io.xstream.EdgeConverter;
import ar.uba.dc.analysis.escape.summary.io.xstream.GlobalNodeConverter;
import ar.uba.dc.analysis.escape.summary.io.xstream.MethodNodeConverter;
import ar.uba.dc.analysis.escape.summary.io.xstream.ParamNodeConverter;
import ar.uba.dc.analysis.escape.summary.io.xstream.StatementNodeConverter;
import ar.uba.dc.analysis.escape.summary.io.xstream.ThisNodeConverter;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpressionFactory;
import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedMemorySummary;
import ar.uba.dc.barvinok.syntax.IsccSyntax;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.soot.xstream.MethodConverter;
import ar.uba.dc.soot.xstream.StatementIdConverter;

import com.thoughtworks.xstream.XStream;

public class XStreamFactory {

	public static XStream getXStream() {
		XStream xstream = new XStream();
		
		xstream.registerConverter(new EscapeBasedMemorySummaryConverter(xstream.getMapper(), new IsccSyntax(), new BarvinokParametricExpressionFactory()));
		xstream.registerConverter(new ParamNodeConverter(xstream.getMapper()));
		xstream.registerConverter(new ThisNodeConverter(xstream.getMapper()));
		xstream.registerConverter(new GlobalNodeConverter(xstream.getMapper()));
		xstream.registerConverter(new EdgeConverter(xstream.getMapper()));
		xstream.registerConverter(new StatementNodeConverter(xstream.getMapper()));
		xstream.registerConverter(new ContainerNodeConverter(xstream.getMapper()));
		xstream.registerConverter(new MethodNodeConverter(xstream.getMapper()));
		xstream.registerConverter(new MethodConverter("class", "signature"));
		xstream.registerConverter(new StatementIdConverter("method", "value", "line-number", "bytecode-offset"));

		xstream.alias("summary", EscapeBasedMemorySummary.class);
		xstream.alias("this", ThisNode.class);
		xstream.alias("param", ParamNode.class);
		xstream.alias("global", GlobalNode.class);
		xstream.alias("edge", Edge.class);
		xstream.alias("statement", StmtNode.class);
		xstream.alias("method", MethodNode.class);
		xstream.alias("container", ContainerNode.class);
		xstream.alias("statement-id", StatementId.class);
		xstream.alias("method-id", SootMethod.class);
		xstream.alias("call", StatementId.class);
		xstream.alias("parameters", TreeSet.class);
		xstream.alias("belongsTo", SootMethod.class);
		
		xstream.aliasField("index", ParamNode.class, "id");
		
		xstream.addDefaultImplementation(HashSet.class, Set.class);
		
		xstream.setMode(XStream.ID_REFERENCES);
		
		return xstream;
	}
	
}
