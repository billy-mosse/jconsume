package ar.uba.dc.config.factory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.beanutils.BeanDeclaration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;
import org.junit.Before;
import org.junit.Test;

import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.memory.impl.summary.io.writer.ChainWriter;
import ar.uba.dc.analysis.memory.impl.summary.io.writer.GraphvizWriter;
import ar.uba.dc.analysis.memory.impl.summary.io.writer.NullWriter;
import ar.uba.dc.analysis.memory.impl.summary.io.writer.XMLWriter;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

public class ContainerBeanFactoryTest {

	protected XMLConfiguration config;
	
	@Before
	public void setUp() throws ConfigurationException {
		config = new XMLConfiguration("test-factory.xml");
		config.append(new PropertiesConfiguration("test.properties"));
		
		BeanHelper.registerBeanFactory("custom", new SingletonBeanFactory(new ReferenceBeanFactory(config)));
		BeanHelper.registerBeanFactory("container", new ContainerBeanFactory(config));
	}
	
	@Test
	public void returnDefaultImplementationIfSourceIsBlank() {
		SummaryWriter<MemorySummary> printer = getObject("test.chain-default");
		
		assertThat(printer, instanceOf(NullWriter.class));
	}
	
	@Test
	public void returnNullIfSourceIsBlankAndDefaultIsBlank() {
		SummaryWriter<MemorySummary> printer = getObject("test.chain-null");
		
		assertThat(printer, is(nullValue()));
	}
	
	@Test
	public void returnInstanceIfSourceHasOnlyOneElement() {
		SummaryWriter<MemorySummary> printer = getObject("test.chain-oneElement");
		
		assertThat(printer, is(notNullValue()));
		assertThat(printer, instanceOf(SummaryWriter.class));
	}
	
	@Test
	public void returnContainerInOrder() {
		SummaryWriter<MemorySummary> printer = getObject("test.chain-multipleElements");
		
		assertThat(printer, is(notNullValue()));
		assertThat(printer, instanceOf(ChainWriter.class));
		ChainWriter container = (ChainWriter) printer;
		
		assertThat(container.getWriters().size(), is(equalTo(3)));
		assertThat(container.getWriters().get(0), instanceOf(GraphvizWriter.class));
		assertThat(container.getWriters().get(1), instanceOf(XMLWriter.class));
		assertThat(container.getWriters().get(2), instanceOf(NullWriter.class));
		
		printer = getObject("test.chain-multipleElementsKey");
		
		assertThat(printer, is(notNullValue()));
		assertThat(printer, instanceOf(ChainWriter.class));
		container = (ChainWriter) printer;
		
		assertThat(container.getWriters().size(), is(equalTo(3)));
		assertThat(container.getWriters().get(0), instanceOf(GraphvizWriter.class));
		assertThat(container.getWriters().get(1), instanceOf(XMLWriter.class));
		assertThat(container.getWriters().get(2), instanceOf(NullWriter.class));
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getObject(String key) {
		BeanDeclaration decl = new XMLBeanDeclaration(config, key);
		return (T) BeanHelper.createBean(decl);
	}
}
