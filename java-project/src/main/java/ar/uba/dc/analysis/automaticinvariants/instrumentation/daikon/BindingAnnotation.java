package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;

import java.util.ArrayList;
import java.util.List;

public class BindingAnnotation {
	//El callee tiene parametros relevantes nuevos.
	//Todos los callers que lo llamen deben propagar esos relevant parameters hacia arriba,
	//Con el mismo nombre (si el nombre se repite sumar 1 a un indice)
	//y agregar un BindingAnnotation al InstrumentationSiteInvariantsReader
	String callee;
	
	public String getCallee() {
		return callee;
	}

	public void setCallee(String callee) {
		this.callee = callee;
	}

	//La lista de los parametros relevantes nuevos del callee
	List<String> newRelevantParameters;
	
	public List<String> getNewRelevantParameters() {
		return newRelevantParameters;
	}

	public void setNewRelevantParameters(List<String> newRelevantParameters) {
		this.newRelevantParameters = newRelevantParameters;
	}

	public BindingAnnotation(String callee)
	{
		this.callee = callee;
		this.newRelevantParameters = new ArrayList<String>();
	}

	public BindingAnnotation(String callee, List<String> newRelevantParameters)
	{
		this.callee = callee;
		this.newRelevantParameters = newRelevantParameters;
	}

	public void addNewRelevantParameter(String param) {
		this.newRelevantParameters.add(param);		
	}
}
