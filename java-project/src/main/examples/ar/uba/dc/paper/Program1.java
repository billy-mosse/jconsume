package ar.uba.dc.paper;

/**
 * @author billy
 * 
 * Ejemplos del paper
 */

public class Program1
{
	public static void main(String[] args) {
		int r = 5;
		A[][] b = new A[r][r];		
		triangle(b, r);
	}


	/**
	 * 
	 * @MemReq = n*(n+1)/2
	 */
	public static void triangle(A[][] a, int n)
	{
		for(int i = 1; i<=n; i++)
		{
			line(a,i);
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