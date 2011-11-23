package ar.uba.dc.tools.instrumentation.resource.tracker.runtime;

import java.util.Map;

public class CLINITFrame extends StackFrame {

	public static final String STATIC_INITIALIZER_NAME = "<clinit>";
	
	public CLINITFrame() {
		super(STATIC_INITIALIZER_NAME);
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.getMethod());
		buffer.append(" memreq=");
		buffer.append(this.memory.size() + this.getMaxCall());
		buffer.append(" maxCall: " + this.getMaxCall());
		buffer.append(" { ");
		buffer.append("\n");
		Map<String, Integer> dump = this.memory.dump();
		for (String name : dump.keySet()) {
			buffer.append("\t");
			buffer.append(name).append("=").append(dump.get(name));
			buffer.append("\n");
		}
		buffer.append("}");
		return buffer.toString();
	}
}
