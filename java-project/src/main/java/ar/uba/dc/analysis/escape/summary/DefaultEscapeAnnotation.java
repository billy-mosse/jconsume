package ar.uba.dc.analysis.escape.summary;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.DIParameter;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.ListDIParameters;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.parameters.SimpleDIParameter;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpression;
import ar.uba.dc.barvinok.expression.DomainSet;

public class DefaultEscapeAnnotation implements EscapeAnnotation, Comparable<DefaultEscapeAnnotation>{
	
	protected String name;
	protected boolean fresh;
	protected String signature;
	protected ListDIParameters relevantParameters;
	protected ListDIParameters parameters;
	
	protected DomainSet methodRequirements;
	
	public DomainSet getMethodRequirements() {
		return methodRequirements;
	}

	public void setMethodRequirements(DomainSet methodRequirements) {
		this.methodRequirements = methodRequirements;
	}

	protected boolean isArtificial;
	protected ParametricExpression maxLive;
	protected ParametricExpression escape;

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	
	public void setRelevantParameters(ListDIParameters relevantParameters) {
		this.relevantParameters = relevantParameters;
	}

	/*protected boolean pure;
	
	public boolean isPure() {
		return pure;
	}

	public void setPure(boolean pure) {
		this.pure = pure;
	}*/

	public void setParameters(ListDIParameters parameters) {
		this.parameters = parameters;
	}

	protected List<Integer> writableParameters;
	
	@Override
	public List<Integer> getWritableParameters() {
		return writableParameters;
	}

	public void setWritableParameters(List<Integer> writableParameters) {
		this.writableParameters = writableParameters;
	}

	public void setFresh(boolean fresh) {
		this.fresh = fresh;
	}
	
	public void setName(String name) {
		this.name = name;
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

	@Override
	public ListDIParameters getRelevantParameters() {
		return this.relevantParameters;
	}

	@Override
	public ListDIParameters getParameters() {
		return this.parameters;
	}

	@Override
	public String getClassName() {
		return this.signature.substring(1,
				this.signature.indexOf(':')
				);
	}
	
	
	public ParametricExpression getMaxLive()
	{
		return this.maxLive;
	}
	
	public ParametricExpression getEscape()
	{
		return this.escape;
	}
	
	public void setMaxLiveFromString(String maxLive)
	{
		
		this.maxLive = new BarvinokParametricExpression(maxLive);
	}
	
	public void setEscapeFromString(String escape)
	{
		this.escape = new BarvinokParametricExpression(escape);
	}
	
	
	public boolean getIsArtificial() {
		return isArtificial;
	}

	public void setIsArtificial(boolean isArtificial) {
		this.isArtificial = isArtificial;
	}

	@Override
	public boolean isArtificial() {
		return getIsArtificial();
	}

}
