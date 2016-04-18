package ar.uba.dc.analysis.memory.impl.report.html;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import ar.uba.dc.analysis.memory.impl.ReportWriter;

public class HtmlReportWriter implements ReportWriter {

	private static Log log = LogFactory.getLog(HtmlReportWriter.class);
	
	private String propertiesFileName;
	
	private String inputFolder;
	
	private String template;
	
	private String outputFolder;
	
	private boolean initialized = false;
	
	private MemorySummaryInterpreter interpreter;
	
	@Override
	public void write(ReportDataSource ds) {
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
	
	protected VelocityContext buildContext(ReportDataSource ds) {
		VelocityContext context = new VelocityContext();
		context.put("dateFormat", new SimpleDateFormat());
		context.put("format","dd-MM-yyyy");
		context.put("currentDate", GregorianCalendar.getInstance().getTime());
		context.put("version","1.0");
		context.put("relativePath", ".");

        context.put("title", ds.getMainClass());
        context.put("mainClass", ds.getMainClass());
       	context.put("classIndex", ds.getClassIndex());
        
       	context.put("helper", new HtmlReportHelper(interpreter));
		
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

	public void setInterpreter(MemorySummaryInterpreter interpreter) {
		this.interpreter = interpreter;
	}
}
