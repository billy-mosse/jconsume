package ar.uba.dc.config;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.beanutils.BeanDeclaration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;

import soot.jimple.toolkits.callgraph.EdgePredicate;
import ar.uba.dc.analysis.common.AbstractInterproceduralAnalysis;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.common.method.information.RuleBasedMethodInformationProvider;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.InterproceduralAnalysis;
import ar.uba.dc.analysis.memory.impl.ReportWriter;
import ar.uba.dc.analysis.memory.impl.report.datasource.RepositoryReportDataSource;
import ar.uba.dc.analysis.memory.impl.runner.PhaseInitializer;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.config.factory.ContainerBeanFactory;
import ar.uba.dc.config.factory.ReferenceBeanFactory;
import ar.uba.dc.config.factory.SingletonBeanFactory;
import ar.uba.dc.invariant.InvariantProvider;
import ar.uba.dc.invariant.spec.SpecCompiler;
import ar.uba.dc.invariant.spec.SpecReader;
import ar.uba.dc.invariant.spec.SpecWriter;
import ar.uba.dc.soot.DirectedCallGraph;
import ar.uba.dc.soot.SootMethodFilter;
import ar.uba.dc.tools.instrumentation.resource.tracker.madeja.LifeTimeOracleAnalysis;
import ar.uba.dc.util.location.MethodLocationStrategy;

/**
 * Factory para los distintos objetos presentenes. 
 * 
 * @author testis
 */
public class Factory {

	/** Configuracion base a partir de la cual se crean los objetos */
	private XMLConfiguration config;
	
	public Factory(CompositeConfiguration configuration) throws ConfigurationException {
		this.config = new XMLConfiguration(configuration.getString(Context.FACTORY_FILE, "factory-config.xml"));
		this.config.append(configuration);
		
		BeanHelper.registerBeanFactory("custom", new SingletonBeanFactory(new ReferenceBeanFactory(config)));
		BeanHelper.registerBeanFactory("container", new SingletonBeanFactory(new ContainerBeanFactory(config)));
	}
	
	/**
	 * Retorna el analisis de memoria
	 */
	public ar.uba.dc.analysis.memory.InterproceduralAnalysis getMemoryAnalysis() {
		return getObject("analysis.memory.interprocedural");
	}
	
	/**
	 * Retorna el analisis de paperMemoria
	 */
	public ar.uba.dc.analysis.memory.PaperInterproceduralAnalysis getPaperMemoryAnalysis() {
		return getObject("analysis.memory.paperInterprocedural");
	}
	
	/**
	 * Retorna el analisis de lifeTime
	 */
	public LifeTimeOracleAnalysis getLifeTimeOracleAnalysis() {
		return getObject("analysis.lifetime.interprocedural");
	}
	
	
	/**
	 * Devuelve el lector de especificaciones en formato spec
	 */
	public SpecReader getSpecReader(String specType) {
		return getObject("invariants.provider.spec." + specType + ".reader");
	}

	/**
	 * Devuelve el compilador de especificaciones en formato spec
	 */
	public SpecCompiler getSpecCompiler(String specType) {
		return getObject("invariants.provider.spec." + specType + ".compiler");
	}

	/**
	 * Devuelve el escritor de especificaciones en formato spec
	 */
	public SpecWriter getSpecWriter() {
		return getObject("invariants.spec.writer");
	}

	/**
	 * Devuelve el proveedor de invariantes
	 */
	public InvariantProvider getInvariantProvider() {
		return getObject("invariants.provider." + config.getString(Context.INVARIANT_PROVIDER_TYPE));
	}
	
	/**
	 * Devuelve un predicado sobre ejes del callgraph que puede ser utilizado para filtrar el callgraph
	 */
	public EdgePredicate getEdgePredicate() {
		return getObject("soot.callgraph.edge-filter");
	}
	
	/**
	 * Devuelve un filtro de metodos para obtener un recorte del callgraph generado el cual sera procesado por
	 * los distintos analisis. El filtro se usa para generar un {@link DirectedCallGraph} 
	 */
	public SootMethodFilter getDirectedGraphMethodFilter() {
		return getObject("soot.callgraph.method-filter");
	}
	
	public MethodLocationStrategy getEscapeRepositoryLocation(String repositoryName) {
		return getObject("analysis.escape.repositories." + repositoryName + ".locationStrategy");
	}

	/**
	 * Metodo generico para construir un objeto en base a la configuracion del factory
	 * @param <T> Tipo del objeto a retornar
	 * @param key Path del XML hasta llevar al objeto a construir
	 * @return Objecto requerido
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getObject(String key) {
		BeanDeclaration decl = new XMLBeanDeclaration(config, key);
		return (T) BeanHelper.createBean(decl);
	}

	public InterproceduralAnalysis getEscapeAnalysis() {
		return getObject("analysis.escape.interprocedural");
	}

	public SummaryRepository<EscapeSummary> getEscapeRepository() {
		return getObject("analysis.escape.repository");
	}
	
	public SummaryRepository<MemorySummary> getMemoryRepository() {
		return getObject("analysis.memory.interprocedural.repository");
	}

	public ReportWriter getMemoryReportWriter() {
		return getObject("analysis.memory.interprocedural.reportWriter");
	}

	public RepositoryReportDataSource getRepositoryReportDataSource() {
		return getObject("analysis.memory.report.datasources.repository");
	}

	public RuleBasedMethodInformationProvider getRuleInformationProvider() {
		return getObject("analysis.common.method-information-provider");
	}
	
	public PhaseInitializer getEscapePhaseInitializer() {
		return getObject("analysis.escape.initializer");	
	}
	
	public AbstractInterproceduralAnalysis getInterproceduralAnalysis(String analysis) {
		return getObject(analysis);	
	}
}