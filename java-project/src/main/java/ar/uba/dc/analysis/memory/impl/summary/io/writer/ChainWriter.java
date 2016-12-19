package ar.uba.dc.analysis.memory.impl.summary.io.writer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.config.Container;

public class ChainWriter implements SummaryWriter<MemorySummary>, Container<SummaryWriter<MemorySummary>> {

	private List<SummaryWriter<MemorySummary>> writers = new ArrayList<SummaryWriter<MemorySummary>>();
	
	public ChainWriter() {
		super();
	}
	
	public void write(MemorySummary summary) {
		for (SummaryWriter<MemorySummary> writer : writers) {
			writer.write(summary);
		}
	}
	
	public List<SummaryWriter<MemorySummary>> getWriters() {
		return new LinkedList<SummaryWriter<MemorySummary>>(writers);
	}
	
	public void register(SummaryWriter<MemorySummary> writer) {
		writers.add(writer);
	}
	
	public void unregister(SummaryWriter<MemorySummary> writer) {
		int lastIndexOfPrinter = writers.lastIndexOf(writer);
		
		if (lastIndexOfPrinter >= 0) {
			writers.remove(lastIndexOfPrinter);
		}
	}

	@Override
	public void setMainClass(String mainClass) {
		throw new java.lang.UnsupportedOperationException();
		
	}

}
