package ar.uba.dc.analysis.memory.impl.report.datasource;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootClass;
import ar.uba.dc.analysis.common.SummaryReader;
import ar.uba.dc.analysis.memory.impl.report.html.ReportDataSource;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

public class RepositoryReportDataSource implements ReportDataSource {

	private static Log log = LogFactory.getLog(RepositoryReportDataSource.class);
	
	protected SummaryReader<MemorySummary> reader;
	
	protected String escapeRepository;
	
	protected String memoryRepository;
	
	protected String reportDir;
	
	protected String mainClass;

	@Override
	@SuppressWarnings("unchecked")
	public Map<SootClass, SortedSet<MemorySummary>> getClassIndex() {
		Map<SootClass, SortedSet<MemorySummary>> summariesGroupedByClass = new TreeMap<SootClass, SortedSet<MemorySummary>>(new BeanComparator("name"));
		try {
			IOFileFilter fileFilter = FileFilterUtils.suffixFileFilter(".xml");
			// Esto es por si se solapan los directorios de repositorios
			IOFileFilter dirFilter = FileFilterUtils.notFileFilter(
										FileFilterUtils.orFileFilter(
											FileFilterUtils.nameFileFilter(new File(escapeRepository).getName()),
											FileFilterUtils.nameFileFilter(new File(reportDir).getName())
										)
									);
			
			Iterator<File> it = FileUtils.iterateFiles(new File(memoryRepository), fileFilter, dirFilter);
			while (it.hasNext()) {
				File summaryFile = it.next();
				log.debug("Reading summary from [" + summaryFile.getAbsolutePath() + "]");
				MemorySummary summary = reader.read(new FileReader(summaryFile));
				SootClass clazz = summary.getTarget().getDeclaringClass();
				SortedSet<MemorySummary> summariesOfClass = summariesGroupedByClass.get(clazz);
				if (summariesOfClass == null) {
					summariesOfClass = new TreeSet<MemorySummary>(new BeanComparator("target.declaration"));
					summariesGroupedByClass.put(clazz, summariesOfClass);
				}
				summariesOfClass.add(summary);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error al recuperar los summaries de memoria", e);
			throw new RuntimeException(e);
		}

		return summariesGroupedByClass;
	}

	public void setReader(SummaryReader<MemorySummary> reader) {
		this.reader = reader;
	}

	@Override
	public String getMainClass() {
		return mainClass;
	}
	
	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	public void setEscapeRepository(String escapeRepository) {
		this.escapeRepository = escapeRepository;
	}

	public void setMemoryRepository(String memoryRepository) {
		this.memoryRepository = memoryRepository;
	}

	public String getReportDir() {
		return reportDir;
	}

	public void setReportDir(String reportDir) {
		this.reportDir = reportDir;
	}
}
