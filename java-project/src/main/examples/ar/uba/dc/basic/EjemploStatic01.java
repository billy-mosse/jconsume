package ar.uba.dc.basic;

@SuppressWarnings({"unused", "static-access"})
public class EjemploStatic01 {
	/**
	 * 
	 * @temporal 8
	 * @residual 24
	 */
	public void main(String[] args) {
		todosLosMetodos(); //maxCall = 5, residual = 4
		variosSets(); //maxCall = 5 (no cambia), residual = 4
		variosGetsSets(); //maxCall = 5, residual = 4 
		ningunSet(); //maxCall = 2, residual = 2
		variosNews(); //maxCall = 8, residual = 12
		
	}
	
	/**
	 * Metodo q muestra q se escapa del metodo solo los news que se colgo de lo estatico y no el resto 
	 * 
	 * @temporal 2
	 * @residual 2
	 */
	public void ningunSet() {
		StaticPoint pointA = new StaticPoint(1,2,3); //tempLocal = 1, tempCall = 1 (el z no estatico), residual = 2 (x, y statico con los new colgados)
		
	}
	
	/**
	 * Metodo q prueba todos los metodos y que todo siga funcionando segun lo espeerado
	 * 
	 * @temporal 5
	 * @residual 4
	 */
	public void todosLosMetodos() {
		Integer x, y, z, sumXY, sumAll, sumXY2, sumAll2;
		StaticPoint pointA = new StaticPoint(1,2,3); //tempLocal = 1, tempCall = 1, residual = 2
	
		x = pointA.GetX();  //maxCall=0, tempCall = 0
		y = pointA.GetY();  //maxCall=0, tempCall = 0
		z = pointA.GetZ();  //maxCall=0, tempCall = 0

		pointA.SetX(4);  //residual = 1
		pointA.SetY(5);  //residual = 1
		pointA.SetZ(6);  //tempCall = 1
		
		x = pointA.GetX();  //maxCall=0, tempCall = 0
		y = pointA.GetY();  //maxCall=0, tempCall = 0
		z = pointA.GetZ();  //maxCall=0, tempCall = 0
		
		sumXY = pointA.sumXY();  //maxCall=0, tempCall = 1
		sumAll = pointA.sumAll();  //maxCall=0, tempCall = 1
		
	}
	
	/**
	 *Metodo que muestra que los sets funcionan de la forma esperada, hacieendo q escapen mas cosas por los news colgando del static
	 * 
	 * @temporal 3
	 * @residual 4
	 */
	public void variosSets() {
		StaticPoint pointA = new StaticPoint(1,2,3); //tempLocal = 1, tempCall = 1, residual = 2
	
		pointA.SetX(4);  //residual = 1
		pointA.SetY(5);  //residual = 1
		pointA.SetZ(6);  //tempCall = 1
		
	}
	
	/**
	 * Metodo q muestra q cuando vuelvo a setear cosas, como son news tambien se escapan y lo q ya se seteo tambien escapa. Y los gets no cambian nada xq solo leen 
	 * 
	 * @temporal 3
	 * @residual 4
	 */
	public void variosGetsSets() {
		Integer x, y, z;
		StaticPoint pointA = new StaticPoint(1,2,3); //tempCall = 2, residual = 2
	
		x = pointA.GetX();  //maxCall=0, tempCall = 0
		y = pointA.GetY();  //maxCall=0, tempCall = 0
		z = pointA.GetZ();  //maxCall=0, tempCall = 0

		pointA.SetX(4);  //residual = 1
		pointA.SetY(5);  //residual = 1
		pointA.SetZ(6);  //tempCall = 1
		
		x = pointA.GetX();  //maxCall=0, tempCall = 0
		y = pointA.GetY();  //maxCall=0, tempCall = 0
		z = pointA.GetZ();  //maxCall=0, tempCall = 0
	}
	

	
	/**
	 * Metodo q muestra que aunque haya 2 puntos, el comportamiento es el esperado con los estaticos escapando
	 * 
	 * @temporal 8
	 * @residual 12
	 */
	public void variosNews() {
		StaticPoint pointA = new StaticPoint(1,2,3); //tempLocal = 1, tempCall = 1, residual = 2
		StaticPoint pointB = new StaticPoint(4,4,4); //tempLocal = 1, tempCall = 1, residual = 2
		
		pointA.SetX(4);  //residual = 1
		pointA.SetY(5);  //residual = 1
		pointA.SetZ(6);  //tempCall = 1
	
		pointB.SetX(7);  //residual = 1
		pointB.SetY(7);  //residual = 1
		pointB.SetZ(7);  //tempCall = 1
		
		pointA.SetX(8);  //residual = 1
		pointA.SetY(9);  //residual = 1
		pointA.SetZ(7);  //tempCall = 1
	
		pointB.SetX(5);  //residual = 1
		pointB.SetY(5);  //residual = 1
		pointB.SetZ(5);  //tempCall = 1
	
		
	}
}


/**
 * Clase Point de Integers con x e y estaticos, y z no estatico
 *
 */
@SuppressWarnings({"static-access"})
class StaticPoint {

	static Integer x, y;
	Integer z;
	
	/**
	 * Constructor, setea las componentes con un New Integer
	 * 
	 * @temporal: 0
	 * @residual: 3
	 */
	public StaticPoint(int paramX, int paramY, int paramZ) {
		this.x = new Integer(paramX);
		this.y = new Integer(paramY);
		this.z = new Integer(paramZ);
	}
	
	/**
	 * @temporal: 0
	 * @residual: 0
	 */
	public static Integer GetX() {
		return x;
	}
	
	/**
	 * @temporal: 0
	 * @residual: 0
	 */
	public static Integer GetY() {
		return y;
	}
	
	/**
	 * @temporal: 0
	 * @residual: 0
	 */
	public Integer GetZ() {
		return z;
	}
	
	/**
	 * Metodo que setea a X con un New Integer
	 * 
	 * @temporal: 0
	 * @residual: 1
	 */
	public static void SetX(int paramX) {
		x = new Integer(paramX);
	}
	
	/**
	 * Metodo que setea a Y con un New Integer
	 * 
	 * @temporal: 0
	 * @residual: 1
	 */
	public static void SetY(int paramY) {
		y = new Integer(paramY);
	}
	
	/**
	 * Metodo que setea a Z con un New Integer
	 * 
	 * @temporal: 0
	 * @residual: 1
	 */
	public void SetZ(int paramZ) {
		z = new Integer(paramZ);
	}
	
	/**
	 * Metodo que suma las componentes estaticas de la clase ,y muestra q est
	 * 
	 * @temporal: 2
	 * @residual: 0
	 */
	public static Integer sumXY() {
		return (x + y);
	}
	
	/**
	 * Metodo que suma las componentes y quiere mostrar que pasa cuando se accede a cosas estaticas y no estaticas
	 * 
	 * @temporal: 0
	 * @residual: 1
	 */
	public Integer sumAll() {
		return (x + y + z);
	}
	

}

