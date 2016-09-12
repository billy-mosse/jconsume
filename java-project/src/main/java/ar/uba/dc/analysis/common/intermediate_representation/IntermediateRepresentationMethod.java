package ar.uba.dc.analysis.common.intermediate_representation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.code.BasicMethodBody;
import ar.uba.dc.analysis.common.code.MethodBody;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.soot.SootUtils;
import soot.SootMethod;

public class IntermediateRepresentationMethod {
	
	protected String name;
	
	//protected String integer_name;
	
	protected Set<IntermediateRepresentationParameterWithType> parameters;
	
	protected IntermediateRepresentationMethodBody body;
	
	protected String returnType;
	
	protected long order;
	
	protected Set<String> relevant_parameters;

	protected DomainSet methodRequirements;

	
	//TODO: ESTO ESTA MAL, NO DEBERIA EXISTIR PORQUE CADA NEW YA TIENE ASOCIADO SU NODO.
	protected Set<String> escapeNodes;
	
	private String declaringClass;
	
	protected String class_owner;
	
	
	
	
	
	
	
	public IntermediateRepresentationMethod()
	{
		
	}
	private static Log log = LogFactory.getLog(IntermediateRepresentationMethod.class);

	//protected SootMethod target;
	
	
	
	private void setParametersFromSootMethod(SootMethod target)
	{	
		//TODO: esto esta horrible
		this.parameters = new LinkedHashSet<IntermediateRepresentationParameterWithType>();
		Set<IntermediateRepresentationParameter> s = SootUtils.getParameters(target, IntermediateRepresentationParameterWithType.class);
		for(IntermediateRepresentationParameter p : s)
		{			
			this.parameters.add((IntermediateRepresentationParameterWithType)p);
		}
		
		
	}	
	
	public String getReturnType()
	{
		return this.returnType;
	}
	
	public void setReturnType(String returnType)
	{
		this.returnType = returnType;
	}
	
	public IntermediateRepresentationMethod(SootMethod m, long order)
	{
		//TODO: NO HACE FALTA PARSEAR. EL SOOTMETHOD YA TIENE TODO!!
		
		this.order = order;		

		log.debug("____Construyendo " + m.toString());
		
		
		this.name = m.getName();
		this.setParametersFromSootMethod(m);
		this.returnType = m.getReturnType().toString();
		this.declaringClass = m.getDeclaringClass().toString();
		
	}
	
	
	public long getOrder() {
		return order;
	}


	public void setOrder(long order) {
		this.order = order;
	}


	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public IntermediateRepresentationMethodBody getBody() {
		return body;
	}
	public void setBody(IntermediateRepresentationMethodBody body) {
		this.body = body;
	}
	public Set<IntermediateRepresentationParameterWithType> getParameters() {
		return parameters;
	}
	
	//TODO: creeria que esto esta mal porque mueren al final. Probemos.
	public void setParameters(Set<IntermediateRepresentationParameterWithType> parameters) {
		this.parameters = parameters;
	}


	public Set<String> getRelevant_parameters() {
		return relevant_parameters;
	}


	public void setRelevant_parameters(Set<String> relevant_parameters) {
		this.relevant_parameters = relevant_parameters;
	}


	public DomainSet getMethodRequirements() {
		return methodRequirements;
	}


	public void setMethodRequirements(DomainSet methodRequirements) {
		this.methodRequirements = methodRequirements;
	}

	public String toHumanReadableString() {
		StringBuffer sbf = new StringBuffer();
        
        //StringBuffer contents
		sbf.append(this.processMethodSignature());		

    	sbf.append(System.getProperty("line.separator"));
        
		sbf.append("Integer signature: " + this.processMethodIntegerSignature());
        
        
        
        if(this.methodRequirements != null)
        {
        	sbf.append(System.getProperty("line.separator"));
            
            sbf.append("Method requirements: " + this.methodRequirements.toHumanReadableString());
        }

    	sbf.append(System.getProperty("line.separator"));
    	
    	sbf.append("Escape info: {");
    	
    	String s = Joiner.on(", ").skipNulls().join(this.escapeNodes);
    	
    	sbf.append(s + "}");
        
        //new line
        
        Set<Line> lines = this.body.getLines(); 
        for(Line line : lines)
        {
            sbf.append(System.getProperty("line.separator"));
            //los NEW ya se duplicaron antes
            //TODO: duplicar los new
        	sbf.append(line.toHumanReadableString());
        }
        //second line
        return sbf.toString();
        
	}
	
	
	public String processMethodIntegerSignature() {
		String s = (this.relevant_parameters != null ? Joiner.on(", ").skipNulls().join(this.relevant_parameters) : "");

		return this.getReturnType() + " " + this.getName() + String.format("(%s)", s);
	}


	public String processMethodSignature() {		
		String s = (this.getParameters() != null ? Joiner.on(", ").skipNulls().join(Iterables.transform(this.getParameters(), new Function<IntermediateRepresentationParameterWithType, String >()
		{
			public String apply(IntermediateRepresentationParameterWithType parameter) { return parameter.getType() + " " + parameter.getName(); }
		}
		
		)) : "");

		return this.getReturnType() + " " + this.getName() + String.format("(%s)", s);
	}


	public void setEscapeInfo(EscapeSummary escapeSummary) {
		Set<Node> escaping = escapeSummary.getEscaping();	
		this.escapeNodes = new LinkedHashSet<String>();
		for (Node n : escaping) {
			this.escapeNodes.add(n.toString());
		}
		
	}
	
	public Set<String> getEscapeNodes()
	{
		return this.escapeNodes;
	}


	public String getDeclaringClass() {
		return declaringClass;
	}


	public void setDeclaringClass(String declaringClass) {
		this.declaringClass = declaringClass;
	}
}
