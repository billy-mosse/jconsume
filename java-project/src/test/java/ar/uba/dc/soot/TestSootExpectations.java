package ar.uba.dc.soot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.List;

import org.junit.Test;

import soot.Body;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.Stmt;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.PseudoTopologicalOrderer;

public class TestSootExpectations {

	@Test
	public void nativeMethodsAreNative() {
		String[] methods = new String[] { 
			"java.lang.Thread:java.lang.Thread currentThread()",
			"java.lang.Double:long doubleToRawLongBits(double)",
			"java.lang.Double:double longBitsToDouble(long)",
			"java.lang.System:long currentTimeMillis()"
		};
		
		for (String info : methods) {
			String[] method = info.split(":");
			SootMethod m = SootUtils.getMethod(method[0], method[1]);
			assertThat(m.isPhantom(), is(false));
			assertThat(m.isAbstract(), is(false));
			assertThat(m.isNative(), is(true));
		}
	}

	/**
	 * Dentro del algoritmo usamos el statement de soot como el ID de los nodos. Es necesario verificar que efectivamente
	 * sólo el statement alcanza y no debemos agregar además el método al cual pertenece el statement.
	 * 
	 * Es por esto que verificamos que dos statements con el mismo toString son distintos si pertenecen a dos metodos distintos
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void statementsWithSameToStringOfDifferentMethodsAreNotEquals() {
		SootClass sootClass = SootUtils.getClass("ar.uba.dc.soot.TestSootExpectations");
		SootMethod testMethod = sootClass.getMethod("void test()");
		SootMethod test1Method = sootClass.getMethod("void test1()");

		
		Body body = testMethod.retrieveActiveBody();
		ExceptionalUnitGraph graph = new ExceptionalUnitGraph(body);
		List<Unit> unitsTest = (List<Unit>) new PseudoTopologicalOrderer().newList(graph, false);
		
		body = test1Method.retrieveActiveBody();
		graph = new ExceptionalUnitGraph(body);
		List<Unit> unitsTest1 = (List<Unit>) new PseudoTopologicalOrderer().newList(graph, false);
		
		for (Integer i = 0; i < unitsTest.size();  i++) {
			Stmt statementOfTest = (Stmt) unitsTest.get(i);
			Stmt statementOfTest1 = (Stmt) unitsTest1.get(i);
			assertThat(statementOfTest.toString(), is(equalTo(statementOfTest1.toString())));
			assertThat(statementOfTest, is(not(equalTo(statementOfTest1))));
		}	
	}
	
	public void test() {
		Integer a = 0;
		a++;
	}
	
	public void test1() {
		Integer a = 0;
		a++;
	}	
}
