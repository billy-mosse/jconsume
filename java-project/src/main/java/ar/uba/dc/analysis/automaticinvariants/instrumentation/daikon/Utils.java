/*
 * Created on Oct 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.AdjustGotoTag;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.InductiveVariablesInfo;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.Utils;
import soot.*;
import soot.jimple.*;
import soot.Body;
import soot.Local;
import soot.Scene;
import soot.Type;
import soot.Value;
import soot.jimple.Jimple;
import soot.tagkit.BytecodeOffsetTag;
import soot.tagkit.LineNumberTag;
import soot.tagkit.PositionTag;
import soot.tagkit.SourceLineNumberTag;
import soot.tagkit.SourceLnPosTag;
import soot.tagkit.Tag;
import soot.util.Chain;

/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Utils {
	public static SootClass varTestClass;
	
	static {
	    //varTestClass    = Scene.v().loadClass("VarTest", SootClass.SIGNATURES);
		varTestClass    = Scene.v().loadClassAndSupport("ar.uba.dc.analysis.automaticinvariants.VarTest");
		//varTestClass    = Scene.v().loadClass("ar.uba.dc.analysis.automaticinvariants.VarTest", SootClass.SIGNATURES);
	  }
	

	protected static boolean isTypeString(Type t) {
		boolean ok = false;
		if (t.toString().endsWith(".String"))
			ok = true;
		return ok;
	}

	protected static boolean isPossibleParameter(Local var) {
		boolean ok = false;
		ok = isPossibleArgument(var);
		/*
		if (var.getName().startsWith("<init>"))
			ok = false;
		*/
		return ok;
	}

	protected static boolean isArray(Value var) {
		return isTypeArray(var.getType());
	}

	protected static boolean isCollection(Value var) {
		return isTypeCollection(var.getType());
	}
	
	public static boolean isObject(Value var) {
		return isTypeObject(var.getType());
	}
	protected static boolean isSizeable(Value v)
	{
		return isCollection(v) || isString(v) || isArray(v);
	}

	public static boolean isPossibleArgument(Value v) {
		boolean ok = false;
		ok = isCollection(v) || isArray(v) || isString(v);
		
		if (isNum(v) || isDouble(v))
			ok = true;
		if(hasSizeableFields(v))
			ok = true;
		
		return ok;
	}
	protected static boolean hasSizeableFields(Value v)
	{
		// OJO! Hay que mirar que tenga campos " sizeables"
		return false;
	}
	protected static boolean isNum(Value v)
	{
		return isTypeNum(v.getType().toString());
	}
	protected static boolean isTypeNum(String t)
	{
		return t.equals("int") /*|| t.equals("long")*/;
	}
	protected static boolean isDouble(Value v)
	{
		return v.getType().toString().equals("double") ||
			v.getType().toString().equals("long");
		
	}
		
	protected static boolean isPossibleType(Type t) {
		boolean ok = false;
		ok = isTypeString(t) || isTypeArray(t) || isTypeCollection(t) 
		|| isTypeNum(t.toString());
			
		return ok;
	}

	protected static boolean isString(Value var) {
		return isTypeString(var.getType());
	}

		
	protected static boolean isTypeArray(Type t) {
		boolean ok = false;
		if (t.toString().endsWith("[]"))
			ok = true;
		return ok;
	}

	protected static boolean isTypeCollection(Type t) {
		boolean ok = false;
		try {
			Class c = Class.forName(t.toString());
			if (Collection.class.isAssignableFrom(c)) {
				ok = true;
			}
		} catch (ClassNotFoundException e) {
		}
		return ok;
	}

	protected static boolean isTypeObject(Type t) {
		boolean ok = false;
		/*
		if(!t.toString().startsWith("java."))
		{
		*/
		
			try {
				Class c = Class.forName(t.toString());
				if (Object.class.isAssignableFrom(c)) {
					ok = true;
				}
			} catch (ClassNotFoundException e) {
				ok=false;
			}
		// }
		return ok;
	}
	protected static boolean isSubclass(Type t1, Type t2)
	{
		boolean ok = false;
		try {
			Class c1 = Class.forName(t1.toString());
			Class c2 = Class.forName(t2.toString());
			if (c1.isAssignableFrom(c2)) {
				ok = true;
			}
		} catch (ClassNotFoundException e) {
			ok=false;
		}
		// }
		return ok;
	}

	public static boolean isIterator(Value var)
	{
		return isTypeIterator(var.getType());
	}
	protected static boolean isTypeIterator(Type t) {
		return t.toString().equals("java.util.Iterator")
			|| t.toString().equals("java.util.Enumeration");
	}
	
	public static boolean isMethodNext(SootMethod m)
	{
		return m.toString().equals("<java.util.Iterator: java.lang.Object next()>")
			   ||  m.toString().equals("<java.util.Enumeration: java.lang.Object nextElement()>");
	}
	
	/**
	 * @param var
	 * @param arg
	 * @param body
	 * @return
	 */
	public static Collection codeForAssig(Value var, Local arg) {
			Vector code = new Vector();
			AssignStmt ass = Jimple.v().newAssignStmt(arg, var);
			code.add(ass);
			return code;
		}

	protected static Collection codeForGetArraySize(Value var, Local arg, Body body) {
		Vector code = new Vector();
			
		/*
		Scene.v().loadClassAndSupport("VarTest");
		SootMethod toCall = Scene.v().getSootClass("VarTest").getMethod(
				"int sizeArray(java.lang.Object[])");
		StaticInvokeExpr invExpr = Jimple.v().newStaticInvokeExpr(toCall, var);
		AssignStmt ass = Jimple.v().newAssignStmt(arg, invExpr);
		*/
		// System.out.println("arg="+arg+" "+ass.toString());
	
		// AssignStmt
		AssignStmt ass=Jimple.v().newAssignStmt(arg,Jimple.v().newLengthExpr(var));
		NeExpr cond1 = Jimple.v().newNeExpr(var,NullConstant.v());
		IfStmt toAdd1 = Jimple.v().newIfStmt(cond1,ass);
		AssignStmt ass0 = Jimple.v().newAssignStmt(arg, IntConstant.v(0));
		GotoStmt jump = Jimple.v().newGotoStmt(ass0);
		
		code.add(toAdd1);
		code.add(ass0);
		code.add(jump);
		code.add(ass);
		
		jump.addTag(new AdjustGotoTag(Integer.toString(1)));
		toAdd1.addTag(new AdjustGotoTag(Integer.toString(2)));
		
		return code;
	
	}

	protected static Collection codeForGetCollectionSize(Value var, Local arg,
			Body body) {
		Vector code = new Vector();
		
		SootMethod toCall = varTestClass.getMethod(
				"int sizeCollection(java.util.Collection)");
		StaticInvokeExpr invExpr = Jimple.v().newStaticInvokeExpr(toCall.makeRef(), var);
		AssignStmt ass = Jimple.v().newAssignStmt(arg, invExpr);
	
		code.add(ass);
		return code;
	
	}
	
	protected static Collection codeForDouble(Value var, Local arg,
			Body body) {
		Vector code = new Vector();
		if(var.getType().toString().equals("double"))
		{
				
			SootMethod toCall = varTestClass.getMethod("int toInt(double)");
			StaticInvokeExpr invExpr = Jimple.v().newStaticInvokeExpr(toCall.makeRef(), var);
			AssignStmt ass = Jimple.v().newAssignStmt(arg, invExpr);
		
			code.add(ass);
		}
		else if(var.getType().toString().equals("long"))
		{
			
		SootMethod toCall = varTestClass.getMethod("int toInt(long)");
		StaticInvokeExpr invExpr = Jimple.v().newStaticInvokeExpr(toCall.makeRef(), var);
		AssignStmt ass = Jimple.v().newAssignStmt(arg, invExpr);
	
		code.add(ass);
	}
		return code;
	
	}


	protected static Collection codeForGetStringSize(Value var, Local arg, Body body) {
		Vector code = new Vector();
		
		SootMethod toCall = varTestClass.getMethod("int sizeString(java.lang.String)");
		//SootMethod toCall = Scene.v().getSootClass("VarTest").getMethod(
		//		"int sizeString(java.lang.String)");
		
		StaticInvokeExpr invExpr = Jimple.v().newStaticInvokeExpr(toCall.makeRef(), var);
		
		AssignStmt ass = Jimple.v().newAssignStmt(arg, invExpr);
		code.add(ass);
		return code;
	
	}
	
	protected static Local addLocal(List l, Local v) {
		
		Local arg = getLocalForName(l, v.getName());
		if(arg==null)
		{
			l.add(v);
		}
		return arg;
	}
	public static void  addAllLocal(List l, Collection l2)
	{
		for(Iterator it = l2.iterator(); it.hasNext();)
		{
			Local v = (Local) it.next();
			addLocal(l,v);
		}
	}
	public static void addString(List l, String v) {
		
		if(!l.contains(v))
		{
			l.add(v);
		}
		return;
	}
	public static void  addAllString(List l, Collection l2)
	{
		for(Iterator it = l2.iterator(); it.hasNext();)
		{
			String v = (String) it.next();
			addString(l,v);
		}
	}
	
	protected  static Local getLocalForName(Chain locals, String name) {
		boolean found = false;
		Local var = null;
		for (Iterator it = locals.iterator(); it.hasNext() && !found;) {
			var = (Local) it.next();
			found = var.getName().equals(name);
		}
		if(found)
			return var;
		else
			return null;
	}
	protected static Local getLocalForName(List locals, String name) {
		boolean found = false;
		Local var = null;
		for (Iterator it = locals.iterator(); it.hasNext() && !found;) {
			var = (Local) it.next();
			found = var.getName().equals(name);
		}
		if(found)
			return var;
		else
			return null;
	}
	protected static Local addLocalToBody(Body body, String varName, Type t) {
		
		Local arg = getLocalForName(body.getLocals(), varName);
		if(arg==null)
		{
			arg = Jimple.v().newLocal(varName,t);
			body.getLocals().add(arg);
		}
		return arg;

	}
	public static boolean containsLocalsForName(Chain locals, String name) {
		boolean found = false;
		for (Iterator it = locals.iterator(); it.hasNext() && !found;) {
			Local var = (Local) it.next();
			found = var.getName().equals(name);
		}
		return found;
	}
	public static boolean containsLocalsForName(List locals, String name) {
		boolean found = false;
		for (Iterator it = locals.iterator(); it.hasNext() && !found;) {
			Local var = (Local) it.next();
			found = var.getName().equals(name);
		}
	return found;
	}
	
	protected static Value generateArgument(Local var) {
		Value res = var;
		if (Utils.isSizeable(var))
			res = var;

		if (isNum(var))
			res = var;
		return res;
	}
	
	public static String normalizeMethodName(String methodName, String className)
	{
		return className.replaceAll("\\.","_")+"_"+methodName.replaceAll("<init>","mI");
	}
	
	protected static List filterVariables(List locals, InductiveVariablesInfo IVInfo) {
		
		List  infoFiltrada = new Vector(); 
		for (Iterator itChain = locals.iterator(); itChain.hasNext();) {
			Local var = (Local) itChain.next();
			if ((IVInfo==null || !IVInfo.isInExcludeInfo(var.getName())) )
				infoFiltrada.add(var);
		}
		return infoFiltrada;
	}
	
	protected static List filterVariables(List locals) {
		
		List  infoFiltrada = new Vector(); 
		for (Iterator itChain = locals.iterator(); itChain.hasNext();) {
			Local var = (Local) itChain.next();
			if (!infoFiltrada.contains(var)  )
				infoFiltrada.add(var);
		}
		return infoFiltrada;
	}
	
	public static String getLineNumber(Stmt s) {
		String sLn = "", sColumn="";
		List tags = s.getTags();
		// System.out.println("Tags de:"+s.toString()+" "+tags.toString());

		int ln = -1;
		int column = -1;
		int bytecode = -1;
		for (Iterator it = tags.iterator(); it.hasNext() /*&& (ln == -1 || column==-1)*/;) {
			Tag tag = (Tag) it.next();
			// System.out.println("Tipo: "+tag.getClass());

			if (tag instanceof LineNumberTag ) {
				byte[] value = tag.getValue();
				ln = ((value[0] & 0xff) << 8) | (value[1] & 0xff);
			} 
			else if (tag instanceof SourceLnPosTag) {
				ln = ((SourceLnPosTag) tag).startLn();
				column = ((SourceLnPosTag) tag).startPos();
			}
			else if (tag instanceof SourceLineNumberTag) {
				ln = ((SourceLineNumberTag) tag).getLineNumber();
				
			}
			else if (tag instanceof PositionTag ) {
				column = ((PositionTag)tag).getStartOffset();
			}
			
			if(tag instanceof BytecodeOffsetTag)
			{
				byte[] value = tag.getValue();
				// bytecode = Integer.parseInt(tag.toString());
				BytecodeOffsetTag bcTag = (BytecodeOffsetTag)tag;
				bytecode = bcTag.getBytecodeOffset();
			}

		}
		if(ln!=-1)
		{
			sLn = pad(Integer.toString(ln),5,'0');
		}
		//if(column!=-1)
		//	sLn +="c"+Integer.toString(column);
		// else 
		if(bytecode!=-1)
			sLn +="c"+pad(Integer.toString(bytecode),5,'0');
		// System.out.println(ln+" "+column+" "+bytecode+"="+sLn);
		return sLn;

	}
	public static String pad(String s, int len, char c)
	{
		String res="";
		for(int i=0;i<len-s.length();i++)
			res=res+c;
		return res+s;
	}
	
	
	public static List parseList(String line)
	{
		Vector v = new Vector();
		line = line.substring(1,line.length()-1);
	
		String[] ls = line.split(",");
		for(int i=0;i<ls.length;i++)
		{
			v.add(ls[i].trim());
		}
		return v;
	}
	public static int countChar(String s, char ch )
	{
		int count = 0;
		for(int i=0;i<s.length();i++)
		{
			if(s.charAt(i)==ch)
				count++;
		}
		return count;
	}
	public static String enclosedString(String exp)
	{
		return "";
	}


}
