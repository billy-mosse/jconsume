package ar.uba.dc.barvinok.expression.operation.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.nfunk.jep.ASTConstant;
import org.nfunk.jep.ASTFunNode;
import org.nfunk.jep.ASTStart;
import org.nfunk.jep.ASTVarNode;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.ParserVisitor;
import org.nfunk.jep.SimpleNode;

/**
 * Visitor para convertir una expression a string.
 * Recibe como parametro un mapping que en caso de no ser null, lo usa para transformar el nombre
 * de una variable en el valor indicado por el mapping.
 * 
 * Tiene la posiblidad de manejar un mapeo entre nombres de funciones y alias. El alias será usado 
 * a la hora de agregar la funcion al string. Ej: si existe el alias para "&&" y es "and", cuando se tenga 
 * que mostrar "&&" se mostrar "and"
 * 
 * @author testis
 */
public class ToStringParserVisitor implements ParserVisitor {

	private static final Integer BASE_10 = 10;
	private static final String SPACE = " ";
	private static final Integer AND_OR_PRECEDENCE = -2;
	private static final Integer EQ_PRECEDENCE = -1;
	private static final Integer REL_PRECEDENCE = 0;
	private static final Integer ADD_PRECEDENCE = 1;
	private static final Integer MUL_PRECEDENCE = 2;
	private static final Integer POW_PRECEDENCE = 3;
	
	private static final String DIV_FUNCITON = "/";
	private static final String MOD_FUNCITON = "%";
	private static final String MINUS_FUNCTION = "-";
	private static final String MUL_FUNCTION = "*";
	
	private static Map<String, String> unaryAlias = new HashMap<String, String>();
	private static List<String> functionWithoutSpaces = Arrays.asList(new String[] { "^", "^^", MOD_FUNCITON, DIV_FUNCITON, ".", MUL_FUNCTION });
	private static Map<String, Integer> functionPrecedence = new HashMap<String, Integer>(); 
	static {
		functionPrecedence.put("||", AND_OR_PRECEDENCE);
		functionPrecedence.put("&&", AND_OR_PRECEDENCE);
		functionPrecedence.put("==", EQ_PRECEDENCE);
		functionPrecedence.put("!=", EQ_PRECEDENCE);
		functionPrecedence.put("<", REL_PRECEDENCE);
		functionPrecedence.put(">", REL_PRECEDENCE);
		functionPrecedence.put("<=", REL_PRECEDENCE);
		functionPrecedence.put(">=", REL_PRECEDENCE);
		functionPrecedence.put("+", ADD_PRECEDENCE);
		functionPrecedence.put(MINUS_FUNCTION, ADD_PRECEDENCE);
		functionPrecedence.put(MUL_FUNCTION, MUL_PRECEDENCE);
		functionPrecedence.put(".", MUL_PRECEDENCE);
		functionPrecedence.put("^^", MUL_PRECEDENCE);
		functionPrecedence.put(DIV_FUNCITON, MUL_PRECEDENCE);
		functionPrecedence.put(MOD_FUNCITON, 2);
		functionPrecedence.put("^", POW_PRECEDENCE);
		
		unaryAlias.put("UMinus", MINUS_FUNCTION);
		unaryAlias.put("Not", "!");
	}
	
	private Map<String, String> functionAlias = new HashMap<String, String>();
	
	public ExpressionParsingResult visit(ASTConstant constant, Object mapping) throws ParseException {
		Double value = (Double) constant.getValue();
		return new ExpressionParsingResult(Long.toString(value.longValue(), BASE_10));
	}
	
	@SuppressWarnings("unchecked")
	public ExpressionParsingResult visit(ASTVarNode variable, Object arg1) throws ParseException {
		Map<String, String> mapping = (Map<String, String>) arg1;
		String varName = variable.getVar().getName();
		
		if (mapping != null) {
			String alias = mapping.get(variable.getVar().getName());
			if (StringUtils.isNotBlank(alias)) {
				varName = alias;
			}
		}
		
		return new ExpressionParsingResult(varName);
	}
	
	public ExpressionParsingResult visit(ASTFunNode function, Object arg1) throws ParseException {
		if (function.getPFMC() instanceof org.nfunk.jep.function.List) { // ingresarion a[x]? 
			return visitSpecialFunction(function, arg1, "[", "]");
		}
		
		if (function.getPFMC() instanceof MaxMathCommand) {
			return visitSpecialFunction(function, arg1, function.getName() + "(", ")");
		}
		if (function.jjtGetNumChildren() == 1) {
			String funcName = getFunctionName(function);
			
			ExpressionParsingResult der = (ExpressionParsingResult) function.jjtGetChild(0).jjtAccept(this, arg1);
			if (der.getLastFunctionApplied() != null) {
				der.addFirst("(");
				der.addLast(")");
			}
			der.addFirst(funcName);
			return der;
		}
		
		if (function.jjtGetNumChildren() > 2) {
			throw new RuntimeException("El constraint tiene una funcion que no es binaria ni unaria: " + function.getName());
		}
		
		ExpressionParsingResult izq = (ExpressionParsingResult) function.jjtGetChild(0).jjtAccept(this, arg1);
		ExpressionParsingResult der = (ExpressionParsingResult) function.jjtGetChild(1).jjtAccept(this, arg1);
		
		if (applyParentheses(der, function, true)) {
			der.addFirst("(");
			der.addLast(")");
		}
		
		if (applyParentheses(izq, function, false)) {
			izq.addFirst("(");
			izq.addLast(")");	 
		}		
		
		izq.addLast(getFunctionName(function));
		izq.addAll(der);
		
		izq.setLastFunctionApplied(function.getName());

		if(function.getName().equals(DIV_FUNCITON))
		{
			izq.addFirst("[");
			izq.addLast("]");

		}
		return izq;
	}
	
	protected ExpressionParsingResult visitSpecialFunction(ASTFunNode function, Object arg1, String preffix, String suffix) throws ParseException {
		ExpressionParsingResult ret = null;
		for (int i = 0; i < function.jjtGetNumChildren(); i++) {
			if (ret == null) {
				ret = (ExpressionParsingResult) function.jjtGetChild(i).jjtAccept(this, arg1);
				ret.addFirst(preffix);
				ret.setLastFunctionApplied(null);
			} else {
				ExpressionParsingResult temp = (ExpressionParsingResult) function.jjtGetChild(i).jjtAccept(this, arg1);
				ret.addLast(", ");
				ret.addAll(temp);
			}
		}
		
		ret.addLast(suffix);
		return ret;
	}

	protected String getFunctionName(ASTFunNode function) {
		Boolean unaryFunction = function.getPFMC().getNumberOfParameters() == 1;
		String defaultName = function.getName();
		
		if (unaryFunction && unaryAlias.containsKey(defaultName)) {
			defaultName = unaryAlias.get(defaultName);
		}
		
		String funcName = functionAlias.get(defaultName);
		if (StringUtils.isBlank(funcName)) {
			funcName = defaultName;
		}
		
		
		if (!unaryFunction && !functionWithoutSpaces.contains(funcName)) {
			funcName = SPACE + funcName + SPACE;
		}
		
		return funcName;
	}
	
	protected boolean applyParentheses(ExpressionParsingResult result, ASTFunNode function, Boolean isRightPart) {
		boolean ret = false;
		
		if (result.getLastFunctionApplied() != null) {
			Integer funcPrecedence = functionPrecedence.get(function.getName());
			Integer resultPrecedence = functionPrecedence.get(result.getLastFunctionApplied());
			
			if (resultPrecedence.compareTo(funcPrecedence) < 0) {
				return true; 
			} else if (resultPrecedence.compareTo(funcPrecedence) == 0) {
				if (AND_OR_PRECEDENCE.equals(funcPrecedence)) {
					ret = !function.getName().equals(result.getLastFunctionApplied());
				} else if (ADD_PRECEDENCE.equals(funcPrecedence)) {
					ret = isRightPart && (MINUS_FUNCTION.equals(function.getName()) || MINUS_FUNCTION.equals(result.getLastFunctionApplied()));
				} else if (!EQ_PRECEDENCE.equals(funcPrecedence) && !REL_PRECEDENCE.equals(funcPrecedence)) {
					ret = !(MUL_FUNCTION.equals(function.getName()) && MUL_FUNCTION.equals(result.getLastFunctionApplied())); 
				}
			} else {
				ret = DIV_FUNCITON.equals(function.getName());
			}
		}
	
		return ret;
	}
	
	public ExpressionParsingResult visit(ASTStart start, Object arg1) throws ParseException {
		throw new RuntimeException("El constraint posee un node start");
	}
	
	public ExpressionParsingResult visit(SimpleNode simpleNode, Object arg1) throws ParseException {
		throw new RuntimeException("El constraint posee un nodo simple");
	}
	
	public void addAliasFor(String function, String alias) {
		functionAlias.put(function, alias);
	}
	
	public void removeAliasFor(String function) {
		functionAlias.remove(function);
	}
}
