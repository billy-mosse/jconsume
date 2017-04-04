package ar.uba.dc.analysis.memory.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.LineWithParent;
import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.memory.InterproceduralAnalysis;
import ar.uba.dc.analysis.memory.PaperInterproceduralAnalysis;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.impl.report.datasource.AnalysisResultsReportDataSource;
import ar.uba.dc.analysis.memory.impl.report.datasource.PaperAnalysisResultsReportDataSource;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.soot.SootMethodFilter;
import ar.uba.dc.util.Timer;
import ar.uba.dc.util.location.MethodLocationStrategy;

/**
 * Implementacion particular del analisis de memoria que agrega la posibilidad de escribir los summaries
 * obtenidos durante el analisis.
 * 
 * @author testis
 */
public class PaperMemoryInterproceduralAnalysis extends PaperInterproceduralAnalysis {

	private static Log log = LogFactory.getLog(PaperMemoryInterproceduralAnalysis.class);

	protected SummaryWriter<MemorySummary> summaryWriter;
	
	protected boolean writeResults;
	
	protected boolean writeUnanalysedSummaries;
	
	protected boolean cleanOutputFolder;
	
	protected String outputFolder;
	
	protected boolean writeReport;
	
	protected boolean writeUnanalyzedSummariesInReport;
	
	protected ReportWriter<String, PaperMemorySummary> reportWriter;
	
	//TODO los primeros dos parametros estan de mas :P
	public void run(String mainClass){
		
		Timer analysisTimer = new Timer();
		analysisTimer.start();
		Timer t = new Timer();
		t.start();
		
		log.info("Running memory analysis for [" + mainClass + "]");
		
		super.doAnalysis(mainClass);	
		
		analysisTimer.stop();
		
		log.debug("Analysis finished");
		
		
		//Esto probablemente no haga falta, y es costoso.....pero necesito el .getHeads()
		Map<String, PaperMemorySummary> summaries = new HashMap<String, PaperMemorySummary>(data);
		
		reportWriter.write(new PaperAnalysisResultsReportDataSource(mainClass, summaries));
		
		log.debug("Report written.");

		if(badLines.size() > 0)
		{
			log.debug("Hay lineas que dieron infinito. Revise sus invariantes");

			for(LineWithParent badLine : badLines)
			{
				log.debug(badLine.belongsTo + " called " + badLine.line.toString() + " | " + badLine.line.getInvariant().toString());				
			}

		}
	}
	
	
	@Override
	protected void init() {
		super.init();
		if (cleanOutputFolder) {
			try {
				File outputDir = new File(outputFolder);
				if (outputDir.exists()) {
					FileUtils.cleanDirectory(outputDir);
				}
			} catch (IOException e) {
				log.warn("No fue posible limpiar el directorio de output para los summaries de memoria: " + e.getMessage(), e);
			}
		}
	}
	
	@Override
	protected void doAnalysis() {
		super.doAnalysis();
		
	}
	
	protected void internalWriteSummaries() {
		
	}

	public void setSummaryWriter(SummaryWriter<MemorySummary> summaryWriter) {
		this.summaryWriter = summaryWriter;
	}
	
	public void setWriteResults(boolean writeResults) {
		this.writeResults = writeResults;
	}

	public void setWriteUnanalysedSummaries(boolean writeUnanalysedSummaries) {
		this.writeUnanalysedSummaries = writeUnanalysedSummaries;
	}
	
	public void setCleanOutputFolder(boolean cleanOutputFolder) {
		this.cleanOutputFolder = cleanOutputFolder;
	}

	public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}

	public void setWriteReport(boolean writeReport) {
		this.writeReport = writeReport;
	}

	public void setWriteUnanalyzedSummariesInReport(boolean writeUnanalyzedSummariesInReport) {
		this.writeUnanalyzedSummariesInReport = writeUnanalyzedSummariesInReport;
	}
	
	public void setReportWriter(ReportWriter reportWriter) {
		this.reportWriter = reportWriter;
	}
}