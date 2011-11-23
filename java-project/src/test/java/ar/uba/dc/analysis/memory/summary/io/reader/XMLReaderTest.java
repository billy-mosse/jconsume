package ar.uba.dc.analysis.memory.summary.io.reader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import soot.SootMethod;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.GlobalNode;
import ar.uba.dc.analysis.escape.graph.node.MethodNode;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;
import ar.uba.dc.analysis.escape.graph.node.StmtNode;
import ar.uba.dc.analysis.escape.graph.node.ThisNode;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpression;
import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedMemorySummary;
import ar.uba.dc.analysis.memory.impl.summary.PointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.PointsToHeapPartitionEdge;
import ar.uba.dc.analysis.memory.impl.summary.io.reader.XMLReader;
import ar.uba.dc.barvinok.BarvinokSyntax;
import ar.uba.dc.barvinok.syntax.IsccSyntax;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

public class XMLReaderTest {
	
	private XMLReader reader;
	private String basePath = "/memory/summary/"; 
	private BarvinokSyntax syntax = new IsccSyntax();
	
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
		EscapeBasedMemorySummary summary = reader.read(new FileReader(getXML("empty.xml")));
		
		assertThat(summary.getParameters().size(), is(equalTo(0)));
		assertThat(summary.getTarget().getDeclaringClass().getName(), is(equalTo("java.lang.Integer")));
		assertThat(summary.getTarget().getSubSignature(), is(equalTo("void <init>(int)")));
		assertThat(summary.getEscapeGlobalyPartitions().size(), is(equalTo(0)));
		assertThat(summary.getReturnedPartitions().size(), is(equalTo(0)));
		assertThat(summary.getAllPartitions().size(), is(equalTo(0)));
		assertThat(summary.getResidualPartitions().size(), is(equalTo(0)));
		assertThat(summary.getEdgesSources().size(), is(equalTo(0)));
		assertThat(toString(summary.getTemporal()), is(equalTo("{ 0 }")));
	}
	
	@Test
	public void thisNodeAndParamsWithSomeRelations() throws FileNotFoundException {		
		EscapeBasedMemorySummary summary = reader.read(new FileReader(getXML("thisAndParamNodeWithSomeRelations.xml")));
		
		assertThat(summary.getParameters().size(), is(equalTo(3)));
		assertThat(summary.getParameters(), hasItem("$t.size"));
		assertThat(summary.getParameters(), hasItem("otherParameter"));
		assertThat(summary.getParameters(), hasItem("n"));
				
		// Target
		assertThat(summary.getTarget().getDeclaringClass().getName(), is(equalTo("ar.uba.dc.simple.Tupla")));
		assertThat(summary.getTarget().getSubSignature(), is(equalTo("void <init>()")));
		
		PointsToHeapPartition hpThis = buildHP(ThisNode.node);
		PointsToHeapPartition hpParam0 = buildHP(new ParamNode(0));
		PointsToHeapPartition hpParam1 = buildHP(new ParamNode(1));
		PointsToHeapPartition hpGlobal = buildHP(GlobalNode.node);
		
		// Grafo
		assertThat(summary.getAllPartitions().size(), is(equalTo(4)));
		assertThat(summary.getAllPartitions(), hasItem(hpThis));
		assertThat(summary.getAllPartitions(), hasItem(hpParam0));
		assertThat(summary.getAllPartitions(), hasItem(hpParam1));
		assertThat(summary.getAllPartitions(), hasItem(hpGlobal));
		assertThat(summary.getEdgesSources().size(), is(equalTo(2)));
		assertThat(summary.getEdgesOutOf(hpThis).size(), is(equalTo(2)));
		assertThat(summary.getEdgesOutOf(hpThis), hasItem(buildEdge(hpThis, "next", hpThis, false)));
		assertThat(summary.getEdgesOutOf(hpThis), hasItem(buildEdge(hpThis, "array", hpParam0, true)));
		assertThat(summary.getEdgesOutOf(hpParam0).size(), is(equalTo(1)));
		assertThat(summary.getEdgesOutOf(hpParam0), hasItem(buildEdge(hpParam0, "z", hpThis, false)));
		assertThat(summary.getEdgesOutOf(hpParam1).size(), is(equalTo(0)));

		assertThat(summary.getReturnedPartitions().size(), is(equalTo(1)));
		assertThat(summary.getReturnedPartitions(), hasItem(hpParam0));
		
		assertThat(summary.getEscapeGlobalyPartitions().size(), is(equalTo(1)));
		assertThat(summary.getEscapeGlobalyPartitions(), hasItem(hpThis));
		
		// Consumo
		assertThat(toString(summary.getTemporal()), is(equalTo("{ [[$t.size] -> []] -> 2*$t.size^2 }")));
		assertThat(summary.getResidualPartitions().size(), is(equalTo(2)));
		assertThat(summary.getResidualPartitions(), hasItem((HeapPartition) hpThis));
		assertThat(summary.getResidualPartitions(), hasItem((HeapPartition) hpParam1));
		assertThat(toString(summary.getResidual(hpThis)), is(equalTo("{ [[n] -> []] -> n + 4 }")));
		assertThat(toString(summary.getResidual(hpParam1)), is(equalTo("{ 45 }")));		
	}
	
	@Test
	public void methodAndStatementNode() throws FileNotFoundException {
		EscapeBasedMemorySummary summary = reader.read(new FileReader(getXML("methodAndStatementNode.xml")));
		
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
		
		PointsToHeapPartition hpStmt1 = buildHP(node1);
		PointsToHeapPartition hpStmt2 = buildHP(node2);
		PointsToHeapPartition hpMethod = buildHP(methodNode);
		
		assertThat(summary.getParameters().size(), is(equalTo(2)));
		assertThat(summary.getParameters(), hasItem("$t.size"));
		assertThat(summary.getParameters(), hasItem("n"));
		
		assertThat(summary.getTarget().getDeclaringClass().getName(), is(equalTo("ar.uba.dc.simple.EjemploSimple01")));
		assertThat(summary.getTarget().getSubSignature(), is(equalTo("ar.uba.dc.simple.Tupla test()")));
		
		// Grafo
		assertThat(summary.getAllPartitions().size(), is(equalTo(3)));
		assertThat(summary.getAllPartitions(), hasItem(hpStmt1));
		assertThat(summary.getAllPartitions(), hasItem(hpStmt2));
		assertThat(summary.getAllPartitions(), hasItem(hpMethod));
		assertThat(summary.getEdgesSources().size(), is(equalTo(0)));

		assertThat(summary.getReturnedPartitions().size(), is(equalTo(2)));
		assertThat(summary.getReturnedPartitions(), hasItem(hpMethod));
		assertThat(summary.getReturnedPartitions(), hasItem(hpStmt1));
		
		assertThat(summary.getEscapeGlobalyPartitions().size(), is(equalTo(1)));
		assertThat(summary.getEscapeGlobalyPartitions(), hasItem(hpStmt2));
		
		// Mutated fields
		assertThat(toString(summary.getTemporal()), is(equalTo("{ [[$t.size,n] -> []] -> 2*$t.size^2 + 5 *n : n > 0; [[$t.size,n] -> []] -> 2*$t.size^2 : n <= 0 }")));
		assertThat(summary.getResidualPartitions().size(), is(equalTo(1)));
		assertThat(summary.getResidualPartitions(), hasItem((HeapPartition) hpStmt1));
		assertThat(toString(summary.getResidual(hpStmt1)), is(equalTo("{ [[n] -> []] -> n + 4 }")));
	}
	
	protected String getXML(String name) {
		return this.getClass().getResource(basePath + name).getFile();
	}
	
	protected PointsToHeapPartition buildHP(Node node) {
		return new PointsToHeapPartition(node, false);
	}
	
	protected String toString(ParametricExpression expr) {
		return syntax.toString(((BarvinokParametricExpression) expr).getExpression());
	}
	
	protected PointsToHeapPartitionEdge buildEdge(PointsToHeapPartition source, String field, PointsToHeapPartition target, boolean inside) {
		return new PointsToHeapPartitionEdge(source, field, target, inside);
	}
}
