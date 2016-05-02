package ar.uba.dc.paper;

import java.util.Date;
import java.util.Random;

public class Op2 extends Op
{
	public Object apply(Object object)
	{
		Date d = new Date();
		Random r = new Random(d.getTime());
		int v = ((Integer) object).intValue();
		return new Integer[]{new Integer(v), new Integer(v*r.nextInt())};
	}
}