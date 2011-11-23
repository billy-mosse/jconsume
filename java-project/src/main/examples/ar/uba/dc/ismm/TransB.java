package ar.uba.dc.ismm;

import java.util.Date;
import java.util.Random;

public class TransB implements ITrans{
	/**
     * @temporal 2
	  * @residual 4
	  */
	public Object apply(Object object) {
		   Date date = new Date();
		   Random random = new Random(date.getTime());
		   int value = ((Integer) object).intValue();
		   return new Integer[]{
			new Integer(value),
			new Integer(value * random.nextInt())};
		 }
}
