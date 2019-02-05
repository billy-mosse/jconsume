package ar.uba.dc.analysis.escape.summary;

import java.util.List;

public interface EscapeAnnotation{
	public boolean isFresh();
	
	//La anotacion pure equivale a que no haya writable parameters. Es totalmente redundante.
	//public boolean isPure();
	public boolean isWritableParameter(int i);
	
	public List<Integer> getWritableParameters();
}
