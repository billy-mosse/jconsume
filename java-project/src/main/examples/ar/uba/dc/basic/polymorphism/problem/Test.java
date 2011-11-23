package ar.uba.dc.basic.polymorphism.problem;


public class Test {

	/**
	 * Esta prueba demuestra que cuando tengo polimorfismo podemos tener problemas si
	 * el CallGraph no logra decidir cual es la implementacion correcta.
	 * 
	 * Esto se debe a que el escape usa como summary la union de todos los summaries de las posibles
	 * implementaciones mientras que el analisis de consumo se queda con alguno de los summaries.
	 * 
	 * Esta diferencia de criterios puede dar casos patologicos. Este es uno.
	 * 
	 * En este ejemplo el analisis devuelve que hay residual cuando no lo hay y eso es por considerar en el 
	 * escape como posibilidad a {@link OmegaSyntax}. 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Syntax syntax = getSyntax(1);

		Expression expr = syntax.parse(args[0]);
		
		expr.value = 3;
		System.out.println(syntax.toString(expr));
	}

	public static Syntax getSyntax(Integer type) {
		if (type == 1)
			return new IsccSyntax();
		else
			return new OmegaSyntax();
	}
}
