/*
 * Created on Dec 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.jolden.em3d;


/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Em3dTest {

	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		for(int i=1;i<=count;i++)
		{
			String[] argsTest=new String[4];
			argsTest[0]="-n";
			argsTest[1]=Integer.toString(98+ i);
			argsTest[2]="-d";
			for(int j=0;j<=2;j++)
			{
			   argsTest[3]=Integer.toString(2+2*j+i);
			   Em3d.mainOrig(argsTest);
			}
		}
	}
}
