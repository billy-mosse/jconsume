package ar.uba.dc.basic.polymorphism.problem;

@SuppressWarnings("unused")
public class OmegaSyntax implements Syntax {

	private static Integer lastPrinted;
	
	public Expression parse(String string) {
		return new Expression();
	}

	public String toString(Expression expr) {
		lastPrinted = expr.value;
		
		return expr.value.toString();
	}

}
