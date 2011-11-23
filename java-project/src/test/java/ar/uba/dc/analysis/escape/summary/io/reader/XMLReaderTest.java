package ar.uba.dc.analysis.escape.summary.io.reader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import soot.Local;
import soot.SootMethod;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.GlobalNode;
import ar.uba.dc.analysis.escape.graph.node.MethodNode;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;
import ar.uba.dc.analysis.escape.graph.node.StmtNode;
import ar.uba.dc.analysis.escape.graph.node.ThisNode;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

public class XMLReaderTest {

	private XMLReader reader;
	private String basePath = "/escape/summary/"; 
	
	@BeforeClass
	public static void setUpClass() {
		Context ctx = ContextFactory.getContext("test.properties");
		SootUtils.setClasspath(ctx);
	}
	
	@Before
	public void setUp() {
		reader = new XMLReader();
	}
	
	@Test
	public void emptySummary() throws FileNotFoundException {
		EscapeSummary summary = reader.read(new FileReader(getXML("empty.xml")));
		
		assertThat(summary.getTarget().getDeclaringClass().getName(), is(equalTo("java.lang.Integer")));
		assertThat(summary.getTarget().getSubSignature(), is(equalTo("void <init>(int)")));
		assertThat(summary.getEscapeGlobaly().size(), is(equalTo(0)));
		assertThat(summary.getReturnedNodes().size(), is(equalTo(0)));
		assertThat(summary.getLocals().size(), is(equalTo(0)));
		assertThat(summary.getNodes().size(), is(equalTo(0)));
		assertThat(summary.getParameterNodes().size(), is(equalTo(0)));
		assertThat(summary.getEdgesSources().size(), is(equalTo(0)));
	}
	
	
	@Test
	public void thisNodeAndParamsWithSomeRelations() throws FileNotFoundException {		
		EscapeSummary summary = reader.read(new FileReader(getXML("thisAndParamNodeWithSomeRelations.xml")));
		
		// Target
		assertThat(summary.getTarget().getDeclaringClass().getName(), is(equalTo("ar.uba.dc.simple.Tupla")));
		assertThat(summary.getTarget().getSubSignature(), is(equalTo("void <init>()")));
		
		// Grafo
		assertThat(summary.getNodes().size(), is(equalTo(4)));
		assertThat(summary.getNodes(), hasItem((Node) ThisNode.node));
		assertThat(summary.getNodes(), hasItem((Node) new ParamNode(0)));
		assertThat(summary.getNodes(), hasItem((Node) new ParamNode(1)));
		assertThat(summary.getNodes(), hasItem((Node) GlobalNode.node));
		assertThat(summary.getEdgesSources().size(), is(equalTo(2)));
		assertThat(summary.getEdgesOutOf(ThisNode.node).size(), is(equalTo(2)));
		assertThat(summary.getEdgesOutOf(ThisNode.node), hasItem(new Edge(ThisNode.node, "next", ThisNode.node, false)));
		assertThat(summary.getEdgesOutOf(ThisNode.node), hasItem(new Edge(ThisNode.node, "array", new ParamNode(0), true)));
		assertThat(summary.getEdgesOutOf(new ParamNode(0)).size(), is(equalTo(1)));
		assertThat(summary.getEdgesOutOf(new ParamNode(0)), hasItem(new Edge(new ParamNode(0), "z", ThisNode.node, false)));
		assertThat(summary.getEdgesOutOf(new ParamNode(1)).size(), is(equalTo(0)));

		// PTG
		assertThat(summary.getParameterNodes().size(), is(equalTo(3)));
		assertThat(summary.getParameterNodes(), hasItem((Node) ThisNode.node));
		assertThat(summary.getParameterNodes(), hasItem((Node) new ParamNode(0)));
		assertThat(summary.getParameterNodes(), hasItem((Node) new ParamNode(1)));
		
		assertThat(summary.getLocals().size(), is(equalTo(2)));
		Iterator<Local> itLocal = summary.getLocals().iterator();
		Local l = itLocal.next();
		assertThat(l.getName(), is(equalTo("$r0")));
		assertThat(summary.nodesPointedBy(l).size(), is(equalTo(1)));
		assertThat(summary.nodesPointedBy(l), hasItem((Node) ThisNode.node));
		
		l = itLocal.next();
		assertThat(l.getName(), is(equalTo("this")));
		assertThat(summary.nodesPointedBy(l).size(), is(equalTo(1)));
		assertThat(summary.nodesPointedBy(l), hasItem((Node) ThisNode.node));
		
		assertThat(summary.getReturnedNodes().size(), is(equalTo(1)));
		assertThat(summary.getReturnedNodes(), hasItem((Node) new ParamNode(0)));
		
		assertThat(summary.getEscapeGlobaly().size(), is(equalTo(1)));
		assertThat(summary.getEscapeGlobaly(), hasItem((Node) ThisNode.node));
		
		// Mutated fields
		assertThat(summary.getMutatedNodes().size(), is(equalTo(2)));
		assertThat(summary.getMutatedNodes(), hasItem((Node) new ParamNode(0)));
		assertThat(summary.getMutatedFieldsOf(new ParamNode(0)).size(), is(equalTo(3)));
		assertThat(summary.getMutatedFieldsOf(new ParamNode(0)), hasItem("arrays"));
		assertThat(summary.getMutatedFieldsOf(new ParamNode(0)), hasItem("size"));
		assertThat(summary.getMutatedFieldsOf(new ParamNode(0)), hasItem("something"));
		
		assertThat(summary.getMutatedNodes(), hasItem((Node) new ParamNode(1)));
		assertThat(summary.getMutatedFieldsOf(new ParamNode(1)).size(), is(equalTo(2)));
		assertThat(summary.getMutatedFieldsOf(new ParamNode(1)), hasItem("other"));
		assertThat(summary.getMutatedFieldsOf(new ParamNode(1)), hasItem("size"));
	}
	
	@Test
	public void methodAndStatementNode() throws FileNotFoundException {
		EscapeSummary summary = reader.read(new FileReader(getXML("methodAndStatementNode.xml")));
		
		SootMethod methodNuevaTupla = SootUtils.getMethod("ar.uba.dc.simple.EjemploSimple01", "ar.uba.dc.simple.Tupla nuevaTupla()");
		SootMethod methodTest = SootUtils.getMethod("ar.uba.dc.simple.EjemploSimple01", "ar.uba.dc.simple.Tupla test()");
		
		StatementId stmtId1 = new StatementId(methodNuevaTupla, SootUtils.findStatementInMethod(methodNuevaTupla, "$r0 = new ar.uba.dc.simple.Tupla", 58, 0));
		StatementId ctx1 = new StatementId(methodTest, SootUtils.findStatementInMethod(methodTest, "$r0 = staticinvoke <ar.uba.dc.simple.EjemploSimple01: ar.uba.dc.simple.Tupla nuevaTupla()>()", 42, 0));
		
		StatementId stmtId2 = new StatementId(methodNuevaTupla, SootUtils.findStatementInMethod(methodNuevaTupla, "$r0 = new ar.uba.dc.simple.Tupla", 58, 0));
		StatementId ctx2 = new StatementId(methodTest, SootUtils.findStatementInMethod(methodTest, "staticinvoke <ar.uba.dc.simple.EjemploSimple01: void cargarTupla(ar.uba.dc.simple.Tupla)>($r0)", 43, 5));
		
		StmtNode node1 = new StmtNode(stmtId1, true, CircularStack.INFINITE);
		node1.changeContext(ctx1);
		
		StmtNode node2 = new StmtNode(stmtId2, false, 2);
		node2.changeContext(ctx2);
		node2.changeContext(ctx1);
		
		MethodNode methodNode = new MethodNode(methodNuevaTupla, 2);
		methodNode.changeContext(ctx2);
		methodNode.changeContext(ctx1);
		
		assertThat(summary.getTarget().getDeclaringClass().getName(), is(equalTo("ar.uba.dc.simple.EjemploSimple01")));
		assertThat(summary.getTarget().getSubSignature(), is(equalTo("ar.uba.dc.simple.Tupla test()")));
		
		// Grafo
		assertThat(summary.getNodes().size(), is(equalTo(3)));
		assertThat(summary.getNodes(), hasItem((Node) node1));
		assertThat(summary.getNodes(), hasItem((Node) node2));
		assertThat(summary.getNodes(), hasItem((Node) methodNode));
		assertThat(summary.getEdgesSources().size(), is(equalTo(0)));

		// PTG
		assertThat(summary.getParameterNodes().size(), is(equalTo(0)));
		
		assertThat(summary.getLocals().size(), is(equalTo(1)));
		Iterator<Local> itLocal = summary.getLocals().iterator();
		Local l = itLocal.next();
		assertThat(l.getName(), is(equalTo("$r0")));
		assertThat(summary.nodesPointedBy(l).size(), is(equalTo(1)));
		assertThat(summary.nodesPointedBy(l), hasItem((Node) node1));
		
		assertThat(summary.getReturnedNodes().size(), is(equalTo(1)));
		assertThat(summary.getReturnedNodes(), hasItem((Node) node2));
		
		assertThat(summary.getEscapeGlobaly().size(), is(equalTo(1)));
		assertThat(summary.getEscapeGlobaly(), hasItem((Node) node1));
		
		// Mutated fields
		assertThat(summary.getMutatedNodes().size(), is(equalTo(0)));
	}
	
	protected String getXML(String name) {
		return this.getClass().getResource(basePath + name).getFile();
	}
}
