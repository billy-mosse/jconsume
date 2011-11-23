/*
 * Created on Dec 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.jolden.bisort;

/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BiSortTest {

	public static void main(String[] args) {
		String[] argsTest = new String[2];
		argsTest[0]="-s";
		int count = Integer.parseInt(args[0]);
		for(int i=2; i<=count;i++)
		{
			argsTest[1]=Integer.toString(i+100);
			BiSort.mainOrig(argsTest);
		}
		
	}
}
