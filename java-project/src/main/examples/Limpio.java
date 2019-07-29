import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Limpio {
	
	public static void main(String[] args)
	{
		System.out.println("Hola");
		List<Integer> l = new ArrayList<Integer>();
		l.add(1);
		l.add(1);
		l.add(1);
		l.add(2);
		l.add(2);
		l.add(3);
		l.add(3);
		l.add(3);
		
		/*
		 * Hmhm, this looks similar to the knapsack problem.
		 * Can I ask you some questions about the problem?
		 * 
		 * Can I assume that we are always going to have a solution? If not, maybe we can do a fast check so we don't run an algorithm with high complexity
		 * 
		 * For example, we can check whether the sum is odd, or maybe we can quickly check the presence of huge outliers.
		 * For example, the list {1,2,1000} won't have a solution
		 * 
		 * */
		
		Collections.sort(l);

		/*
		 * Let's sort the list in ascending order, first. I'm sure the complexity of the algorithm will be big, and we don't lose anything by sorting it.
		 * 
		 * Good, now we have repeated values right next to each other.
		 * 
		 * Just to start thinking, how would we brute force this problem?
		 * 
		 * We could process all subsets of the list,
		 * compute the sum and if it the right one, we keep it in an array, where the position is the amount of unique elements the solution uses
		 * Then, we search the array for the solution with the highest amount of unique values.
		 * 
		 * This solution, obviously, has lots of problems:
		 * First, the complexity of the algorithm is huge. We must iterate 2^n sets and compute the sum for each one, so that's 2^n * n.
		 * We also have to get the amount of unique values for each solution. We can assume that it will be ordered, so we only need to iterate through each element once.
		 * Then the complexity is 2^n * n^2. That's huge.
		 * 
		 * We also have to store all possible solutions. In practice that might not be a lot of space, unless we have lots of repeating values.
		 * 
		 * Well, maybe the only good thing about this solution is that you definitely get the optimal value. Maybe we can use this solution if n is extremely small
		 * and/or we absolutely need the optimal solution.
		 * 
		 * Do we need the optimal solution? You told me that we should keep as many unique values as possible.
		 * 
		 * OK, so maybe we could try a heuristic approach.
		 * */
		
		heuristic1(l, 1);		
		
		List<Integer> l2 = Arrays.asList(1,2,3,1,1,1,2,2,2,3,3,3);
		
		Collections.sort(l2);
		heuristic1(l2, 1);
		
		
	}
	
	
	public static boolean kthBitOn(long i, int k)
	{
		return (i & (1 << k-1)) >= 1;
	}

	
	public static void bruteForce(List<Integer> l, int n, int toGive)
	{
		/*
		 * So maybe we can now process all subsets in a nice order.
		 * I guess we should try with subsets containing only unique values first...
		 * So first we try with subsets of [1,2,3], then we can maybe try with [1,1], then [1,1,2]
		 * Hmm, it looks like we can just iterate through the list using a binary notation.
		 * */
		
		long maxSubsetIndex = (long) (Math.pow(2, n) -1);
		for(long i  = 1; i <= maxSubsetIndex; i++)
		{
			int sum = 0;
			for(int k = 1; k <= n; k++)
			{
				if(kthBitOn(i, k))
				{
					sum += l.get(k-1);					
				}					
			}
			if(sum == toGive)
			{
				for(int k = 1; k <= n; k++)
				{
					if(kthBitOn(i, k))
					{
						System.out.println(l.get(k-1));
					}
				}
				return;					
			}
		}	
	}
	
	class SubSolution
	{
		public SubSolution()
		{
			elts = new ArrayList();
		}
		public SubSolution clone()
		{
			SubSolution subSolution = new SubSolution();
			subSolution.elts.addAll(this.elts);
			return subSolution;
		}
		List<String> elts;
	}
	
	public static void dp(ArrayList<Integer> l, int n, int toGive)
	{
		
		//something like this
		SubSolution[][] dp = new SubSolution[n][toGive];
		
		//TODO: initial states
		
		
		for(int k = 0; k < n; k++)
		{
			for(int sum = 0; sum <= toGive; sum++)
			{
				int elt = l.get(k);
				//We have a solution iff dp[k-1][sum - elt] + elt = sum
				//or if dp[k-1][sum] holds
				/*
				 * But wait, we need to store the partial solutions, so we should have an array of some objects...
				 * */
				
				if(dp[k-1][sum] != null)
				{
					dp[k-1][sum] = dp[k-1][sum];
				}
				else
				{
					if(dp[k-1][sum-elt] != null)
					{
						//It isn't clear which solution is better
						//This is an opportunity for a nice experiment comparing both versions
						dp[k][sum] = dp[k-1][sum-elt].clone(); 
					}
				}
			}
		}
		
		if(dp[n-1][toGive] != null)
		{
			System.out.println(dp[n-1][toGive].elts);
		}
	}
	
	
	public static void heuristic1(List<Integer> l, int method)
	{
		/*
		 * We need to keep as many unique values as possible.
		 * Maybe we could order the list in such a way that the subset uses unique values.
		 * */
		
		int n = l.size();
		ArrayList<Integer> repeated = new ArrayList<Integer>();
		ArrayList<Integer> uniqueFirst = new ArrayList<Integer>();
		for(int i = 0; i < n-1; i++)
		{
			if(l.get(i) == l.get(i+1))
			{
				repeated.add(l.get(i));
			}
			else
			{
				uniqueFirst.add(l.get(i));
			}
		}
		uniqueFirst.add(l.get(n-1));
		
		uniqueFirst.addAll(repeated);
		
		System.out.println(uniqueFirst);
		
		int toGive = 0;
		for(Integer i: l)
		{
			toGive += i;
		}
		if(toGive % 2 == 1)
		{
			System.out.println("False");
		}
			
		toGive = toGive/2;
		
		if(method == 1)
			bruteForce(uniqueFirst, n, toGive);

		if(method == 2)
			dp(uniqueFirst, n, toGive);
			
	}
	
	/*
	 * OK, so, is this solution the optimal one?
	 * What if there is a solution that, for example, uses number 3 twice but all other values are unique....
	 * 
	 * For example...
	 * 
	 * [1,2,3,1,1,2,2,3]
	 * 
	 * Let's play with that example...
	 * We should add an odd numbers so that there is a solution:
	 * [1,2,3,1,1,1,2,2,3]
	 * sum is 16, half of that is 8.
	 * So our current algorithm would give us..., well, we can run it.
	 * 
	 * [1,2,3,1,1,1,2,2,2,3,3,3]
	 * Half of this is 12.
	 * Our current algorithm returns us a list with only one unique value.
	 * But we can also return 1,2,3,3, that has 2 unique values, so our algorithm does not give us an optimal solution.
	 * 
	 * Well, now we can try 2 optimizations: one would be to reduce computing time/space, and the other would be to get a better solution.
	 * 
	 * Maybe we can try dynamic programming, as we know that the sum of the elements should always be a reasonable amount.
	 * */
	
	
}
