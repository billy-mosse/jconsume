/*
 * Created on 29/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.pruebas;

import ar.uba.dc.analysis.automaticinvariants.pruebas.FieldRefWrapper;
import soot.SootFieldRef;
import soot.Value;
import soot.ValueBox;
import soot.jimple.FieldRef;
import soot.jimple.InstanceFieldRef;
import soot.jimple.internal.AbstractInstanceFieldRef;
import soot.jimple.internal.JimpleLocalBox;

/**
 * @author Diego
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FieldRefWrapper extends AbstractInstanceFieldRef {
	
	/**
	 * @param baseBox
	 * @param fieldRef
	 */
	ValueBox bb;
	SootFieldRef fr;
	InstanceFieldRef ifr;
	
	public FieldRefWrapper(ValueBox baseBox, SootFieldRef fieldRef) {
		super(baseBox, fieldRef);
		bb = baseBox;
		fr = fieldRef;
	}

	public Object clone() {
		JimpleLocalBox bbClone = new JimpleLocalBox(this.bb.getValue());
		FieldRefWrapper copia = new FieldRefWrapper(bbClone,this.fr);
		return copia;
	}
	

	/* (non-Javadoc)
	 * @see soot.jimple.InstanceFieldRef#setBase(soot.Value)
	 */
	public void setBase(Value base) {
			super.setBase(base);
			bb.setValue(base);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object arg0) {
		boolean eq = false;
		if(arg0 instanceof FieldRefWrapper)
		{
			FieldRefWrapper arg = (FieldRefWrapper)arg0;
			eq = (this.fr.toString().equals(arg.fr.toString()) 
					&& this.bb.getValue().equals(arg.bb.getValue())) ;
		}
		return eq;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return bb.hashCode()+fr.toString().hashCode();
	}
}
