package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import ar.uba.dc.analysis.common.method.information.JsonInstrumentationSiteInvariantsReader;
import ar.uba.dc.annotations.AnnotationSiteInvariantForJson;

public class InstrumentationSiteInvariantsReader {

	protected List<AnnotationSiteInvariantForJson> annotations;
	protected List<BindingAnnotation> bindingAnnotations;
	public List<AnnotationSiteInvariantForJson> getAnnotations() {
		return annotations;
	}
	
	public List<BindingAnnotation> getBindingAnnotations() {
		return bindingAnnotations;
	}
	
	public void setAnnotations(List<AnnotationSiteInvariantForJson> annotations) {
		this.annotations = annotations;
	}
	public void analyze(String location) {
		File srcFile = new File(location);
		this.annotations = new ArrayList<AnnotationSiteInvariantForJson>();
		this.bindingAnnotations = new ArrayList<BindingAnnotation>();
		
		
		if (srcFile.exists()) {
			try {
				JsonInstrumentationSiteInvariantsReader reader= new JsonInstrumentationSiteInvariantsReader();
				JsonReader jsonReader = new JsonReader(new FileReader(srcFile));
				/*while(jsonReader.hasNext() && !jsonReader.peek().equals(JsonToken.END_DOCUMENT))
				{
					AnnotationSiteInvariantForJson annotation = reader.read(jsonReader);					
					this.annotations.add(annotation);				
					
					BindingAnnotation bindingAnnotation = new BindingAnnotation(annotation.getMethodName(), annotation.getNewRelevantParameters());
					this.bindingAnnotations.add(bindingAnnotation);
				}*/
				
				List<AnnotationSiteInvariantForJson> fileAnnotations = reader.read(jsonReader);
				for(AnnotationSiteInvariantForJson annotation : fileAnnotations)
				{
					this.annotations.add(annotation);
					BindingAnnotation bindingAnnotation = new BindingAnnotation(annotation.getMethodName(), annotation.getNewRelevantParameters());
					this.bindingAnnotations.add(bindingAnnotation);
				}
				
				
				
			} catch (FileNotFoundException e) {
				//log.error("Error al recuperar el summary de escape para el metodo [" + method.toString() + "] a xml: " + e.getMessage(), e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
