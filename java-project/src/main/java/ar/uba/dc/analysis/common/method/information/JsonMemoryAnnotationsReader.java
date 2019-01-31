package ar.uba.dc.analysis.common.method.information;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import ar.uba.dc.analysis.common.SummaryReader;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import ar.uba.dc.analysis.common.intermediate_representation.io.reader.JsonIRReader.PaperPointsToHeapPartitionBindingDeserializer;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethodBody;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartitionBinding;
import ar.uba.dc.analysis.memory.impl.summary.SimplePaperPointsToHeapPartition;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.util.location.MethodLocationStrategy;
import decorations.Binding;
import decorations.BindingPair;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;


//TODO lo voy a tener que llamar al principio y tener un mapa
public class JsonMemoryAnnotationsReader implements SummaryReader<MemoryAnnotationsContainer> {
	private static Log log = LogFactory.getLog(JsonMemoryAnnotationsReader.class);

protected Gson gson;
	
	public JsonMemoryAnnotationsReader() {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		
		
		//builder.registerTypeAdapter(IntermediateRepresentationParameterWithType.class, new IntermediateRepresentationParameterWithTypeSerializer());
		//builder.registerTypeAdapter(IntermediateRepresentationMethodBody.class, new IntermediateRepresentationMethodBodySerializer());	
		//builder.registerTypeAdapter(Line.class, new LineSerializer());
		builder.registerTypeAdapter(MemoryAnnotationsContainer.class, new AnnotationsContainerDeserializer());
		//builder.registerTypeAdapter(Invocation.class, new InvocationSerializer());
		//builder.registerTypeAdapter(DefaultIntermediateRepresentationParameter.class, new DefaultIntermediateRepresentationParameterSerializer());
		
		
		this.gson = builder.create();

	}
	

	@Override
	public MemoryAnnotationsContainer read(Reader reader) {
		

		try {
			MemoryAnnotationsContainer m = this.gson.fromJson(reader, MemoryAnnotationsContainer.class); 
			return m;
		} catch (Exception e) {
			log.error("Error. " + e.getMessage(), e);
			return null;
		}
		
	}
	
	
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
		
	}
}
