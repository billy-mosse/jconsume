package ar.uba.dc.analysis.common.code;

import java.util.List;

import soot.SootMethod;
import soot.Type;

public class HSootMethod extends SootMethod{
	 public HSootMethod(String name, List<Type> parameterTypes, Type returnType) {
		super(name, parameterTypes, returnType);
		// TODO Auto-generated constructor stub
	}

	@Override
	    public int hashCode() {
	        return this.equivHashCode();
	    }

}
