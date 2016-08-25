package ar.uba.dc.analysis.common.code;

import java.util.Comparator;

import soot.SootMethod;

public class SootMethodComparator  implements Comparator<SootMethod>{

@Override
public int compare(SootMethod m1, SootMethod m2) {
    if (m1 == null && m2 == null){
        return 0;
    }
    else if (m1 == null){
        return -1;
    }
    else if (m2 == null){
        return 1;
    }
    else 
    {
    	return m1.getSignature().compareTo(m2.getSignature());
    }
}

}