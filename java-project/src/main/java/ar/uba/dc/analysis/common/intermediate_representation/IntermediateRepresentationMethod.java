package ar.uba.dc.analysis.common.intermediate_representation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hamcrest.core.IsInstanceOf;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.code.BasicMethodBody;
import ar.uba.dc.analysis.common.code.MethodBody;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.ContainerNode;
import ar.uba.dc.analysis.escape.graph.node.GlobalNode;
import ar.uba.dc.analysis.escape.graph.node.MethodNode;
import ar.uba.dc.analysis.escape.graph.node.StmtNode;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.soot.SootUtils;
import soot.RefLikeType;
import soot.SootMethod;

public class IntermediateRepresentationMethod {
	
	protected String name;
	
	//protected String integer_name;
	
	protected Set<IntermediateRepresentationParameter> parameters;
	
	protected IntermediateRepresentationMethodBody body;
	
	protected String returnType;
	
	protected long order;
	
	protected Set<String> relevant_parameters;

	protected DomainSet methodRequirements;
	
	protected boolean isReturnRefLikeType;
	
	protected int number;
	protected String declaration;
	
	protected String subSignature;

	
	
	public String getSubSignature() {
		return subSignature;
	}


	public void setSubSignature(String subSignature) {
		this.subSignature = subSignature;
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public String getDeclaration() {
		return declaration;
	}


	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}
	
	
	public boolean isReturnRefLikeType()
	{
		return this.isReturnRefLikeType;
	}
	
	
	public void setIsReturnRefLikeType(boolean isReturnRefLikeType)
	{
		this.isReturnRefLikeType = isReturnRefLikeType;
	}
	
	
	//TODO: ESTO ESTA MAL, NO DEBERIA EXISTIR PORQUE CADA NEW YA TIENE ASOCIADO SU NODO.
	protected Set<PaperPointsToHeapPartition> escapeNodes;
	
	private String declaringClass;
	
	protected String class_owner;
	
	
	
	
	
	
	
	public IntermediateRepresentationMethod()
	{
		
	}
	private static Log log = LogFactory.getLog(IntermediateRepresentationMethod.class);

	//protected SootMethod target;
	
	
	
	private void setParametersFromSootMethod(SootMethod target)
	{	
		this.parameters = new LinkedHashSet<IntermediateRepresentationParameter>();
		Set<IntermediateRepresentationParameter> s = SootUtils.getParameters(target, true);
		for(IntermediateRepresentationParameter p : s)
		{			
			this.parameters.add(p);
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
		
		this.isReturnRefLikeType = m.getReturnType() instanceof RefLikeType;
		
		
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
	public Set<IntermediateRepresentationParameter> getParameters() {
		return parameters;
	}
	
	//TODO: creeria que esto esta mal porque mueren al final. Probemos.
	public void setParameters(Set<IntermediateRepresentationParameter> parameters) {
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

	@Override
	public String toString()
	{
		return this.declaringClass  + "." + this.name;
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
		String s = (this.getParameters() != null ? Joiner.on(", ").skipNulls().join(Iterables.transform(this.getParameters(), new Function<IntermediateRepresentationParameter, String >()
		{
			public String apply(IntermediateRepresentationParameter parameter) { return parameter.getType() + " " + parameter.getName(); }
		}
		
		)) : "");

		return this.getReturnType() + " " + this.getName() + String.format("(%s)", s);
	}


	public void setEscapeInfo(EscapeSummary escapeSummary) {
		Set<Node> escaping = escapeSummary.getEscaping();	
		this.escapeNodes = new LinkedHashSet<PaperPointsToHeapPartition>();
		for (Node n : escaping) {
			
			if(n.getClass() == StmtNode.class)
			{
				StmtNode stmtNode = (StmtNode) n;
				this.escapeNodes.add(new PaperPointsToHeapPartition(false, n, stmtNode.belongsTo().getDeclaringClass() + "." + stmtNode.belongsTo().getName()));
			} 
			else if(n.getClass() == MethodNode.class) //Podria chequear si tienen el metodo belongsTo(), pero no tengo internet para fijarme como se hace
			{
				MethodNode stmtNode = (MethodNode) n;
				this.escapeNodes.add(new PaperPointsToHeapPartition(false, n, stmtNode.belongsTo().getDeclaringClass() + "." + stmtNode.belongsTo().getName()));
			} 
			else if(n.getClass() == GlobalNode.class) //Podria chequear si tienen el metodo belongsTo(), pero no tengo internet para fijarme como se hace
			{
				GlobalNode stmtNode = (GlobalNode) n;
				this.escapeNodes.add(new PaperPointsToHeapPartition(false, n, stmtNode.belongsTo().getDeclaringClass() + "." + stmtNode.belongsTo().getName()));
			}
			else if(n.getClass() == ContainerNode.class) //Podria chequear si tienen el metodo belongsTo(), pero no tengo internet para fijarme como se hace
			{
				ContainerNode stmtNode = (ContainerNode) n;
				this.escapeNodes.add(new PaperPointsToHeapPartition(false, n, stmtNode.belongsTo().getDeclaringClass() + "." + stmtNode.belongsTo().getName()));
			}
			else
			{
				this.escapeNodes.add(new PaperPointsToHeapPartition(false, n, this.getDeclaringClass() + "." + this.getName()));
			}
		}
		
	}
	
	public Set<PaperPointsToHeapPartition> getEscapeNodes()
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
