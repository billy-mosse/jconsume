package ar.uba.dc.basic.polymorphism.problem;

public interface Syntax {

	Expression parse(String string);
	
	String toString(Expression expr);
}
