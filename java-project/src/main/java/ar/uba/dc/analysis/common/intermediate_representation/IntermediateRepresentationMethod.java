package ar.uba.dc.analysis.common.intermediate_representation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.code.MethodBody;
import ar.uba.dc.analysis.escape.IntermediateLanguageRepresentationBuilder;
import ar.uba.dc.invariant.InvariantProvider;
import soot.IntType;
import soot.Local;
import soot.SootMethod;
import soot.util.Chain;

public class IntermediateRepresentationMethod {
	
	
	private static Log log = LogFactory.getLog(IntermediateRepresentationMethod.class);

	protected SootMethod target;
	

	private void assertLength(String[] v, int length, String fullString)
	{
		if (v.length != length)
		{
			throw new RuntimeException("size of vector should be 3. Check " + fullString + "for any errors. Or is there a case not considered?"); 
		}
	}
	
	private void setParameters()
	{

		this.parameters = new HashSet<IntermediateRepresentationParameterWithType>();
		
		
		//TODO: esto esta horrible
		List parameterTypes = new ArrayList(target.getParameterTypes());	
		
		
		String lines[] = target.getActiveBody().toString().split("\\r?\\n");
		
		for (String line: lines)
		{
			for(int i = 0; i < parameterTypes.size(); i++)
			{
				String numbered_parameter ="@parameter" + i;
				if(line.contains(numbered_parameter))
				{
					String parameter = line.split(" := ")[0].trim();
					String parameterType = parameterTypes.remove(i).toString();
					
					this.parameters.add(new IntermediateRepresentationParameterWithType(parameter, parameterType));
				}
			}
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
	
	
	public IntermediateRepresentationMethod(MethodBody methodBody)
	{
		//TODO: NO HACE FALTA PARSEAR. EL SOOTMETHOD YA TIENE TODO!!
		
		this.target = methodBody.belongsTo();
		

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
	
	
	
	protected String name;
	
	protected String type;
	
	protected Set<IntermediateRepresentationParameterWithType> parameters;
	
	protected IntermediateRepresentationMethodBody body;
	
	protected String returnType;
	
	
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
}
