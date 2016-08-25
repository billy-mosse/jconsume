package ar.uba.dc.analysis.common.intermediate_representation.io.reader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
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
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameterWithType;
import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonWriter;
import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonWriter.DefaultIntermediateRepresentationParameterSerializer;
import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonWriter.IntermediateRepresentationMethodBodySerializer;
import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonWriter.IntermediateRepresentationMethodSerializer;
import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonWriter.IntermediateRepresentationParameterWithTypeSerializer;
import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonWriter.InvocationSerializer;
import ar.uba.dc.analysis.common.intermediate_representation.io.writer.JsonWriter.LineSerializer;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.summary.io.xstream.XStreamFactory;
import ar.uba.dc.soot.xstream.StatementIdConverter;
import ar.uba.dc.util.location.MethodLocationStrategy;


public class JsonReader implements SummaryReader<IntermediateRepresentationMethod> {
	private static Log log = LogFactory.getLog(JsonWriter.class);

protected Gson gson;
	
	protected MethodLocationStrategy locationStrategy;
	
	public JsonReader() {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		
		builder.registerTypeAdapter(IntermediateRepresentationMethod.class, new IntermediateRepresentationMethodDeserializer());
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
		
		return this.gson.fromJson(reader, IntermediateRepresentationMethod.class);
	}
	
	
	public static class IntermediateRepresentationMethodDeserializer implements JsonDeserializer<IntermediateRepresentationMethod> 
	{
	    
		@Override
		public IntermediateRepresentationMethod deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {

		    JsonObject jobject = (JsonObject) json;

		    IntermediateRepresentationMethod m = new IntermediateRepresentationMethod();
		    m.setName(jobject.get("name").getAsString());
		    
		    Set<IntermediateRepresentationParameterWithType> parameters  = new LinkedHashSet<IntermediateRepresentationParameterWithType>();
		    
		    
		    JsonArray jparameters = jobject.get("parameters").getAsJsonArray();
		    for(int i = 0; i < jparameters.size(); i++)
		    {
		    	//No necesita un custom deserializer porque tiene tipos primitivos adentro
		    	IntermediateRepresentationParameterWithType p = context.deserialize(jparameters.get(i), IntermediateRepresentationParameterWithType.class);
		    	parameters.add(p);
		    }
		    
		    m.setParameters(parameters);
		    
		    
		   /* m.setBody(
		    		(IntermediateRepresentationMethodBody) context.deserialize(jobject.get("body").getAsJsonObject(), 
		    																	IntermediateRepresentationMethodBody.class));*/
	    
	    
		    return m;
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
