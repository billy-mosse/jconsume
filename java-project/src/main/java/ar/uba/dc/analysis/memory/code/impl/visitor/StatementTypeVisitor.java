package ar.uba.dc.analysis.memory.code.impl.visitor;

import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.StatementVisitor;
import ar.uba.dc.invariant.spec.compiler.compilation.InvariantId.Type;

public class StatementTypeVisitor implements StatementVisitor<Type> {

	public static StatementTypeVisitor INSTANCE = new StatementTypeVisitor(); 
	
	@Override
	public Type visit(CallStatement stmt) {
		return Type.CALL;
	}

	@Override
	public Type visit(NewStatement stmt) {
		return Type.NEW;
	}

}
