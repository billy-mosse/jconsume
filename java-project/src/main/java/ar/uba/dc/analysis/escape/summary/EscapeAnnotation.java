package ar.uba.dc.analysis.escape.summary;

import java.util.List;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListDIParameters;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;

public interface EscapeAnnotation{
	public boolean isFresh();
	
	//La anotacion pure equivale a que no haya writable parameters. Es totalmente redundante.
	//public boolean isPure();
	public boolean isWritableParameter(int i);
	
	public List<Integer> getWritableParameters();

	//los relevant parameters son de tipo DI_JsonParameter
	public ListDIParameters getRelevantParameters();

	public ListDIParameters getParameters();
	
	public String getSignature();

	public String getClassName();
	

	public boolean isArtificial();
	
	public ParametricExpression getMaxLive();
	
	public ParametricExpression getEscape();
	
	//public String[] getRelevantParameters();
}
