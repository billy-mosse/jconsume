package ar.uba.dc.invariant.spec.compiler.constraints.parser;

import org.nfunk.jep.ASTConstant;
import org.nfunk.jep.ASTFunNode;
import org.nfunk.jep.ASTStart;
import org.nfunk.jep.ASTVarNode;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.SimpleNode;

import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsInfo;

public class InvariantReferenceVisitor extends AbstractVisitor {

	@Override
	public Object visit(ASTConstant arg0, Object arg1) throws ParseException {
		ConstraintsInfo info = (ConstraintsInfo) arg1;
		info.addReference(arg0.getValue().toString());
		return null;
	}

	@Override
	public Object visit(ASTFunNode arg0, Object arg1) throws ParseException {
		throw new UnsupportedOperationException("Si estamos viendo este error es porque hubo un problema con el parsero de los invariantes");
	}

	@Override
	public Object visit(ASTStart arg0, Object arg1) throws ParseException {
		throw new UnsupportedOperationException("Si estamos viendo este error es porque hubo un problema con el parsero de los invariantes");
	}

	@Override
	public Object visit(ASTVarNode arg0, Object arg1) throws ParseException {
		ConstraintsInfo info = (ConstraintsInfo) arg1;
		info.addReference(arg0.getName());
		return null;
	}

	@Override
	public Object visit(SimpleNode arg0, Object arg1) throws ParseException {
		throw new UnsupportedOperationException("Si estamos viendo este error es porque hubo un problema con el parsero de los invariantes");
	}
}
