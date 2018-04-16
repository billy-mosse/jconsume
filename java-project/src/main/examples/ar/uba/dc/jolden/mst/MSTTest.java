/*
 * Created on Dec 27, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.jolden.mst;


/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * @temporal 0
 * @residual 0
 */
public class MSTTest {
	
	/**
	 * @temporal 0
	 * @residual 0
	 */
	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]); 
		
		for(int i=1;i<=count;i++)  
		{
			String[] argsTest=new String[2]; //residual = args
			argsTest[0]="-v";
			argsTest[1]=Integer.toString( i +10  );
			MST.mainOrig(argsTest); 
		} 
	}
}
