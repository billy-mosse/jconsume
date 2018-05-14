package ar.uba.dc.annotations;

public class AnnotationReflection2Test {
	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		
		String[] argsTest=new String[1];
		
		argsTest[0]="createOneObject";				
		AnnotationReflection2.main(argsTest);

		argsTest[0]="createTwoObjects";				
		AnnotationReflection2.main(argsTest);
	
	}
}
