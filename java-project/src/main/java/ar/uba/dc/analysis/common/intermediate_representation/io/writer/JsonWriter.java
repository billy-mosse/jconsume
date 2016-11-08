package ar.uba.dc.analysis.common.intermediate_representation.io.writer;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.GeneralSecurityException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.analysis.escape.summary.io.xstream.XStreamFactory;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.util.location.MethodLocationStrategy;
import decorations.Binding;
import decorations.BindingPair;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethodBody;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;

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

	private static Log log = LogFactory.getLog(JsonWriter.class);
	
	//protected XStream xstream;
	
	protected Gson gson;
	
	protected MethodLocationStrategy locationStrategy;
	private String mainClass;
	
	public JsonWriter() {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		
		builder.registerTypeAdapter(IntermediateRepresentationMethod.class, new IntermediateRepresentationMethodSerializer());
		//builder.registerTypeAdapter(IntermediateRepresentationParameterWithType.class, new IntermediateRepresentationParameterWithTypeSerializer());
		builder.registerTypeAdapter(IntermediateRepresentationMethodBody.class, new IntermediateRepresentationMethodBodySerializer());	
		builder.registerTypeAdapter(Line.class, new LineSerializer());
		
		builder.registerTypeAdapter(Invocation.class, new InvocationSerializer());
		//builder.registerTypeAdapter(IntermediateRepresentationParameter.class, new DefaultIntermediateRepresentationParameterSerializer());
		builder.registerTypeAdapter(Binding.class, new BindingSerializer());
		
		builder.registerTypeAdapter(DomainSet.class, new DomainSetSerializer());
		builder.registerTypeAdapter(PaperNode.class, new PaperNodeSerializer());
		
		this.gson = builder.create();
	}
	
	public void write(IntermediateRepresentationMethod ir_method) {
		
		log.debug("estoy escribiendo el metodo " + ir_method.toString());
		
		String location = locationStrategy.getJsonLocation(ir_method, mainClass);
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
		} catch(Exception e) {
			log.error("Que carajo esta pasando??");
		}
	}

	public MethodLocationStrategy getLocationStrategy() {
		return locationStrategy;
	}

	public void setLocationStrategy(MethodLocationStrategy locationStrategy) {
		this.locationStrategy = locationStrategy;
	}
	
	
	public String getMainClass() {
		return mainClass;
	}

	@Override
	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}


	public static class IntermediateRepresentationMethodSerializer implements JsonSerializer<IntermediateRepresentationMethod> 
	{
	    
		public JsonElement serialize(final IntermediateRepresentationMethod ir_method, final Type type, final JsonSerializationContext context) {
	        JsonObject result = new JsonObject();
	        result.add("name", new JsonPrimitive (ir_method.getName()));
	        
	        JsonArray parameters = new JsonArray();
	        
	        
	        for(IntermediateRepresentationParameter p : ir_method.getParameters())
	        {	        
	        	parameters.add(context.serialize(p));
	        }
	        

	        JsonArray escapeNodes = new JsonArray();
	        
	        for(PaperPointsToHeapPartition escapeNode : ir_method.getEscapeNodes())
	        {
	        	escapeNodes.add(context.serialize(escapeNode));
	        }
	        
	        result.add("escapeNodes", escapeNodes);
	        

	        JsonArray nodes = new JsonArray();
	        
	        for(PaperPointsToHeapPartition node : ir_method.getNodes())
	        {
	        	nodes.add(context.serialize(node));
	        }
	        
	        result.add("nodes", nodes);
	        
	        //TODO: la verdad no necesito los parametros. Me parece que voy a volar xml y humanReadable, y todo lo que no necesite, y dejar solo json
	        result.add("parameters", parameters);
	        JsonArray relevant_parameters = new JsonArray();
	        
	        
	        for(String rp : ir_method.getRelevant_parameters())
	        {	        
	        	relevant_parameters.add(context.serialize(rp));
	        }
	        
	        //TOOD: falta ver que pasa con method requirements
	        
	        //y el binding

	        result.add("declaring_class", new JsonPrimitive(ir_method.getDeclaringClass()));

	        result.add("number", new JsonPrimitive(ir_method.getNumber()));
	        result.add("declaration", new JsonPrimitive(ir_method.getDeclaration()));
	        result.add("sub_signature", new JsonPrimitive(ir_method.getSubSignature()));
	        
	        
	        
	        result.add("relevant_parameters", relevant_parameters);
	        
	        result.add("is_return_ref_like_type", new JsonPrimitive(ir_method.isReturnRefLikeType()));
	        
	        result.add("return_type", new JsonPrimitive(ir_method.getReturnType()));
	        result.add("body", context.serialize(ir_method.getBody()));
	        
	        
	        return result;
	    }
	}
	
	
	/*public static class IntermediateRepresentationParameterWithTypeSerializer implements JsonSerializer<IntermediateRepresentationParameterWithType> 
	{
	    
		public JsonElement serialize(final IntermediateRepresentationParameterWithType ir_parameter, final Type type, final JsonSerializationContext context) {
			JsonObject result = new JsonObject();
	        result.add("name", new JsonPrimitive (ir_parameter.getName()));
	        result.add("type", new JsonPrimitive (ir_parameter.getType()));
	        return result;
	    }
	}*/
	
	
	
	
	
	public static class IntermediateRepresentationMethodBodySerializer implements JsonSerializer<IntermediateRepresentationMethodBody> 
	{
	    
		public JsonElement serialize(final IntermediateRepresentationMethodBody ir_body, final Type type, final JsonSerializationContext context) {
			JsonObject result = new JsonObject();
			
			
			JsonArray arr = new JsonArray();
	        
	        
	        for(Line line : ir_body.getLines())
	        {	        
	        	arr.add(context.serialize(line));
	        }	        

	        result.add("lines", arr);
	        return result;
	    }
	}
	
	public static class LineSerializer implements JsonSerializer<Line> 
	{
	    
		public JsonElement serialize(final Line line, final Type type, final JsonSerializationContext context) {
			JsonObject result = new JsonObject();
						
			//TODO: Hacer que las flechas se vean bien
			result.add("invariant", context.serialize(line.getInvariant()));
			
			
			result.add("binding", context.serialize(line.getBinding()));			
			

			result.add("name", new JsonPrimitive( line.getName()));
			result.add("ir_name", new JsonPrimitive( line.getIrName()));
			
			result.add("line_number", new JsonPrimitive( line.getLineNumber()));
			
			JsonArray arr = new JsonArray();
			
			for(Invocation i : line.getInvocations())
			{
				arr.add(context.serialize(i));
			}
			
			result.add("invocations", arr);
			
			
	        return result;
	    }
	}

	
	public static class DomainSetSerializer implements JsonSerializer<DomainSet> 
	{
	    
		public JsonElement serialize(final DomainSet invariant, final Type type, final JsonSerializationContext context) {
			JsonObject result = new JsonObject();
			result.add("expression", new JsonPrimitive(invariant.toHumanReadableString()));
			
			JsonArray arr = new JsonArray();			
			for(String s : invariant.getVariables())
			{
				arr.add(new JsonPrimitive(s));
			}
			
			result.add("variables", arr);
			
			
			return result;
		
		}
	}
	
	
	public static class BindingSerializer implements JsonSerializer<Binding> 
	{
	    
		public JsonElement serialize(final Binding binding, final Type type, final JsonSerializationContext context) {
			JsonObject result = new JsonObject();
			
			JsonArray arr = new JsonArray();
			
			for(BindingPair bp : binding.getBindingPairs())
			{
				arr.add(context.serialize(bp));
			}
			
			result.add("bindingPairs", arr);
			
			return result;
		}
	}
	
	/*public static class BindingPairSerializer implements JsonSerializer<BindingPair> 
	{
	    
		public JsonElement serialize(final BindingPair bindingPair, final Type type, final JsonSerializationContext context) {
			JsonObject result = new JsonObject();
			
			result.add("caller", new JsonPrimitive(bindingPair.getCa));
			
			return result;
		}
	}*/
		
	
	
	
	public static class InvocationSerializer implements JsonSerializer<Invocation> 
	{
	    
		public JsonElement serialize(final Invocation invocation, final Type type, final JsonSerializationContext context) {
			JsonObject result = new JsonObject();
			result.add("class_called", new JsonPrimitive(invocation.getClass_called()));

			//result.add("name", new JsonPrimitive( invocation.getName()));
			
			JsonArray arr = new JsonArray();
			
			for(IntermediateRepresentationParameter p : invocation.getParameters())
			{
				arr.add(context.serialize(p));
			}
			result.add("parameters", arr);
			result.add("called_implementation_signature", new JsonPrimitive (invocation.getCalled_implementation_signature()));
			result.add("name_called", new JsonPrimitive (invocation.getNameCalled()));
			result.add("hp",  context.serialize(invocation.getHeapPartition()));
			
	        result.add("is_return_ref_like_type", new JsonPrimitive(invocation.isReturnRefLikeType()));

			
	        return result;
	    }
	}
	
	
	public static class PaperNodeSerializer implements JsonSerializer<PaperNode> 
	{
	    
		public JsonElement serialize(final PaperNode paperNode, final Type type, final JsonSerializationContext context) {
			JsonObject result = new JsonObject();

			String className = paperNode.getClass().getName();
			result.addProperty("className", className);
			
			JsonElement elem = context.serialize(paperNode);
			
			result.add("instance", elem);
			
	        return result;
	    }
	}
	
	
	
	
	/*public static class IntermediateRepresentationParameterSerializer implements JsonSerializer<IntermediateRepresentationParameter> 
	{
	    
		public JsonElement serialize(final IntermediateRepresentationParameter parameter, final Type type, final JsonSerializationContext context) {
			JsonObject result = new JsonObject();
			result.add("name", new JsonPrimitive(parameter.getName()));
			
	        return result;
	    }
	}*/
	
}