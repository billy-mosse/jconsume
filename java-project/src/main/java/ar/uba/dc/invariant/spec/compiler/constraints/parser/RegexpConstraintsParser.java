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
	
	
	//TODO sacar lo de la propagacion, que ya lo hago en otro lado...
	public void parse(SiteSpecification site, ConstraintsInfo info, Set<String> parameters, Set<DerivedVariable> new_parameters) {


		Set<String> variables = info.getVariables();
		Set<String> inductives = info.getInductives();
		String constraints = site.getConstraints();
		
		Set<DerivedVariable> derivedVariables = new HashSet<DerivedVariable>();

		
		//agrego los objeto.field que aparecen en el binding para la propagacion tambien
		//en realidad solo tengo que agregar el local!
		if (site.getClass().equals(CallSiteSpecification.class))
		{
			CallSiteSpecification s = (CallSiteSpecification) site;
			String allBindings = s.getBinding();
			if(allBindings.length() > 0)
			{
				for (String binding : allBindings.split(" and ")){
					Pattern pattern = Pattern.compile("\\$t.(\\w+) == (\\w+)");
					Pattern.compile("\\$t.(\\w+) == (\\w+)");
					Matcher match = pattern.matcher(binding);
					while (match.find()) {
						//variables.add(match.group(1));
						variables.add(match.group(2));
					}
				}
			}
			
		}
		
		
		String constraintToParse = new String(constraints).trim();
		Pattern pattern = Pattern.compile("\\B@(\\S+)\\b");
		Matcher match = pattern.matcher(constraints);
		
		while (match.find()) {
			String ref = match.group(1);
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
			
			for(String var : variables)
			{

				if(!var.contains("cont_") && !var.contains("_init"))
				{
					DerivedVariable dv = null;
					boolean b = false;
					
					//esto ya es obsoleto porque los size_ los cambiamos por __f__size
					if(var.contains("size_"))
					{
						String name = var.substring(5);
						dv = new DerivedVariable("size", name);
						
						String new_constraint = dv.toString() + ">= 0";
						if(!new_constraints.contains(new_constraint))
							new_constraints.add(new_constraint);
					}
					else
					{
						if(var.contains("__f__"))
						{
							String name = var.substring(0, var.indexOf("__f__"));
							String field = var.substring(var.indexOf("__f__") + 5);
							dv = new DerivedVariable(field, name);
							
							if(field.equals("size"))
							{
								String new_constraint = dv.toString() + ">= 0";
								if(!new_constraints.contains(new_constraint))
									new_constraints.add(new_constraint);
							}
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
