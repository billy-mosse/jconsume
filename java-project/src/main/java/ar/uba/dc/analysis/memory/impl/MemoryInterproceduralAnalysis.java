package ar.uba.dc.analysis.memory.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootClass;
import soot.SootMethod;
import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.memory.InterproceduralAnalysis;
import ar.uba.dc.analysis.memory.impl.report.datasource.AnalysisResultsReportDataSource;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.util.Timer;

/**
 * Implementacion particular del analisis de memoria que agrega la posibilidad de escribir los summaries
 * obtenidos durante el analisis.
 * 
 * @author testis
 */
public class MemoryInterproceduralAnalysis extends InterproceduralAnalysis {

	private static Log log = LogFactory.getLog(MemoryInterproceduralAnalysis.class);

	protected SummaryWriter<MemorySummary> summaryWriter;
	
	protected boolean writeResults;
	
	protected boolean writeUnanalysedSummaries;
	
	protected boolean cleanOutputFolder;
	
	protected String outputFolder;
	
	protected boolean writeReport;
	
	protected boolean writeUnanalyzedSummariesInReport;
	
	protected ReportWriter<SootClass, MemorySummary> reportWriter;
	
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
		
		if (writeResults) {
			log.info("Writing Memory summaries");
			Timer t = new Timer();
			t.start();
			
			internalWriteSummaries();
	
			t.stop();
			log.info("Writing finished. Took " + t.getElapsedTime() + " (" + t.getElapsedSeconds() + " seconds)");
		}
		
		if (writeReport) {
			log.info("Writing Memory report");
			Timer t = new Timer();
			t.start();
			
			Map<SootMethod, MemorySummary> summaries = new HashMap<SootMethod, MemorySummary>(data);
			if (writeUnanalyzedSummariesInReport) {
				summaries.putAll(unanalysed);
			}
			if(directedCallGraph.size()>0)
 				reportWriter.write(new AnalysisResultsReportDataSource(directedCallGraph.getHeads().iterator().next(), summaries));
	
			t.stop();
			log.info("Writing finished. Took " + t.getElapsedTime() + " (" + t.getElapsedSeconds() + " seconds)");
		}
	}
	
	protected void internalWriteSummaries() {
		for (MemorySummary summary : data.values()) {
			log.debug(" |- Writing summary of analyzed method: " + summary.getTarget());
			summaryWriter.write(summary);
		}

		if (writeUnanalysedSummaries) {
			for (MemorySummary summary : unanalysed.values()) {
				log.debug(" |- Writing summary of unanalyzed method: " + summary.getTarget());
				summaryWriter.write(summary);
			}
		}
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