package ar.uba.dc.basic.polymorphism.expectation;

/**
 * Este ejemplo intenta probar una sola cosa: 
 * Cunado hay polimorfismo, el algoritmo busca el peor residual entre las implementaciones.
 * Esto quiere decir que si 2 particiones B_1 y B_1' van a parar al caller en A y en otra particion B_2 y B_2' van a parar a A,
 * entonces el algoritmo asigna a A el consumo A + max(B_1 + B_1', B_2 + B_2').
 * 
 * El ejemplo esta pensado para ser analizado usando sensitividad 1 (k = 1)
 * 
 * @author testis
 */
public class BasicTest {

	/**
	 * @residual 0
	 * @temporal 7
	 */
	public static void main(String[] args) {
		BasicTest t = getInstance(false);
		t.test();
	}
	
	/**
	 * @residual 2
	 * @temporal 0
	 */
	public static BasicTest getInstance(boolean b) {
		return b ? new BasicTest() : new BasicExtension();
	}
	
	/**
	 * Este metodo debe dar 4. Aca es donde se juntan todas las particiones representadas por los news hechos 
	 * en todas las implementaciones. En este metodo es donde probamos q efectivamente el algoritmo sume en c/particion.
	 * El ejemplo esta pensando para correrse con k=1 porque usamos este metodo para "igualar contextos" y asi "agrupar particiones". 
	 * 
	 * @residual 5 (los 2 arrays de c/implementacion son distintos, cuentan 2 de c/array y 3 del maximo new hecho)
	 * @temporal 0
	 */
	public Integer[] test() {
		return buildArray();
	}
	
	/**
	 * @residual 3
	 * @temporal 0
	 */
	public Integer[] buildArray() {
		return new Integer[] { newInteger(), newInteger() };
	}
	
	/**
	 * @residual 1
	 * @temporal 0
	 */
	public Integer newInteger() {
		return new Integer(1);
	}
	
}

class BasicExtension extends BasicTest {

	/**
	 * @residual 4
	 * @temporal 0
	 */
	public Integer[] buildArray() {
		return new Integer[] { newInteger(), newInteger(), newInteger() };
	}
	
}
