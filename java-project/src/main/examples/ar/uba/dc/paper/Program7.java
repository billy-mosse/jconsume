package ar.uba.dc.paper;

/**
 * @author billy
 * 
 * Ejemplos del paper
 */

public class Program7
{
	
	public static final void main(String args[])
	{		
		//mainOrig(args);
		
		int r = Integer.parseInt(args[0]);
		A[] c = new A[r];
		
		for(int i = 1; i < c.length; i++)
		{
			Integer k = new Integer(i);
			k+=1;
		}
		
		/*for(int i = 1; i <= r; i++)
		{
			Integer k = new Integer(i);
			k += 1;
		}*/
		//A[][] b = new A[r][r];		
		//triangle(b, r);
	}
  
  /**
   * 
   * @temporal 0
   * @residual 0
   */
  /*public static final void mainOrig(String args[])
  {
	  int r = Integer.parseInt(args[0]);
	  mainParameters(r);
  }*/
  
	/*public static void mainParameters(int r) {
		
		
		Integer[] B = new Integer[r];
		
		for(int i = 1; i < B.length; i++)
		{
			Integer A =  new Integer(4);
			A++;
		}
		
		f();
		/*int i = 1;
		int j =4;
		while(i < 10 && j < 40)
		{
			Integer a = new Integer(4);
			i +=1;
			j+=4;
		}*/
		
		/*int s = r;
		
		int j = r + r2;
		
		//esto es para marcar a j como inductive
		for(int k = 1; k < j + s + r; k++)
		{
			Integer f = new Integer(4);
		}*/
		
		/*for(int i = 1; i < r; i++)
		{
			j = i;
			k = r;
			l = j + k;
			Integer A = new Integer(4);
		}*/
		
	/*	A[][] b = new A[r][r];		
		triangle(b, r);
	}*/


	/*public static void f() {
		// TODO Auto-generated method stub
		
	}*/

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