/*
 * Created on 06/11/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;

import soot.tagkit.StringTag;

/**
 * @author Diego
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AdjustGotoTag extends StringTag {

	/**
	 * @param arg0
	 */
	public AdjustGotoTag(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getName() {
		return "AdjustGotoTag";
	}

}
