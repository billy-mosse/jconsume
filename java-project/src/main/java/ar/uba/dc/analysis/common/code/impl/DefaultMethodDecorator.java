package ar.uba.dc.analysis.common.code.impl;

import soot.SootMethod;
import soot.Unit;
import soot.jimple.AnyNewExpr;
import soot.jimple.AssignStmt;
import soot.jimple.Stmt;
import soot.tagkit.Tag;
import soot.jimple.AbstractStmtSwitch;

import java.util.List;

import ar.uba.dc.analysis.common.code.BasicMethodBody;
import ar.uba.dc.analysis.common.code.MethodBody;
import ar.uba.dc.analysis.common.code.MethodDecorator;





public class DefaultMethodDecorator implements MethodDecorator {

	public MethodBody decorate(SootMethod method)
	{
		return decorate(method, false);
	}
	
	public MethodBody decorate_indexing_news_and_calls_together(SootMethod method) {
		return decorate(method, true);
	}

	public MethodBody decorate(SootMethod method, boolean indexing_news_and_calls_together) {
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
				
				if(indexing_news_and_calls_together) newCounter++;
				
			} else if (isNew) {
				body.addStatement(new DefaultNewStatement(method, stmt, newCounter));
				newCounter++;
				
				if(indexing_news_and_calls_together) callCounter++;
			}
		}
		
		return body;
	}
	
	
	public BasicMethodBody decorate_for_IR(SootMethod method) {
		BasicMethodBody body = new BasicMethodBody(method);
		
		// Ya no iteramos mas en orden del flujo sino que en orden de jimple
		//ExceptionalUnitGraph flowGraph = new ExceptionalUnitGraph(method.retrieveActiveBody());
		//List<Unit> topoOrder = new PseudoTopologicalOrderer<Unit>().newList(flowGraph, false);
		
		Long newCounter = 0L;
		Long callCounter = 0L;
		for (Unit u : method.retrieveActiveBody().getUnits()) {
			Stmt stmt = (Stmt) u;
			Boolean isCall = stmt.containsInvokeExpr();
			Boolean isNew = false;
			
			

			//int s = stmt.getJavaSourceStartLineNumber();
			//List<Tag> tagList = stmt.getTags();			
			
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
