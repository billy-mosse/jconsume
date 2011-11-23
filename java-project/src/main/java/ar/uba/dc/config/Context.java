package ar.uba.dc.config;

import java.util.List;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;

import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.barvinok.BarvinokCalculator;
import ar.uba.dc.invariant.InvariantProvider;

/**
 * Contexto de ejecucion. Permite acceder al {@link Factory}
 * y a las configuraciones de la ejecucion
 * 
 * @author testis
 */
public class Context {

	/** Valor para representar la sensibilidad INFINITA del analisis de escape */
	private static final Integer INFINITE = -1;
	
	/** Key para recuperar el {@link InvariantProvider} */
	public static final String INVARIANT_PROVIDER_TYPE = "invariants.provider";

	/** Key para recuperar el classpath a pasar a soot para procesar un programa */
	public static final String APPLICATION_CLASSPATH = "application.classpath";

	/** Key para recuperar la ruta del output folder */
	public static final String OUTPUT_FOLDER = "output.folder";

	/** Key para recuperar el metodo desde el cual analizar si no se especifica ninguno */
	public static final String DEFAULT_MAIN_METHOD = "default.main-method";

	/** Key para recuperar la impresora de summaries de escape {@link SummaryWriter} */
	public static final String ESCAPE_SUMMARY_PRINTER = "escape.summary.printer";

	/** Key para recuperar el tipo de calculadora (la implementacion de {@link BarvinokCalculator}) a utilizar */
	public static final String CALCULATOR_TYPE = "calculator.type";
	
	/** Key para recuperar la estrategia de comparacion de {@link ParametricExpression} a utilizar (add o lazy) */
	public static final String CALCULATOR_COMPARE_STRATEGY = "barvinok.calculator.compare-strategy";
	
	public static final String CALCULATOR_BINDING_VALIDATOR_TYPE = "barvinok.calculator.binding-validator.type";
	
	/** Key para recuperar la impresora de summaries de memoria {@link SummaryWriter} */
	public static final String MEMORY_SUMMARY_PRINTER = "memory.summary.printer";

	/** Key para recuperar el algoritmo de generacion de callgraph a utilizar (spark o cha) */
	public static final String CALLGRAPH_ALGORITHM = "callgraph.algorithm";
	
	public static final String SOOT_PHASE_OPTIONS = "soot.phase.option";

	public static final String SOOT_SPARK_OPTIONS = "soot.cg.spark";

	public static final String SOOT_CHA_OPTIONS = "soot.cg.cha";

	public static final String SOOT_OPTION_KEEP_LINE_NUMBER = "soot.option.keep_line_number";

	public static final String SOOT_OPTION_BYTECODE_OFFSET = "soot.option.keep_offset";

	public static final String RUN_ESCAPE_ANALYSIS = "memory.run.escape.before";

	public static final String SOOT_OPTION_APP = "soot.option.app";

	public static final String SOOT_FILTER_CALL_GRAPH = "soot.cg.filter.enabled";

	public static final String SOOT_CG_TRIM_CLINIT = "soot.cg.trim.clinit";

	public static final String INSTRUMENTATION_OUTPUT_FOLDER = "instrumented.code.output.dir";

	public static final String INSTRUMENTATION_OUTPUT_FORMAT = "instrumented.code.output.format";
	
	public static final String ESCAPE_SENSITIVITY = "escape.sensitivity";

	public static final String ESCAPE_OUTPUT_FOLDER = "escape.summary.output.dir";

	public static final String FACTORY_FILE = "configuration.factory.file";

	public static final String INSTRUMENTATION_SHOW_MAPPING = "instrumented.code.show.mapping";

	public static final String TOOL_CLASSPATH = "project.classes.classpath";	
	
	private Configuration config;
	private Factory factory;
	
	public Context(CompositeConfiguration configuration) throws ConfigurationException {
		this.config = configuration;
		this.factory = new Factory(configuration);
	}
	
	/**
	 * Retorna una propiedad de tipo string dado su key 
	 */
	public String getString(String key) {
		return config.getString(key);
	}
	
	public void setString(String key, String value) {
		config.setProperty(key, value);
	}
	
	/**
	 * Retorna una lista de propiedades dado su key 
	 */
	public List<?> getList(String key) {
		return config.getList(key);
	}
	
	/**
	 * Retorna un propiedad de tipo boolean dado su key 
	 */
	public boolean getBoolean(String key) {
		return config.getBoolean(key);
	}

	/**
	 * Retorna el {@link Factory} asociado a la configuracion 
	 */
	public Factory getFactory() {
		return factory;
	}

	/**
	 * Retorna un propiedad de tipo integer dado su key 
	 */
	public Integer getInteger(String key) {
		return config.getInteger(key, INFINITE);
	}
}
