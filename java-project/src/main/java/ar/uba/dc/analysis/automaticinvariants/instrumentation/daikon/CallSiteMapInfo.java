/*
 * Created on Nov 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;

import java.util.List;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CallSiteMapInfo;



/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CallSiteMapInfo {
	
	List args;
	List params;
	List paramsInit;
	String methodCallee;
	String classCallee;
	/**
	 * @param args
	 * @param params
	 * @param methodCallee
	 */
	public CallSiteMapInfo(List args, List params, List paramsInit, String methodCallee, String classCallee) {
		super();
		this.args = args;
		this.params = params;
		this.paramsInit= paramsInit;
		this.methodCallee = methodCallee;
		this.classCallee = classCallee;
	}
		

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String res="";
		res = args + ";" + methodCallee + ";" + params+";"+paramsInit+";"+classCallee;
		return res;

	}
	
	public static CallSiteMapInfo fromString(String line)
	{
		String[] tokens = line.split(";");		
		String arg = tokens[0];
		String method = tokens[1];
		String param = tokens[2];
		String paramInit = tokens[3];
		String classCallee = tokens[4];
		
		
		List args = parseList(arg);
		List params =parseList(param);
		List paramsInit =parseList(paramInit);
	
		
		return new CallSiteMapInfo(args,params,paramsInit,method,classCallee);
	}
	private static List parseList(String line)
	{
		Vector v = new Vector();
		line = line.substring(1,line.length()-1);
	
		String[] ls = line.split(",");
		for(int i=0;i<ls.length;i++)
		{
			v.add(ls[i].trim());
		}
		return v;
	}
	
	/**
	 * @return Returns the args.
	 */
	public List getArgs() {
		return args;
	}
	/**
	 * @return Returns the methodCallee.
	 */
	public String getMethodCallee() {
		return methodCallee;
	}
	/**
	 * @return Returns the params.
	 */
	public List getParams() {
		return params;
	}
	/**
	 * @return Returns the paramsInit.
	 */
	public List getParamsInit() {
		return paramsInit;
	}
	/**
	 * @return Returns the classCallee.
	 */
	public String getClassCallee() {
		return classCallee;
	}
}
