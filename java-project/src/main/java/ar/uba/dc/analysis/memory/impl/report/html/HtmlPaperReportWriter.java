package ar.uba.dc.analysis.memory.impl.report.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.SortedSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.google.gson.Gson;

import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.ReportWriter;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import soot.SootClass;
import soot.SootMethod;

public class HtmlPaperReportWriter implements ReportWriter<String, PaperMemorySummary> {

	private static Log log = LogFactory.getLog(HtmlPaperReportWriter.class);
	
	private String propertiesFileName;
	
	private String inputFolder;
	
	private String template;
	
	private String outputFolder;
	
	private boolean initialized = false;
	
	private MemorySummaryInterpreter<PaperMemorySummary> interpreter;
	
	@Override
	public void write(ReportDataSource<String, PaperMemorySummary> ds) {
		init();	

		VelocityContext context = buildContext(ds);
		
		//El HtmlReportHelper se encarga de escribir
		
		//File templateFolder = new File(Thread.currentThread().getContextClassLoader().getResource(inputFolder).getFile());
		
		File templateFolder = new File(inputFolder);
		
		File reportFolder = new File(outputFolder);
		
		if (!reportFolder.exists()) {
			reportFolder.mkdirs();
		}
		
		try {
			FileUtils.cleanDirectory(reportFolder);
		} catch (IOException e) {
			log.warn("No fue posible limpiar el directorio de reportes [" + reportFolder.getAbsolutePath() + "]: " + e.getMessage());
		}
		
		try {
			FileUtils.copyDirectory(templateFolder, reportFolder, FileFilterUtils.makeSVNAware(FileFilterUtils.notFileFilter(FileFilterUtils.suffixFileFilter(".vm"))), true);
		} catch (IOException e) {
			log.warn("No fue posible copiar el contenido de la carpeta de template [" + templateFolder.getAbsolutePath() + "]: " + e.getMessage());
		}
		
		try {
			FileWriter w = new FileWriter(new File(reportFolder, "index.html"));
			Velocity.mergeTemplate(this.template, "UTF-8", context, w);
		    w.close();
		} catch (Exception e) {
			log.error("No fue posible generar el reporte: " + e.getMessage());
		}
		
		
		
		JsonReportWriter jsonWriter = new JsonReportWriter();
		jsonWriter.build();
		
		String location = reportFolder + "/report.json";
		log.debug("Writing JSON output...");
		
		File srcFile = new File(location);
		
				
		try {
			//xstream.toXML(ir_method, new FileWriter(srcFile, false));
			//PrintWriter writer = new PrintWriter(srcFile, "UTF-8");
			//writer.println(ir_method.toHumanReadableString());
			//writer.close();
			
            //BufferedWriter bwr = new BufferedWriter(new FileWriter(srcFile));
            
            BufferedWriter bwr = new BufferedWriter
            	    (new OutputStreamWriter(new FileOutputStream(srcFile),"UTF-8"));
            
            for (Entry<String, SortedSet<PaperMemorySummary>> item : ds.getClassIndex().entrySet()) {
    		    String className = item.getKey();
    		    JsonClassSummary jsonClassSummary = new JsonClassSummary(className);
    		    
    		    //Hack: por que es un sorted set? no deberia haber un solo memory summary por metodo?
    		    SortedSet<PaperMemorySummary> paperMemorySummaries = item.getValue();
    		    
    		    for(PaperMemorySummary paperMemorySummary : paperMemorySummaries){
    		    	String rsd = ((HtmlPaperReportHelper)context.get("helper")).getNonHTMLResidual(paperMemorySummary);
    		    	String memoryRequirement = ((HtmlPaperReportHelper)context.get("helper")).getNonHTMLMemoryRequirement(paperMemorySummary);
    		    	String parameters = paperMemorySummary.getParameters().toString();
    		    	JsonMethodSummary jsonMethodSummary = new JsonMethodSummary(parameters, paperMemorySummary.getTarget().getName(), memoryRequirement, rsd);
    		    	jsonClassSummary.addJsonMemorySummary(jsonMethodSummary);
    		    }    		    
    		    
    		    String s =  StringEscapeUtils.unescapeJava(jsonWriter.gson.toJson(jsonClassSummary)); 
    	        bwr.write(s);
    		}     
            bwr.flush();
            bwr.close();
		}
        catch(Exception e )
        {	
        	log.error("No fue posible generar el reporte en formato JSON: " + e.getMessage());
        }
	}


	protected boolean init() {
		if (!initialized) {
			try {
				Properties props = new Properties();
				props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFileName));
				Velocity.init(props);
			} catch (Exception e) {
				return false;
			}
			
			initialized = true;
		}
		return true;
	}
	
	protected VelocityContext buildContext(ReportDataSource<String, PaperMemorySummary> ds) {
		VelocityContext context = new VelocityContext();
		context.put("dateFormat", new SimpleDateFormat());
		context.put("format","dd-MM-yyyy");
		context.put("currentDate", GregorianCalendar.getInstance().getTime());
		context.put("version","1.0");
		context.put("relativePath", ".");

        context.put("title", ds.getMainClass());
        context.put("mainClass", ds.getMainClass());
       	context.put("classIndex", ds.getClassIndex());
        
       	context.put("helper", new HtmlPaperReportHelper(interpreter));
		
		return context;
	}
	
	public void setPropertiesFileName(String propertiesFileName) {
		this.propertiesFileName = propertiesFileName;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public void setInputFolder(String inputFolder) {
		this.inputFolder = inputFolder;
	}

	public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}

	public void setInterpreter(MemorySummaryInterpreter<PaperMemorySummary> interpreter) {
		this.interpreter = interpreter;
	}
}
