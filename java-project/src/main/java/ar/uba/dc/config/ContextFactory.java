package ar.uba.dc.config;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.PhaseOptions;
import soot.options.Options;

/**
 * Factory del contexto de ejecucion. Esta clase 
 * genera un objeto {@link Context} el cual permite al programador
 * acceder a la configuracion para la ejecucion asi como al {@link Factory}
 * y de esta forma flexibilizar las herramientas y analisis generados
 * 
 * @author testis
 */
public class ContextFactory {

	private static Log log = LogFactory.getLog(ContextFactory.class);
	
	/**
	 * Genera un context con la configuracion default.
	 * A su vez inicializa soot con las propiedades encontradas en los archivos por defecto
	 */
	public static Context getContext() {
		return getContext(null);
	}
	
	/**
	 * Genera un {@link Context} a partir de un archivo .properties. A su vez inicializa soot
	 * con los parametros encontrados en el archivo.
	 */
	public static Context getContext(String propertiesFile) {
		return getContext(propertiesFile, true);
	}
	
	/**
	 * Genera un {@link Context} a partir de un archivo .properties. Permite especificar si soot
	 * debe ser inicializado o no con las propiedades encontradas en el archivo especificado.
	 */
	public static Context getContext(String propertiesFile, boolean initSoot) {
		return getContext(propertiesFile, null, initSoot);
	}
	
	public static Context getContext(String propertiesFile, Map<String, String> override, boolean initSoot) {
		log.info("Building context from [" + propertiesFile + "]");
		try {
			CompositeConfiguration configuration = new CompositeConfiguration();
			configuration.addConfiguration(new SystemConfiguration());
			if (StringUtils.isNotBlank(propertiesFile)) {
				URL propertiesURL = ContextFactory.class.getClassLoader().getResource(propertiesFile);
				if(propertiesURL != null)
					configuration.addConfiguration(new PropertiesConfiguration(propertiesURL));
				else if(new File(propertiesFile).exists()) 
					configuration.addConfiguration(new PropertiesConfiguration(propertiesFile));
				
			}
			configuration.addConfiguration(new PropertiesConfiguration(ContextFactory.class.getClassLoader().getResource("application.properties")));

			if(override != null) {
				for (String key : override.keySet()) {
					configuration.setProperty(key, override.get(key));
				}
			}

			// Agregamos una propiedad que indique donde estan las clases del proyecto. Es util para los archivos de configuracion
		/*	String projectClasspath = ContextFactory.class.getClassLoader().getResource(StringUtils.EMPTY).getPath();
			if (projectClasspath.endsWith(File.separator)) {
				projectClasspath = projectClasspath.substring(0, projectClasspath.length() - File.separator.length());
			}
			configuration.addProperty("project.classes.classpath", projectClasspath);
			*/
			configuration.addProperty("project.classes.classpath", "");
			if (initSoot) {
				initSoot(configuration);
			}
			Context context = new Context(configuration);
			log.info("Building done");
			return context;
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * Inicializa soot en base a una configuracion dada
	 */
	protected static void initSoot(Configuration config) {
		if (config.getBoolean(Context.SOOT_OPTION_KEEP_LINE_NUMBER, false)) {
			Options.v().set_keep_line_number(true);
		}
		
		if (config.getBoolean(Context.SOOT_OPTION_BYTECODE_OFFSET, false)) {
			Options.v().set_keep_offset(true);
		}
		
		if (config.getBoolean(Context.SOOT_OPTION_APP, false)) {
			Options.v().set_app(true);
		}
		
		Options.v().set_output_dir(config.getString(Context.OUTPUT_FOLDER, "sootOutput"));

		for (Object opt : config.getList(Context.SOOT_PHASE_OPTIONS)) {
			String[] parts = opt.toString().split(":");
			PhaseOptions.v().processPhaseOptions(parts[0], parts[1] + ":" + parts[2]);
		}
	}
}