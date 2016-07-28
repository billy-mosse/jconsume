package ar.uba.dc.analysis.common.intermediate_representation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.common.code.MethodBody;
import ar.uba.dc.soot.SootUtils;
import soot.SootMethod;

public class IntermediateRepresentationMethod {
	
protected String name;
	
	protected String type;
	
	protected Set<IntermediateRepresentationParameterWithType> parameters;
	
	protected IntermediateRepresentationMethodBody body;
	
	protected String returnType;
	
	protected long order;
	
	
	
	public IntermediateRepresentationMethod()
	{
		
	}
	private static Log log = LogFactory.getLog(IntermediateRepresentationMethod.class);

	protected SootMethod target;
	
	
	
	private void setParameters()
	{	
		//TODO: esto esta horrible
		this.parameters = new LinkedHashSet<IntermediateRepresentationParameterWithType>();
		Set<IntermediateRepresentationParameter> s = SootUtils.getParameters(this.target, IntermediateRepresentationParameterWithType.class);
		for(IntermediateRepresentationParameter p : s)
		{			
			this.parameters.add((IntermediateRepresentationParameterWithType)p);
		}
		
		
	}
	
	
	private void setReturnType()
	{
		this.returnType = target.getReturnType().toString();
	}

	private void setName()
	{
		this.name = target.getName();
	}
	
	
	public IntermediateRepresentationMethod(MethodBody methodBody, long order)
	{
		//TODO: NO HACE FALTA PARSEAR. EL SOOTMETHOD YA TIENE TODO!!
		
		this.target = methodBody.belongsTo();
		this.order = order;
		

		log.debug("____Construyendo " + target.toString());
		
		
		this.setName();
		this.setParameters();
		this.setReturnType();
		
		
		
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


	//TODO: terminar
	public String toFormattedString()
	{
		return "name: " + this.name + ", type: " + this.type;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Set<IntermediateRepresentationParameterWithType> getParameters() {
		return parameters;
	}
	public void setParameters(Set<IntermediateRepresentationParameterWithType> arguments) {
		this.parameters = arguments;
	}

	public SootMethod getTarget() {
		// TODO Auto-generated method stub
		return this.target;
	}


	public String toHumanReadableString() {
		StringBuffer sbf = new StringBuffer();
        
        //StringBuffer contents
        sbf.append(this.name);
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
}
