package ar.uba.dc.config.factory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.beanutils.BeanDeclaration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;
import org.junit.Before;
import org.junit.Test;

import ar.uba.dc.util.location.FileLocationStrategy;

public class ReferenceBeanFactoryTest {

	protected XMLConfiguration config;
	
	@Before
	public void setUp() throws ConfigurationException {
		config = new XMLConfiguration("test-factory.xml");
		config.append(new PropertiesConfiguration("test.properties"));
		
		BeanHelper.deregisterBeanFactory("custom");
		BeanHelper.registerBeanFactory("custom", new ReferenceBeanFactory(config));
	}
	
	@Test
	public void resolveReferences() {
		FileLocationStrategy refSingletonStrategy = getObject("test.ref-singleton");
		
		assertThat(refSingletonStrategy, is(notNullValue()));
		assertThat(refSingletonStrategy.getRepositoryFile(), is(equalTo("/xml")));
		
		FileLocationStrategy refCloneStrategy = getObject("test.ref-copy");
		
		assertThat(refCloneStrategy, is(notNullValue()));
		assertThat(refCloneStrategy.getRepositoryFile(), is(equalTo("/dot")));
	}
	
	@Test
	public void referenceCanHaveProperties() {
		config.addProperty("ref.type", "xml");
		
		FileLocationStrategy refSingletonStrategy = getObject("test.ref-with-ref");
		
		assertThat(refSingletonStrategy, is(notNullValue()));
		assertThat(refSingletonStrategy.getRepositoryFile(), is(equalTo("/xml")));
		
		config.setProperty("ref.type", "dot");
		
		FileLocationStrategy refCloneStrategy = getObject("test.ref-with-ref");
		
		assertThat(refCloneStrategy, is(notNullValue()));
		assertThat(refCloneStrategy.getRepositoryFile(), is(equalTo("/dot")));
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getObject(String key) {
		BeanDeclaration decl = new XMLBeanDeclaration(config, key);
		return (T) BeanHelper.createBean(decl);
	}
}
