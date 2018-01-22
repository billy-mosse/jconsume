/*
 * Created on Nov 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CreationSiteMapInfo;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListDIParametersNoRep;

public class CreationSiteMapInfo implements Comparable {
//	// Esto es horrible pero es para agregar offsets cuando no habia
//	private static String prevMethod = "";
//	private static int newsCounter = 0;
//	private static int callsCounter = 0;
//	
	String insSite;
	List vars;
	String method;
	String type;
	String creationSiteType;
	List csArrayParams;
	int order;
	List vivas;
	Set inductivesFake;
	String methodCaller;
	//ListDIParametersNoRep objectVars;

	/**
	 * @param vars
	 * @param method
	 */
	public CreationSiteMapInfo(String insSite, int order, List vars, String method, String type, String csType, List csArrayP) {
		super();
		this.insSite = insSite;
		this.vars = vars;
		this.method = method;
		this.type = type;
		this.creationSiteType=csType;
		this.csArrayParams = csArrayP;
		this.order = order;
		
		
	}
	
	public CreationSiteMapInfo(String insSite, int order, List vars, String method, String type, String csType, List csArrayP, List vivas) {
		super();
		this.insSite = insSite;
		this.vars = vars;
		this.method = method;
		this.type = type;
		this.creationSiteType=csType;
		this.csArrayParams = csArrayP;
		this.order = order;
		this.vivas = vivas;
		
	}
	
	public CreationSiteMapInfo(String insSite, int order, List vars, String method, String type, String csType, List csArrayP, List vivas, Set inductivesFake, String methodName/*, ListDIParametersNoRep objectVars*/) {
		super();
		this.insSite = insSite;
		this.vars = vars;
		this.method = method;
		this.type = type;
		this.creationSiteType=csType;
		this.csArrayParams = csArrayP;
		this.order = order;
		this.vivas = vivas;
		this.inductivesFake = inductivesFake;
		
		this.methodCaller = methodName;
		//this.objectVars = objectVars;
	}
	

	/*public ListDIParametersNoRep getObjectVars() {
		return objectVars;
	}*/

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String res="";
		res = order + ";" + vars.toString()+";"+method+";"+type+";"+creationSiteType+";"+csArrayParams;
		return res;
	}
	public static CreationSiteMapInfo fromString(String line)
	{
		String insSite=line.substring(0,line.indexOf("="));
		String res = line.substring(line.indexOf("=")+1);
		
		String[] tokens = res.split(";");
		int tok = 0;
		int order = -1;
		
		if(tokens.length==6) {
			String sOrder = tokens[tok++];
			order = Integer.parseInt(sOrder);
		}
		
		String sVars = tokens[tok++];
		String method = tokens[tok++];
		String type = tokens[tok++];
		String csType = tokens[tok++];
		String sArrayParams = tokens[tok++];
		// Agregamos artificialmente los offsets
//		if(tokens.length!=6) {
//			if(!prevMethod.equals(method)) {
//				newsCounter = 0;
//				callsCounter = 0;
//				prevMethod = method;
//			}
//			if(type.equals("CC")) {
//				order = callsCounter;
//				callsCounter++;
//			}
//			else {
//				order = newsCounter;
//				newsCounter++;
//			}
//		}
		
		List vars = parseList(sVars);
		List arrayParams = parseList(sArrayParams);

		//esto quiere decir que en el metodo method hay una llamada a InstrumentedMethod con id insSite de tipo csType (CALL/CREATE)
		return new CreationSiteMapInfo(insSite, order, vars,method,type,csType, arrayParams);
	}
	private static List parseList(String line)
	{
		Vector v = new Vector();
		line = line.substring(1,line.length()-1);
	
		String[] ls = line.split(",");
		for(int i=0;i<ls.length;i++)
		{
			if(ls[i].trim().length()>0)
			{
				v.add(ls[i].trim());
			}
		}
		return v;
	}
	/**
	 * @return Returns the method.
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @return Returns the vars.
	 */
	public List getVars() {
		return vars;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}
	public boolean isCall()
	{
		return type.equals("CC");
	}
	public boolean isCreationSite()
	{
		return type.equals("CS");
	}
	
	/**
	 * @return Returns the insSite.
	 */
	public String getInsSite() {
		return insSite;
	}
	public int getOrder()
	{
		return order;
	}
	public List getArrayParams()
	{
		return this.csArrayParams;
	}
	public void setOffset(int offset)
	{
		order = offset;
	}

	@Override
	public boolean equals(Object arg0) {
		if(arg0==null) return false;
		CreationSiteMapInfo cs = (CreationSiteMapInfo)arg0;
		return insSite.equals(cs.insSite) && type.equals(cs.type);
	}



	@Override
	public int hashCode() {
		return insSite.hashCode()+type.hashCode();
	}


	@Override
	public int compareTo(Object o) {		
		if(o==null) return 0;
		CreationSiteMapInfo cs = (CreationSiteMapInfo)o;
		return (this.insSite+this.type).compareTo(cs.insSite+cs.type);
	}
}

