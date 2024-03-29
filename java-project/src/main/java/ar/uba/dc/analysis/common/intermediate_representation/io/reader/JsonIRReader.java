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
import java.util.TreeSet;

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

import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.LineWithConsumption;
import ar.uba.dc.analysis.common.SummaryReader;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethodBody;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpression;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartitionBinding;
import ar.uba.dc.analysis.memory.impl.summary.SimplePaperPointsToHeapPartition;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.util.location.MethodLocationStrategy;
import decorations.Binding;
import decorations.BindingPair;


public class JsonIRReader implements SummaryReader<IntermediateRepresentationMethod> {
	private static Log log = LogFactory.getLog(JsonIRReader.class);

protected Gson gson;
	
	protected MethodLocationStrategy locationStrategy;
	
	public JsonIRReader() {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();

		builder.registerTypeAdapter(IntermediateRepresentationMethod.class, new IntermediateRepresentationMethodDeserializer());
		builder.registerTypeAdapter(Line.class, new LineDeserializer());
		builder.registerTypeAdapter(Invocation.class, new InvocationDeserializer());
		builder.registerTypeAdapter(IntermediateRepresentationMethodBody.class, new IntermediateRepresentationMethodBodyDeserializer());
		builder.registerTypeAdapter(Binding.class, new BindingDeserializer());
		builder.registerTypeAdapter(DomainSet.class, new DomainSetDeserializer());
		builder.registerTypeAdapter(PaperNode.class, new PaperNodeDeserializer());
		builder.registerTypeAdapter(PaperPointsToHeapPartition.class, new PaperPointsToHeapPartitionDeserializer());
		builder.registerTypeAdapter(PaperPointsToHeapPartitionBinding.class, new PaperPointsToHeapPartitionBindingDeserializer());
		
		
		
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
		    
		    
		    if(jobject.has("declaring_class"))
		    		m.setDeclaringClass(jobject.get("declaring_class").getAsString());
		    
		    Set<IntermediateRepresentationParameter> parameters  = new LinkedHashSet<IntermediateRepresentationParameter>();
		    
		    if(jobject.has("parameters"))
		    {
			    JsonArray jparameters = jobject.get("parameters").getAsJsonArray();
			    for(int i = 0; i < jparameters.size(); i++)
			    {
			    	//No necesita un custom deserializer porque tiene tipos primitivos adentro
			    	IntermediateRepresentationParameter p = context.deserialize(jparameters.get(i), IntermediateRepresentationParameter.class);
			    	parameters.add(p);
			    }
			    
	
			    m.setParameters(parameters);
		    }
		    
			DomainSet methodRequirements = context.deserialize(jobject.get("requirements"), DomainSet.class);
			m.setMethodRequirements(methodRequirements);
			
			
		    Set<String> relevant_parameters = new TreeSet<String>();
		    JsonArray jrelevant_parameters = jobject.get("relevant_parameters").getAsJsonArray();
		    for(int i = 0; i < jrelevant_parameters.size(); i++)
		    {
		    	//No necesita un custom deserializer porque tiene tipos primitivos adentro
		    	
		    	relevant_parameters.add(jrelevant_parameters.get(i).getAsString());
		    }
		    m.setRelevantParameters(relevant_parameters);
		    
		    if(jobject.has("return_type"))
		    	m.setReturnType(jobject.get("return_type").getAsString());
		    
		    if(jobject.has("is_return_ref_like_type"))
		    	m.setIsReturnRefLikeType(jobject.get("is_return_ref_like_type").getAsBoolean());
		    
	    	    
		    IntermediateRepresentationMethodBody body = context.deserialize(jobject.get("body"), IntermediateRepresentationMethodBody.class);
		    
		    
		    m.setBody(body);
		    
		    
		    JsonArray jEscapeNodes = jobject.get("escapeNodes").getAsJsonArray();
			
			Set<PaperPointsToHeapPartition> escapeNodes = new LinkedHashSet<PaperPointsToHeapPartition>();
			
			
		    for(int i = 0; i < jEscapeNodes.size(); i++)
		    {
		    	PaperPointsToHeapPartition escapeNode = new SimplePaperPointsToHeapPartition(jEscapeNodes.get(i).getAsString());	
		    	
		    	//el belongsTo no es necesariamente el metodo....entender por que
		    	//escapeNode.belongsTo = m.getFullName();
		    	escapeNodes.add(escapeNode);
		    }

		    m.setEscapeNodes(escapeNodes);
		    
		    
		    
		    
		    JsonArray jNodes = jobject.get("nodes").getAsJsonArray();
			
			Set<PaperPointsToHeapPartition> nodes = new LinkedHashSet<PaperPointsToHeapPartition>();
			
			
		    for(int i = 0; i < jNodes.size(); i++)
		    {
		    	PaperPointsToHeapPartition node = new SimplePaperPointsToHeapPartition(jNodes.get(i).getAsString());	
		    	//node.belongsTo = m.getFullName();
		    	nodes.add(node);
		    }

		    m.setNodes(nodes);		    
		    
		    m.setDeclaration(jobject.get("declaration").getAsString());
		    
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
			
			JsonElement jconsumption = jobject.get("consumption");
			if(jconsumption != null)
			{
				line = new LineWithConsumption();
				BarvinokParametricExpression consumption = context.deserialize(jconsumption, BarvinokParametricExpression.class);
				((LineWithConsumption)line).setConsumption(consumption);
			}
			else
			{
				DomainSet invariant = context.deserialize(jobject.get("invariant"), DomainSet.class);
				line.setInvariant(invariant);
			}
			
			

			if(jobject.has("name"))
				line.setName(jobject.get("name").getAsString());
			
			line.setIrName(jobject.get("ir_name").getAsString());
			line.setIrClass(jobject.get("ir_class").getAsString());			

	    	log.debug("Deserializando " + line.getName());			
			
	    	//line.setLineNumber(jobject.get("line_number").getAsInt());			
			
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
		    

		    //line.magicalStmtName = jobject.get("magical_stmt_name").getAsString();
		    
		    return line;
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
			
			if(bindingPairs != null)
				binding.setBindingPairs(bindingPairs);
			
			//Set<String> variables = new TreeSet<String>();
			
			//JsonArray arr = jobject.get("variables").getAsJsonArray();
			
			
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
			
			//invocation.setCalled_implementation_signature(jobject.get("called_implementation_signature").getAsString());
			
			invocation.setNameCalled(jobject.get("name_called").getAsString());
			
			JsonArray jparameters = jobject.get("parameters").getAsJsonArray();
			
			Set<IntermediateRepresentationParameter> parameters = new LinkedHashSet<IntermediateRepresentationParameter>();
			
		    for(int i = 0; i < jparameters.size(); i++)
		    {
		    	//No necesita un custom deserializer porque tiene tipos primitivos adentro
		    	IntermediateRepresentationParameter p = context.deserialize(jparameters.get(i), IntermediateRepresentationParameter.class);
		    	parameters.add(p);
		    }
		    
		    invocation.setParameters(parameters);

		    
		    
		    Set<PaperPointsToHeapPartitionBinding> hp_bindings = new HashSet<PaperPointsToHeapPartitionBinding>();
			
			JsonArray jbindings = jobject.get("hp_bindings").getAsJsonArray();

			
		    for(int i = 0; i < jbindings.size(); i++)
		    {
		    	//No necesita un custom deserializer porque tiene tipos primitivos adentro
		    	PaperPointsToHeapPartitionBinding hp_binding = context.deserialize(jbindings.get(i), PaperPointsToHeapPartitionBinding.class);
		    	hp_bindings.add(hp_binding);
		    }

		    invocation.setHpBindings(hp_bindings);
		    invocation.setHeapPartition((PaperPointsToHeapPartition)context.deserialize(jobject.get("hp"), PaperPointsToHeapPartition.class));
		    
		    invocation.setIsReturnRefLikeType(jobject.get("is_return_ref_like_type").getAsBoolean());
		    
		    return invocation;
		    
		}
	}

	public static class PaperPointsToHeapPartitionBindingDeserializer implements JsonDeserializer<PaperPointsToHeapPartitionBinding> 
	{
	    
		@Override
		public PaperPointsToHeapPartitionBinding deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {
			JsonObject jobject = json.getAsJsonObject();
			

			PaperPointsToHeapPartition hp_caller = context.deserialize(jobject.get("hp_caller"), PaperPointsToHeapPartition.class);
			PaperPointsToHeapPartition hp_callee = context.deserialize(jobject.get("hp_callee"), PaperPointsToHeapPartition.class);
			
			PaperPointsToHeapPartitionBinding hp_binding = new PaperPointsToHeapPartitionBinding(hp_callee, hp_caller);
			
			return hp_binding;
		
		}
	}	
	
	public static class PaperPointsToHeapPartitionDeserializer implements JsonDeserializer<PaperPointsToHeapPartition> 
	{
	    
		@Override
		public PaperPointsToHeapPartition deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {
			JsonObject jobject = json.getAsJsonObject();
			
			String number =jobject.get("name").getAsString().substring(3); 
			PaperPointsToHeapPartition hp = new SimplePaperPointsToHeapPartition(Integer.parseInt(number));
			
			return hp;
		
		}
	}
	
	
	
	public static class PaperNodeDeserializer implements JsonDeserializer<PaperNode> 
	{
	    
		@Override
		public PaperNode deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {
			JsonObject jobject = json.getAsJsonObject();
			JsonPrimitive prim = (JsonPrimitive) jobject.get("className");
		    String className = prim.getAsString();
		    Class<?> klass = null;
		    try {
		        klass = Class.forName(className);
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		        throw new JsonParseException(e.getMessage());
		    }
		    return context.deserialize(jobject.get("instance"), klass);
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
