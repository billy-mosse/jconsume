package ar.uba.dc.config.factory;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.beanutils.BeanDeclaration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.beanutils.DefaultBeanFactory;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.config.Container;

/**
 * Factory que permite definir contenedores de varias instancias.
 * 
 * La clase contenedora a instanciar sera la indicada por el atributo <code>config-class</code>.
 * 
 * Los objetos contenidos por el contenedor seran recuperados haciendo la seleccion de un path dado.
 * 
 * El path a buscar estara indicado por el atributo <code>config-source</code>. Luego, el contendor contendra
 * los descendientes de ese path indicados por <code>config-select</code>. Se asume que ese atributo es una 
 * lista separada por comas.
 * 
 * En caso de que <code>config-select</code> este en blanco o no se haya indicado, se instanciara en lugar del
 * contenedor el objeto <code>config-source</code>.<code>config-default</code>
 * 
 * Si <code>config-default</code> y <code>config-select</code> estan en blanco, se devuelve null
 * 
 * @author testis
 */
public class ContainerBeanFactory extends DefaultBeanFactory {

	public static final String ATTR_CONTAINER_SOURCE = XMLBeanDeclaration.ATTR_PREFIX + "source]";
	
	public static final String ATTR_CONTAINER_SELECT = XMLBeanDeclaration.ATTR_PREFIX + "select]";
	
	public static final String ATTR_CONTAINER_SELECT_KEY = XMLBeanDeclaration.ATTR_PREFIX + "select-key]";
	
	public static final String ATTR_CONTAINER_DEFAULT = XMLBeanDeclaration.ATTR_PREFIX + "default]";
	
	private static Log log = LogFactory.getLog(ContainerBeanFactory.class);
	
	protected XMLConfiguration config;
	
	public ContainerBeanFactory(XMLConfiguration config) {
		this.config = config;
	}

	@SuppressWarnings("unchecked")
	public Object createBean(Class beanClass, BeanDeclaration data, Object parameter) throws Exception {
		Object ret = null;
		XMLBeanDeclaration decl = (XMLBeanDeclaration) data;
		String source = decl.getConfiguration().getString(ATTR_CONTAINER_SOURCE);
	
		log.debug("Source [" + source + "]");
		if (StringUtils.isNotBlank(source)) {
			 String[] elements = decl.getConfiguration().getStringArray(ATTR_CONTAINER_SELECT);
			
			 if (ArrayUtils.isEmpty(elements) || (elements.length == 1 && StringUtils.isBlank(elements[0]))) {
				String selectKey = decl.getConfiguration().getString(ATTR_CONTAINER_SELECT_KEY);
				log.debug("SelectKey [" + selectKey + "]");
				elements = config.getStringArray(selectKey);
			}			
			
			if (ArrayUtils.isEmpty(elements) || (elements.length == 1 && StringUtils.isBlank(elements[0]))) {
				elements = new String[] { decl.getConfiguration().getString(ATTR_CONTAINER_DEFAULT, StringUtils.EMPTY) };
			}
			
			if (elements.length > 1) {
				Container<Object> container = (Container<Object>) super.createBean(beanClass, data, parameter);
				for (String element : elements) {
					container.register(doCreateBean(source, element));
				}
				ret = container;
			} else {
				if (StringUtils.isNotBlank(elements[0])) {
					ret = doCreateBean(source, elements[0]);
				}
			}
		}
		
		return ret;
	}
	
	protected Object doCreateBean(String source, String target) {
		return  BeanHelper.createBean(new XMLBeanDeclaration(config, source + "." + target));
	}
}
