package ar.uba.dc.tools.analysis.summary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootUtils;

/**
 * Arma un ranking de los summaries existenes en un repositorio en base a la cantidad de nodos que poseen
 * 
 * @author testis
 */
public class NodesCounterTool {

	private static Log log = LogFactory.getLog(NodesCounterTool.class);
	
	public static void main(String[] args) throws FileNotFoundException, DocumentException {
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
		Boolean recursive = true;
		
		if (args.length >= 4) {
			dir = ctx.getString(args[3]);
		}
		
		if (args.length >= 5) {
			recursive = Boolean.valueOf(args[4]);
		}
		
		NodesCounterTool tool = new NodesCounterTool();
		tool.run(dir, type, recursive);
	}
	
	@SuppressWarnings("unchecked")
	private void run (String dir, String type, boolean recursive) throws FileNotFoundException, DocumentException {
		log.info("Rank summaries in [" + dir + "] of type [" + type + "]");
		
		String pathToNodes = "heap/graph/nodes"; 
		if (StringUtils.equalsIgnoreCase("escape", type)) {
			pathToNodes = "points-to-graph/graph/nodes";
		}
		String classAttr = "className";
		String methodAttr = "methodSignature";
		
		Iterator<File> itFiles = FileUtils.iterateFiles(new File(dir), new String[] { "xml" }, recursive);
		
		List<SummaryInfo> summaryInfo = new LinkedList<SummaryInfo>();
		while (itFiles.hasNext()) {
			File summaryFile = itFiles.next();
			
			log.debug(" |- processing file [" + summaryFile.getAbsolutePath() + "]");
					
			SAXReader reader = new SAXReader();
			Document document = reader.read(summaryFile);
			
			String className = document.getRootElement().attributeValue(classAttr);
			String methodSignature = document.getRootElement().attributeValue(methodAttr);
			
			Element root = document.getRootElement();
			
			for (String path : pathToNodes.split("/")) {
				root = root.element(path);
			}
			
			summaryInfo.add(new SummaryInfo(className + ": " + methodSignature, root.elements().size()));
		}
		
		Collections.sort(summaryInfo, Collections.reverseOrder());
		
		Integer totalSize = 0;
		log.info("RANKING");
		log.info("=======");
		for (SummaryInfo info : summaryInfo) {
			log.info(info.getMethod() + " - " + info.getSize());
			totalSize += info.getSize();
		}
		log.info("Total Methods: " + summaryInfo.size());
		log.info("Total Nodes: " + totalSize);
		
		log.info("Finished");
	}
	
	class SummaryInfo implements Comparable<SummaryInfo> {
		private String method;
		private Integer size;
		
		public SummaryInfo(String method, Integer size) {
			this.method = method;
			this.size = size;
		}

		@Override
		public int compareTo(SummaryInfo arg0) {
			int ret = size.compareTo(arg0.size);
			
			if (ret == 0) {
				ret = method.compareTo(arg0.method);
			}
			
			return ret;
		}

		public String getMethod() {
			return method;
		}

		public Integer getSize() {
			return size;
		}

	}

}
