/*
 * Created on 06/01/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameter;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Array;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Byte;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Collection;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Double;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Int;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Integer;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Long;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Object;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_String;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_ValueNull;
import ar.uba.dc.util.List;
import soot.Body;
import soot.Local;
import soot.Scene;
import soot.SootClass;
import soot.Type;
import soot.Value;

/**
 * @author Diego
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DIParameterFactory {

	/**
	 * 
	 */
	
	//En realidad aca esta la papota
	public static DIParameter createDIParameter(Local var, Collection filter, Body body, boolean recursive)
	{
		if(isCollection(var))
			return new DI_Collection(var);
		else if(isString(var))
			return new DI_String(var);
		else if(isArray(var))
			return new DI_Array(var);
		else if(isDouble(var))
			return new DI_Double(var);
		else if(isLong(var))
			return new DI_Long(var);
		else if(isByte(var))
			return new DI_Byte(var);
		else if(isNum(var))
			return new DI_Int(var);
		else if(isInteger(var))
			return new DI_Integer(var);
//		else if(isIterator(var))
//			return new DI_Iterator(var);
		else if(isObject(var) && recursive)
		{
			SootClass c = Scene.v().getSootClass(var.getType().toString());
			return new DI_Object(var, c, body, filter);
		}
		
		return null;
	}
	
	public static DIParameter createDIParameter2(Local var, Collection filter, Body body, boolean recursive)
	{
		if(isCollection(var))
			return new DI_Collection(var, true);
		else if(isString(var))
			return new DI_String(var);
		else if(isArray(var))
			return new DI_Array(var);
		else if(isDouble(var))
			return new DI_Double(var);
		else if(isLong(var))
			return new DI_Long(var);
		else if(isByte(var))
			return new DI_Byte(var);
		else if(isNum(var))
			return new DI_Int(var);
		else if(isInteger(var))
			return new DI_Integer(var);
//		else if(isIterator(var))
//			return new DI_Iterator(var);
		else if(isObject(var) && recursive)
		{
			SootClass c = Scene.v().getSootClass(var.getType().toString());
			return new DI_Object(var, c, body, filter, true);
		}
		
		return null;
	}
	
	public static DIParameter createDIParameter(String vn, Type t, Collection filter, Body body, boolean recursive)
	{
		if(isTypeCollection(t))
			return new DI_Collection(vn, t);
		else if(isTypeString(t))
			return new DI_String(vn, t);
		else if(isTypeArray(t))
			return new DI_Array(vn, t);
		else if(t.toString().equals("double"))
			return new DI_Double(vn);
		else if(t.toString().equals("long"))
			return new DI_Long(vn);
		else if(t.toString().equals("byte"))
			return new DI_Byte(vn);
		else if(t.toString().equals("int"))
			return new DI_Int(vn);
		else if(t.toString().equals("java.lang.Integer"))
			return new DI_Integer(vn, t);
		else if(isTypeObject(t) && recursive)
		{
			SootClass c = Scene.v().getSootClass(t.toString());
			return new DI_Object(vn,t,c,  body, filter);
		}
		
		return null;
		
	}
	
	
	public static DIParameter createDIParameter(Local var, Body body, boolean recursive)
	{
		if(isCollection(var))
			return new DI_Collection(var);
		else if(isString(var))
			return new DI_String(var);
		else if(isArray(var))
			return new DI_Array(var);
		else if(isDouble(var))
			return new DI_Double(var);
		else if(isLong(var))
			return new DI_Long(var);
		else if(isByte(var))
			return new DI_Byte(var);
		else if(isNum(var))
			return new DI_Int(var);
		else if(isInteger(var))
			return new DI_Integer(var);
//		else if(isIterator(var))
//			return new DI_Iterator(var);
		else if(isObject(var) && recursive)
		{
			SootClass c = Scene.v().getSootClass(var.getType().toString());
			return new DI_Object(var, c, body);
		}
		
		return null;
	}
	public static DIParameter createDIParameter(String vn, Type t, Body body, boolean recursive)
	{
		if(isTypeCollection(t))
			return new DI_Collection(vn, t);
		else if(isTypeString(t))
			return new DI_String(vn, t);
		else if(isTypeArray(t))
			return new DI_Array(vn, t);
		else if(t.toString().equals("double"))
			return new DI_Double(vn);
		else if(t.toString().equals("long"))
			return new DI_Long(vn);
		else if(t.toString().equals("byte"))
			return new DI_Byte(vn);
		else if(t.toString().equals("int"))
			return new DI_Int(vn);
		else if(t.toString().equals("java.lang.Integer"))
			return new DI_Integer(vn, t);
		else if(isTypeObject(t) && recursive)
		{
			SootClass c = Scene.v().getSootClass(t.toString());
			return new DI_Object(vn,t,c,  body);
		}
		
		return null;
		
	}
	
	public static DIParameter createDIParameter(Local var)
	{
		if(isCollection(var))
			return new DI_Collection(var);
		else if(isString(var))
			return new DI_String(var);
		else if(isArray(var))
			return new DI_Array(var);
		else if(isDouble(var))
			return new DI_Double(var);
		else if(isLong(var))
			return new DI_Long(var);
		else if(isByte(var))
			return new DI_Byte(var);
		else if(isNum(var))
			return new DI_Int(var);
		else if(isInteger(var))
			return new DI_Integer(var);
		
		return null;
	}
	public static DIParameter createDIParameter(String vn, Type t)
	{
		if(isTypeCollection(t))
			return new DI_Collection(vn, t);
		else if(isTypeString(t))
			return new DI_String(vn, t);
		else if(isTypeArray(t))
			return new DI_Array(vn, t);
		else if(t.toString().equals("double"))
			return new DI_Double(vn);
		else if(t.toString().equals("long"))
			return new DI_Long(vn);
		else if(t.toString().equals("byte"))
			return new DI_Byte(vn);
		else if(t.toString().equals("int"))
			return new DI_Int(vn);
		else if(t.toString().equals("java.lang.Integer"))
			return new DI_Integer(vn, t);
		else if(t.toString().equals("null_type"))
			return DI_ValueNull.nullLocal;
		
		return null;
	}
	
	
	
	protected static boolean isArray(Value var) {
		return isTypeArray(var.getType());
	}

	protected static boolean isCollection(Value var) {
		return isTypeCollection(var.getType());
	}
	
	protected static boolean isObject(Value var) {
		return isTypeObject(var.getType());
	}
	protected static boolean isSizeable(Value v)
	{
		return isCollection(v) || isString(v) || isArray(v);
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
	protected static boolean isTypeString(Type t) {
		boolean ok = false;
		if (t.toString().endsWith(".String"))
			ok = true;
		return ok;
	}
	protected static boolean isDouble(Value v)
	{
		return v.getType().toString().equals("double") ;
		
	}
	protected static boolean isLong(Value v)
	{
		return	v.getType().toString().equals("long");
		
	}
	protected static boolean isByte(Value v)
	{
		return	v.getType().toString().equals("byte");
	}
	protected static boolean isNum(Value v)
	{
		return isTypeNum(v.getType().toString());
	}
	protected static boolean isInteger(Value v)
	{
		return isTypeInteger(v.getType().toString());
	}
	protected static boolean isTypeNum(String t)
	{
		return t.equals("int"); /*|| t.equals("long")*/
	}
	protected static boolean isTypeInteger(String t)
	{
		return t.equals("java.lang.Integer");
	}
	public static boolean isIterator(Value var)
	{
		return isTypeIterator(var.getType());
	}
	protected static boolean isTypeIterator(Type t) {
		return t.toString().equals("java.util.Iterator");
	}
	
	//importable = that can (and needs to) be imported for it to be used
	//return null when it's local or non importable
	//TODO: podria haber "mas de un" importable, por ejemplo con un map
	public static ArrayList<String> getImportables(String classname)
	{
		ArrayList<String> imp = new ArrayList<String>();
		if(isTypeInteger(classname) || classname.endsWith(".String") || classname.equals("double") || isTypeNum(classname))
			return imp;

		if(classname.contains("[]"))
		{
			classname = classname.substring(0,classname.indexOf("[]"));
			imp.addAll(getImportables(classname));
			return imp;
		}
		
		if(classname.contains("<") && classname.contains(">"))
		{
			String s = StringUtils.substringBetween(classname, "<", ">");
			
			String[] classnames = s.split(",");
			for(String c : classnames)
			{
				imp.addAll(getImportables(c));
			}
			return imp;
		}
		
		imp.add(classname);
		return imp;
	}



}
