package ar.uba.dc.ismm;

import java.util.Date;
import java.util.Random;

import ar.uba.dc.util.TransformerE;

/**
 * @author martin
 *
 */
@SuppressWarnings("unused")
public class Transform implements TransformerE {
	 
	/**
	 * Residual = 1
	 * Temporal = 2
	 * El init de Random tiene 1 residual. Para ajustarlo al paper TACAS/PLDI hice un override del dando summary.
	 * 
	 * @param object
	 * @return
	 */
	public Object apply(Object object) {
		Date date = new Date();
		Random random = new Random(date.getTime()); 
		int value = ((Integer) object).intValue();
		return new Integer( value * 2); 
	}
}