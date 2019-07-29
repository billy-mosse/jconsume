import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import java_cup.internal_error;

/*
 * Tal vez me hagan preguntas del CV y/o teóricas.
 * Quieren testear mi capacidad de comunicación. Me van a tomar un ejercicio de Pair programming en Java.
 * Number of elements, remove duplicates, keep as many unique elements as possible
 * O give half of my candy to my little sister while keeping as many unique elements as I can
 * Preguntarle por mail cosas a Samir si no entiendo
 * 
 * */
public class temp {
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

		/*l.add(1);
		l.add(1);
		l.add(2);*/
		
		dividePriorityQueue(l);

	}
	public static boolean kthBitOn(int number, int k)
	{
		return (number & (1 << k-1)) >= 1;
	}
	
	
	
	//esta solucion me parece que esta mal
	/*
	 * Por ejemplo, si tengo [1, 1,1,1,1,2,2,3,3,4,5,6,8,8]
	 * y tengo que sumar 3,
	 * no me conviene usar 1,1,1,
	 * sino 1 y 2 (así queda un elemento único en la lista).
	 * 
	 * 
	 * No, este ejemplo no sirve porque no está ordenado.
	 * A ver...
	 * [1,1,1,1,2] U [1,2]
	 * Claro, pero acá sí sirve, porque una solución es 1,1,1 y me caga.
	 * ...pero si tengo [1, 2] U [1, 1, 1, 1, 2] ahí sí anda. No, tampoco.
	 * Por qué no?
	 * ...pero si tengo [1, 2] U [1, 2, 1, 1, 1] ahí sí anda.
	 * 
	 * 
	 * O sea, esta sería una heurística pero se rompe. Puedo mencionar eso.
	 * */
	
	
	static Comparator<EltWithAmount> myComparator = new Comparator<EltWithAmount>() {
	    public int compare(EltWithAmount o1, EltWithAmount o2) {
	    	if(o1.amount < o2.amount)
	    	{
	    		return 1;
	    	}
	    	else
	    		return 0;
	    }
	};
	
	public static void dividePriorityQueue(List<Integer> l)
	{
		Collections.sort(l);
        PriorityQueue<EltWithAmount> pQueue = 
                new PriorityQueue<EltWithAmount>(myComparator);
        
        int i = 0;
        int size = l.size();
        	
        while(i < size)
		{
        	int num = l.get(i);
        	i += 1;
        	int count = 1;
        	while(i < size && l.get(i) == num)
        	{
        		count+=1;
        		i +=1;
        	}
        	pQueue.add(new EltWithAmount(num, count));
		}
        
       //esto es util porque permite paralelizarse...
       // int sum = l.stream().reduce(0, (x,y) -> x+y);
        
        int sum = 0;
        for(int j = 0; j < size; j++)
        {
        	sum += l.get(j);
        }
        List<Integer> current = new ArrayList<Integer>();
        boolean res = backtracking(pQueue, sum/2, current);
        
        System.out.println("respuesta:");
        System.out.println(answer);
        System.out.println(res);
        
	}
	
	public static int sumPriorityQueue(PriorityQueue<EltWithAmount> pQueue)
	{
		Iterator<EltWithAmount> it = pQueue.iterator();
		int res = 0;
		while(it.hasNext())
		{
			EltWithAmount elt = it.next();
			res += elt.amount * elt.number;
		}
		return res;
	}	
	
	 /* 
	 * 1) The first solution is bruteforcing the problem. We compute all possible subsets and order them by number of unique elements.
	 * Complexity should be 2^n n
	 * 
	 * Do we want an exact solution? Or can we use heuristics?
	 * 2) Maybe an idea could be to first try using repeated elements - binary. See solution divideBruteForce
	 * 
	 * 3) Maybe we could use a Priority Queue...but how?
	 * 
	 * */
	
	static List<Integer> answer = new ArrayList<Integer>();
	static boolean found = false;
	public static boolean backtracking(PriorityQueue<EltWithAmount> pQueue, int sum, List<Integer> current)
	{	
		System.out.println(current);
		System.out.println(sum);
		System.out.println(pQueue);
		System.out.println("________");


		List<Integer> new_current = new ArrayList<Integer>();
		for(Integer i : current)
			new_current.add(new Integer(i));
		
		if(sum <= 0)
			return false;
		
		if(pQueue.size() == 1 && pQueue.peek().amount == 1)
		{
			System.out.println(new_current);
			System.out.println(sum);
			System.out.println(pQueue);
			System.out.println("!!!!");	
			new_current.add(pQueue.peek().number);
			if(pQueue.peek().number == sum)
			{
				System.out.println(new_current);
				if (!found) answer = new_current;
				
				found = true;
				return true;
			}
			else
			{
				return false;
			}
		}	
		
		//aca saco un elemento de la pqueue
		EltWithAmount elt = pQueue.poll();
		if(elt.amount >= 2)
		{
			elt.amount -=1;
			pQueue.add(elt);
			if(sumPriorityQueue(pQueue) == sum)
				{System.out.println("new current");
				System.out.println(new_current);
				System.out.println(sum);
				for(EltWithAmount eltt : pQueue)
				{
					for(int i = 0; i < eltt.amount; i++)
					{
						new_current.add(eltt.number);
					}
				}
				if (!found)
				{
					
					answer = new_current;
					found = true;
				}
				return true;
			}
			else
			{
				//Dos opciones: o lo uso o no lo uso
				
				//El elemento ya no esta. Si no lo uso, no debo restar nada
				boolean res2 = backtracking(pQueue, sum, new_current);
				if(res2)
				{
					System.out.println("new current");
					System.out.println(new_current);	
					System.out.println(sum);

					return true;
				}
				
				//Si lo uso, lo agrego a current y resto el amount
				new_current.add(elt.number);
				boolean res1 = backtracking(pQueue, sum - elt.amount, new_current);
				if(res1)
				{
					System.out.println("new current");
					System.out.println(new_current);
					System.out.println(sum);
					return true;
				}
			}
		}
		else
		{
			if(sumPriorityQueue(pQueue) == sum)
			{
				System.out.println("new current");

				System.out.println(new_current);	
				System.out.println(sum);
				return true;

			}
			else
			{
				//Dos opciones, o lo uso o no lo uso
				
				
				//Si no lo uso...
				boolean res2 = backtracking(pQueue, sum, new_current);
				if(res2)
				{					
					System.out.println("new current");
					System.out.println(new_current);			
					System.out.println(sum);
					return true;
				}
				
				//Si lo uso...
				new_current.add(elt.number);
				boolean res1 = backtracking(pQueue, sum - elt.number,new_current);
				if(res1)
				{					
					System.out.println("new current");
					System.out.println(new_current);	
					System.out.println(sum);
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * Algunos comentarios: la complejidad es efectivamente exponencial.
	 * Si todos los elementos fueran únicos, sería el problema de la mochila, que ya se sabe que es exponencial....esto es mentira.
	 * Ahora bien, hay otras soluciones que tal vez son mejores, no?
	 * Esto que estoy haciendo es fuerza bruta. Es una bosta.
	 * Puedo hacer backtracking con podas? Como yo quiero la primer solucion, no sé si me sirve backtracking.
	 * Sirve hacer DP?
	 */
	public static void divideBruteForce(List<Integer> l)
	{
		//exponential complexity	
		Collections.sort(l);
		System.out.println(l);
		//Is it the knapsack problem?
		
		//I can try first giving away repeated elements, or a subset of those
		//So maybe I should order the numbers so non-unique elements appear first
		List<Integer> repeatedFirst = new ArrayList<Integer>();
		List<Integer> unique = new ArrayList<Integer>();
		
		
		int sum = 0;
		int lastIndex = l.size() - 1;
		for(int i = 0; i < lastIndex; i++)
		{
			sum = sum + l.get(i);
			if(l.get(i) == l.get(i+1))
			{
				repeatedFirst.add(l.get(i));
			}
			else
			{
				unique.add(l.get(i));
			}
		}
		unique.add(lastIndex);
		sum = sum + l.get(lastIndex);
		int toSister = sum/2;		
		
		repeatedFirst.addAll(unique);
		System.out.println("Hay que darle: " + toSister);
		
		int pow = (int) Math.pow(2, lastIndex + 1);
		int cantSoluciones = 0;
		for(int i = 1; i < pow; i++)
		{
			int subgroupSum = 0;
			for(int bit = 1; bit <= lastIndex + 1; bit++)
			{
				if(kthBitOn(i, bit))
				{
					subgroupSum = subgroupSum + repeatedFirst.get(bit-1);
				}
			}
			if(subgroupSum == toSister)
			{
				System.out.println("Solucion: ");
				for(int bit = 1; bit <= lastIndex + 1; bit++)
				{
					if(kthBitOn(i, bit))
					{
						System.out.println(repeatedFirst.get(bit-1));
					}
				}
				cantSoluciones = cantSoluciones + 1;
				if(cantSoluciones == 50)
					return;
			}
		}
	}
	
	/*
	 * Questions:
	 * 1) Should I solve the problem one? Or many times? Maybe I could try a heuristic... 
	 * 2) Size of the inputs?
	 * 3) Do we need an exact solution?
	 * 4) What happens if n is odd?
	 * */
	
	//esto es SUBSET SUM PROBLEM
	//PARTITION PROBLEM, mas bien
	/*
	 * Q(i,s) para DP. Q(i,s) es cierto si existe un subset de {x1,...xi} que suma s.
	 * No es tan facil hacer una DP porque quizas para subsets mas chicos tenes que usar elementos no unicos
	 * pero a la hora de mergear se rompe
	 * 
	 * ¿Se podrá hacer una DP manteniendo cantidad de elementos unicos que se pueden usar? Creo que sí.
	 * ES LA MISMA DP, porque el orden en que encuentra las soluciones funciona bien.
	 * 
	 * Finalmente, podemos intentar usar meet in the middle. Partimos el set en 2, repetidos y únicos tal vez.
	 * Calculamos todas las posibles sumas manteniendo la cantidad de elementos utilizada en cada suma parcial.
	 * 
	 */
	
	
	
	
	public static void divideDP(List<Integer> l)
	{
		//Para w = 1, 
		
		Collections.sort(l);
		System.out.println(l);
		//Is it the knapsack problem?
		
		//I can try first giving away repeated elements, or a subset of those
		//So maybe I should order the numbers so non-unique elements appear first
		List<Integer> repeatedFirst = new ArrayList<Integer>();
		List<Integer> unique = new ArrayList<Integer>();
		
		
		int sum = 0;
		int lastIndex = l.size() - 1;
		
		for(int i = 0; i < lastIndex; i++)
		{
			sum = sum + l.get(i);
			if(l.get(i) == l.get(i+1))
			{
				repeatedFirst.add(l.get(i));
			}
			else
			{
				unique.add(l.get(i));
			}
		}
		unique.add(lastIndex);
		sum = sum + l.get(lastIndex);
		
		if(sum/2 % 2 == 1)
			return;
		
		int toSister = sum/2;

		repeatedFirst.addAll(unique);
		subSolution[][] dp = new subSolution[lastIndex+2][toSister+1];
		
		
		for(int i = 0; i <= lastIndex; i++)
		{
			dp[i][0] = new subSolution();
		}		
		
		
		for(int i = 1; i <= lastIndex; i++)
		{
			for(int s = 0; s <= toSister; s++)
			{
				int size = s - repeatedFirst.get(i);
				
				if(size < 0)
					continue;
				
				subSolution oldSolution = dp[i-1][s-repeatedFirst.get(i)];
				if(oldSolution != null)
				{
					subSolution subsolution = oldSolution.clone();
					subsolution.elts.add(repeatedFirst.get(i));
					dp[i][s] = subsolution;
				}
				else
				{
					
				}
				
				
				
				
				if(s == toSister && dp[i][s] != null)
				{
					System.out.println(dp[i][s].elts);
				}
			}
		}
	}
}

		