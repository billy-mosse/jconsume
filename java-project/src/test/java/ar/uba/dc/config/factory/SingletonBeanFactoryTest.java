package ar.uba.dc.config.factory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.beanutils.BeanDeclaration;
import org.apache.commons.configuration.beanutils.BeanFactory;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.beanutils.DefaultBeanFactory;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;
import org.junit.Before;
import org.junit.Test;

import ar.uba.dc.barvinok.calculators.ConstantCalculator;
import ar.uba.dc.util.location.FileLocationStrategy;

/**
 * Implementacion de {@link BeanFactory} que permite agregar un scope a una declaracion
 * si la declaracion del bean tiene scope <code>singleton</code> entonces el factory
 * almacena la instancia creada y si se vuelve a pedir un objeto con el mismo path
 * se devuelve la instancia. 
 * 
 * De esta forma tenemos un factory que soporta singletons por path en la declaracion.
 * 
 * Este factory delega en otro factory la creacion de objetos. De esta forma permitimos
 * agregar a cualquier factory existente la capacidada de soportar singletons
 * 
 * @author testis
 */
public class SingletonBeanFactoryTest {

	protected XMLConfiguration config;
	
	@Before
	public void setUp() throws ConfigurationException {
		config = new XMLConfiguration("test-factory.xml");
		config.append(new PropertiesConfiguration("test.properties"));
		
		BeanHelper.deregisterBeanFactory("custom");
		BeanHelper.registerBeanFactory("custom", new SingletonBeanFactory(DefaultBeanFactory.INSTANCE));
	}

	@Test
	public void singletonInstancesAreSingleton() {		
		ConstantCalculator singleton = getObject("test.singleton");
		
		assertThat(singleton, sameInstance(getObject("test.singleton")));
		
		FileLocationStrategy locationStrategy = getObject("location.type.xml");
		FileLocationStrategy otherlocationStrategy = getObject("location.type.xml");
		
		assertThat(locationStrategy, sameInstance(otherlocationStrategy));
		assertThat(locationStrategy.getRepositoryFile(), is(equalTo(otherlocationStrategy.getRepositoryFile())));
	}

	@Test
	public void singletonAreIdentifiedByKey() {
		ConstantCalculator singleton = getObject("test.singleton");
		ConstantCalculator clone = getObject("test.copy");
		
		assertThat(singleton, is(not(sameInstance(clone))));
	}
	
	@Test
	public void singletonDelegateCreationToOtherFactory() {
		BeanHelper.deregisterBeanFactory("custom");
		BeanHelper.registerBeanFactory("custom", new SingletonBeanFactory(new ReferenceBeanFactory(config)));

		FileLocationStrategy refSingletonPrinter = getObject("test.ref-singleton");
		
		assertThat(refSingletonPrinter, sameInstance(getObject("location.type.xml")));
		
		FileLocationStrategy refCloneStrategy = getObject("test.ref-copy");
		FileLocationStrategy cloneOriginal = getObject("location.type.dot");
		
		assertThat(refCloneStrategy, is(not(sameInstance(cloneOriginal))));
		assertThat(refCloneStrategy.getRepositoryFile(), is(equalTo(cloneOriginal.getRepositoryFile())));
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getObject(String key) {
		BeanDeclaration decl = new XMLBeanDeclaration(config, key);
		return (T) BeanHelper.createBean(decl);
	}
}
