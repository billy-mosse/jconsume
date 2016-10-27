package decorations;

public class BindingPair {
	protected String caller_parameter;
	protected String callee_parameter;

	public String getCaller_parameter() {
		return caller_parameter;
	}

	public void setCaller_parameter(String caller_parameter) {
		this.caller_parameter = caller_parameter;
	}

	public String getCallee_parameter() {
		return callee_parameter;
	}

	public String getCallee_parameter_with_prefix() {
		return PREFIX + callee_parameter;
	}

	public void setCallee_parameter(String callee_parameter) {
		this.callee_parameter = callee_parameter;
	}

	protected static transient String SEPARATOR = "==";
	protected static transient String PREFIX = "$t." ; //Esta constante ya esta en otros lados, deberia hacer un BindingPairBuilder que le pase este prefijo.
	
	
	
	public BindingPair(String b)
	{
		String[] pair = b.split(SEPARATOR);
		if(pair.length != 2)
		{
			throw new Error("Incorrect amount of variables. Binding: " + b);
		}
		else
		{
			boolean is0callee = pair[0].startsWith(PREFIX);
			boolean is1callee = pair[1].startsWith(PREFIX);
			if(is0callee && !is1callee)
			{
				this.callee_parameter = pair[0].substring(3).trim();
				this.caller_parameter = pair[1].trim();
			}
			else
			{
				if(!is0callee && is1callee)
				{
					this.caller_parameter = pair[0].trim();
					this.callee_parameter = pair[1].substring(3).trim();
				}
				else
				{
					throw new Error("It isn't happening that one is caller and the other one is callee. Binding: " + b);
				}
			}
		}
	}

	public String toString()
	{
		return caller_parameter + " " + SEPARATOR + " " + PREFIX + callee_parameter; 
	}
}
