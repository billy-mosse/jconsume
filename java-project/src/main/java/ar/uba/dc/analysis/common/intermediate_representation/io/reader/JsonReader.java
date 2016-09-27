package ar.uba.dc.analysis.common.intermediate_representation.io.reader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.SummaryReader;
import ar.uba.dc.analysis.common.intermediate_representation.DefaultIntermediateRepresentationParameter;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethodBody;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameterWithType;
import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonWriter;
import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonWriter.DefaultIntermediateRepresentationParameterSerializer;
import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonWriter.IntermediateRepresentationMethodBodySerializer;
import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonWriter.IntermediateRepresentationMethodSerializer;
//import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonWriter.IntermediateRepresentationParameterWithTypeSerializer;
import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonWriter.InvocationSerializer;
import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonWriter.LineSerializer;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.summary.io.xstream.XStreamFactory;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.soot.xstream.StatementIdConverter;
import ar.uba.dc.util.location.MethodLocationStrategy;
import decorations.Binding;
import decorations.BindingPair;


public class JsonReader implements SummaryReader<IntermediateRepresentationMethod> {
	private static Log log = LogFactory.getLog(JsonWriter.class);

protected Gson gson;
	
	protected MethodLocationStrategy locationStrategy;
	
	public JsonReader() {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();

		builder.registerTypeAdapter(IntermediateRepresentationMethod.class, new IntermediateRepresentationMethodDeserializer());
		builder.registerTypeAdapter(Line.class, new LineDeserializer());
		builder.registerTypeAdapter(Invocation.class, new InvocationDeserializer());
		builder.registerTypeAdapter(IntermediateRepresentationMethodBody.class, new IntermediateRepresentationMethodBodyDeserializer());
		builder.registerTypeAdapter(Binding.class, new BindingDeserializer());
		
		
		
		//builder.registerTypeAdapter(IntermediateRepresentationParameterWithType.class, new IntermediateRepresentationParameterWithTypeSerializer());
		//builder.registerTypeAdapter(IntermediateRepresentationMethodBody.class, new IntermediateRepresentationMethodBodySerializer());	
		//builder.registerTypeAdapter(Line.class, new LineSerializer());
		
		//builder.registerTypeAdapter(Invocation.class, new InvocationSerializer());
		//builder.registerTypeAdapter(DefaultIntermediateRepresentationParameter.class, new DefaultIntermediateRepresentationParameterSerializer());
		
		this.gson = builder.create();

	}
	
	public MethodLocationStrategy getLocationStrategy() {
		return locationStrategy;
	}

	public void setLocationStrategy(MethodLocationStrategy locationStrategy) {
		this.locationStrategy = locationStrategy;
	}

	@Override
	public IntermediateRepresentationMethod read(Reader reader) {
		

		try {
			IntermediateRepresentationMethod m = this.gson.fromJson(reader, IntermediateRepresentationMethod.class); 
			return m;
		} catch (Exception e) {
			log.error("Error. " + e.getMessage(), e);
			return null;
		}
		
	}
	
	
	public static class IntermediateRepresentationMethodDeserializer implements JsonDeserializer<IntermediateRepresentationMethod> 
	{
	    
		@Override
		public IntermediateRepresentationMethod deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {

		    JsonObject jobject = (JsonObject) json;

			log.debug("json: " + jobject.toString());
		    IntermediateRepresentationMethod m = new IntermediateRepresentationMethod();
		    m.setName(jobject.get("name").getAsString());
		    
		    m.setDeclaringClass(jobject.get("declaring_class").getAsString());
		    
		    Set<IntermediateRepresentationParameterWithType> parameters  = new LinkedHashSet<IntermediateRepresentationParameterWithType>();
		    
		    
		    JsonArray jparameters = jobject.get("parameters").getAsJsonArray();
		    for(int i = 0; i < jparameters.size(); i++)
		    {
		    	//No necesita un custom deserializer porque tiene tipos primitivos adentro
		    	IntermediateRepresentationParameterWithType p = context.deserialize(jparameters.get(i), IntermediateRepresentationParameterWithType.class);
		    	parameters.add(p);
		    }
		    
		    m.setParameters(parameters);
		    
		    m.setReturnType(jobject.get("return_type").getAsString());
		    
		    
		    IntermediateRepresentationMethodBody body = context.deserialize(jobject.get("body"), IntermediateRepresentationMethodBody.class);
		    
		    
		    m.setBody(body);
		    
		    
		    JsonArray jEscapeNodes = jobject.get("escapeNodes").getAsJsonArray();
			
			Set<String> escapeNodes = new LinkedHashSet<String>();
			
			
		    for(int i = 0; i < jEscapeNodes.size(); i++)
		    {
		    	String escapeNode = jEscapeNodes.get(i).getAsString();		    	
		    	escapeNodes.add(escapeNode);
		    }
		    
		    
		    
		    
		    
		    
		   /* m.setBody(
		    		(IntermediateRepresentationMethodBody) context.deserialize(jobject.get("body").getAsJsonObject(), 
		    																	IntermediateRepresentationMethodBody.class));*/
	    
	    
		    return m;
		}			
			
	}
	
	public static class IntermediateRepresentationMethodBodyDeserializer implements JsonDeserializer<IntermediateRepresentationMethodBody> 
	{
	    
		@Override
		public IntermediateRepresentationMethodBody deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {

			IntermediateRepresentationMethodBody body = new IntermediateRepresentationMethodBody();
			JsonObject jobject = (JsonObject) json;
			JsonArray jlines = jobject.get("lines").getAsJsonArray();
			
			Set<Line> lines = new LinkedHashSet<Line>();
			
		    for(int i = 0; i < jlines.size(); i++)
		    {
		    	//No necesita un custom deserializer porque tiene tipos primitivos adentro
		    	Line l = context.deserialize(jlines.get(i), Line.class);
		    	lines.add(l);
		    }
		    
		    body.setLines(lines);
		    return body;
		}
	}
	
	
	public static class LineDeserializer implements JsonDeserializer<Line> 
	{
	    
		@Override
		public Line deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {


			Line line = new Line();
			
			
			JsonObject jobject = (JsonObject) json;
			
			
			//TODO: agregar inductives y toda la bola
			line.setInvariant(new DomainSet(jobject.get("invariant").getAsString()));
			line.setName(jobject.get("name").getAsString());
			
			line.setLineNumber(jobject.get("line_number").getAsInt());
			
			
			JsonArray jinvocations = jobject.get("invocations").getAsJsonArray();
			
			List<Invocation> invocations = new LinkedList<Invocation>();
			
			
		    for(int i = 0; i < jinvocations.size(); i++)
		    {
		    	//No necesita un custom deserializer porque tiene tipos primitivos adentro
		    	Invocation invocation = context.deserialize(jinvocations.get(i), Invocation.class);
		    	invocations.add(invocation);
		    }
		    
		    line.setInvocations(invocations);
		    
		    
		    line.setBinding((Binding)context.deserialize(jobject.get("binding"), Binding.class));
		    
		    return line;
		}
	}
	
	
	
	public static class BindingDeserializer implements JsonDeserializer<Binding> 
	{
	    
		@Override
		public Binding deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {
			Binding binding = new Binding();
			
			JsonObject jobject = (JsonObject) json;
			JsonArray jbindingPairs = jobject.get("bindingPairs").getAsJsonArray();
			
			Set<BindingPair> bindingPairs = new HashSet<BindingPair>();
			for(int i = 0; i < jbindingPairs.size(); i++)
			{
				BindingPair bp = context.deserialize(jbindingPairs.get(i), BindingPair.class);
				bindingPairs.add(bp);				
			}
			binding.setBindingPairs(bindingPairs);
			
			return binding;
		}
		
	}
	
	public static class InvocationDeserializer implements JsonDeserializer<Invocation> 
	{
	    
		@Override
		public Invocation deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {


			Invocation invocation = new Invocation();
			
			
			JsonObject jobject = (JsonObject) json;

			invocation.setClass_called(jobject.get("class_called").getAsString());			
			
			//invocation.setName( jobject.get("name").getAsString());
			
			
			invocation.setCalled_implementation_signature(jobject.get("called_implementation_signature").getAsString());
			
			
			PaperPointsToHeapPartition hp = context.deserialize(jobject.get("hp"), PaperPointsToHeapPartition.class);
			invocation.setHeapPartition(hp);
			
			
			JsonArray jparameters = jobject.get("parameters").getAsJsonArray();
			
			Set<DefaultIntermediateRepresentationParameter> parameters = new LinkedHashSet<DefaultIntermediateRepresentationParameter>();
			
			
		    for(int i = 0; i < jparameters.size(); i++)
		    {
		    	//No necesita un custom deserializer porque tiene tipos primitivos adentro
		    	DefaultIntermediateRepresentationParameter p = context.deserialize(jparameters.get(i), DefaultIntermediateRepresentationParameter.class);
		    	parameters.add(p);
		    }
		    
		    
		    
		    invocation.setParameters(parameters);
		    
		    return invocation;
		    
		}
	}
	
	
	
	/*public static class IntermediateRepresentationParameterWithTypeDeserializer implements JsonDeserializer<IntermediateRepresentationParameterWithType> 
	{
	    
		@Override
		public IntermediateRepresentationParameterWithType deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {

			JsonObject jobject = (JsonObject) json;

		    IntermediateRepresentationParameterWithType p = new IntermediateRepresentationParameterWithType();
		    
		    p.setName(jobject.get("name").getAsString());
		    return p;
		}

	}*/
}
