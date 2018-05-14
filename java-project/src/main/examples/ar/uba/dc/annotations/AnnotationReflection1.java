package ar.uba.dc.annotations;

import java.lang.reflect.*;

public class AnnotationReflection1 {

	public static void main(String[] args) {
		
		assert(args.length == 1);
		
		String methodName = args[0];
		
		Class objectCreator = ObjectCreator.class;
		
		Class[] classes = new Class[0];
		
		try {
			
			//pongo el consumo en unanalizable methods
			Method m = objectCreator.getMethod(methodName, classes);
			
			
			Object[] params = new Object[0];
			try {
				
				//ANOTO el consumo aparte
				m.invoke(objectCreator, params);
				
				
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
