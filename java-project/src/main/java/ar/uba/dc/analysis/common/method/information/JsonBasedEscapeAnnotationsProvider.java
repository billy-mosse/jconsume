package ar.uba.dc.analysis.common.method.information;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DI_JsonParameter;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListDIParameters;
import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonIRWriter;
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
	
	
	public Collection<EscapeAnnotation> getAnnotations()
	{
		return this.annotations.values();
	}
	
	public void fetchAnnotations(String mainClass)
	{
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		

		//builder.registerTypeAdapter(ListDIParameters.class, new ListDIParametersDeserializer());
		builder.registerTypeAdapter(DefaultEscapeAnnotation.class, new DefaultEscapeAnnotationDeserializer());
		builder.registerTypeAdapter(DI_JsonParameter.class, new DI_JsonParameterDeserializer());
		builder.registerTypeAdapter(DomainSet.class, new DomainSetDeserializer());
		
		
		
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
			log.error("Error. " + e.getMessage(), e);
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
	
	
	
	private static String getNameFromSignature(String signature)
	{
		//<ar.uba.dc.annotations.Demo03: ar.uba.dc.annotations.MyInteger doSomething(ar.uba.dc.annotations.MyInteger)>
		String[] parts = signature.split(" ");
		String className = parts[0];
		className = className.substring(1, className.length()-1);
		String name = parts[2].substring(0, parts[2].indexOf("("));
		
		return className + "." + name;
	}
	
	public static class DefaultEscapeAnnotationDeserializer implements JsonDeserializer<DefaultEscapeAnnotation> 
	{
	    
		@Override
		public DefaultEscapeAnnotation deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {
			JsonObject jobject = (JsonObject) json;			
			
			DefaultEscapeAnnotation annotation = new DefaultEscapeAnnotation();		
			
			
			Boolean fresh = jobject.get("fresh").getAsBoolean();
			annotation.setFresh(fresh);
			
		
			
			String maxLive= jobject.get("maxLive").getAsString();
			annotation.setMaxLiveFromString(maxLive);			
			
			String escape= jobject.get("escape").getAsString();
			annotation.setEscapeFromString(escape);
			

			String signature = jobject.get("signature").getAsString();
			annotation.setSignature(signature);
			
			annotation.setName(getNameFromSignature(signature));
			
			//DomainSet requirements = context.deserialize(jobject.get("requirements"), DomainSet.class);
			
			annotation.setMethodRequirements(new DomainSet());			
			
			
			Integer[] writableParameters = context.deserialize(jobject.get("writableParameters"), Integer[].class);
			annotation.setWritableParameters(Arrays.asList(writableParameters));

			JsonArray jrelevantParams = jobject.get("relevantParameters").getAsJsonArray();
			ListDIParameters lRelevantParams = new ListDIParameters();
			int size = jrelevantParams.size();
			for(int i = 0;  i < jrelevantParams.size(); i++)
			{
				DI_JsonParameter jsonParameter = context.deserialize(jrelevantParams.get(i), DI_JsonParameter.class);
				
				//el nombre deberia ser completo?
				//jsonParameter.setName(name + "." + jsonParameter.getName());
				lRelevantParams.add(jsonParameter);
			}			
			annotation.setRelevantParameters(lRelevantParams);
			
			
			updateParamsNames(lRelevantParams);
			
			JsonArray jParams = jobject.get("parameters").getAsJsonArray();
			ListDIParameters lParams = new ListDIParameters();
			for(int i = 0;  i < jParams.size(); i++)
			{
				DI_JsonParameter jsonParameter = context.deserialize(jParams.get(i), DI_JsonParameter.class);
				//jsonParameter.setName(name + "." + jsonParameter.getName());
				lParams.add(jsonParameter);
			}
			
			updateParamsNames(lParams);
			
			annotation.setParameters(lParams);
			
			
			return annotation;
			
		}
	}
	
	public static class DomainSetDeserializer implements JsonDeserializer<DomainSet> 
	{
	    
		@Override
		public DomainSet deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {
			JsonObject jobject = (JsonObject) json;
			DomainSet invariant = new DomainSet();
			
			Set<String> variables = new TreeSet<String>();
			
			JsonArray arr = jobject.get("variables").getAsJsonArray();
			
			for(int i = 0; i < arr.size(); i ++)
			{
				variables.add(arr.get(i).getAsString());
			}
			
			
			String barbinok_expression = jobject.get("expression").getAsString();
			invariant = new DomainSet(barbinok_expression , variables, true);

			return invariant;
			
		}
	}
	
	
	//prefix always ends with a dot, for an easier implementation
	public static void visit(DI_JsonParameter jsonParameter, String prefix)
	{		
		Iterator<DI_JsonParameter> it = jsonParameter.getDerivedVariables2().iterator();
		while(it.hasNext())
		{
			DI_JsonParameter param = it.next();
			
			//las que no son hojas tambien necesitan updetearse, me parece
			param.name = prefix + param.name; 
			String new_prefix = param.name + ".";
			visit(param, new_prefix);
		}		
	}
	
	
	public static void updateParamsNames(ListDIParameters params)
	{
		Iterator<DI_JsonParameter> it = params.iterator();
		while(it.hasNext())
		{
			DI_JsonParameter param = it.next();
			visit(param, param.name + ".");
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
			ListDIParameters fields = new ListDIParameters();
			
			for(int i = 0;  i < jfields.size(); i++)
			{
				//recursive
				DI_JsonParameter jsonParameterField = context.deserialize(jfields.get(i), DI_JsonParameter.class);
				//jsonParameterField.name = jsonParameter.name + "." + jsonParameterField.name; 
				fields.add(jsonParameterField);
			}	
			jsonParameter.setDerivedVarsForSpec(fields);
			
			return jsonParameter; 
			
		}
	}
	
	
}