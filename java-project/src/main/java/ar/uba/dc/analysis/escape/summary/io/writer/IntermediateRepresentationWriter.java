package ar.uba.dc.analysis.escape.summary.io.writer;

import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;

public class IntermediateRepresentationWriter implements SummaryWriter<IntermediateRepresentationMethod> {


	protected String mainClass;
	
	@Override
	public void write(IntermediateRepresentationMethod summary) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	

}
