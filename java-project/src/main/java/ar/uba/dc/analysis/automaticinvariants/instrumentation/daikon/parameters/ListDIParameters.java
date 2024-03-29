/*
 * Created on Jan 6, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.InductiveVariablesInfo;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.Utils;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameter;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListDIParameters;
import soot.Local;

import soot.Body;

/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ListDIParameters extends Vector implements List{
	public List codeForParameters()
	{
		List code = new Vector();
		for (Iterator iter = this.iterator(); iter.hasNext();) {
			DIParameter element = (DIParameter) iter.next();
			code.addAll(element.codeForVar());
		}
		return code;
	}
	public List toList()
	{
		List res = new Vector();
		for (Iterator iter = this.iterator(); iter.hasNext();) {
			DIParameter element = (DIParameter) iter.next();
			if(element!=null) res.addAll(element.toList());
		}
		return res;
	}
	
	public List toListDerivedVariablesForSpec()
	{
		List res = new Vector();
		for (Iterator iter = this.iterator(); iter.hasNext();) {
			DIParameter element = (DIParameter) iter.next();
			if(element!=null) res.addAll(element.toListDerivedVariablesForSpec());
		}
		return res;
	}
	
	
	public List toListOnlyEnterizedVariables()
	{
		List res = new Vector();
		for (Iterator iter = this.iterator(); iter.hasNext();) {
			DIParameter element = (DIParameter) iter.next();
			if(element!=null) res.addAll(element.toListOnlyEnterizedVariables());
		}
		return res;
	}
	
	public ListDIParameters toListDIP()
	{
		ListDIParameters res = new ListDIParameters();
		for (Iterator iter = this.iterator(); iter.hasNext();) {
			DIParameter element = (DIParameter) iter.next();
			res.addAll(element.toListDIP());
		}
		return res;
	}
	
	public List toList(InductiveVariablesInfo IVInfo)
	{
		List res = new Vector();
		for (Iterator iter = toList().iterator(); iter.hasNext();) {
			Local element = (Local) iter.next();
			if(InductiveVariablesInfo.isAcceptedInductive(IVInfo, element.getName()))
				res.add(element);
		}
		return res;
	}
	
	public HashSet getStringListTypes()
	{
		HashSet res = new HashSet();
		for (Iterator iter = iterator(); iter.hasNext();) {
			DIParameter element = (DIParameter) iter.next();
			String s = element.getLocal().getType().toString();
			res.add(s);
		}
		return res;
	}
	
	public List toStringList()
	{
		List res = new Vector();
		for (Iterator iter = iterator(); iter.hasNext();) {
			DIParameter element = (DIParameter) iter.next();
			res.addAll(element.toStringList());
		}
		return res;
	}
	
	public List toStringList2()
	{
		List res = new Vector();
		for (Iterator iter = iterator(); iter.hasNext();) {
			DIParameter element = (DIParameter) iter.next();
			
			//Esto me parece un poco horrible
			//con el otro and me parece mucho muy horrible
			if (Utils.isIterator(element.getLocal()) && element.getClass().equals(DI_Object.class))
				continue;
			
			res.addAll(element.toStringList2());
		}
		return res;
	}
	
	public List toStringList(InductiveVariablesInfo IVInfo)
	{
		List res = new Vector();
		for (Iterator iter = this.toStringList().iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			
			
			if(InductiveVariablesInfo.isAcceptedInductive(IVInfo, element))
				res.add(element);
		}
		return res;
	}
	 
	public void addToBody(Body body)
	{
		for (Iterator iter = this.iterator(); iter.hasNext();) {
			DIParameter element = (DIParameter) iter.next();
			element.addToBody(body);
		}
	}
	public DIParameter getParameterFromLocal(Local var)
	{
		DIParameter res = null;
		for (Iterator iter = this.iterator(); iter.hasNext();) {
			DIParameter element = (DIParameter) iter.next();
			if(element.getLocal().equals(var))
				return element;
		}
		return res;
	}
	public DIParameter getParameterFromName(String name)
	{
		DIParameter res = null;
		for (Iterator iter = this.iterator(); iter.hasNext();) {
			DIParameter element = (DIParameter) iter.next();
			if(element.getName().equals(name))
				return element;
		}
		return res;
	}
	public void filter(InductiveVariablesInfo IVInfo)
	{
		ListDIParameters res = new ListDIParameters();
		for (Iterator iter = this.iterator(); iter.hasNext();) {
			DIParameter element = (DIParameter) iter.next();
			if(InductiveVariablesInfo.isAcceptedInductive(IVInfo,element)
				&& !element.getName().equals("nullLocal"))
			{
				element.filterDerived(IVInfo);
				res.add(element);
			}
		}
		this.clear();
		this.addAll(res);
	}
	public ListDIParameters filterNonObjects() {
		ListDIParameters res = new ListDIParameters();
		
		for(Iterator iter = this.iterator(); iter.hasNext(); )
		{
			DIParameter element = (DIParameter) iter.next();
			Class c = element.getClass(); 
			if (!(c.equals(DI_Int.class) || c.equals(DI_Integer.class) || c.equals(DI_Long.class) || c.equals(DI_Iterator.class)))
			{
				res.add(element);
			}
		}
		return res;
		
	}
	
}
