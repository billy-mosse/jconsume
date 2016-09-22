package ar.uba.dc.binding.spec;

import ar.uba.dc.binding.spec.compiler.CompiledClassBindingProvider;
import ar.uba.dc.binding.spec.compiler.compilation.AllwaysEmptyBindingProvider;
import ar.uba.dc.invariant.spec.SpecReader;
import ar.uba.dc.invariant.spec.bean.ClassSpecification;
import ar.uba.dc.invariant.spec.bean.Specification;
import ar.uba.dc.invariant.spec.compiler.CompiledClassInvariantProvider;
import ar.uba.dc.invariant.spec.compiler.compilation.AllwaysEmptyInvariantProvider;
import ar.uba.dc.util.location.ClassLocationStrategy;
import soot.SootClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import ar.uba.dc.binding.BindingProvider;

public class SpecBindingProvider implements BindingProvider{
	protected ClassLocationStrategy locationStrategy = null;
	protected SpecCompiler compiler = null;
	protected SpecReader reader = null;
	
	protected String lastFilePathUsed = null;
	protected CompiledClassBindingProvider bindingProvider = null;
	protected Specification currentSpec = null;
	
	
	protected void loadProvider(SootClass sootClass) {
		// El provider que tenemos es para la clase que necesitamos? Si no lo es, generamos uno nuevo

		
		if (bindingProvider == null || !bindingProvider.forClass(sootClass.getName())) {
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
			bindingProvider = new AllwaysEmptyBindingProvider(sootClass.getName());
			
			if (currentSpec != null) {
					// Buscamos la especificacion para la clase
				ClassSpecification classSpec = currentSpec.get(sootClass.getName());
				if (classSpec != null) {
						// Compilamos la especificacion
					bindingProvider = compiler.compile(classSpec);
				}
			}
		}
	}
}
