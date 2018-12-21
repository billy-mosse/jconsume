package ar.uba.dc.analysis.common.method.information;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.annotations.AnnotationSiteInvariantForJson;
import ar.uba.dc.util.location.MethodLocationStrategy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import decorations.Binding;

public class JsonInstrumentationSiteInvariantsWriter
{
	private static Log log = LogFactory.getLog(JsonInstrumentationSiteInvariantsWriter.class);
	
	//protected XStream xstream;
	
	protected Gson gson;
	
	//protected MethodLocationStrategy locationStrategy;
	private String mainClass;
	
	
	public JsonInstrumentationSiteInvariantsWriter() {
		
	}
	
	public void registerTypeAdapters(boolean isDebug)
	{
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
			
		
		//builder.registerTypeAdapter(IntermediateRepresentationParameterWithType.class, new IntermediateRepresentationParameterWithTypeSerializer());
		//builder.registerTypeAdapter(InstrumentationSiteInvariantForJson.class, new IntermediateRepresentationMethodBodySerializer());	
	
		
		this.gson = builder.create();
	}
	
	
/*	public void write(AnnotationSiteInvariantForJson siteInvariant)
	{
		write(siteInvariant, false);
	}*/

public void write(AnnotationSiteInvariantForJson siteInvariant, BufferedWriter bwr, Gson gson) {
		
		
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
            
         
            String s =  StringEscapeUtils.unescapeJava(gson.toJson(siteInvariant)); 
            bwr.write(s);
            bwr.flush();
            //bwr.close();

		} catch (IOException e) {
			log.error("Error al imprimir el summary para el site invariant [" + siteInvariant.toString() + "] a xml: " + e.getMessage(), e);
		} catch(Exception e) {
			log.error("Que carajo esta pasando??");
		}
	}
}