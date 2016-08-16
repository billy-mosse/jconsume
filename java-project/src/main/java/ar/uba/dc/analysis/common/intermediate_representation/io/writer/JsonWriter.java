package ar.uba.dc.analysis.common.intermediate_representation.io.writer;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.escape.summary.io.xstream.XStreamFactory;
import ar.uba.dc.util.location.MethodLocationStrategy;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameterWithType;

import com.thoughtworks.xstream.XStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonWriter implements SummaryWriter<IntermediateRepresentationMethod>{

	private static Log log = LogFactory.getLog(XMLWriter.class);
	
	//protected XStream xstream;
	
	protected Gson gson;
	
	protected MethodLocationStrategy locationStrategy;
	
	public JsonWriter() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(IntermediateRepresentationMethod.class, new IntermediateRepresentationMethodSerializer());
		builder.registerTypeAdapter(IntermediateRepresentationParameterWithType.class, new IntermediateRepresentationParameterWithTypeSerializer());			
		this.gson = builder.create();

	}
	
	public void write(IntermediateRepresentationMethod ir_method) {
		
		log.debug("estoy escribiendo el metodo " + ir_method.toString());
		
		String location = locationStrategy.getJsonLocation(ir_method);
		log.debug("Location for summary: [" + location + "]");
		
		File srcFile = new File(location);
		
		if (!srcFile.getParentFile().exists()) {
			srcFile.getParentFile().mkdirs();
		}
				
		try {
			//xstream.toXML(ir_method, new FileWriter(srcFile, false));
			//PrintWriter writer = new PrintWriter(srcFile, "UTF-8");
			//writer.println(ir_method.toHumanReadableString());
			//writer.close();
			
            BufferedWriter bwr = new BufferedWriter(new FileWriter(srcFile));
            
            
            bwr.write(gson.toJson(ir_method));
            bwr.flush();
            bwr.close();

		} catch (IOException e) {
			log.error("Error al imprimir el summary para el metodo [" + ir_method.getName() + "] a xml: " + e.getMessage(), e);
		}
	}

	public MethodLocationStrategy getLocationStrategy() {
		return locationStrategy;
	}

	public void setLocationStrategy(MethodLocationStrategy locationStrategy) {
		this.locationStrategy = locationStrategy;
	}
	
	
	public static class IntermediateRepresentationMethodSerializer implements JsonSerializer<IntermediateRepresentationMethod> 
	{
	    
		public JsonElement serialize(final IntermediateRepresentationMethod ir_method, final Type type, final JsonSerializationContext context) {
	        JsonObject result = new JsonObject();
	        result.add("name", new JsonPrimitive (ir_method.getName()));
	        
	        JsonArray arr = new JsonArray();
	        
	        
	        for(IntermediateRepresentationParameterWithType p : ir_method.getParameters())
	        {	        
	        	arr.add(context.serialize(p));
	        }
	        
	        result.add("parameters", arr);
	        
	        return result;
	    }
	}
	
	
	public static class IntermediateRepresentationParameterWithTypeSerializer implements JsonSerializer<IntermediateRepresentationParameterWithType> 
	{
	    
		public JsonElement serialize(final IntermediateRepresentationParameterWithType ir_parameter, final Type type, final JsonSerializationContext context) {
			JsonObject result = new JsonObject();
	        result.add("parameter name", new JsonPrimitive (ir_parameter.getName()));
	        return result;
	    }
	}

}