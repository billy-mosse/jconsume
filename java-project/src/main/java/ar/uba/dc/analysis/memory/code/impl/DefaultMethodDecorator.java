package ar.uba.dc.analysis.memory.code.impl;

import soot.SootMethod;
import soot.Unit;
import soot.jimple.AnyNewExpr;
import soot.jimple.AssignStmt;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.memory.code.MethodBody;
import ar.uba.dc.analysis.memory.code.MethodDecorator;

public class DefaultMethodDecorator implements MethodDecorator {

	public MethodBody decorate(SootMethod method) {
		MethodBody body = new MethodBody(method);
		
		// Ya no iteramos mas en orden del flujo sino que en orden de jimple
		//ExceptionalUnitGraph flowGraph = new ExceptionalUnitGraph(method.retrieveActiveBody());
		//List<Unit> topoOrder = new PseudoTopologicalOrderer<Unit>().newList(flowGraph, false);
		
		Long newCounter = 0L;
		Long callCounter = 0L;
		for (Unit u : method.retrieveActiveBody().getUnits()) {
			Stmt stmt = (Stmt) u;
			Boolean isCall = stmt.containsInvokeExpr();
			Boolean isNew = false;
			
			if (stmt instanceof AssignStmt) {
				AssignStmt assign = (AssignStmt) stmt;
				isNew = (assign.getRightOp() instanceof AnyNewExpr);
			}
			
			if (isCall) {
				body.addStatement(new DefaultCallStatement(method, stmt, callCounter));
				callCounter++;
			} else if (isNew) {
				body.addStatement(new DefaultNewStatement(method, stmt, newCounter));
				newCounter++;
			}
		}
		
		return body;
	}
}
