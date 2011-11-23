package ar.uba.dc.basic.polymorphism.problem;

public class IsccSyntax implements Syntax {

	public Expression parse(String string) {
		return new Expression();
	}

	public String toString(Expression expr) {
		return expr.toString();
	}

}
