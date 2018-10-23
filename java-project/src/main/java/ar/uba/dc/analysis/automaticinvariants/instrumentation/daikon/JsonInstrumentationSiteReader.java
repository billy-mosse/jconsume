package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;


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
import com.google.gson.stream.JsonReader;

import ar.uba.dc.analysis.common.Invocation;
import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.SummaryReader;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethodBody;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartitionBinding;
import ar.uba.dc.analysis.memory.impl.summary.SimplePaperPointsToHeapPartition;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.util.location.MethodLocationStrategy;
import decorations.Binding;
import decorations.BindingPair;

public class JsonInstrumentationSiteReader {
	private static Log log = LogFactory.getLog(JsonInstrumentationSiteReader.class);

	protected Gson gson;
		
	public JsonInstrumentationSiteReader() {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();

		builder.registerTypeAdapter(CreationSiteMapInfo.class, new CreationSiteMapInfoDeserializer());
		
		
		
		//builder.registerTypeAdapter(IntermediateRepresentationParameterWithType.class, new IntermediateRepresentationParameterWithTypeSerializer());
		//builder.registerTypeAdapter(IntermediateRepresentationMethodBody.class, new IntermediateRepresentationMethodBodySerializer());	
		//builder.registerTypeAdapter(Line.class, new LineSerializer());
		
		//builder.registerTypeAdapter(Invocation.class, new InvocationSerializer());
		//builder.registerTypeAdapter(DefaultIntermediateRepresentationParameter.class, new DefaultIntermediateRepresentationParameterSerializer());
		
		this.gson = builder.create();

	}

	public CreationSiteMapInfo read(JsonReader reader) {
		

		try {
			CreationSiteMapInfo m = this.gson.fromJson(reader, CreationSiteMapInfo.class); 
			return m;
		} catch (Exception e) {
			log.error("Error. " + e.getMessage(), e);
			return null;
		}
		
	}
	
	
	public static class CreationSiteMapInfoDeserializer implements JsonDeserializer<CreationSiteMapInfo> 
	{
	    
		@Override
		public CreationSiteMapInfo deserialize(JsonElement json, Type type,
		        JsonDeserializationContext context) throws JsonParseException {

			CreationSiteMapInfo csInfo = new CreationSiteMapInfo();
			JsonObject jobject = (JsonObject) json;

	        
	        csInfo.setInsSite(jobject.get("insSite").getAsString());
	        
	        
	    	JsonArray jVars = jobject.get("vars").getAsJsonArray();		
		    for(int i = 0; i < jVars.size(); i++)
		    {
			    csInfo.getVars().add(jVars.get(i).getAsString());
		    }
	        
		    JsonArray jObjectVars = jobject.get("objectVars").getAsJsonArray();	
		    for(int i = 0; i < jObjectVars.size(); i++)
		    {
			    csInfo.getObjectVars().add(jObjectVars.get(i).getAsString());
		    }
		    
		    csInfo.setMethod(jobject.get("method").getAsString());

		    csInfo.setType(jobject.get("type").getAsString());

		    csInfo.setCreationSiteType(jobject.get("creationSiteType").getAsString());

        	JsonArray jArrayParams = jobject.get("arrayParams").getAsJsonArray();
		    for(int i = 0; i < jArrayParams.size(); i++)
		    {
			    csInfo.getCsArrayParams().add(jArrayParams.get(i).getAsString());
		    }
		    
		    csInfo.setOrder(jobject.get("order").getAsInt());
		    
		    /*JsonArray jVivas = jobject.get("vivas").getAsJsonArray();
		    for(int i = 0; i < jVivas.size(); i++)
		    {
			    csInfo.getVivas().add(jVivas.get(i).getAsString());
		    }*/
		    
		    JsonArray jInductivesFake = jobject.get("inductivesFake").getAsJsonArray();
		    for(int i = 0; i < jInductivesFake.size(); i++)
		    {
			    csInfo.getInductivesFake().add(jInductivesFake.get(i).getAsString());
		    }
		    
		    csInfo.setMethodCaller(jobject.get("methodCaller").getAsString());

		    return csInfo;			
		}
	}
}
