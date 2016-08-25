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

	protected Set<Node> escapeNodes;
	
	private String declaringClass;
	
	
	
	
	
	
	
	public IntermediateRepresentationMethod()
	{
		
	}
	private static Log log = LogFactory.getLog(IntermediateRepresentationMethod.class);

	//protected SootMethod target;
	
	
	
	private void setParameters(SootMethod target)
	{	
		//TODO: esto esta horrible
		this.parameters = new LinkedHashSet<IntermediateRepresentationParameterWithType>();
		Set<IntermediateRepresentationParameter> s = SootUtils.getParameters(target, IntermediateRepresentationParameterWithType.class);
		for(IntermediateRepresentationParameter p : s)
		{			
			this.parameters.add((IntermediateRepresentationParameterWithType)p);
		}
		
		
	}
	
	
	private void setReturnType(SootMethod target)
	{
		this.returnType = target.getReturnType().toString();
	}

	public String getReturnType()
	{
		return this.returnType;
	}
	
	private void setName(SootMethod target)
	{
		this.name = target.getName();
	}
	
	
	public IntermediateRepresentationMethod(BasicMethodBody methodBody, long order)
	{
		//TODO: NO HACE FALTA PARSEAR. EL SOOTMETHOD YA TIENE TODO!!
		
		SootMethod target = methodBody.belongsTo();
		this.order = order;
		

		log.debug("____Construyendo " + target.toString());
		
		
		this.setName(target);
		this.setParameters(target);
		this.setReturnType(target);
		this.declaringClass = target.getDeclaringClass().toString();
		
		String s = target.getSignature();
		s = s;
		
		
		
		/*String completeName = m.toString();
		String[] v = completeName.split(" |<|>");
		
		assertLength(v, 4, completeName);
		
		this.type = v[2];
		
		String name_with_arguments = v[3];
		String[] v2 = name_with_arguments.split("\\(|\\)"); //creo que los parentesis se tienen que escapar. El | es un OR en regex
		assertLength(v2, 2, completeName);
		
		this.name = v2[0];
		
		String arguments_string = v2[1];
		
		String[] arguments = arguments_string.split(",");
		
		
		for(int i = 0; i < arguments.length;i++)
		{
			IntermediateRepresentationArgumentWithType arg = new IntermediateRepresentationArgumentWithType (arguments[i]);
		}*/
		
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
    	
    	String s = Joiner.on(", ").skipNulls().join(Iterables.transform(this.escapeNodes, new Function<Node, String >()
		{
			public String apply(Node n) { return n.toString(); }
		}
		
		));
    	
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
		this.escapeNodes = new LinkedHashSet<Node>();
		for (Node n : escaping) {
			this.escapeNodes.add(n);
		}
		
	}


	public String getDeclaringClass() {
		return declaringClass;
	}


	public void setDeclaringClass(String declaringClass) {
		this.declaringClass = declaringClass;
	}
}
