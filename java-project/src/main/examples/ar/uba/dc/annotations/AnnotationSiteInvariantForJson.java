package ar.uba.dc.annotations;

import java.util.ArrayList;
import java.util.List;

public class AnnotationSiteInvariantForJson {
	//String type; //vacio si es CallSite, CS si es creation site
    //int index;
    
	public AnnotationSiteInvariantForJson()
	{
		this.constraints = new ArrayList<String>();
		this.newRelevantParameters = new ArrayList<String>();
		this.newInductives = new ArrayList<String>();
		this.newVariables = new ArrayList<String>();
	}
	public AnnotationSiteInvariantForJson(String methodName)
	{
		this.constraints = new ArrayList<String>();
		this.newRelevantParameters = new ArrayList<String>();
		this.newInductives = new ArrayList<String>();
		this.newVariables = new ArrayList<String>();
		this.methodName = methodName;
	}
	
	
	List<String> constraints;
    
    
    public List<String> getConstraints() {
		return constraints;
	}
	public void setConstraints(List<String> constraints) {
		this.constraints = constraints;
	}
	public List<String> getNewRelevantParameters() {
		return newRelevantParameters;
	}
	public void setNewRelevantParameters(List<String> newRelevantParameters) {
		this.newRelevantParameters = newRelevantParameters;
	}
	public List<String> getNewInductives() {
		return newInductives;
	}
	public void setNewInductives(List<String> newInductives) {
		this.newInductives = newInductives;
	}
	public List<String> getNewVariables() {
		return newVariables;
	}
	public void setNewVariables(List<String> newVariables) {
		this.newVariables = newVariables;
	}
	List<String> newRelevantParameters;
    List<String> newInductives;
    List<String> newVariables;
    
    String methodName;
    public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	private int index;
	String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}	

	public void addNewRelevantParameter(String param) {
		this.newRelevantParameters.add(param);
	}
}
