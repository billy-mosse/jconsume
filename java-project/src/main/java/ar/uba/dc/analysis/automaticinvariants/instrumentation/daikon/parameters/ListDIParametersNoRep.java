/*
 * Created on Jan 6, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;

import java.util.Collection;
import java.util.Iterator;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameter;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListDIParameters;

/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ListDIParametersNoRep extends ListDIParameters {
	/* (non-Javadoc)
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	@Override
	public synchronized boolean add(Object arg0) {
		DIParameter dip = (DIParameter) arg0;
		// Hay un bug en Soot? Me genera una local con el mismo nombre
		// pero la Local es diferentes
		// if(getParameterFromName(dip.getName())!=null)
		// Lo arregle cambiando hashCode e equals de DIParameter
		if(this.contains(dip))
			return false;
		return super.add(dip);
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	@Override
	public synchronized boolean addAll(Collection arg0) {
		for (Iterator iter = arg0.iterator(); iter.hasNext();) {
			DIParameter element = (DIParameter)iter.next();
			// ojo que esto puede ser malo...
			// if ( !(element instanceof DI_Value) || !((DI_Value)element).isNull()  ) 
				this.add(element);
		}
		// TODO Auto-generated method stub
		return true;
	}
	
}
