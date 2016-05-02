package ar.uba.dc.paper;

import java.util.Date;
import java.util.Random;


public class Op3 extends Op
{
	public Object apply(Object o)
	{
		Date d = new Date();
		Random r;
		r = new Random(d.getTime());
		int v = ((Integer) o).intValue();
		return new Integer(v * r.nextInt());
	}
}