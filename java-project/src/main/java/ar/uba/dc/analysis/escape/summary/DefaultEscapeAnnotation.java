package ar.uba.dc.analysis.escape.summary;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;

public class DefaultEscapeAnnotation implements EscapeAnnotation, Comparable<DefaultEscapeAnnotation>{
	
	protected String name;
	protected boolean fresh;
	protected boolean omega;
	
	public boolean isOmega() {
		return omega;
	}

	public void setOmega(boolean omega) {
		this.omega = omega;
	}

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
		throw new NotImplementedException();
	}
	
	//La anotacion pure equivale a que no haya writable parameters. Es totalmente redundante.
	//public boolean isPure();
	public boolean isWritableParameter(int i)
	{
		throw new NotImplementedException();
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
