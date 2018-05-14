package ar.uba.dc.annotations;

public class AnnotationReflection1Test {
	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		
		String[] argsTest=new String[1];
		
		argsTest[0]="createOneObject";				
		AnnotationReflection1.main(argsTest);

		argsTest[0]="createTwoObjects";				
		AnnotationReflection1.main(argsTest);
	
	}
}
