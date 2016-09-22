package ar.uba.dc.invariant.spec;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootClass;
import soot.SootMethod;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.escape.InterproceduralAnalysis;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.invariant.InvariantProvider;
import ar.uba.dc.invariant.spec.bean.ClassSpecification;
import ar.uba.dc.invariant.spec.bean.Specification;
import ar.uba.dc.invariant.spec.compiler.CompiledClassInvariantProvider;
import ar.uba.dc.invariant.spec.compiler.compilation.AllwaysEmptyInvariantProvider;
import ar.uba.dc.util.location.ClassLocationStrategy;

/**
 * Recuperamos los invariantes con el formato spec.
 * 
 * El formato es simple. Un archivo xml que contiene un tag spec. El mismo contiene un conjunto de clases.
 * Las clases poseen metodos para los cuales se especifican los parametros relevantes y los create-site y call-site.
 * A su vez podemos tener invariantes referenciados por Id
 * 
 * @author testis
 */
public class SpecInvariantProvider implements InvariantProvider {

	protected ClassLocationStrategy locationStrategy = null;
	protected SpecCompiler compiler = null;
	protected SpecReader reader = null;
	
	protected String lastFilePathUsed = null;
	protected CompiledClassInvariantProvider invariantProvider = null;
	protected Specification currentSpec = null;
	
	public SpecInvariantProvider() {
		super();
	}
	
	public DomainSet getInvariant(Statement stmt) {
			// Obtenemos la clase para la cual hay que buscar el invariante
		SootClass sootClass = stmt.belongsTo().getDeclaringClass();
		loadProvider(sootClass);
		
		return invariantProvider.getInvariant(stmt);
	}
	
	public DomainSet getInvariantWithBinding(Statement stmt, Operation operation) {
		SootClass sootClass = stmt.belongsTo().getDeclaringClass();
		loadProvider(sootClass);

		return invariantProvider.getInvariantWithBinding(stmt, operation);
	}

	public Set<String> getRelevantParameters(SootMethod method) {
			// Obtenemos la clase a la que pretenece el metodo
		loadProvider(method.getDeclaringClass());
			// recuperamos los parametros formales (aquellos q nos interesan para consumo
		return invariantProvider.getRelevantParameters(method);
	}
	
	public DomainSet getRequeriments(SootMethod method) {
		// Obtenemos la clase a la que pretenece el metodo
		loadProvider(method.getDeclaringClass());
		
		return invariantProvider.getRequirements(method);
	}
	
	public boolean isLoopInvariant(Statement stmt) {
		// Obtenemos la clase a la que pretenece el metodo
		loadProvider(stmt.belongsTo().getDeclaringClass());

		return invariantProvider.isLoopInvariant(stmt);
	}
	
	public boolean captureAllPartitions(Statement stmt) {
		// Obtenemos la clase a la que pretenece el metodo
		loadProvider(stmt.belongsTo().getDeclaringClass());
		
		return invariantProvider.captureAllPartitions(stmt);
	}

	private static Log log = LogFactory.getLog(SpecInvariantProvider.class);
	

	protected void loadProvider(SootClass sootClass) {
		// El provider que tenemos es para la clase que necesitamos? Si no lo es, generamos uno nuevo

		
		if (invariantProvider == null || !invariantProvider.forClass(sootClass.getName())) {
				// Obtenemos de donde sacar el archivo con la especificacion de invariantes para la clase que necesitamos
			String specFile = locationStrategy.getLocation(sootClass);
				// Es el ultimo archivo que leimos? De ser asi no hace falta recargar la especificacion 
			if (lastFilePathUsed == null || !lastFilePathUsed.equals(specFile)) {
				lastFilePathUsed = specFile;

				if(specFile.startsWith("jar")) {
					String[] values = specFile.split(":");
					try {
						URL spec = new URL("file:" + values[1]);
						URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{spec}, ClassLoader.getSystemClassLoader());
						InputStream is = classLoader.getResourceAsStream(values[2]);
						System.out.println("Buscando spec:" + values[0] + " " + values[1] + " " + values[2]);
						if(is != null) {
							currentSpec = reader.read(new InputStreamReader(is));
							System.out.println("Spec encontrada:" + values[0] + " " + values[1] + " " + values[2]);
						} else {
							currentSpec = null;
						}
					} catch (MalformedURLException e) {
						throw new RuntimeException("Ocurrio un error al procesar el archivo de especificaciones [" + specFile + "]: " + e.getMessage(), e);					
					}
					
				} else {
					//Primero busco en los resources
					InputStream is = this.getClass().getClassLoader().getResourceAsStream(specFile);
					if(is != null) {
						currentSpec = reader.read(new InputStreamReader(is));	
					} else if (new File(lastFilePathUsed).exists()) {
						try {
							currentSpec = reader.read(new FileReader(specFile));	
						} catch (FileNotFoundException e) {
							throw new RuntimeException("Ocurrio un error al procesar el archivo de especificaciones [" + specFile + "]: " + e.getMessage(), e);
						}
						
					} else {
						currentSpec = null;
					}
				}
					
			}
			
			// Usamos un provider que siempre devuelve invariantes "nullos" o "vacios"
			invariantProvider = new AllwaysEmptyInvariantProvider(sootClass.getName());
			
			if (currentSpec != null) {
					// Buscamos la especificacion para la clase
				ClassSpecification classSpec = currentSpec.get(sootClass.getName());
				if (classSpec != null) {
						// Compilamos la especificacion
					invariantProvider = compiler.compile(classSpec);
				}
			}
		}
	}

	public void setLocationStrategy(ClassLocationStrategy locationStrategy) {
		this.locationStrategy = locationStrategy;
	}

	public void setCompiler(SpecCompiler compiler) {
		this.compiler = compiler;
	}

	public void setReader(SpecReader reader) {
		this.reader = reader;
	}
}
