package ar.uba.dc.analysis.common.method.information;

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
import ar.uba.dc.analysis.common.SummaryReader;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethodBody;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartitionBinding;
import ar.uba.dc.analysis.memory.impl.summary.SimplePaperPointsToHeapPartition;
import ar.uba.dc.annotations.AnnotationSiteInvariantForJson;
import ar.uba.dc.annotations.InstrumentationSiteInvariant;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.util.location.MethodLocationStrategy;
import decorations.Binding;
import decorations.BindingPair;


public class JsonInstrumentationSiteInvariantsReader implements SummaryReader<AnnotationSiteInvariantForJson> {
	private static Log log = LogFactory.getLog(JsonInstrumentationSiteInvariantsReader.class);

protected Gson gson;
	
	protected MethodLocationStrategy locationStrategy;
	
	public JsonInstrumentationSiteInvariantsReader() {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();

		this.gson = builder.create();

	}
	
	public MethodLocationStrategy getLocationStrategy() {
		return locationStrategy;
	}

	public void setLocationStrategy(MethodLocationStrategy locationStrategy) {
		this.locationStrategy = locationStrategy;
	}

	@Override
	public AnnotationSiteInvariantForJson read(Reader reader) {
		

		try {
			AnnotationSiteInvariantForJson m = this.gson.fromJson(reader, AnnotationSiteInvariantForJson.class); 
			return m;
		} catch (Exception e) {
			log.error("Error. " + e.getMessage(), e);
			return null;
		}
		
	}
	
}
