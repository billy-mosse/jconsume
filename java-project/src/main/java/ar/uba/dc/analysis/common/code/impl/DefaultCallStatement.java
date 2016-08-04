package ar.uba.dc.analysis.common.code.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.StatementVisitor;
import ar.uba.dc.analysis.common.intermediate_representation.DefaultIntermediateRepresentationParameter;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameterWithType;
import ar.uba.dc.analysis.escape.IntermediateLanguageRepresentationBuilder;
import ar.uba.dc.soot.SootUtils;
import soot.Local;
import soot.SootMethod;
import soot.jimple.Stmt;

public class DefaultCallStatement extends AbstractStatement implements CallStatement {

	
	private static Log log = LogFactory.getLog(DefaultCallStatement.class);

	
	private SootMethod calledMethod;
	private SootMethod calledImplementation;
	
	public DefaultCallStatement(SootMethod belongsTo, Stmt statement, Long counter) {
		this(belongsTo, statement, counter, statement.getInvokeExpr().getMethod(), statement.getInvokeExpr().getMethod());
	}
	
	public DefaultCallStatement(SootMethod belongsTo, Stmt statement, Long counter, SootMethod calledMethod, SootMethod calledImplementation) {
		super(belongsTo, statement, counter);
		this.calledMethod = calledMethod;
		this.calledImplementation = calledImplementation;
	}

	public SootMethod getCalledMethod() {
		return calledMethod;
	}

	@Override
	public SootMethod getCalledImplementation() {
		return calledImplementation;
	}

	@Override
	public <T> T apply(StatementVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public CallStatement virtualInvoke(SootMethod callee) {
		return new DefaultCallStatement(belongsTo, statement, counter, calledMethod, callee);
	}

	@Override
	public String getIntermediateRepresentationName() {
		return this.getCalledImplementation().getName();
	}

	@Override
	public Set<DefaultIntermediateRepresentationParameter> getIntermediateRepresentationParameters() {
		SootMethod m = this.getCalledImplementation();
		
		//log.debug(m.getActiveBody().toString());
		
		Set<DefaultIntermediateRepresentationParameter> parameterList = new LinkedHashSet<DefaultIntermediateRepresentationParameter>();
		Set<IntermediateRepresentationParameter> s = SootUtils.getParameters(m, DefaultIntermediateRepresentationParameter.class);
		for(IntermediateRepresentationParameter p : s)
		{
			parameterList.add((DefaultIntermediateRepresentationParameter)p);
		}
		
		return parameterList;
	}
	
}
