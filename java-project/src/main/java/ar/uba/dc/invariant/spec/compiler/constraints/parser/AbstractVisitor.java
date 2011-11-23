package ar.uba.dc.invariant.spec.compiler.constraints.parser;

import org.nfunk.jep.ASTConstant;
import org.nfunk.jep.ASTFunNode;
import org.nfunk.jep.ASTStart;
import org.nfunk.jep.ASTVarNode;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.ParserVisitor;
import org.nfunk.jep.SimpleNode;

public class AbstractVisitor implements ParserVisitor {

	public Object visit(SimpleNode arg0, Object arg1) throws ParseException {
		return arg0.childrenAccept(this, arg1);
	}

	public Object visit(ASTStart arg0, Object arg1) throws ParseException {
		return arg0.childrenAccept(this, arg1);
	}

	public Object visit(ASTFunNode arg0, Object arg1) throws ParseException {
		return arg0.childrenAccept(this, arg1);
	}

	public Object visit(ASTVarNode arg0, Object arg1) throws ParseException {
		return arg0.childrenAccept(this, arg1);
	}

	public Object visit(ASTConstant arg0, Object arg1) throws ParseException {
		return arg0.childrenAccept(this, arg1);
	}

}
