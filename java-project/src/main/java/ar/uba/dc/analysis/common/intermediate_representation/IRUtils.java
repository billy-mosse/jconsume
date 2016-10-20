package ar.uba.dc.analysis.common.intermediate_representation;

import ar.uba.dc.analysis.common.Invocation;

public class IRUtils {


	public static String key(Invocation invocation, String name)
	{
		return invocation.getClass_called() + "." + name; 
	}
	
	
	public static String key(IntermediateRepresentationMethod ir_method)
	{
		return ir_method.getDeclaringClass() + "." + ir_method.getName(); 
	}
	
}
