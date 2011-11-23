package ar.uba.dc.config.factory;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.beanutils.BeanDeclaration;
import org.apache.commons.configuration.beanutils.BeanFactory;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;
import org.apache.commons.configuration.tree.ConfigurationNode;
import org.apache.commons.lang.StringUtils;

/**
 * Implementacion de {@link BeanFactory} que premite que las instancias sean singleton.
 * 
 * Para que una instancia sea singleton debe estar presente el atributo <code>config-scope</code> y
 * tener el valor <code>singleton</code>.
 * 
 * Para generar instancias singleton se asocia el objeto creado al path dentro del XML
 * que define el comportamiento del factory. De esta forma, si se combina este factory con el {@link ReferenceBeanFactory} es
 * posible generar un contenedor que permite referenciar a instancias singleton
 * 
 * @author testis
 */
public class SingletonBeanFactory implements BeanFactory {

	public static final String ATTR_BEAN_SCOPE = XMLBeanDeclaration.ATTR_PREFIX + "scope]";
	public static final String BEAN_SCOPE_SINGLETON = "singleton";
	
	protected Map<String, Object> instances;
	
	protected BeanFactory factory;
	
	public SingletonBeanFactory(BeanFactory instance) {
		this.factory = instance;
		this.instances = new HashMap<String, Object>();
	}

	@SuppressWarnings("unchecked")
	public Object createBean(Class beanClass, BeanDeclaration data, Object param) throws Exception {
		XMLBeanDeclaration decl = (XMLBeanDeclaration) data;
		String scope = decl.getConfiguration().getString(ATTR_BEAN_SCOPE);
		
		Object bean = null;
		String id = null;
		if (BEAN_SCOPE_SINGLETON.equals(scope)) {
			id = retriveId(decl);
			bean = instances.get(id);
		}
		
		if (bean == null) {
			bean = factory.createBean(beanClass, decl, param);
		}
		
		if (bean != null && BEAN_SCOPE_SINGLETON.equals(scope)) {
			instances.put(id, bean);
		}
		
		return bean;		
	}

	@SuppressWarnings("unchecked")
	public Class getDefaultBeanClass() {
		return factory.getDefaultBeanClass();
	}
	
	protected String retriveId(XMLBeanDeclaration beanDecl) {
		String id = StringUtils.EMPTY;
		
		ConfigurationNode node = beanDecl.getNode();
		while (node.getParentNode() != null) {
			id = "." + node.getName() + id;
			node = node.getParentNode();
		}
		
		return id.substring(1); 
	}
}
