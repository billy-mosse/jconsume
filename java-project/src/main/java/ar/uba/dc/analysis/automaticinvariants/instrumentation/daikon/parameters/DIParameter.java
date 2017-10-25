/*
 * Created on 05/01/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.InductiveVariablesInfo;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.Utils;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameter;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListDIParameters;
import soot.Body;
import soot.Local;
import soot.Type;
import soot.jimple.Jimple;

/**
 * @author Diego
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class DIParameter  {
	protected Local var;
	protected String nameVar;
	protected ListDIParameters derivedVars = new ListDIParameters();
	protected ListDIParameters derivedVarsForSpec = new ListDIParameters();
	private static Set localsPool = new HashSet(); 
	
	DIParameter()
	{}
	DIParameter(Local var)
	{
		this.var = var;
		this.nameVar = var.getName();
	}
	DIParameter(String vn, Type type)
	{
		Local varAux = getLocalForName(localsPool,vn);
		if(varAux==null)
		{
			this.var =  Jimple.v().newLocal(vn,type);
			getLocalsPool().add(this.var);
		}
		else
			this.var = varAux;
		this.nameVar = vn;
	}
	
	public String getName()
	{

		if(this.getClass().equals(DI_Iterator.class))
			return "cont_" + var.getName();
		else
			return var.getName();
	}
	public Local getLocal()
	{
		return var;
	}
	public boolean hasDerivedVariables()
	{
		boolean  b = !derivedVars.isEmpty();
		if(b)
		{
			System.out.println("Lleno");
		}
		else{
			System.out.println("Vacio");
		}
		return b;
	}
	
	public boolean hasDerivedVariables2()
	{
		boolean  b = !derivedVarsForSpec.isEmpty();
		if(b)
		{
			System.out.println("Lleno");
		}
		else{
			System.out.println("Vacio");
		}
		return b;
	}
	public ListDIParameters getDerivedVariables()
	{
		return derivedVars;
	}
	
	public ListDIParameters getDerivedVariables2()
	{
		return derivedVarsForSpec;
	}
	
	public int getDerivedVariables2_size()
	{
		return Math.max(derivedVarsForSpec.size(), 1);
	}
	
	public List getDerivedVariablesNames()
	{
		List res = new Vector();
		for (Iterator iter = derivedVars.iterator(); iter.hasNext();) {
			DIParameter element = (DIParameter) iter.next();
			// res.add(element.var.getName());
			res.addAll(element.toStringList());
		}
		return res;
	}
	
	public List getDerivedVariablesNames2()
	{
		List res = new Vector();
		for (Iterator iter = derivedVarsForSpec.iterator(); iter.hasNext();) {
			DIParameter element = (DIParameter) iter.next();
			// res.add(element.var.getName());
			
			
			//Por que antes era .toStringList
			res.addAll(element.toStringList2());
		}
		return res;
	}
	public List toList()
	{
		List res = new Vector();
		boolean b = hasDerivedVariables();
		
		//Puedo preguntar si es iterator tambien
		if(b)
			res = getDerivedVariables().toList();
		else
		{
			res.add(getLocal());
		}
		return res;
	}
	
	public List toList2()
	{
		List res = new Vector();
		boolean b = hasDerivedVariables2();
		
		//Puedo preguntar si es iterator tambien
		if(b)
			res = getDerivedVariables2().toList2();
		else
		{
			res.add(getLocal());
		}
		return res;
	}
	
	public ListDIParameters toListDIP()
	{
		ListDIParameters res = new ListDIParameters();
		if(hasDerivedVariables())
			res = getDerivedVariables().toListDIP();
		else
		{
			res.add(this);
		}
		return res;
	}
	
	public ListDIParameters toListDIP2()
	{
		ListDIParameters res = new ListDIParameters();
		if(hasDerivedVariables2())
			res = getDerivedVariables2().toListDIP();
		else
		{
			res.add(this);
		}
		return res;
	}
	public List toStringList()
	{
		List res = new Vector();
		
		//BILLY hago que nunca se fije si tiene variables derivadas porque a mi me interesa registrar el objeto entero por ahora
		//tal vez mas adelante vea que fuwnciona tambien registrar solo el field
		//en realidad creo que si funciona registrar solo el field....por que no lo hago?
		if(false)
			res = getDerivedVariablesNames();
		else
		{
			res.add(getName());
		}
		return res;
	}
	
	public List toStringList2()
	{
		List res = new Vector();
		
		//BILLY hago que nunca se fije si tiene variables derivadas porque a mi me interesa registrar el objeto entero por ahora
		//tal vez mas adelante vea que fuwnciona tambien registrar solo el field
		//en realidad creo que si funciona registrar solo el field....por que no lo hago?
		if(hasDerivedVariables2())
			res = getDerivedVariablesNames2();
		else
		{
			res.add(getName());
		}
		return res;
	}
	public void filterDerived(InductiveVariablesInfo IVInfo)
	{
		ListDIParameters filteredDV = new ListDIParameters();
		for (Iterator iter = getDerivedVariables().iterator(); iter.hasNext();) {
			DIParameter element = (DIParameter) iter.next();
			if(IVInfo==null || !IVInfo.isInExcludeInfo(element.getName()))
			{
				filteredDV.add(element);
			}
		}
		derivedVars = filteredDV;
	}
	
	
	public ListDIParameters filter(InductiveVariablesInfo IVInfo)
	{
		ListDIParameters filteredDV = new ListDIParameters();
		this.toList();
		if(hasDerivedVariables())
		{
			for (Iterator iter = getDerivedVariables().iterator(); iter.hasNext();) {
				DIParameter element = (DIParameter) iter.next();
				if(IVInfo==null || InductiveVariablesInfo.isAcceptedInductive(IVInfo,element))
				{
					filteredDV.add(element);
				}
			}
		}
		else
		{
			if(IVInfo==null || InductiveVariablesInfo.isAcceptedInductive(IVInfo,this))
			{
				filteredDV.add(this);
			}
		}
		return  filteredDV;
	}
	public List codeForVar()
	{
		return new Vector();
	}
	public void addToBody(Body body)
	{
		if(!Utils.containsLocalsForName(body.getLocals(),this.var.getName()))
			body.getLocals().add(this.var);
		for (Iterator iter = derivedVars.iterator(); iter.hasNext();) {
			DIParameter element = (DIParameter) iter.next();
			element.addToBody(body);
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "("+this.var+","+this.toStringList()+")";
	}
	/**
	 * @return Returns the localsPool.
	 */
	public static Set getLocalsPool() {
		return localsPool;
	}
	/**
	 * @param localsPool The localsPool to set.
	 */
	public static void setLocalsPool(Set newPool) {
		DIParameter.localsPool = newPool;
	}
	protected static Local getLocalForName(Collection locals, String name) {
		boolean found = false;
		Local var = null;
		for (Iterator it = locals.iterator(); it.hasNext() && !found;) {
			var = (Local) it.next();
			found = var.getName().equals(name);
		}
		if(found)
			return var;
		else
			return null;
	}
	
	//algun dia voy a entender como implementar un buen equals
	// Me importa solo la local para identificar un parametro
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		DIParameter dip =(DIParameter) arg0;
		//return this.getName().equals(dip.getName());
		 return this.var.equals(dip.var) && nameVar.equals(dip.nameVar) && this.getName().equals(dip.getName()) && this.getClass().equals(dip.getClass());
		// return this.getLocal().equals(dip.getLocal());
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.getName().hashCode();
		// return this.var.hashCode()+this.nameVar.hashCode();
		// return this.getLocal().hashCode();
	}
	public DIParameter getFather()
	{
		return null;
	}
	
	
}
