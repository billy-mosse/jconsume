package ar.uba.dc.barvinok.syntax;

import org.apache.commons.lang.StringUtils;

import ar.uba.dc.barvinok.BarvinokSyntax;

public class SyntaxUtils {

	public static boolean containsExists(String constraints) {
		return StringUtils.startsWith(constraints, BarvinokSyntax.EXISTS + " ")
				|| StringUtils.startsWith(constraints, "(" + BarvinokSyntax.EXISTS + " ")
				|| StringUtils.startsWith(constraints, "(" + BarvinokSyntax.EXISTS + "(")
				|| StringUtils.startsWith(constraints, BarvinokSyntax.EXISTS + "(");
	}

	
	public static ExistsInfo retriveExistsInformation(String constraints) {
		if (StringUtils.startsWith(constraints, "(" + BarvinokSyntax.EXISTS + " ")
				|| StringUtils.startsWith(constraints, "(" + BarvinokSyntax.EXISTS + "(")) {
			constraints = stripPart(constraints, "(", ")");
		}
		
		constraints = constraints.substring(BarvinokSyntax.EXISTS.length()).trim();
		// Lo que queda es de sacar el exists "def : constraints" o bien "( def : constraints )".
		// Quitamos el () si es que existen
		constraints = stripPart(constraints, "(", ")");
		
		// Estamos seguros que ahora tenemos en constraints algo de la forma "def :  constraints".
		int separatorIndex = constraints.indexOf(":");
		String definitions = constraints.substring(0, separatorIndex).trim();
		String existsConstratins = constraints.substring(separatorIndex + 1).trim();
		
		return new ExistsInfo(definitions, existsConstratins);
	}
	
	private static String stripPart(String part, String prefix, String suffix) {
		part = part.trim();
		if (part.startsWith(prefix) && part.endsWith(suffix) ) {
			part = part.substring(prefix.length(), part.length() - suffix.length()).trim();
		}
		return part;
	}


	public static String buildExists(String definitions, String constraints) {
		return  BarvinokSyntax.EXISTS + "(" + definitions + " : " + constraints + ")";
	}
}
