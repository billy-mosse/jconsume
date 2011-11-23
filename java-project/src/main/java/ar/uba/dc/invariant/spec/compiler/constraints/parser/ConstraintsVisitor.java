package ar.uba.dc.invariant.spec.compiler.constraints.parser;

import org.nfunk.jep.ASTFunNode;
import org.nfunk.jep.ASTVarNode;
import org.nfunk.jep.ParseException;

import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsInfo;


public class ConstraintsVisitor extends AbstractVisitor {

	private InvariantReferenceVisitor visitor;
	private String invariantReferenceFunction;
	
	public ConstraintsVisitor(String invariantReferenceFunction) {
		this.invariantReferenceFunction = invariantReferenceFunction;
		this.visitor = new InvariantReferenceVisitor();
	}

	@Override
	public Object visit(ASTFunNode arg0, Object arg1) throws ParseException {
		if (arg0.getName().equals(invariantReferenceFunction)) {
			return arg0.childrenAccept(visitor, arg1);
		} else {
			return super.visit(arg0, arg1);
		}
	}

	@Override
	public Object visit(ASTVarNode arg0, Object arg1) throws ParseException {
		ConstraintsInfo info = (ConstraintsInfo) arg1;
		info.addVariable(arg0.getName());
		return super.visit(arg0, arg1);
	}
}
