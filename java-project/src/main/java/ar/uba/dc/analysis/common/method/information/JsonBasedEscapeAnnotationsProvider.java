package ar.uba.dc.analysis.common.method.information;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootMethod;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_JsonParameter;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListDIParameters;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.SimpleDIParameter;
import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.io.reader.JsonIRReader.IntermediateRepresentationMethodDeserializer;
import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonIRWriter;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.summary.DefaultEscapeAnnotation;
import ar.uba.dc.analysis.escape.summary.EscapeAnnotation;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.util.location.MethodLocationStrategy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.sun.tools.javac.util.List;

public class JsonBasedEscapeAnnotationsProvider{

	private Map<String, EscapeAnnotation> annotations;
	private String location;
	

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
	
	public JsonBasedEscapeAnnotationsProvider()
	{
		
	}
	
	public JsonBasedEscapeAnnotationsProvider(String location)
	{
		this.location = location;
	}
	
	public void fetchAnnotations(String mainClass)
	{
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		

		//builder.registerTypeAdapter(ListDIParameters.class, new ListDIParametersDeserializer());
		builder.registerTypeAdapter(EscapeAnnotation.class, new EscapeAnnotationDeserializer());
		builder.registerTypeAdapter(DI_JsonParameter.class, new DI_JsonParameterDeserializer());
		
		
		
		this.gson = builder.create();

		if(location==null)
			location = locationStrategy.getEscapeAnnotationsLocation(mainClass);
		else
		{
			location = location + mainClass + ".json";
		}
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

	public boolean hasAnnotationFor(String name) {
		return annotations.containsKey(name);
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
	
	public static class EscapeAnnotationDeserializer implements JsonDeserializer<EscapeAnnotation> 
	{
	    
		@Override
		public EscapeAnnotation deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {
			JsonObject jobject = (JsonObject) json;			
			
			DefaultEscapeAnnotation annotation = new DefaultEscapeAnnotation();		
			
			
			Boolean fresh = jobject.get("fresh").getAsBoolean();
			annotation.setFresh(fresh);
			
			String name = jobject.get("name").getAsString();
			annotation.setName(name);
			
			
			annotation.setSignature(jobject.get("signature").getAsString());			
			
			Integer[] writableParameters = context.deserialize(jobject.get("writableParameters"), Integer[].class);
			annotation.setWritableParameters(List.from(writableParameters));

			JsonArray jrelevantParams = jobject.get("relevantParameters").getAsJsonArray();
			ListDIParameters lRelevantParams = new ListDIParameters();
			for(int i = 0;  i < jrelevantParams.size(); i++)
			{
				DI_JsonParameter jsonParameter = context.deserialize(jrelevantParams.get(i), DI_JsonParameter.class);
				lRelevantParams.add(jsonParameter);
			}			
			annotation.setRelevantParameters(lRelevantParams);
			
			JsonArray jParams = jobject.get("parameters").getAsJsonArray();
			ListDIParameters lParams = new ListDIParameters();
			for(int i = 0;  i < jParams.size(); i++)
			{
				DI_JsonParameter jsonParameter = context.deserialize(jParams.get(i), DI_JsonParameter.class);
				lParams.add(jsonParameter);
			}
			
			annotation.setParameters(lParams);
			
			
			return annotation;
			
		}
	}
	
	
	/*public static class ListDIParametersDeserializer implements JsonDeserializer<ListDIParameters> 
	{
	    
		@Override
		public ListDIParameters deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {
			JsonObject jobject = (JsonObject) json;
			JsonArray jparameters = jobject.get("fields").getAsJsonArray();
		    
			
			
			
			String name = context.deserialize(jobject.get("name"), DomainSet.class);
			JsonArray jfields = jobject.get("fields").getAsJsonArray();
		    for(int i = 0; i < jfields.size(); i++)
		    {
		    	DI_JsonParameter = context.deserialize(jfields.get(i), DI_JsonParameter.class);
		    }
		}
	}*/
	
	public static class DI_JsonParameterDeserializer implements JsonDeserializer<DI_JsonParameter> 
	{
	    
		@Override
		public DI_JsonParameter deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {
			JsonObject jobject = (JsonObject) json;
			DI_JsonParameter jsonParameter = new DI_JsonParameter();
			
			jsonParameter.name = jobject.get("name").getAsString();
			JsonArray jfields = jobject.get("fields").getAsJsonArray();
			ArrayList<DI_JsonParameter> fields = new ArrayList<DI_JsonParameter>();
			
			for(int i = 0;  i < jfields.size(); i++)
			{
				//recursive
				DI_JsonParameter jsonParameterField = context.deserialize(jfields.get(i), DI_JsonParameter.class);
				fields.add(jsonParameterField);
			}	
			jsonParameter.setFields(fields);
			
			return jsonParameter; 
			
		}
	}
	
	
}