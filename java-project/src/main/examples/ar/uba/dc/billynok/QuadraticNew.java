package ar.uba.dc.billynok;

/**
 * Temporal indica los objetos que no escapan al mua.
 *   Temporal local = # { objetos creados en el cuerpo del mua que no escapan } 
 *   Temporal callees  = # { objetos residuales producidos por las call que son capturados por mua }
 *   
 * Residual indica los objetos que escapan al mua.
 *    Residual local = # { objetos creados en el cuerpo del mua que escapan }
 *    Temporal callees  = # { objetos residuales producidos por las call que a su vez escapan a mua }
 *    
 * @author billy
 *
 */

public class QuadraticNew {

	public static void main(String[] args) {
		int n = args[0].length();
		for(int i = 1; i <= Math.pow(n,2); i++)
		{
			Integer num = new Integer(i);			
		}
	}	
	
}
