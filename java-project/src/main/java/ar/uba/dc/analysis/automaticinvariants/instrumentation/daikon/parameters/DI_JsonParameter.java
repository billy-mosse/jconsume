/*
 * Created on 06/01/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.Utils;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_Sizeable;
import soot.Local;
import soot.Type;


import soot.Body;
import soot.Local;
import soot.SootMethod;
import soot.Type;
import soot.jimple.AssignStmt;
import soot.jimple.Jimple;
import soot.jimple.StaticInvokeExpr;

/**
 * @author Diego
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DI_JsonParameter extends DIParameter {

	// es un objeto recursivo para representar objetos con fields.
	// Si, por ejemplo, a es un DI_JsonParameter con field b, y b tiene field c, y c no tiene fields,
	//  entonces el relevant parameter ENTERIZADO es a.b.c
	//
	
	//public List<DI_JsonParameter> derivedVariablesForSpec;
	
	public DI_JsonParameter()
	{
		this.derivedVarsForSpec = new ListDIParameters();
	}
	public List<DI_JsonParameter> getDerivedVarsForSpec() {
		return derivedVarsForSpec;
	}
	public void setDerivedVarsForSpec(ListDIParameters fields) {
		this.derivedVarsForSpec = fields;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String name;
	
	public DI_JsonParameter clone()
	{
		DI_JsonParameter clone = new DI_JsonParameter();
		clone.setName(this.name);
		clone.setDerivedVarsForSpec(this.derivedVarsForSpec);
		return clone;
	}
	
	@Override
	public void addToBody(Body body)
	{
		//no nos importa hacer nada con metodos no analizables
	}

}
