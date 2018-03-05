/*
 * Created on 06/01/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.Utils;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameter;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Field;
import soot.Body;
import soot.Local;
import soot.SootClass;
import soot.SootField;
import soot.Type;
import soot.jimple.FieldRef;
import soot.jimple.Jimple;
import soot.util.Chain;
import soot.util.HashChain;

/**
 * @author Diego
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DI_Object extends DIParameter {

	/**
	 * @param var
	 */
	Local thisRef;
	SootClass c;
	Collection analyzedfields = new Vector();
	boolean hasFields = false;
	public DI_Object(Local var,  SootClass c, Body body) {
		super(var);
		thisRef = var;
		this.c = c;
		generateFieldReferences(c,body,thisRef);
		//System.out.print("CHAPA:"+var+" "+c+" "+derivedVars);
		
		
		//Hasta que entienda que tiene que pasar primero por los threads.
		/*try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param vn
	 * @param type
	 */
	public DI_Object(String vn,  Type type, SootClass c, Body body) {
		super(vn, type);
		thisRef = var;
		this.c = c;
		generateFieldReferences(c,body,thisRef);
		
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param vn
	 * @param type
	 */
	public DI_Object(String vn,  Type type, SootClass c, Body body, Collection filter) {
		super(vn, type);
		thisRef = var;
		this.c = c;
		generateFilteredFieldReferences(c,body,thisRef,filter);
		
		// TODO Auto-generated constructor stub
	}
	
	
	public DI_Object(Local var,  SootClass c, Body body, Collection filter) {
		super(var);
		thisRef = var;
		this.c = c;
		generateFilteredFieldReferences(c,body,thisRef,filter);
		//System.out.print("CHAPA:"+var+" "+c+" "+derivedVars);
		// TODO Auto-generated constructor stub
	}
	
	public DI_Object(Local var,  SootClass c, Body body, Collection filter, boolean _) {
		super(var);
		thisRef = var;
		this.c = c;
		generateFilteredFieldReferences2(c,body,thisRef,filter);
		//System.out.print("CHAPA:"+var+" "+c+" "+derivedVars);
		// TODO Auto-generated constructor stub
	}
	
	private void generateFilteredFieldReferences(SootClass c, Body body, Local thisRef, Collection filter) {
		
		//System.out.println("DiegoClass:"+c.getName()+" "+fields.size()+" "+fields);
		//	para cada campo que es int, collection, array o String habria que
		// 	generar una variable nueva, con el codigo que obtiene el size y usarlo de
		// argumento
		derivedVarsForSpec.clear();
		for (Iterator itChain = filter.iterator(); itChain.hasNext();) {

			// Value v= (Value)itChain.next();
			
			// if(v instanceof FieldRef)
			{
				// FieldRef fieldRef = (FieldRef)v;
				SootField f = (SootField)itChain.next();
				// SootField f = fieldRef.getField();
				String thisRefName;
				FieldRef var;
				
				if(!f.isPrivate() || true)
				{
					if(!f.isStatic()  && thisRef!=null) //Lo agregue yo el isPrivate. Billy
					{
						var = Jimple.v().newInstanceFieldRef(thisRef, f.makeRef());
						thisRefName = thisRef.getName();
					}
					else
					{
						var = Jimple.v().newStaticFieldRef(f.makeRef());
						thisRefName = c.getName().replaceAll("\\.","_");
					}
					
					//saque el && false
					if(Utils.isPossibleArgument(var))
					{
						hasFields = true; 
						DI_Field fieldParam = new DI_Field(f, thisRef,null, body);
						derivedVarsForSpec.add(fieldParam);
						fieldParam.setFather(this);
						if(!analyzedfields.contains(f))
							analyzedfields.add(f);
					}
				}
				
			}
		}
		return;
	}
	
	
private void generateFilteredFieldReferences2(SootClass c, Body body, Local thisRef, Collection filter) {
		
		//System.out.println("DiegoClass:"+c.getName()+" "+fields.size()+" "+fields);
		//	para cada campo que es int, collection, array o String habria que
		// 	generar una variable nueva, con el codigo que obtiene el size y usarlo de
		// argumento
	derivedVarsForSpec.clear();
		for (Iterator itChain = filter.iterator(); itChain.hasNext();) {

			// Value v= (Value)itChain.next();
			
			// if(v instanceof FieldRef)
			{
				// FieldRef fieldRef = (FieldRef)v;
				SootField f = (SootField)itChain.next();
				// SootField f = fieldRef.getField();
				String thisRefName;
				FieldRef var;
				
				if(!f.isPrivate())
				{
					if(!f.isStatic()  && thisRef!=null) //Lo agregue yo el isPrivate. Billy
					{
						var = Jimple.v().newInstanceFieldRef(thisRef, f.makeRef());
						thisRefName = thisRef.getName();
					}
					else
					{
						var = Jimple.v().newStaticFieldRef(f.makeRef());
						thisRefName = c.getName().replaceAll("\\.","_");
					}
					
					//saque el && false
					if(Utils.isPossibleArgument(var))
					{
						hasFields = true; 
						DI_Field fieldParam = new DI_Field(f, thisRef,null, body);
						derivedVarsForSpec.add(fieldParam);
						fieldParam.setFather(this);
						if(!analyzedfields.contains(f))
							analyzedfields.add(f);
					}
				}
				
			}
		}
		return;
	}
	
	
	private void generateFieldReferences(SootClass c, Body body, Local thisRef) {
		Chain fields= new HashChain();
		
//		if(true)
//			return;
		
		List cadenaClasses = new Vector();
		if(c.hasSuperclass())
		{
			SootClass superClass = c.getSuperclass();
			while(superClass.hasSuperclass())
			{
				cadenaClasses.add(0,superClass);
				superClass = superClass.getSuperclass();
			}
		}
		for (Iterator iter = cadenaClasses.iterator(); iter.hasNext();) {
			SootClass sc = (SootClass) iter.next();
			fields.addAll(sc.getFields());
			
		}
		// Chain fields = c.getFields();
		fields.addAll(c.getFields());
		//System.out.println("DiegoClass:"+c.getName()+" "+fields.size()+" "+fields);
		//	para cada campo que es int, collection, array o String habria que
		// 	generar una variable nueva, con el codigo que obtiene el size y usarlo de
		// argumento
		derivedVarsForSpec.clear();
		for (Iterator itChain = fields.iterator(); itChain.hasNext();) {
			SootField f = (SootField) itChain.next();
			String thisRefName;
			if(isPossibleField(c,f,body.getMethod().getDeclaringClass()))
			{
				// FieldRef var = Jimple.v().newInstanceFieldRef(thisRef, f);
				FieldRef var;
				if(!f.isStatic()  && thisRef!=null)
				{
					var = Jimple.v().newInstanceFieldRef(thisRef, f.makeRef());
					thisRefName = thisRef.getName();
					
				}
				else
				{
					var = Jimple.v().newStaticFieldRef(f.makeRef());
					thisRefName = c.getName().replaceAll("\\.","_");
				}
				// SootClass cf = Scene.v().getSootClass(var.getType().toString());
				if(Utils.isPossibleArgument(var))
				{
					hasFields = true; 
					SootClass c2 = null;
					// DIParameterFactory.createDIParameter(f.getName(),f.getType(),body, true);
					DI_Field fieldParam = new DI_Field(f, thisRef,null, body);
					derivedVarsForSpec.add(fieldParam);
					fieldParam.setFather(this);
					if(!analyzedfields.contains(f))
						analyzedfields.add(f);
				}
				
			}
		}
		return;
	}
	public static boolean isPossibleField(SootClass fieldClass,SootField f, SootClass methodClass )
	{
		boolean ok = false;
		if(f.isPublic())
			ok=true;
		else 
		{
			if(/*fieldClass.isProtected() || fieldClass.isPublic() || */fieldClass.equals(methodClass) )
				ok = true;
			else
				if(fieldClass.getPackageName().equals(methodClass.getPackageName()))
					ok=true;
				// methodClass.getPackageName().equals(fieldClass.getPackageName()
			
		}
		
		return ok && !f.isFinal();
	}
	

	/* (non-Javadoc)
	 * @see instrumentation.daikon.parameters.DIParameter#codeForVar()
	 */
	@Override
	public List codeForVar() {
		List fields = derivedVars;
		List code = new Vector();
		for (Iterator itChain = fields.iterator(); itChain.hasNext();) {
			DI_Field dip = (DI_Field)itChain.next();
			code.addAll(dip.codeForVar());
		}
		return code;

	}
	
	/* (non-Javadoc)
	 * @see instrumentation.daikon.parameters.DIParameter#hasDerivedVariables()
	 */
	@Override
	public boolean hasDerivedVariables() {
		
		//Por ahora no enterizamos objetos
		//capaz haga falta solo para los parametros
		
		
		//A ver volvamos a entender esto
		//hay dos conjuntos
		//este, que deberia estar vacio y es un hack, para InstrumentedMethod, porque le estamos pasando objetos
		//Y derivedVarsForSpec, que tiene los fields para escribir las cosas
		return !derivedVars.isEmpty();
	}
	
	@Override
	public boolean hasDerivedVariables2() {
		
		//Por ahora no enterizamos objetos
		//capaz haga falta solo para los parametros
		return !derivedVarsForSpec.isEmpty();
	}
	/**
	 * @return Returns the fields.
	 */
	public Collection getFields() {
		return analyzedfields;
	}
}
