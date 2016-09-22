package ar.uba.dc.barvinok.expression.operation.impl;

import org.apache.commons.lang.StringUtils;

import ar.uba.dc.barvinok.BarvinokSyntax;
import ar.uba.dc.barvinok.expression.operation.DomainUnifier;
import ar.uba.dc.barvinok.syntax.ExistsInfo;
import ar.uba.dc.barvinok.syntax.SyntaxUtils;

public class DefaultDomainUnifier implements DomainUnifier {
	
	public String unify(String constraints, String constraints2) {
		String prefix = StringUtils.EMPTY;
		String suffix = StringUtils.EMPTY;
		
		if (SyntaxUtils.containsExists(constraints)) {
			ExistsInfo info = SyntaxUtils.retriveExistsInformation(constraints);
			constraints = info.getConstratins();
			prefix = BarvinokSyntax.EXISTS + "(" + info.getDefinitions() + " : ";
			suffix = ")";
		} else if (SyntaxUtils.containsExists(constraints2)) {
			ExistsInfo info = SyntaxUtils.retriveExistsInformation(constraints2);
			constraints2 = info.getConstratins();
			prefix = BarvinokSyntax.EXISTS + "(" + info.getDefinitions() + " : ";
			suffix = ")";
		}
		
		String result = doUnify(constraints, constraints2);
		
		return prefix + result + suffix;
	}
	
	protected String doUnify(String constraints, String constraints2) {
		constraints = constraints.replaceAll("(?i) (and|&&) ", " and ").replaceAll("(?i) (or|\\|\\|) ", " or ");
		constraints2 = constraints2.replaceAll("(?i) (and|&&) ", " and ").replaceAll("(?i) (or|\\|\\|) ", " or ");
		
		// Nos encargamos de los casos "empty" en c/u de los parametros
		if (StringUtils.isBlank(constraints)) {
			return StringUtils.defaultString(constraints2);
		}
		
		if (StringUtils.isBlank(constraints2)) {
			return StringUtils.defaultString(constraints);
		}
		
		if (!StringUtils.contains(constraints, "or") && !StringUtils.contains(constraints2, "or")) {
			return constraints + " and " + constraints2;
		}
		

		//constraints = "A or B";
		//constraints2 = "1 or 2";
		
		String[] left = constraints.split("\\s? or \\s?");
		String[] right = constraints2.split("\\s? or \\s?");
		
		StringBuilder sb = new StringBuilder();
		
		for (String l : left) {
			for (String r : right) {
				sb.append(" or ");
				sb.append(process(l, r));
			}
		}
		
		if (sb.length() > 0) {
			return sb.substring(4);
		}
		
		return sb.toString();
	}
	
	protected String process(String left, String right) {
		left = removeParentheses(left);
		right = removeParentheses(right);
		
		return "(" + left + " and " + right + ")";
	}

	protected String removeParentheses(String target) {
		if (target.startsWith("(") && target.endsWith(")")) {
			target = target.substring(1, target.length() - 1);
		}
		
		return target;
	}
}
