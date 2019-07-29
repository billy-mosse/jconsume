import java.util.ArrayList;
import java.util.List;


public class subSolution
	{
		public List<Integer> elts;
		
		
		public subSolution()
		{
			elts = new ArrayList<Integer>();
		}
		
		public subSolution clone()
		{
			subSolution s = new subSolution();
			s.elts = new ArrayList<Integer>();
			s.elts.addAll(elts);
			return s;
		}
	}