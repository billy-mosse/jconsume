package ar.uba.dc.barvinok.calculators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.barvinok.BarvinokCalculator;
import ar.uba.dc.barvinok.BarvinokException;
import ar.uba.dc.barvinok.BarvinokExecutor;
import ar.uba.dc.barvinok.BarvinokFactory;
import ar.uba.dc.barvinok.BarvinokSyntax;
import ar.uba.dc.barvinok.BarvinokUtils;
import ar.uba.dc.barvinok.ComparePolynomialStrategy;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;
import ar.uba.dc.barvinok.expression.operation.BindingException;
import ar.uba.dc.barvinok.expression.operation.BindingValidator;
import ar.uba.dc.barvinok.expression.operation.DomainUnifier;
import ar.uba.dc.barvinok.expression.operation.ExpressionMapper;

/**
 * Calculadora que aproxima el maximo entre polinomios con la suma de los mismos
 * 
 * @author testis
 */
public class CommandLineCalculator implements BarvinokCalculator {

	private static Log log = LogFactory.getLog(CommandLineCalculator.class);
	
	public static final String UPPER_BOUND_OPERATION = "ub";
	public static final String SUM_OPERATION = "sum";
	public static final String ADD_OPERATOR = "+";
	public static final String SUBSTRACT_OPERATOR = "-";
	public static final String COMPARE_OPERATOR = ".";
	
	/**
	 * Flag que indica si la implementacion que estamos usando para la calculadora
	 * soporta recibir un polinomio infinito como parametro. Hasta al version 0.31 sabemos
	 * que no lo soporta. Dejamos este flag para poder hacer sencillos los cambios si es que
	 * esto cambia en una versión futura.
	 */
	protected Boolean barvinokSupportInfiniteOnSumOperator;
	
	protected BarvinokExecutor executor;
	
	protected ExpressionMapper mapper;
	
	protected String prefixForPolinomialParametersInRangedOperations = StringUtils.EMPTY;
	
	protected BarvinokSyntax syntax;
	
	protected DomainUnifier domainUnifier;
	
	protected ComparePolynomialStrategy compareStrategy;
	
	protected BindingValidator bindingValidator;
	
	/**
	 * card P:
	 * -------
	 * 
	 *  card P = [params] -> { cuasipolinomio_1; ... ; cuasipolinomio_n }
	 *  params = a, b, c, ... , n
	 *  cuasipolinomio = expr : condicones
	 */
	public PiecewiseQuasipolynomial countExecutions(DomainSet invariant) {
		if (invariant.getConstraints().length() == 0) {
			return BarvinokFactory.constant(1L);
		}
		
		Long constantVal = BarvinokUtils.toConstant(invariant);
		// Si es una cte, devolvemos cte
		if (constantVal != null) {
			return BarvinokFactory.constant(constantVal);
		}
		
		Map<String, String> mapping = new HashMap<String, String>();
		DomainSet inv = mapper.simplify(invariant, mapping);
		
		if (log.isDebugEnabled()) {
			log.debug("    Mapping: " + syntax.toString(invariant) + " to " + syntax.toString(inv));
		}
		
		//For debugging more easily
		String command = "card " + syntax.toString(inv) + ";";
		String ret = execCommand(command);
		
		PiecewiseQuasipolynomial pqs = syntax.parsePiecewiseQuasipolynomial(ret);
		
		pqs = expand(pqs, mapping);
		
		if (log.isDebugEnabled()) {
			log.debug("    card " + syntax.toString(invariant) + " = " + syntax.toString(pqs));
		}
		
		return pqs;
	}

	/**
	 * sum F:
	 * ------
	 * 
	 * sum F = [params] -> { cuasipolinomio_1; ... ; cuasipolinomio_n }
	 * params = a, b, c, ... , n
	 * cuasipolinomio = expr : condicones
	 */
	public PiecewiseQuasipolynomial sumConsumtion(PiecewiseQuasipolynomial expr, DomainSet invariant) {
		if (StringUtils.isBlank(invariant.getConstraints())) {
			return expr.clone();
		}

		if (!barvinokSupportInfiniteOnSumOperator && BarvinokUtils.hasInfinitePiece(expr)) {
			return BarvinokFactory.infinite(invariant.getParameters());
		}
		
		Map<String, String> mapping = new HashMap<String, String>();
		
		PiecewiseQuasipolynomial result = null;
		if(invariant.getConstraints().indexOf("/")!=-1)
		{
			System.out.print("");
		}
		if (BarvinokUtils.hasFoldPiece(expr)) {
			//result = sumConsumtionWithFold(SUM_OPERATION, invariant, expr, mapping);
			expr = sumCandidatesOnFoldPieces(expr);
			result = execOperationOverDomain(SUM_OPERATION, invariant, expr, mapping);
		} else {
			result = execOperationOverDomain(SUM_OPERATION, invariant, expr, mapping);
		}
		
		result = expand(result, mapping);
		
		if (log.isDebugEnabled()) {
			log.debug("    Result: " + mapping);
		}
		
		return result;
	}

	protected PiecewiseQuasipolynomial sumCandidatesOnFoldPieces(PiecewiseQuasipolynomial expr) {
		
		StringBuilder command = new StringBuilder();
		String maxCandidatesPrefix = BarvinokSyntax.MAX_CANDIDATES + "(";
		List<Integer> resultIndexes = new ArrayList<Integer>(expr.getPieces().size());
		Integer idx = 0;
		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.addAllParameters(expr.getParameters());
		for (QuasiPolynomial piece : expr.getPieces()) {
			if (BarvinokUtils.isFold(piece)) {
				String max = piece.getPolynomial().substring(maxCandidatesPrefix.length(), piece.getPolynomial().length() - 1);
				String[] candidates = max.split(",");

				StringBuilder subcommand = new StringBuilder();
				for (String candidate : candidates) {
					p.clearPieces();
					p.add(new QuasiPolynomial(candidate, piece.getConstraints()));
					subcommand.append(" + " + syntax.toString(p));
				}
				
				command.append(subcommand.substring(3) + ";");
				
				resultIndexes.add(idx);
			}
			idx++;				
		}
		
		PiecewiseQuasipolynomial result = expr.clone();
		List<PiecewiseQuasipolynomial> results = syntax.parseMultiplePiecewiseQuasipolynomial(execCommand(command.toString()));
		idx = 0;
		for (PiecewiseQuasipolynomial polynomial : results) {
			result.getPieces().set(resultIndexes.get(idx), polynomial.getPieces().get(0));
			idx++;
		}
		
		return result;
	}

	protected PiecewiseQuasipolynomial sumConsumtionWithFold(String sumOperation, DomainSet invariant, PiecewiseQuasipolynomial polynomial, Map<String, String> mapping) {
		if (log.isDebugEnabled()) {
			log.debug("    Operation: " + sumOperation);
			log.debug("    Invariant: " + syntax.toString(invariant));
			log.debug("    Expr: " + syntax.toString(polynomial));
			log.debug("    mapping: " + mapping);
		}
		invariant = mapper.simplify(invariant, mapping);
		polynomial = mapper.simplify(polynomial, mapping, prefixForPolinomialParametersInRangedOperations);
		
		if (log.isDebugEnabled()) {
			log.debug("    Mapping pre validation: " + mapping);
		}
		
		validate(polynomial, invariant, mapping);
		
		StringBuilder sb = new StringBuilder();
		
		PiecewiseQuasipolynomial pqp = new PiecewiseQuasipolynomial(); 
		
		// Parametros de la expresion
		pqp.addAllParameters(invariant.getParameters());
		// variables de la expresion
		Set<String> variables = new TreeSet<String>(polynomial.getParameters());
		variables.addAll(invariant.getVariables());
		
		for (QuasiPolynomial q : polynomial.getPieces()) {
			List<String> candidates = null;
			
			if (BarvinokUtils.isFold(q)) {
				candidates = getFoldCandidates(q);
			} else {
				candidates = new ArrayList<String>();
				candidates.add(q.getPolynomial());
			}
			
			// Constraints y armado de los polinomios
			String constraints = domainUnifier.unify(q.getConstraints(), invariant.getConstraints());
			for (String candidate : candidates) {
				pqp.clearPieces();
				pqp.add(new QuasiPolynomial(candidate.trim(), constraints, variables));
				// Ejecutamos el comando
				sb.append(sumOperation + " " + syntax.toString(pqp) + ";" + System.getProperty("line.separator"));
			}
		}
		
		String command = sb.toString();
		
		if (log.isDebugEnabled()) {
			log.debug("    exec: " + command);
		}
		
		List<PiecewiseQuasipolynomial> results = syntax.parseMultiplePiecewiseQuasipolynomial(execCommand(command));
		
		sb = new StringBuilder();
		
		String separator = " " + COMPARE_OPERATOR + " ";
		for (PiecewiseQuasipolynomial p : results) {
			BarvinokUtils.surroundWithMax(p);
			sb.append(separator + syntax.toString(p));
		}
		command = sb.substring(separator.length()) + ";";
		
		PiecewiseQuasipolynomial result = syntax.parsePiecewiseQuasipolynomial(execCommand(command));
		result.addAllParameters(invariant.getParameters());
		BarvinokUtils.cleanMaxIfPossible(result);
		
		
		if (log.isDebugEnabled()) {
			log.debug("    Mapping: " + mapping);
			log.debug("    " + sumOperation + " " + syntax.toString(polynomial) + " = " + syntax.toString(result));
		}
		
		return result;
	}
	
	private List<String> getFoldCandidates(QuasiPolynomial q) {
		String value = q.getPolynomial().trim();
		value = value.substring("max(".length(), value.length()-1).trim(); 
		
		return Arrays.asList(value.split(","));
	}

	/**
	 * ub F:
	 * -----
	 * 
	 * ub F = [params] -> { max(expr_1, ..., expr_n): condiciones}
	 * params = a, b, c, ... , n
	 * cuasipolinomio = expr : condicones
	 */
	public PiecewiseQuasipolynomial upperBound(PiecewiseQuasipolynomial value, DomainSet invariant) {
		if (StringUtils.isBlank(invariant.getConstraints())) {
			return value.clone();
		}
		
		return compareStrategy.upperBound(value, invariant, this);
	}
	
	public PiecewiseQuasipolynomial max(PiecewiseQuasipolynomial expr1, PiecewiseQuasipolynomial expr2) {
		PiecewiseQuasipolynomial e1 = expr1.clone();
		PiecewiseQuasipolynomial e2 = expr2.clone();
		
		// verificamos que ambos polinomios tengan los mismos parametros. 
		// Sino la comparacion no se puede hacer
		
		//BILLY: en realidad esta no parece una funcion de validacion sino de "correccion". Le agrega los parametros faltantes a cada polinomio
		validate(e1, e2);
		
		return compareStrategy.compare(e1, e2, this);
	}
	
	public PiecewiseQuasipolynomial substract(PiecewiseQuasipolynomial expression1, PiecewiseQuasipolynomial expression2)
	{			
		Map<String, String> mapping = new HashMap<String, String>();
		PiecewiseQuasipolynomial[] polynomials = new PiecewiseQuasipolynomial[2];
		
		//BILLY. TODO: esto no es prolijo
		polynomials[0] = expression1.clone();
		polynomials[1] = expression2.clone();

		
		PiecewiseQuasipolynomial result = null;

		//BILLY: Posible bug: tengo que clonar el pol para usar esta funcion?

		boolean e1HasFoldPiece = BarvinokUtils.hasFoldPiece(expression1);
		boolean e2HasFoldPiece = BarvinokUtils.hasFoldPiece(expression2);
		
		//Las calculo antes porque igual las voy a usar
		if(e1HasFoldPiece || e2HasFoldPiece)
		{
			// Operamos nosotros porque la calculadora no soporta hacer una resta donde hay mas de un polinomio con fold
			result = substractPolynomialsWithMoreThanOneFold(expression1, expression2, mapping, e1HasFoldPiece, e2HasFoldPiece);

		}
		else
		{
			result = execOperationOverExpressions(SUBSTRACT_OPERATOR, mapping, polynomials);
		}

		
		result = expand(result, mapping);
		
		if (log.isDebugEnabled()) {
			log.debug("    mapping: " + mapping);
			log.debug("    expanded result: " + result);
		}
		
		return result; 
		
	}
	
	
	private PiecewiseQuasipolynomial substractPolynomialsWithMoreThanOneFold(PiecewiseQuasipolynomial expression1,
			PiecewiseQuasipolynomial expression2, Map<String, String> mapping, boolean e1HasFoldPiece, boolean e2HasFoldPiece) {
		
		PiecewiseQuasipolynomial[] polynomials = new PiecewiseQuasipolynomial[2];

		
		polynomials[0] = e1HasFoldPiece ? BarvinokUtils.lowerBoundToRemoveFolds(expression1) : expression1;
		

		// Aunque es el sustraendo, como la cuenta es max(M1-E1,...,Mn-En) + E1 + ... + En
		//Si E1 tiene fold y lo acotamos por arriba por cota(E1), pueden pasar dos opciones:
		//1) Que M1-E1 sea el maximo, con lo cual cota(E1) se cancela con la cota(E1) que sumamos
		//2) Que no se realice, con lo cual estamos sumando cota(E1) >= E1
		
		//Lo que hay que hacer es que si estamos restando M1-E1, y acotamos E1, tenemos que acordarnos de acotarlo tambien en la suma
		polynomials[1] = e1HasFoldPiece ? BarvinokUtils.lowerBoundToRemoveFolds(expression1) : expression2;
		
		PiecewiseQuasipolynomial result = null;

		result = execOperationOverExpressions(SUBSTRACT_OPERATOR, mapping, polynomials);
		return result;
	}

	public PiecewiseQuasipolynomial add(PiecewiseQuasipolynomial... exprs) {
		Map<String, String> mapping = new HashMap<String, String>();
		PiecewiseQuasipolynomial[] polynomials = new PiecewiseQuasipolynomial[exprs.length];
		List<PiecewiseQuasipolynomial> polynomialsWithFoldPiece = new ArrayList<PiecewiseQuasipolynomial>(exprs.length);
		List<PiecewiseQuasipolynomial> polynomialsWithoutFoldPiece = new ArrayList<PiecewiseQuasipolynomial>(exprs.length);
		
		for (Integer i = 0; i < exprs.length; i++) {
			polynomials[i] = exprs[i].clone();
			
			if (BarvinokUtils.hasFoldPiece(polynomials[i])) {
				polynomialsWithFoldPiece.add(polynomials[i]);
			} else {
				polynomialsWithoutFoldPiece.add(polynomials[i]);
			}
		}
		
		validate(polynomials);
		
		PiecewiseQuasipolynomial result = null;
		if (polynomialsWithFoldPiece.size() > 1) {
			// Operamos nosotros porque la calculadora no lo soporta
			result = addPolynomialsWithMoreThanTwoFolds(polynomialsWithFoldPiece, polynomialsWithoutFoldPiece, mapping);
		} else {
			// La calculadora soporta sumar hasta 1 fold. 
			result = execOperationOverExpressions(ADD_OPERATOR, mapping, polynomials);
		}
		
		result = expand(result, mapping);
		
		if (log.isDebugEnabled()) {
			log.debug("    mapping: " + mapping);
			log.debug("    expanded result: " + result);
		}
		
		return result; 
	}
	
	/**
	 *	1: M := aquellos operandos fold.
	 *	2: surroundWithMax(M).
	 *	3: R := { M[0] }
	 *	4: Para c/M_i restante, generar todos los posibles escenarios de M_i (calculo todos los escenarios de ganadores parte por parte)
	 *	5: Calculamos todos los posibles resultados haciendo R + alguna combinacion de ganadores de cada M_i con i > 0
	 *	6: Combinar todos los resultados obtenidos en 5 con el operador . para eliminar escenarios que no tengan sentido
	 *	7: Hacer el resultado de 6 + resto de los operandos
	 */
	protected PiecewiseQuasipolynomial addPolynomialsWithMoreThanTwoFolds(List<PiecewiseQuasipolynomial> polynomialsWithFoldPiece, List<PiecewiseQuasipolynomial> polynomialsWithoutFoldPiece, Map<String, String> mapping) {
		// Buscamos si algun polinomio tiene parametros. Esto es para laburar siempre con los mismos parametros
		LinkedList<PiecewiseQuasipolynomial> all = new LinkedList<PiecewiseQuasipolynomial>(polynomialsWithFoldPiece);
		all.addAll(polynomialsWithoutFoldPiece);
		Set<String> params = new TreeSet<String>();
		
		for (PiecewiseQuasipolynomial p : all) {
			if (!p.getParameters().isEmpty()) {
				params = p.getParameters();
			}
		}
		
		// Si alguna parte de algun polinomio con fold no era un fold, lo agregamos. Asi operamos todo de forma omogenea.
		for (PiecewiseQuasipolynomial p : polynomialsWithFoldPiece) {
			if (p.getParameters().isEmpty()) {
				p.addAllParameters(params);
			}
			BarvinokUtils.surroundWithMax(p);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("Base := " + syntax.toString(mapper.simplify(polynomialsWithFoldPiece.get(0), mapping, StringUtils.EMPTY)) + ";" + System.getProperty("line.separator"));
		
		// Para c/M_i restante, generar todos los posibles escenarios de M_i (calculo todos los escenarios de ganadores parte por parte)
		List<Integer> scenarios = addPossibleScenarios(polynomialsWithFoldPiece.subList(1, polynomialsWithFoldPiece.size()), mapping, "E_", sb);
		
		// Calculamos todos los posibles resultados haciendo R + alguna combinacion de ganadores de cada M_i con i > 0
		int possibleResults = combinePossibleScenarios("RESULT_", "Base", "E_", scenarios, sb);
		
		// Combinar todos los resultados obtenidos en 5 con el operador . para eliminar escenarios que no tengan sentido
		sb.append("SUM_FOLDS := ");
		for (int i = 1; i <= possibleResults; i++) {
			if (i > 1) {
				sb.append(" . ");
			}
			sb.append("RESULT_" + i);
		}
		sb.append(";" + System.getProperty("line.separator"));
		
		// Sumamos lo que conseguimos con los polinomios que faltaban
		sb.append("SUM_FOLDS");
		for (PiecewiseQuasipolynomial p : polynomialsWithoutFoldPiece) {
			if (p.getParameters().isEmpty()) {
				p.addAllParameters(params);
			}
			sb.append(" + " + syntax.toString(mapper.simplify(p, mapping, StringUtils.EMPTY)));
		}
		sb.append(";");
		
		// Ejecutamos el set de comandos que armamos
		String command = sb.toString();
		
		if (log.isDebugEnabled()) {
			log.debug("    Command: " + command);
			log.debug("    mapping: " + mapping);
		}
		
		PiecewiseQuasipolynomial result = syntax.parsePiecewiseQuasipolynomial(execCommand(command));
		
		BarvinokUtils.cleanMaxIfPossible(result);
		
		if (log.isDebugEnabled()) {
			log.debug("    pre-expand result: " + syntax.toString(result));
		}
		
		return result;
	}

	private List<Integer> addPossibleScenarios(List<PiecewiseQuasipolynomial> polynomials, Map<String, String> mapping, String scenarioPrefix, StringBuilder sb) {
		List<Integer> polynomialsInfo = new ArrayList<Integer>(polynomials.size());
		
		int idx = 0;
		for (PiecewiseQuasipolynomial polynomial : polynomials) {
			
			// Procesamos el polinomio
			PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
			p.addAllParameters(polynomial.getParameters());

			LinkedList<PiecewiseQuasipolynomial> scenarios = new LinkedList<PiecewiseQuasipolynomial>();
			scenarios.add(p);
			for (QuasiPolynomial q : polynomial.getPieces()) {
				ListIterator<PiecewiseQuasipolynomial> it = (ListIterator<PiecewiseQuasipolynomial>) scenarios.iterator();
				while (it.hasNext()) {
					PiecewiseQuasipolynomial actual = it.next();
					it.remove();
					for (String candidate : getFoldCandidates(q)) {
						PiecewiseQuasipolynomial clone = actual.clone();
						clone.add(new QuasiPolynomial(candidate.trim(), q.getConstraints(), q.getVariables()));
						it.add(clone);
					}
				}
			}
			
			int numScenario = 1; 
			for (PiecewiseQuasipolynomial scenario : scenarios) {
				sb.append(scenarioPrefix + (idx + 1) + "_" + numScenario + " := " + syntax.toString(mapper.simplify(scenario, mapping, StringUtils.EMPTY)) + ";" + System.getProperty("line.separator"));
				numScenario++;
			}
			
			polynomialsInfo.add(idx, scenarios.size());
			idx++;
		}
		
		return polynomialsInfo;
	}

	private int combinePossibleScenarios(String resultPrefix, String base, String scenarioPrefix, List<Integer> polynomialsInfo, StringBuilder sb) {
		int result = 0;
		
		if (polynomialsInfo.size() > 0) {
			Stack<String> combinations = new Stack<String>();
			Stack<List<Integer>> stack = new Stack<List<Integer>>();
			
			int numEscenario = 1;
			combinations.push(StringUtils.EMPTY);
			stack.push(polynomialsInfo);
			while(!stack.isEmpty()) {
				List<Integer> actualInfo = stack.pop();
				String actualCombination = combinations.pop();
				
				if (actualInfo.isEmpty()) {
					sb.append(resultPrefix + numEscenario + " := " + base + actualCombination + ";" + System.getProperty("line.separator"));
					numEscenario++;
					result++;
				} else {
					Integer numOfScenarios = actualInfo.get(0);
					List<Integer> nextInfo = actualInfo.subList(1, actualInfo.size());
					for (int i = numOfScenarios; i >= 1; i--) {
						combinations.push(actualCombination + " + " + scenarioPrefix + (polynomialsInfo.size() - actualInfo.size()  + 1) + "_" + i);
						stack.push(nextInfo);
					}
				}
			}
		}
		
		
		return result;
	}

	/**
	 * METODOS Utiles para las calculadoras
	 * ==================================== 
	 */
	
	protected String execCommand(String command) {
		return executor.execCommand(command);
	}
	
	public PiecewiseQuasipolynomial expand(PiecewiseQuasipolynomial result, Map<String, String> mapping) {
		return mapper.expand(result, mapping);
	}
	
	public DomainSet expand(DomainSet result, Map<String, String> mapping) {
		return mapper.expand(result, mapping);
	}
	
	public QuasiPolynomial addAll(List<String> pols, Set<String> parameters, String constraints) {
		String command = StringUtils.EMPTY;

		PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
		p.addAllParameters(parameters);
		for (String polynomial : pols) {
			p.clearPieces();
			p.add(new QuasiPolynomial(polynomial, constraints));
			command += " + " + syntax.toString(p);
		}
		
		command = command.substring(3) + ";";
		if (log.isDebugEnabled()) {
			log.debug("    addAll: Executing command [" + command + "]");
		}
		
		PiecewiseQuasipolynomial result = syntax.parsePiecewiseQuasipolynomial(execCommand(command));
		
		if (result.getPieces().size() != 1) {
			throw new RuntimeException("Multiple quasipolynomials are not expected when + is applied over de same domain");
		}

		return result.getPieces().get(0);
	}

	public PiecewiseQuasipolynomial execOperationOverDomain(String operation, DomainSet invariant, PiecewiseQuasipolynomial p, Map<String, String> mapping) {		
		if (log.isDebugEnabled()) {
			log.debug("    Operation: " + operation);
			log.debug("    Invariant: " + syntax.toString(invariant));
			log.debug("    Expr: " + syntax.toString(p));
			log.debug("    mapping: " + mapping);
		}
		
		if(operation == "ub")
		{
			log.debug("    For debugging: case UB");
		}
		
		invariant = mapper.simplify(invariant, mapping);
		p = mapper.simplify(p, mapping, prefixForPolinomialParametersInRangedOperations);
		
		if (log.isDebugEnabled()) {
			log.debug("    Mapping pre validation: " + mapping);
		}
		
		validate(p, invariant, mapping);
		
		PiecewiseQuasipolynomial pqp = new PiecewiseQuasipolynomial(); 
		
		// Parametros de la expresion
		for (String param: invariant.getParameters()) {
			pqp.addParameter(param);
		}
		
		// variables de la expresion
		Set<String> variables = new TreeSet<String>(p.getParameters());
		variables.addAll(invariant.getVariables());
		
		// Constraints y armado de los polinomios
		for (QuasiPolynomial polynomial : p.getPieces()) {
			String constraints = domainUnifier.unify(polynomial.getConstraints(), invariant.getConstraints());
			if(invariant.hasInductives())
				pqp.add(new QuasiPolynomial(polynomial.getPolynomial(), constraints, variables, invariant.getInductives()));
			else
			{
				TreeSet<String> inductives = new TreeSet<String>();
				inductives.addAll(variables);
				inductives.addAll(p.getParameters());
				pqp.add(new QuasiPolynomial(polynomial.getPolynomial(), constraints, variables, inductives));
			}
		}
		
		// Ejecutamos el comando
		String ret = execCommand(operation + " " + syntax.toString(pqp) + ";");
		
		PiecewiseQuasipolynomial result = syntax.parsePiecewiseQuasipolynomial(ret);
		result.addAllParameters(invariant.getParameters());
		
		
		if (log.isDebugEnabled()) {
			log.debug("    Mapping: " + mapping);
			log.debug("    " + operation + " " + syntax.toString(pqp) + " = " + syntax.toString(result));
		}
		
		return result;
	}
	
	public PiecewiseQuasipolynomial execOperationOverExpressions(String operation, Map<String, String> mapping, PiecewiseQuasipolynomial... polynomials) {
		StringBuffer cmd = new StringBuffer();
		
		compareStrategy.prepareFor(operation, polynomials);
		
		String operationSeparator = " " + operation + " ";
		
		Set<String> parametersForConstants = new TreeSet<String>();
		for (PiecewiseQuasipolynomial polynomial : polynomials) {
			if (!polynomial.getParameters().isEmpty()) {
				parametersForConstants = polynomial.getParameters();
				break;
			}
		}

		for (PiecewiseQuasipolynomial polynomial : polynomials) {
			PiecewiseQuasipolynomial p = polynomial.clone();
			
			if (p.getParameters().isEmpty()) {
				p.addAllParameters(parametersForConstants);
			}
			cmd.append(operationSeparator + syntax.toString(mapper.simplify(p, mapping, StringUtils.EMPTY)));
		}
		
		String command = cmd.substring(operationSeparator.length());
		
		if (log.isDebugEnabled()) {
			log.debug("    Mapping: " + mapping);
			log.debug("    Command: " + command);
		}
		
		String ret = execCommand(command + ";");
		
		PiecewiseQuasipolynomial result = syntax.parsePiecewiseQuasipolynomial(ret);

		compareStrategy.cleanResult(result);
		
		if (log.isDebugEnabled()) {
			log.debug("    " + command + " = " + syntax.toString(result));
		}
		
		return result;
	}
	
	public PiecewiseQuasipolynomial combineDomainWithPolynomial(String operation, DomainSet invariant, PiecewiseQuasipolynomial polynomial, Map<String, String> mapping) {
		if (log.isDebugEnabled()) {
			log.debug("    Operation: " + operation);
			log.debug("    Invariant: " + syntax.toString(invariant));
			log.debug("    Expr: " + syntax.toString(polynomial));
			log.debug("    mapping: " + mapping);
		}
		
		invariant = mapper.simplify(invariant, mapping);
		polynomial = mapper.simplify(polynomial, mapping, prefixForPolinomialParametersInRangedOperations);
		
		if (log.isDebugEnabled()) {
			log.debug("    Mapping pre validation: " + mapping);
		}
		
		validate(polynomial, invariant, mapping);
		
		PiecewiseQuasipolynomial clone = polynomial.clone();
		clone.addAllParameters(invariant.getVariables());
		
		String command = syntax.toString(invariant) + " " + operation + " " + syntax.toString(clone, false);
		// Ejecutamos el comando
		String ret = execCommand(command + ";");
		
		PiecewiseQuasipolynomial result = syntax.parsePiecewiseQuasipolynomial(ret);
		result.addAllParameters(invariant.getParameters());

		if (log.isDebugEnabled()) {
			log.debug("    Mapping: " + mapping);
			log.debug("    " + command + " = " + syntax.toString(result));
		}
		
		return result;
	}
	
	protected void validate(PiecewiseQuasipolynomial... polynomials) {
		// Todos los polinomios deben tener los mismos parametros para poder operar
		if (polynomials.length > 0) {
			Set<String> params = new TreeSet<String>();
			
			for (PiecewiseQuasipolynomial p : polynomials) {
				params.addAll(p.getParameters());
			}	
			
			for (PiecewiseQuasipolynomial p : polynomials) {
				p.addAllParameters(params);
			}
		}
	}
	
	protected void validate(PiecewiseQuasipolynomial polynomial, DomainSet invariant, Map<String, String> mapping) {
		try {
			bindingValidator.validate(polynomial, invariant);
		} catch (BindingException e) {
			Set<String> params = new TreeSet<String>();
			
			for (String param : e.getParameters()) {
				params.add(mapping.get(param));
			}
			
			PiecewiseQuasipolynomial original = expand(polynomial, mapping);
			DomainSet originalInvariant = expand(invariant, mapping);
			BindingException newException = new BindingException(e.getMessage());
			newException.setParameters(params);
			throw new BarvinokException("Al aplicar el dominio [" + syntax.toString(originalInvariant) + "] al polinomio [" + syntax.toString(original) + "] falto especificar el bindin para los parametros [" + params + "]", newException);
		}
	}
	
	/**
	 * Getters y Setters
	 * ==================================== 
	 */
	
	public void setDomainUnifier(DomainUnifier domainUnifier) {
		this.domainUnifier = domainUnifier;
	}

	public void setCompareStrategy(ComparePolynomialStrategy compareStrategy) {
		this.compareStrategy = compareStrategy;
	}
	
	public ComparePolynomialStrategy getCompareStrategy() {
		return compareStrategy;
	}

	public void setMapper(ExpressionMapper mapper) {
		this.mapper = mapper;
	}

	public ExpressionMapper getMapper() {
		return mapper;
	}

	public void setPrefixForPolinomialParametersInRangedOperations(String prefixForPolinomialParametersInRangedOperations) {
		this.prefixForPolinomialParametersInRangedOperations = prefixForPolinomialParametersInRangedOperations;
	}

	public void setBarvinokSupportInfiniteOnSumOperator(Boolean barvinokSupportInfiniteOnSumOperator) {
		this.barvinokSupportInfiniteOnSumOperator = barvinokSupportInfiniteOnSumOperator;
	}

	public void setExecutor(BarvinokExecutor executor) {
		this.executor = executor;
	}

	public void setSyntax(BarvinokSyntax syntax) {
		this.syntax = syntax;
	}

	public BarvinokSyntax getSyntax() {
		return syntax;
	}

	public void setBindingValidator(BindingValidator bindingValidator) {
		this.bindingValidator = bindingValidator;
	}

	@Override
	public PiecewiseQuasipolynomial boundIfHasFold(PiecewiseQuasipolynomial polynomial_with_max) {
		return BarvinokUtils.boundIfHasFold(polynomial_with_max);
	}
}