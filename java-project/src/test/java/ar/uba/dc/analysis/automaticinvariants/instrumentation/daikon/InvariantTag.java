package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;
import soot.tagkit.StringTag;

/*
 * Created on 30/07/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author diego
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InvariantTag extends StringTag {

	/**
	 * @param arg0
	 */
	public InvariantTag(String arg0) {
		super(arg0);
	
	}

	/* (non-Javadoc)
	 * @see soot.tagkit.Tag#getName()
	 */
	@Override
	public String getName() {
		return "InvariantTag";
	}

}
