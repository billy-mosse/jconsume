package ar.uba.dc.paper;

public class Triangle
{
	/**
	 * 
	 * @MemReq = n*(n+1)/2
	 */
	public void triangle(A [][] a, int n)
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
	public void line(A[][] a, int m)
	{
		for(int j=1; j>=m; j++)
		{
			a[m-1][j-1] = new A();
		}
	}
}