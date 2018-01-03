package billynok;

/**
 * Todas las particiones de un conjunto donde importa el orden
 * @author billy
 *
 */


//TODO: ver que pasa cuando n = 1.
public class Linear {
	public static void main(String args[])
	{
		for(int n = 2; n <= Integer.parseInt(args[0]);n++)
		{
			allPartitions(n);
		}
		//System.out.println("hola");
		
				
		System.out.println("____________-");
		
		//Bound: [n ^(n+1) - 1] / (n+1)
		
		
		for(int n = 2; n <= Integer.parseInt(args[0]);n++) {
			double bound = (Math.pow(n,(n+1)) - 1) / (n+1) + Math.pow(n, n);
			System.out.printf(n + "---");
			
			System.out.printf("%.0f\n", bound);
			//System.out.printf("dexp: %f\n", dexp);
		}
	}
	
	public static void allPartitions(int n){
		long count = 0;
		for(long b = 2; b <= n; b++){
			for(long i = 0; i <= Math.pow(b,n); i++){
				count += 1;
		//		if(count % 1000000 == 0)
			//		System.out.println(count + "  -----  " + n );
			}
		}
		System.out.println(n + "---" + count);
	}
}
