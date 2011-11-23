package ar.uba.dc.analysis.common.method.information;

import ar.uba.dc.analysis.common.MethodInformationProvider;
import soot.SootMethod;

public class DefaultMethodInformationProvider implements MethodInformationProvider {
	
	// unanalysed methods assumed pure (& return a new obj)
    // class name prefix / method name
    static private final String[][] pureMethods = {
    	{"java.lang.String","<init>"},
    	{"java.lang.","valueOf"}, {"java.lang.","parseInt"},
    	{"java.","equals"}, {"javax.","equals"}, {"sun.","equals"},
    	{"java.","compare"}, {"javax.","compare"},{"sun.","compare"},
    	{"java.","getClass"},{"javax.","getClass"},{"sun.","getClass"},
    	{"java.","hashCode"},{"javax.","hashCode"},{"sun.","hashCode"},
    	{"java.","toString"},{"javax.","toString"},{"sun.","toString"},
    	{"java.","valueOf"},{"javax.","valueOf"},{"sun.","valueOf"},
    	{"java.","compareTo"},{"javax.","compareTo"},{"sun.","compareTo"},
    	{"java.lang.System","identityHashCode"},
    	// we assume that all standard class initialisers are pure!!!
    	{"java.","<clinit>"}, {"javax.","<clinit>"}, {"sun.","<clinit>"},
	
		// if we define these as pure, the analysis will find them impure as
		// they call static native functions that could, in theory,
		// change the whole program state under our feets
		{ "java.lang.Math","abs" },
		{ "java.lang.Math","acos" },
		{ "java.lang.Math","asin" },
		{ "java.lang.Math","atan" },
		{ "java.lang.Math","atan2" },
		{ "java.lang.Math","ceil" },
		{ "java.lang.Math","cos" },
		{ "java.lang.Math","exp" },
		{ "java.lang.Math","floor" },
		{ "java.lang.Math","IEEEremainder" },
		{ "java.lang.Math","log" },
		{ "java.lang.Math","max" },
		{ "java.lang.Math","min" },
		{ "java.lang.Math","pow" },
		{ "java.lang.Math","rint" },
		{ "java.lang.Math","round" },
		{ "java.lang.Math","sin" },
		{ "java.lang.Math","sqrt" },
		{ "java.lang.Math","tan" },

		{"java.lang.Throwable", "<init>"},
	
		// to break the cycle exception -> getCharsAt -> exception
		{"java.lang.StringIndexOutOfBoundsException","<init>"},
		
		// Agrego los que necesitamos para el MST
		{"java.lang.Integer","toString"},
		{"java.lang.Integer","<init>"},
		{"java.lang.Integer","intValue"},
		{"java.lang.StringBuilder","<init>"},
		{"java.lang.StringBuilder","toString"},
		{"java.lang.StringBuilder","append"},
		{"java.lang.RuntimeException","<init>"},
		{"java.lang.String","startsWith"},
		{"java.lang.System","exit"},
		{"com.sun.","hashCode"},
		
		// Para correr: em3d
		{"java.", "hasMoreElements"}, {"sun.", "hasMoreElements"}, {"javax.", "hasMoreElements"},
		{"java.", "nextElement"}, {"sun.", "nextElement"}, {"javax.", "nextElement"},
		{"java.util.Random", "nextDouble"},
		{"java.util.Random", "nextInt"},
		{"java.util.Random", "<init>"},
		{"java.io.","print"}, 
		{"java.io.","println"},
    };

    // unanalysed methods that modify the whole environment
    static private final String[][] impureMethods = { 
		{"java.io.","<init>"},
		{"java.io.","close"},
		{"java.io.","read"},
		{"java.io.","write"},
		{"java.io.","flush"}, 
		{"java.io.","flushBuffer"}, 
		//{"java.io.","print"}, 
		//{"java.io.","println"}, 
	 	{"java.lang.Runtime","exit"}
    };
    
    // unanalysed methods that alter its arguments, but have no side effect
    static private final String[][] alterMethods = { 
		{"java.lang.System","arraycopy"},
	
		// these are really huge methods used internally by StringBuffer
		// printing => put here to speed-up the analysis
		{"java.lang.FloatingDecimal","dtoa"},
		{"java.lang.FloatingDecimal","developLongDigits"},
		{"java.lang.FloatingDecimal","big5pow"},
		{"java.lang.FloatingDecimal","getChars"},
		{"java.lang.FloatingDecimal","roundup"},
		
		// Los agregamos porque el test de MST daba mal porque son metodos nativos y no hay escape graph para esto
		{"java.lang.Thread","currentThread"},
		{"java.lang.Double","doubleToRawLongBits"},
		{"java.lang.Double","longBitsToDouble"},
		{"java.lang.System","currentTimeMillis"},
		{"sun.misc.FloatingDecimal","big5pow"}
    };
	
	public Boolean isAnalyzable(SootMethod method) {
		// could be optimized with HashSet....
		if (inArray(method, pureMethods) || inArray(method, impureMethods) || inArray(method, alterMethods)) {
			return false;
		}
			    
	    return true;
	}

	public Boolean hasNonConservativaGraph(SootMethod method) {
		return inArray(method, impureMethods);
	}
	
	public Boolean hasConservativaGraph(SootMethod method) {
		return inArray(method, alterMethods);
	}

	public Boolean hasFreshGraph(SootMethod method) {
		return inArray(method, pureMethods);
	}
	
	private Boolean inArray(SootMethod method, String[][] array) {
		String c = method.getDeclaringClass().toString();
    	String m = method.getName();
    	
		for (String[] element : array) {
			if (m.equals(element[1]) && c.startsWith(element[0])) {
				return true;
			}
	    }
		
		return false;
	}

	/**
	 * En esta implmenetacion no hay metodos que se exlcuyan
	 */
	public Boolean isExcluded(SootMethod method) {
		return false;
	}
}
