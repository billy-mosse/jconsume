package ar.uba.dc.paper;

/**
 * @author billy
 * 
 * Ejemplos del paper
 */

public class FieldInvariant
{
	
	public static final void main(String args[])
	{
		System.out.println(args[0]);
		mainOrig(args);
	}
  
  /**
   * 
   * @temporal 0
   * @residual 0
   */
  public static final void mainOrig(String args[])
  {
	  int r = Integer.parseInt(args[0]);
	  mainParameters(r);
  }
  
  
	public static void mainParameters(int r) {
		A[][] b = new A[r][r];		
		triangle(b, r);
	}


	/**
	 * 
	 * @MemReq = n*(n+1)/2
	 */
	public static void triangle(A[][] a, int n)
	{
		Counter c = new Counter();
		for(c.j = 1; c.j<=n; c.j++)
		{
			line(a,c.j);
		}
	}
	
	/**
	 * 
	 * @MemReq = m
	 * 
	 */
	public static void line(A[][] a, int m)
	{
		for(int j=1; j<=m; j++)
		{
			a[m-1][j-1] = new A();
		}
	}
}