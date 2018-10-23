package ar.uba.dc.annotations;

import java.awt.geom.Area;


//For annotations
import java.lang.annotation.Documented;

@Documented
@interface MemorySummary {
    String[] formalParameters();
    String PiecewiseQuasipolynomial(); //This should be my custom class
}

@MyAnnotation
public class AnnotationFailed {
	public static void main(String[] args)
	{
		Integer r = Integer.parseInt(args[0]);
		mainParameters(r);
	}
	
	public static void mainParameters(Integer r)
	{
		System.out.println("hola");
		DoSomethingMysterious(r);
	}

	// I don't really want to analyze this method so I use an annotation
	//I could annotate only the call to intersect(), that will be the following example (Annotation2)
	
	
	
	
	//esto esta mal porque justamente tengo el codigo. El MemorySummary deberia ir a un metodo LLAMADO desde aqui.
	@Deprecated
	@MemorySummary(
		formalParameters={"r"},
		PiecewiseQuasipolynomial="2*r^2: r <= 3, 3*r^4 r>3"
		)
	
	@MyAnnotation
	private static void DoSomethingMysterious(Integer r) {
		Area a = new Area();
		Area b = new Area();
		a.intersect(b); //I don't know how much this method consumes.
		
	}
}
