/*
 * Created on 06/01/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;

import java.util.List;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.AdjustGotoTag;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameter;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameterFactory;
import soot.Body;
import soot.Local;
import soot.SootClass;
import soot.SootField;
import soot.Type;
import soot.Unit;
import soot.jimple.AssignStmt;
import soot.jimple.FieldRef;
import soot.jimple.GotoStmt;
import soot.jimple.IfStmt;
import soot.jimple.IntConstant;
import soot.jimple.Jimple;
import soot.jimple.NeExpr;
import soot.jimple.NullConstant;

/**
 * @author Diego
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DI_Field extends DIParameter {

	FieldRef fr; 
	String thisRefName;
	Local thisRef = null;
	DIParameter father;
	/* (non-Javadoc)
	 * @see instrumentation.daikon.parameters.DIParameter#getFather()
	 */
	@Override
	public DIParameter getFather() {
		// TODO Auto-generated method stub
		return father;
	}
	SootClass c;
	boolean isStatic;
	DIParameter fieldParam;
	/**
	 * @param var
	 */
	
	public DI_Field(SootField f, Local thisRef, SootClass c, Body body) {
		
		super();
		if(!f.isStatic() )
		{
			if(thisRef==null)
			{
				System.out.print("Oops");
			}
			fr = Jimple.v().newInstanceFieldRef(thisRef, f.makeRef());
			thisRefName =  thisRef.getName();
			this.thisRef = thisRef;
			this.c = c;
			isStatic=false;
		}
		else
		{
			fr = Jimple.v().newStaticFieldRef(f.makeRef());
			thisRefName = c.getName().replaceAll("\\.","_");
			this.c = c;
			isStatic=true;
			// System.out.println("Diego:"+thisRefName);
		}
		
		//esto lo borre
		fieldParam = DIParameterFactory.createDIParameter(thisRefName + "__f__" + f.getName(),f.getType());
		
		
		// fieldParam = DIParameterFactory.createDIParameter("_f_"+thisRefName +"_"+f.getName(),f.getType(), body, true);
		
		this.var = fieldParam.getLocal();
		this.nameVar=fieldParam.getName();
		this.derivedVars=fieldParam.getDerivedVariables();
		
	}
	private static String calculateThisReName(SootField f, Local thisRef, SootClass c) {
		String trn="";
		FieldRef fr2;
		if(!f.isStatic() && thisRef!=null)
		{
			trn =  thisRef.getName();
		}
		else
		{
			trn = c.getName().replaceAll("\\.","_");
		}
		return trn;
	}
	

	/**
	 * @param vn
	 * @param type
	 */
	public DI_Field(String vn, Type type) {
		super(vn, type);
		// TODO Auto-generated constructor stub
	}
	

	/* (non-Javadoc)
	 * @see instrumentation.daikon.parameters.DIParameter#codeForVar()
	 */
	@Override
	public List codeForVar() {
		List code = new Vector();
		List codeToAdd = new Vector();
		
		AssignStmt ass = Jimple.v().newAssignStmt(var, fr);
		codeToAdd.add(ass);
		codeToAdd.addAll(fieldParam.codeForVar());
		if(codeToAdd.size()>0 && thisRef!=null)
		{
			
			NeExpr cond1 = Jimple.v().newNeExpr(thisRef,NullConstant.v());
			IfStmt ifStmt = Jimple.v().newIfStmt(cond1,(Unit)codeToAdd.get(0));
			Local auxVar = (Local)fieldParam.toList().get(0);
			// Local auxVar = fieldParam.getLocal();
			AssignStmt ass0 = Jimple.v().newAssignStmt(auxVar, IntConstant.v(0));
			GotoStmt jump = Jimple.v().newGotoStmt(ass0);
			
			code.add(ifStmt);
			code.add(ass0);
			code.add(jump);
			
			ifStmt.addTag(new AdjustGotoTag(Integer.toString(2)));			
			jump.addTag(new AdjustGotoTag(Integer.toString(codeToAdd.size())));
		}
		code.addAll(codeToAdd);
		
		return code;
		}
	/**
	 * @param father The father to set.
	 */
	public void setFather(DIParameter father) {
		this.father = father;
	}
	
}
