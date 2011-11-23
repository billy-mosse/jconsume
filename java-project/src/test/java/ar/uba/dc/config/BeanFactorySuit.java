package ar.uba.dc.config;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ar.uba.dc.config.factory.ContainerBeanFactoryTest;
import ar.uba.dc.config.factory.ReferenceBeanFactoryTest;
import ar.uba.dc.config.factory.SingletonBeanFactoryTest;

@RunWith(Suite.class)
@SuiteClasses({	ContainerBeanFactoryTest.class, 
				SingletonBeanFactoryTest.class,
				ReferenceBeanFactoryTest.class })
public class BeanFactorySuit {

}
