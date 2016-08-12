package ar.uba.dc.analysis.memory.code.impl.visitor;

import org.apache.commons.lang.StringUtils;

import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.StatementVisitor;

public class CalledImplementationVisitor implements StatementVisitor<String> {

	public static CalledImplementationVisitor INSTANCE = new CalledImplementationVisitor(); 
	
	@Override
	public String visit(CallStatement stmt) {
		return stmt.getCalledImplementation().getDeclaringClass().getName();
	}

	@Override
	public String visit(NewStatement stmt) {
		return StringUtils.EMPTY;
	}
}
