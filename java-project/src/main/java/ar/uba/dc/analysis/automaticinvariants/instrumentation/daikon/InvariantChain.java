/*
 * Created on Nov 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;

import java.util.List;
import java.util.Vector;

import soot.SootMethod;

/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


public class InvariantChain {
	List insSites;
	List methods=new Vector();
	String invariants;
	SootMethod method;
	
	/**
	 * @param insSites
	 * @param invariants
	 * @param method
	 */
	public InvariantChain(SootMethod method, List insSites, String invariants ) {
		super();
		this.insSites = insSites;
		this.invariants = invariants;
		this.method = method;
	}
	public InvariantChain(SootMethod method, List insSites, String invariants, List methods ) {
		super();
		this.insSites = insSites;
		this.invariants = invariants;
		this.method = method;
		this.methods = methods;
	}
	
	
	
	public InvariantChain(SootMethod method, List insSites) {
		super();
		this.insSites = insSites;
		this.invariants = "";
		this.method = method;
	}
	
	/**
	 * @return Returns the insSites.
	 */
	public List getInsSites() {
		return insSites;
	}
	/**
	 * @return Returns the invariants.
	 */
	public String getInvariants() {
		return invariants;
	}
	/**
	 * @return Returns the method.
	 */
	public SootMethod getMethod() {
		return method;
	}
	
	public List getsMethods() {
		return methods;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "<"+insSites.toString()+";"+invariants+";"+method+">";
	}
}
