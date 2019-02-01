package ar.uba.dc.analysis.escape.summary;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;

public class DefaultEscapeAnnotation implements EscapeAnnotation, Comparable<DefaultEscapeAnnotation>{
	
	protected String name;
	protected boolean fresh;
	
	/*protected boolean pure;
	
	public boolean isPure() {
		return pure;
	}

	public void setPure(boolean pure) {
		this.pure = pure;
	}*/

	protected List<Integer> writableParameters;
	
	public List<Integer> getWritableParameters() {
		return writableParameters;
	}

	public void setWritableParameters(List<Integer> writableParameters) {
		this.writableParameters = writableParameters;
	}

	public void setFresh(boolean fresh) {
		this.fresh = fresh;
	}

	public boolean isFresh()
	{
		return fresh;
	}
	
	//La anotacion pure equivale a que no haya writable parameters. Es totalmente redundante.
	//public boolean isPure();
	public boolean isWritableParameter(int i)
	{
		return this.writableParameters.contains(i);
	}

	@Override
	public int compareTo(DefaultEscapeAnnotation o) {
		return this.name.compareTo(o.getName());
	}
	
	public String getName()
	{
		return this.name;
	}	
	
	
}
