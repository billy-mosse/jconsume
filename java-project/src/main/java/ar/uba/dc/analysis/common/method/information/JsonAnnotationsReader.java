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
public class JsonAnnotationsReader implements SummaryReader<AnnotationsContainer> {
	private static Log log = LogFactory.getLog(JsonAnnotationsReader.class);

protected Gson gson;
	
	public JsonAnnotationsReader() {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		
		
		//builder.registerTypeAdapter(IntermediateRepresentationParameterWithType.class, new IntermediateRepresentationParameterWithTypeSerializer());
		//builder.registerTypeAdapter(IntermediateRepresentationMethodBody.class, new IntermediateRepresentationMethodBodySerializer());	
		//builder.registerTypeAdapter(Line.class, new LineSerializer());
		builder.registerTypeAdapter(AnnotationsContainer.class, new AnnotationsContainerDeserializer());
		//builder.registerTypeAdapter(Invocation.class, new InvocationSerializer());
		//builder.registerTypeAdapter(DefaultIntermediateRepresentationParameter.class, new DefaultIntermediateRepresentationParameterSerializer());
		
		
		this.gson = builder.create();

	}
	

	@Override
	public AnnotationsContainer read(Reader reader) {
		

		try {
			AnnotationsContainer m = this.gson.fromJson(reader, AnnotationsContainer.class); 
			return m;
		} catch (Exception e) {
			log.error("Error. " + e.getMessage(), e);
			return null;
		}
		
	}
	
	
	public static class AnnotationsContainerDeserializer implements JsonDeserializer<AnnotationsContainer> 
	{
	    
		@Override
		public AnnotationsContainer deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {
			AnnotationsContainer container = new AnnotationsContainer();
			
			JsonObject jobject = (JsonObject) json;
			JsonArray jbindingPairs = jobject.get("annotations").getAsJsonArray();
			
			Map<String, Annotation> mapAnnotations = new HashMap<String,Annotation>();
			
			for(int i = 0; i < jbindingPairs.size(); i++)
			{
				JsonObject annotationJson = (JsonObject) jbindingPairs.get(i);
				String method_name = annotationJson.get("method_name").getAsString();
				String class_name = annotationJson.get("class_name").getAsString();
				String sConsumption = annotationJson.get("consumption").getAsString();
				
				PiecewiseQuasipolynomial consumption = new PiecewiseQuasipolynomial(sConsumption);
				//TODO encapsular
				
				Annotation annotation = new Annotation(consumption);
				container.annotations.put(class_name + "." + method_name , annotation);
			}
			
			
			return container;
		}
		
	}
}
