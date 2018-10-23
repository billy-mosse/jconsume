package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.annotations.AnnotationSiteInvariantForJson;
import ar.uba.dc.util.location.MethodLocationStrategy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import decorations.Binding;

public class JsonInstrumentationSiteWriter
{
	private static Log log = LogFactory.getLog(JsonInstrumentationSiteWriter.class);
	
	//protected XStream xstream;
	
	protected Gson gson;
	
	//protected MethodLocationStrategy locationStrategy;
	private String mainClass;
	
	
	public JsonInstrumentationSiteWriter() {
		
	}
	
	public void registerTypeAdapters()
	{
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		//builder.registerTypeAdapter(CreationSiteMapInfo.class, new CreationSiteMapInfoSerializer());
			
		
		//builder.registerTypeAdapter(IntermediateRepresentationParameterWithType.class, new IntermediateRepresentationParameterWithTypeSerializer());
		//builder.registerTypeAdapter(InstrumentationSiteInvariantForJson.class, new IntermediateRepresentationMethodBodySerializer());	
	
		builder.registerTypeAdapter(CreationSiteMapInfo.class, new CreationSiteMapInfoSerializer());
		this.gson = builder.serializeNulls().create();
	}
	
	
/*	public void write(AnnotationSiteInvariantForJson siteInvariant)
	{
		write(siteInvariant, false);
	}*/

public void write(CreationSiteMapInfo siteInvariant, BufferedWriter bwrCS) {
		
		log.debug("estoy escribiendo el siteInvariant " + siteInvariant.toString());
		
		//TODO usar FACTORY para esto
		
				
				//locationStrategy.getJsonIRLocation(siteInvariant, mainClass);
		//log.debug("Location for summary: [" + location + "]");
		
		//File srcFile = new File(location);
		
		/*if (!srcFile.getParentFile().exists()) {
			srcFile.getParentFile().mkdirs();
		}*/
				
		try {
			//xstream.toXML(ir_method, new FileWriter(srcFile, false));
			//PrintWriter writer = new PrintWriter(srcFile, "UTF-8");
			//writer.println(ir_method.toHumanReadableString());
			//writer.close();
			
            //BufferedWriter bwr = new BufferedWriter(new FileWriter(srcFile));
            
            //BufferedWriter bwr = new BufferedWriter
            	//    (new OutputStreamWriter(new FileOutputStream(srcFile),"UTF-8"));           
            
         
            String s =  StringEscapeUtils.unescapeJava(this.gson.toJson(siteInvariant)); 
            bwrCS.write(s);
            bwrCS.flush();
            //bwr.close();

		} catch (IOException e) {
			log.error("Error al imprimir el summary para el site invariant [" + siteInvariant.toString() + "] a xml: " + e.getMessage(), e);
		} catch(Exception e) {
			log.error("Que carajo esta pasando??");
		}
	}



public static class CreationSiteMapInfoSerializer implements JsonSerializer<CreationSiteMapInfo> 
{
    
	public JsonElement serialize(final CreationSiteMapInfo cs, final Type type, final JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        
        
        result.add("insSite", context.serialize(cs.getInsSite()));
        result.add("vars",context.serialize(cs.getVars()) );
        result.add("objectVars",context.serialize(cs.getObjectVars()) );
        result.add("method",context.serialize(cs.getMethod()) );
        result.add("type",context.serialize(cs.getType()) );
        result.add("creationSiteType",context.serialize(cs.getCreationSiteType()) );
        result.add("arrayParams", context.serialize(cs.getCsArrayParams()));
        result.add("order",context.serialize(cs.getOrder()) );
//        result.add("vivas",context.serialize(cs.getVivas()) );
        result.add("inductivesFake",context.serialize(cs.getInductivesFake()) );
        result.add("methodCaller",context.serialize(cs.getMethodCaller()));
        
        return result;
    }
}



}



