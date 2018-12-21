package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.invariantwriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstraintUtils {

	public static String adaptOneConstraint(String s, Set objectVars)
	{
		//TODO revisar que no haya invariantes con ")" que no provengan de size!
		//Tal vez si si la relacion es lineal pero con cuentas con parentesis??
		
		
		 //supongo que tambien lo podria hacer con regular expressions
		
		//TODO hacer regexp
		
		//s = s.replaceAll("(size)\\((\\w.+)\\)", "$2__f__$1");
		
		
		 s = s.trim();
		 Pattern patt = Pattern.compile("(size)\\(([^)]*)\\)");
		 Matcher m = patt.matcher(s);
		 StringBuffer sb = new StringBuffer();
		 while(m.find())
		 {
			 	String fo = m.group(1);
			 	//System.out.println(fo);
			 	if (objectVars != null)
			 	{
			 		String object = m.group(2).trim();
			 		if(!objectVars.contains(object))
			 			objectVars.add(object);
			 	}
			 		
			 
			 m.appendReplacement(sb, m.group(2) + "__f__" + m.group(1));
		 }
		 m.appendTail(sb); // append the rest of the contents
		 s = sb.toString();
		 
		 s = s.replaceAll("(\\w) == (\\w) *\\(mod (\\d+)\\)", "$1 % $3 == $2 % $3");
		 
		 
		 
		 
		 /**
		  * Si tengo a.b.f, tengo que reemplazar . por __f__
		  * pero ademas quiero agregar "a.b", y "a" a objectVars
		  * 
		  * 
		  * */
		 
		 StringBuilder ss = new StringBuilder();
		 List<String> separators = Arrays.asList("<", "=", ">");
		 
		 //Ejemplo parts = [this.a.f, ==, B.c]
		 String[] parts = s.split(" ");
		 
		 for(int i = 0; i < parts.length; i++)
		 {
			 String part = parts[i];
			 if(i > 0)
				 ss.append(' ');
			
			 if(part.contains(".") && !part.contains("$t."))
			 {
				 StringBuilder object = new StringBuilder();
				 for (int j = 0; j < part.length(); j++)
				 {
					 char c = part.charAt(j);
					 if(c == '.')
					 {
						 ss.append("__f__");
						 
						 if (objectVars == null)
							 objectVars = new HashSet();
						 
						 if(!objectVars.contains(object))
							 objectVars.add(object.toString());
						 
						 //Sigo agregando caracteres
						 //Asi despues de agregar "this", agrego tambien "this.a"
						 //Lo que quiero es que inmediatamente despues de encontrar un ".", agregar todo lo anterior a la lista de objetos
						 object.append(c); 
					 }
					 else
					 {
						 ss.append(c);
						 if(c== ' ' && object.length() > 0)
						 {
							 
							// objectVars.add(object.toString());
							 object = new StringBuilder();
						 }
						 else
						 {
							 if(c!= '<' && c != '>' && c != '=')
							 {
								 
								object.append(c); 
							 }
						 }
					 }
				 }
			 }
			 else
			 {
				 ss.append(part);
			 }
		 }
		 
		 s = ss.toString();
		 
		 /*
		 Pattern patt2 = Pattern.compile("((?<!\\$t))(\\.)");
		 Matcher m2 = patt2.matcher(parts[0]);
		 StringBuffer sb2 = new StringBuffer();
		 while(m2.find())
		 {
			 String fo = m2.group(1);
			 System.out.println(fo);
			 if (objectVars != null)
				 objectVars.add(m2.group(1).trim());
			 
			 m2.appendReplacement(sb2, m2.group(1) + "__f__");
		 }
		 m2.appendTail(sb2); // append the rest of the contents
		 s = sb2.toString();*/
		 
		 
		 
		
		//s = s.replaceAll("(size)\\(([^)]*)\\)", "$2__f__$1");
		//s = s.replaceAll("(?<!\\$t)[.]", "__f__");
		/*
		if(s.contains("size("))
		{
			 String t = s.substring(0, s.indexOf("size("));
			 String var;
			 for(int j=0; j<s.length(); j++) {
			     char c = s.charAt(j);
			     if(c=='+' || c=='-' || c=='*' || c==')' && s.indexOf("size(") < j) {
			    	 StringBuilder str = new StringBuilder(s);
			    	 str.insert(j, "__f__size");
			    	 s = str.toString();
			    	 break;
			     }
			 }					 
			 s= s.replace("size(", "");
			 s = s.replace(")", "");
			 
			 if(t.contains("==") && !(t.contains("+") || t.contains("-")))
			 {
				 t = t.substring(0, t.indexOf("=="));
				 t=t.trim();
				 s = s + " && " + t + ">=0";
			 }
			 
		}*/
		  
		return s;
	}
}
