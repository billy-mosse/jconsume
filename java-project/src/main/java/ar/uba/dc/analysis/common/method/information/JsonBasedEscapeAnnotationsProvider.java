package ar.uba.dc.analysis.common.method.information;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonIRWriter;
import ar.uba.dc.analysis.escape.summary.DefaultEscapeAnnotation;
import ar.uba.dc.analysis.escape.summary.EscapeAnnotation;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.util.location.MethodLocationStrategy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class JsonBasedEscapeAnnotationsProvider {

	private Map<String, EscapeAnnotation> annotations;
	

	/*public JsonBasedEscapeAnnotationsProvider() {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		
		this.gson = builder.create();

		String location = locationStrategy.getEscapeAnnotationsLocation(mainClass);
		Reader reader;
		try {
			reader = new FileReader(location);
			annotations = this.read(reader);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public void fetchAnnotations(String mainClass)
	{
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		
		this.gson = builder.create();

		String location = locationStrategy.getEscapeAnnotationsLocation(mainClass);
		Reader reader;
		try {
			reader = new FileReader(location);
			annotations = this.read(reader);
		}		
			
		 catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			this.annotations = new HashMap<String, EscapeAnnotation>();
		}
	}
	
	public EscapeAnnotation get(String methodName)
	{
		return annotations.get(methodName);
	}
	
	private static Log log = LogFactory.getLog(JsonIRWriter.class);
	
	//protected XStream xstream;
	
	protected Gson gson;
	
	protected MethodLocationStrategy locationStrategy;
	private String mainClass;
	//protected String basePath;
	
	
	public MethodLocationStrategy getLocationStrategy() {
		return locationStrategy;
	}

	public void setLocationStrategy(MethodLocationStrategy locationStrategy) {
		this.locationStrategy = locationStrategy;
	}
	
	
	public String getMainClass() {
		return mainClass;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	public Map<String, EscapeAnnotation> read(Reader reader) {
		
		//foreach...
		try {
			DefaultEscapeAnnotation[] annotations = this.gson.fromJson(reader, DefaultEscapeAnnotation[].class); 
			
			Map<String, EscapeAnnotation> annotationsMap = new HashMap<String, EscapeAnnotation>();
			for(int i = 0; i< annotations.length; i++)
			{
				DefaultEscapeAnnotation escapeAnnotation = annotations[i];
				annotationsMap.put(escapeAnnotation.getName(), escapeAnnotation);
			}
			return annotationsMap;

		} catch (Exception e) {
			//log.error("Error. " + e.getMessage(), e);
			return null;
		}
		
	}
	
	/*
	public static class AnnotationsContainerDeserializer implements JsonDeserializer<MemoryAnnotationsContainer> 
	{
	    
		@Override
		public MemoryAnnotationsContainer deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {
			MemoryAnnotationsContainer container = new MemoryAnnotationsContainer();
			
			JsonObject jobject = (JsonObject) json;
			JsonArray jbindingPairs = jobject.get("annotations").getAsJsonArray();
			
			Map<String, MemoryAnnotation> mapAnnotations = new HashMap<String,MemoryAnnotation>();
			
			for(int i = 0; i < jbindingPairs.size(); i++)
			{
				JsonObject annotationJson = (JsonObject) jbindingPairs.get(i);
				String method_name = annotationJson.get("method_name").getAsString();
				String class_name = annotationJson.get("class_name").getAsString();
				String sConsumption = annotationJson.get("consumption").getAsString();
				
				PiecewiseQuasipolynomial consumption = new PiecewiseQuasipolynomial(sConsumption);
				//TODO encapsular
				
				MemoryAnnotation annotation = new MemoryAnnotation(consumption);
				container.annotations.put(class_name + "." + method_name , annotation);
			}			
			
			return container;
		}
		
	}	*/
}