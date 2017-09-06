/*
 * Created on Dec 29, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameter;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.InductiveVariablesInfo;

/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InductiveVariablesInfo {
	List  inductiveInfo;
	List  excludeInfo;
	
	/**
	 * 
	 */
	public InductiveVariablesInfo(List ind, List exc ) {
		this.inductiveInfo = ind;
		this.excludeInfo = exc;
	
	}
	
	
	public static List UtilsparseList(String line)
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

	
	//TODO: parchecito porque quiero avanzar y no trabarme en tratar de entender por que no encuentra la clase Utils
	public static InductiveVariablesInfo fromString(String res)
	{
		String[] elems = res.split(";");
		
		List  inductiveInfo = UtilsparseList(elems[0]);
		List  excludeInfo = UtilsparseList(elems[1]);
		return new InductiveVariablesInfo(inductiveInfo, excludeInfo);
	}
		
	public boolean isInIncludeInfo(String varName)
	{
		String varName2 = varName.replaceAll("\\$","__");
		return inductiveInfo.contains(varName) || inductiveInfo.contains(varName2) ;
		
	}
	public boolean isInExcludeInfo(String varName)
	{
		String varName2 = varName.replaceAll("\\$","__");
		return excludeInfo.contains(varName) || excludeInfo.contains(varName2)|| excludeAllInductives();
	}
	public boolean takeAllInductives()
	{
		return inductiveInfo.contains("*");
	}
	public boolean excludeAllInductives()
	{
		return excludeInfo.contains("*");
	}
	public static boolean isAcceptedInductive(InductiveVariablesInfo IVInfo, DIParameter var)
	{
		return (IVInfo==null 
				|| ( !IVInfo.isInExcludeInfo(var.getName())
					 && (IVInfo.takeAllInductives()
						 || IVInfo.isInIncludeInfo(var.getName())
						 || (var.getFather()!=null 
						 		 && (IVInfo.isInIncludeInfo(var.getFather().getName()))
						 	)
						 )
					)
				);
	}

	
	public static boolean isAcceptedInductive(InductiveVariablesInfo IVInfo, String name)
	{
		return (IVInfo==null 
				|| ( !IVInfo.isInExcludeInfo(name)
					 && (IVInfo.takeAllInductives()
						 || IVInfo.isInIncludeInfo(name)
						 )
					)
				);
	}
	
	public static List filterInductives(List vars, InductiveVariablesInfo IVInfo,String cs)
	{
		List res = new Vector();
		String csFilter = cs.replaceAll("\\.","_");
		for (Iterator iter = vars.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			
			String elementFilter = element;
			if(cs.length()>0) element.substring(cs.length()+1);
			if(isAcceptedInductive(IVInfo,elementFilter))
			{
				res.add(element);
			}
		}
		return res;
	}

	/**
	 * @return Returns the excludeInfo.
	 */
	public List getExcludeInfo() {
		return excludeInfo;
	}
	/**
	 * @return Returns the inductiveInfo.
	 */
	public List getInductiveInfo() {
		return inductiveInfo;
	}
}
