package ar.uba.dc.analysis.escape.summary;

import java.util.List;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListDIParameters;

public interface EscapeAnnotation{
	public boolean isFresh();
	
	//La anotacion pure equivale a que no haya writable parameters. Es totalmente redundante.
	//public boolean isPure();
	public boolean isWritableParameter(int i);
	
	public List<Integer> getWritableParameters();

	public ListDIParameters getRelevantParameters();

	public ListDIParameters getParameters();
	
	public String getSignature();
	
	//public String[] getRelevantParameters();
}
