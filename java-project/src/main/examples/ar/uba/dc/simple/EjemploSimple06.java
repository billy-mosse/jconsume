package ar.uba.dc.simple;

public class EjemploSimple06 {

	public static void main(String[] args) {
		Integer a = m3();
	}
	
	public static Integer m3()
	{
		Integer i = new Integer(5);
		Integer j = new Integer(2);
		Integer k = m1(i);
		return m2(j);
	}
	
	public static Integer m1(Integer i)
	{
		return m2(i);
	}

	public static Integer m2(Integer i)
	{
		return new Integer(42);
	}
}
