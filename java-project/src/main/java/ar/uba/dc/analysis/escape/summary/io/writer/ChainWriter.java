package ar.uba.dc.analysis.escape.summary.io.writer;

import java.util.ArrayList;
import java.util.List;


import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.config.Container;

public class ChainWriter implements SummaryWriter<EscapeSummary>, Container<SummaryWriter<EscapeSummary>> {

	private List<SummaryWriter<EscapeSummary>> writers = new ArrayList<SummaryWriter<EscapeSummary>>();
	
	public ChainWriter() {
		super();
	}
	

	public void write(EscapeSummary summary) {
		for (SummaryWriter<EscapeSummary> writer : writers) {
			writer.write(summary);
		}
	}
	
	public void register(SummaryWriter<EscapeSummary> writer) {
		writers.add(writer);
	}
	
	public void unregister(SummaryWriter<EscapeSummary> writer) {
		int lastIndexOfPrinter = writers.lastIndexOf(writer);
		
		if (lastIndexOfPrinter >= 0) {
			writers.remove(lastIndexOfPrinter);
		}
	}
}
