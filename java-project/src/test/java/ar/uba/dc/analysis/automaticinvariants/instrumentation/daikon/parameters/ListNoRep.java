/*
 * Created on Jan 6, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ListNoRep extends Vector implements List{

	/**
	 * 
	 */
	public ListNoRep() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ListNoRep(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ListNoRep(int arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ListNoRep(Collection arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	@Override
	public synchronized boolean add(Object arg0) {
		if(this.contains(arg0))
			return false;
		return super.add(arg0);
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	@Override
	public synchronized boolean addAll(Collection arg0) {
		for (Iterator iter = arg0.iterator(); iter.hasNext();) {
			Object element = iter.next();
			this.add(element);
		}
		// TODO Auto-generated method stub
		return true;
	}

}
