/*package ar.uba.dc.analysis.common;

import java.util.Comparator;

//import ar.uba.dc.analysis.common.AbstractInterproceduralAnalysis.IntComparator;
import soot.SootMethod;

public class Utils {
	protected Comparator<SootMethod> getOrderComparator() {
		return new IntComparator();
	}
	
	// queue class
	class IntComparator implements Comparator<SootMethod> {
		public int compare(SootMethod o1, SootMethod o2) {
			Integer v1 = order.get(o1);
			Integer v2 = order.get(o2);
			if(v1!=null && v2!=null)	return v1.intValue() - v2.intValue();
			else if(v1==null && v2!=null) return -v2.intValue();
			else if(v1!=null) return v1.intValue();
			else return 0;
		}
	};
}
*/