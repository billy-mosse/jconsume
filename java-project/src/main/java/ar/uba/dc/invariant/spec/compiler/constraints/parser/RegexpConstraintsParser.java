package ar.uba.dc.invariant.spec.compiler.constraints.parser;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.nfunk.jep.JEP;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.Not;

import ar.uba.dc.invariant.spec.bean.CallSiteSpecification;
import ar.uba.dc.invariant.spec.bean.SiteSpecification;
import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsInfo;
import ar.uba.dc.invariant.spec.compiler.constraints.ConstraintsParser;
import ar.uba.dc.invariant.spec.compiler.constraints.ReferenceResolver;

public class RegexpConstraintsParser implements ConstraintsParser {

	public static final String INCLUDE_INVARIANT_FUNCTION = "_at";
	
	private JEP parser;
	
	private ReferenceResolver resolver;
	
	public RegexpConstraintsParser() {
		this.parser = new JEP();
		this.parser.setAllowUndeclared(true);
		//this.parser.setImplicitMul(true);
		this.parser.addFunction(INCLUDE_INVARIANT_FUNCTION, new Not());
	}
	
	
	public void parse(SiteSpecification site, ConstraintsInfo info, Set<String> parameters, Set<DerivedVariable> new_parameters) {
		if (site.getClass().equals(CallSiteSpecification.class))
		{
			CallSiteSpecification s = (CallSiteSpecification) site;
			System.out.println(s.getBinding());
		}
		Set<String> variables = info.getVariables();
		Set<String> inductives = info.getInductives();
		String constraints = site.getConstraints();
		
		String constraintToParse = new String(constraints).trim();
		Pattern pattern = Pattern.compile("\\B@(\\S+)\\b");
		Matcher match = pattern.matcher(constraints);
		
		while (match.find()) {
			String ref = match.group(1);
			info.addReference(ref);
			constraintToParse = resolver.resolve(constraintToParse, ref, INCLUDE_INVARIANT_FUNCTION + "(\"" + ref + "\")");
		}
		
		try {
			constraintToParse = constraintToParse.replaceAll("(?i) and ", " && ");
			constraintToParse = constraintToParse.replaceAll("(?i) or ", " || ");
			
			//TODO: se va a romper que haya ORs
			String[] s = constraintToParse.split("&&");
			
			String[] constraints_array = constraints.split("&&");
			HashSet<String> new_constraints = new HashSet<String>();
			
			parser.initSymTab();

			System.out.println(constraintToParse);
			for(int i = 0; i < s.length; i++)
			{ 
				parser.initSymTab();
				String t = s[i].trim();
				System.out.println(t);
				parser.parse(t);
				//boolean consistent = true;

				for (Object var : parser.getSymbolTable().keySet()) {
					variables.add(var.toString());
				}
				
				new_constraints.add(constraints_array[i].trim());
			}

			variables.addAll(inductives);
			
			Set<DerivedVariable> derivedVariables = new HashSet<DerivedVariable>();
			for(String var : variables)
			{

				if(!var.contains("cont_") && !var.contains("_init"))
				{
					DerivedVariable dv = null;
					boolean b = false;
					if(var.contains("size_"))
					{
						String name = var.substring(5);
						dv = new DerivedVariable("size", name);
						new_constraints.add(dv.toString() + ">= 0");
					}
					else
					{
						if(var.contains("__f__"))
						{
							String name = var.substring(0, var.indexOf("__f__"));
							String field = var.substring(var.indexOf("__f__") + 5);
							dv = new DerivedVariable(field, name);
						}
					}
					if(dv!=null)
					{
						derivedVariables.add(dv);
						variables.add(dv.toString());
						
					}	
				}
			}
			
			for(int i = 0 ; i < constraints_array.length; i++)
			{
				String constraint = constraints_array[i];
				String[] constraint_pair = constraint.split("==");
				if(constraint_pair.length == 2)
				{
					String c0 = constraint_pair[0].trim();
					String c1 = constraint_pair[1].trim();
					
					//hack, deberia hacer un mejor chequeo para que el contains() no se rompa mas adelante
					if(!StringUtils.isNumeric(c0) && !StringUtils.isNumeric(c1))
					{							
						for(DerivedVariable dv: derivedVariables)
						{
							String name = dv.getName();
							String field = dv.getField();
							if (name.equals(c0))
							{
								
								
								//String field = s.replace(constraint_pair[0], "");
								DerivedVariable dv2 = new DerivedVariable(field, c1);
								variables.add(dv2.toString());
								

								if(parameters.contains(c1))
									new_parameters.add(dv2);
								
								String constraint2 = dv.toString() + " == " + dv2.toString();							
								new_constraints.add(constraint2);
								
								//si una variable es igual a un size no me agrega el >= 0 porque se deriva de que es un size!!
								/*if(field.equals("size"))
								{
									String another_constraint = c1 + ">= 0";
									new_constraints.add(another_constraint);
								}*/
								
								
								break;
							}
							if (name.equals(c1))
							{
								DerivedVariable dv2 = new DerivedVariable(field, c0);
								variables.add(dv2.toString());
								
								if(parameters.contains(c0))
									new_parameters.add(dv2);
								
								
								String constraint2 = dv.toString() + " == " + dv2.toString();		
								new_constraints.add(constraint2);
								
								/*if(field.equals("size"))
								{
									String another_constraint = c0 + ">= 0";
									new_constraints.add(another_constraint);
								}*/
								
								break;
							}
						
						}
					}
				}
			}
			
			variables.removeAll(new_parameters);
			constraints = StringUtils.join(new_constraints, " and ");
			site.setConstraints(constraints);
			
				
		}catch (ParseException e) {
			throw new RuntimeException("Problemas al parsear la constraint [" + constraintToParse + "]: " + e.getMessage(), e);
		}
	}
	
	public void parse_NOT_USED(SiteSpecification site, ConstraintsInfo info) {
		Set<String> variables = info.getVariables();
		String constraints = site.getConstraints();
		
		String constraintToParse = new String(constraints).trim();
		Pattern pattern = Pattern.compile("\\B@(\\S+)\\b");
		Matcher match = pattern.matcher(constraints);
		
		while (match.find()) {
			String ref = match.group(1);
			info.addReference(ref);
			constraintToParse = resolver.resolve(constraintToParse, ref, INCLUDE_INVARIANT_FUNCTION + "(\"" + ref + "\")");
		}
		
		try {
			constraintToParse = constraintToParse.replaceAll("(?i) and ", " && ");
			constraintToParse = constraintToParse.replaceAll("(?i) or ", " || ");
			
			//TODO: se va a romper que haya ORs
			String[] s = constraintToParse.split("&&");
			
			String[] constraints_array = constraints.split("&&");
			HashSet<String> new_constraints = new HashSet<String>();
			
			
			
			parser.initSymTab();

			System.out.println(constraintToParse);
			for(int i = 0; i < s.length; i++)
			{ 
				System.out.println("HOLA_1");
				parser.initSymTab();
				String t = s[i].trim();
				System.out.println(t);
				parser.parse(t);
				boolean consistent = true;
				System.out.println("HOLA_2");

				for (Object var : parser.getSymbolTable().keySet()) {
					System.out.println(var);
					//no quiero agregar las variables que veo en las constraints!
					if (!variables.contains(var.toString()))
					{
						consistent = false;
					}
					
					info.addConstraintVariable(var.toString());
				}
				if(consistent)
				{
					new_constraints.add(constraints_array[i].trim());
				}
			}
			//TODO: convendria cambiar "_" por "__" cuando pongo un size porque puede pasar que
			//una variable ya venga con "_" en el nombre
			System.out.println("aca");
			Set<DerivedVariable> derivedVariables = new HashSet<DerivedVariable>();
			for(String var : variables)
			{

				if(!var.contains("cont_") && !var.contains("_init"))
				{
					DerivedVariable dv = null;
					boolean b = false;
					if(var.contains("size_"))
					{
						String name = var.substring(5);
						dv = new DerivedVariable("size", name);
						new_constraints.add(dv.toString() + ">= 0");
					}
					else
					{
						if(var.contains("__f__") )
						{
							String name = var.substring(0, var.indexOf("__f__"));
							String field = var.substring(var.indexOf("__f__") + 5);
							dv = new DerivedVariable(field, name);
						}
					}
					if(dv!=null)
						derivedVariables.add(dv);
				}
			}
			
			
			//ArrayList<ConstraintPair> equal_objects = new ArrayList<ConstraintPair>();
			for(int i = 0 ; i < constraints_array.length; i++)
			{
				String constraint = constraints_array[i];
				String[] constraint_pair = constraint.split("==");
				if(constraint_pair.length == 2)
				{
					String c0 = constraint_pair[0].trim();
					String c1 = constraint_pair[1].trim();
					
					//hack, deberia hacer un mejor chequeo para que el contains() no se rompa mas adelante
					if(!StringUtils.isNumeric(c0) && !StringUtils.isNumeric(c1))
					{							
						for(DerivedVariable dv: derivedVariables)
						{
							String name = dv.getName();
							String field = dv.getField();
							if (name.equals(c0))
							{
								//String field = s.replace(constraint_pair[0], "");
								DerivedVariable dv2 = new DerivedVariable(field, c1);
								String constraint2 = dv.toString() + " == " + dv2.toString();							
								new_constraints.add(constraint2);
								
								//si una variable es igual a un size no me agrega el >= 0 porque se deriva de que es un size!!
								/*if(field.equals("size"))
								{
									String another_constraint = c1 + ">= 0";
									new_constraints.add(another_constraint);
								}*/
								
								
								break;
							}
							if (name.equals(c1))
							{
								DerivedVariable dv2 = new DerivedVariable(field, c0);
								String constraint2 = dv.toString() + " == " + dv2.toString();		
								new_constraints.add(constraint2);
								
								/*if(field.equals("size"))
								{
									String another_constraint = c0 + ">= 0";
									new_constraints.add(another_constraint);
								}*/
								
								break;
							}
						
						}
					}
				}
			}
			
			
			constraints = StringUtils.join(new_constraints, " and ");
			site.setConstraints(constraints);
		} catch (ParseException e) {
			throw new RuntimeException("Problemas al parsear la constraint [" + constraintToParse + "]: " + e.getMessage(), e);
		}
	}
	
	public ConstraintsInfo parse(String constraints) {
		ConstraintsInfo info = new ConstraintsInfo();
		
		String constraintToParse = new String(constraints);
		Pattern pattern = Pattern.compile("\\B@(\\S+)\\b");
		Matcher match = pattern.matcher(constraints);
		
		while (match.find()) {
			String ref = match.group(1);
			info.addReference(ref);
			constraintToParse = resolver.resolve(constraintToParse, ref, INCLUDE_INVARIANT_FUNCTION + "(\"" + ref + "\")");
		}
		
		try {
			constraintToParse = constraintToParse.replaceAll("(?i) and ", " && ");
			constraintToParse = constraintToParse.replaceAll("(?i) or ", " || ");
			
			parser.initSymTab();
			
			parser.parse(constraintToParse);
			
			for (Object var : parser.getSymbolTable().keySet()) {
				//no quiero agregar las variables que veo en las constraints!
				info.addConstraintVariable(var.toString());
			}
		} catch (ParseException e) {
			throw new RuntimeException("Problemas al parsear la constraint [" + constraintToParse + "]: " + e.getMessage(), e);
		}
				
		return info;
	}

	public void setResolver(ReferenceResolver resolver) {
		this.resolver = resolver;
	}
}
