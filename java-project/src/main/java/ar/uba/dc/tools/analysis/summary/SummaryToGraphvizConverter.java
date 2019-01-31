package ar.uba.dc.tools.analysis.summary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootMethod;
import ar.uba.dc.analysis.common.SummaryReader;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedMemorySummary;
import ar.uba.dc.annotations.AnnotationSiteInvariantForJson;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.util.location.MethodLocationStrategy;

public class SummaryToGraphvizConverter {

	private static Log log = LogFactory.getLog(SummaryToGraphvizConverter.class);
	
	public static void main(String[] args) throws FileNotFoundException {
		String propertiesFile = null;

		if (args.length >= 2) {
			if (StringUtils.isNotBlank(args[2])) {
				propertiesFile = args[2];
			}
		}
		
		Context ctx = ContextFactory.getContext(propertiesFile);
		SootUtils.setClasspath(ctx);
		String dir = args[0];
		String type = args[1];
		
		SummaryToGraphvizConverter tool = new SummaryToGraphvizConverter();
		tool.run(dir, type);
	}
	
	@SuppressWarnings("unchecked")
	private void run (String dir, String type) throws FileNotFoundException {
		log.info("Convert summaries from in [" + dir + "] of type [" + type + "]");
		
		SummaryConverter converter = null;
		
		if (StringUtils.equalsIgnoreCase("escape", type)) {
			converter = new EscapeConverter();
		} else {
			converter = new MemoryConverter();
		}
		
		Iterator<File> itFiles = FileUtils.iterateFiles(new File(dir), new String[] { "xml" }, true);
		
		while (itFiles.hasNext()) {
			File summaryFile = itFiles.next();
			try {
				log.info(" |- processing file [" + summaryFile.getAbsolutePath() + "]");
				converter.convert(summaryFile);
			} catch (Throwable e) {
				log.error(" | |- Conversion fail: " + e.getMessage());
			}
		}
		
		log.info("Finished");
	}
	
	interface SummaryConverter {
		void convert(File summaryFile) throws FileNotFoundException;
	}
	
	class EscapeConverter implements SummaryConverter {

		private SummaryReader<EscapeSummary> reader = null;
		private ar.uba.dc.analysis.escape.summary.io.writer.GraphvizWriter writer = null;
		
		public EscapeConverter() {
			super();
			this.reader = new ar.uba.dc.analysis.escape.summary.io.reader.XMLReader();
			this.writer = new ar.uba.dc.analysis.escape.summary.io.writer.GraphvizWriter();
			this.writer.setGraphSizeLimit(250);
			this.writer.setLeaveSourceFile(false);
		}

		@Override
		public void convert(File summaryFile) throws FileNotFoundException {
			final String destPath = StringUtils.chomp(summaryFile.getAbsolutePath(), ".xml") + ".dot";  
			MethodLocationStrategy strategy = new MethodLocationStrategy() {
				
				@Override
				public String getLocation(SootMethod method) {
					return destPath;
				}
				
				@Override
				public String getLocation(IntermediateRepresentationMethod method) {
					throw new java.lang.UnsupportedOperationException();
				}
				
				@Override
				public String getXMLLocation(IntermediateRepresentationMethod method, String mainClass) {
					throw new java.lang.UnsupportedOperationException();

				}

				@Override
				public String getBasePath() {
					throw new java.lang.UnsupportedOperationException();
				}
				
				@Override
				public String getHumanReadableLocation(IntermediateRepresentationMethod ir_method, String mainClass) {
					throw new java.lang.UnsupportedOperationException();
				}
				
				@Override
				public String getJsonIRLocation(IntermediateRepresentationMethod ir_method, String mainClass) {
					throw new java.lang.UnsupportedOperationException();
				}

				@Override
				public String getJsonIRLocation(
						AnnotationSiteInvariantForJson siteInvariant,
						String mainClass) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String getEscapeAnnotationsLocation(String mainClass) {
					// TODO Auto-generated method stub
					throw new java.lang.UnsupportedOperationException();
				}
				
				
			};
			writer.setLocationStrategy(strategy);
			writer.write(reader.read(new FileReader(summaryFile)));
		}
		
	}
	
	class MemoryConverter implements SummaryConverter {

		private SummaryReader<EscapeBasedMemorySummary> reader = null;
		private ar.uba.dc.analysis.memory.impl.summary.io.writer.GraphvizWriter writer = null;
		
		public MemoryConverter() {
			super();
			reader = new ar.uba.dc.analysis.memory.impl.summary.io.reader.XMLReader();
			writer = new ar.uba.dc.analysis.memory.impl.summary.io.writer.GraphvizWriter();
			this.writer.setGraphSizeLimit(250);
			this.writer.setLeaveSourceFile(false);
		}
		
		@Override
		public void convert(File summaryFile) throws FileNotFoundException {
			final String destPath = StringUtils.chomp(summaryFile.getAbsolutePath(), ".xml") + ".dot";  
			MethodLocationStrategy strategy = new MethodLocationStrategy() {
				
				
				@Override
				public String getLocation(IntermediateRepresentationMethod method) {
					throw new java.lang.UnsupportedOperationException();
				}
				
				
				@Override
				public String getLocation(SootMethod method) {
					return destPath;
				}

				@Override
				public String getXMLLocation(IntermediateRepresentationMethod method, String mainClass) {
					throw new java.lang.UnsupportedOperationException();

				}

				@Override
				public String getBasePath() {
					throw new java.lang.UnsupportedOperationException();
				}
				
				@Override
				public String getHumanReadableLocation(IntermediateRepresentationMethod ir_method, String mainClass) {
					throw new java.lang.UnsupportedOperationException();
				}
				
				@Override
				public String getJsonIRLocation(IntermediateRepresentationMethod ir_method, String mainClass) {
					throw new java.lang.UnsupportedOperationException();
				}


				@Override
				public String getJsonIRLocation(
						AnnotationSiteInvariantForJson siteInvariant,
						String mainClass) {
					// TODO Auto-generated method stub
					return null;
				}


				@Override
				public String getEscapeAnnotationsLocation(String mainClass) {
					// TODO Auto-generated method stub
					throw new java.lang.UnsupportedOperationException();
				}
				
			};
			writer.setLocationStrategy(strategy);
			writer.write(reader.read(new FileReader(summaryFile)));
		}
		
	}
}
