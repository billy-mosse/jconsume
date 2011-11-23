package ar.uba.dc.config.factory;

import org.apache.commons.configuration.ConfigurationRuntimeException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.beanutils.BeanDeclaration;
import org.apache.commons.configuration.beanutils.BeanFactory;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.beanutils.DefaultBeanFactory;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;
import org.apache.commons.lang.StringUtils;

/**
 * Implementacion de {@link BeanFactory} que premite referenciar a otras definciones.
 * 
 * Si se encuentra definido el atributo <code>config-ref-to</code> el factory crea
 * el objeto buscando en el archivo de configuracion la declaracion de objetos con la clave
 * <code>config-ref-to</code>.
 * 
 * En caso de no existir el atributo o estar en blanco, la clase instancia el objeto definido 
 * por la declaracion de forma normal.
 * 
 * @author testis
 */
public class ReferenceBeanFactory extends DefaultBeanFactory {

	public static final String ATTR_BEAN_REF_TO = XMLBeanDeclaration.ATTR_PREFIX + "ref-to]";
	
	protected XMLConfiguration config;
	
	public ReferenceBeanFactory(XMLConfiguration config) {
		this.config = config;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized Object createBean(Class beanClass, BeanDeclaration decl, Object param) throws Exception {
		XMLBeanDeclaration beanDecl = (XMLBeanDeclaration) decl;
		Object bean = null;
		String refTo = beanDecl.getConfiguration().getString(ATTR_BEAN_REF_TO);
		
		if (StringUtils.isBlank(refTo)) {
			bean = super.createBean(beanClass, decl, param);
		} else {
			XMLBeanDeclaration pivoteDeclaration = new XMLBeanDeclaration(config, refTo);
			bean = BeanHelper.createBean(pivoteDeclaration);
			if (bean != null && !beanClass.isInstance(bean)) {
				throw new ConfigurationRuntimeException("El objeto definido por [" + refTo + "] no es instancia de la clase [" + beanClass.getName() + "]");
			}
		}
		
		return bean;
	}
}
