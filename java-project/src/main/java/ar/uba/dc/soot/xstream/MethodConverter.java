package ar.uba.dc.soot.xstream;

import soot.SootMethod;
import ar.uba.dc.soot.SootUtils;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class MethodConverter implements Converter {

	private String classNameNode;
	private String methodSignatureNode;
	
	public MethodConverter() {
		this("class", "signature");
	}
	
	public MethodConverter(String classNameNode, String methodSignatureNode) {
		this.classNameNode = classNameNode;
		this.methodSignatureNode = methodSignatureNode;
	}
	
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		SootMethod method = (SootMethod) value;
		
		writer.startNode(classNameNode);
        writer.setValue(method.getDeclaringClass().getName());
        writer.endNode();
        writer.startNode(methodSignatureNode);
        writer.setValue(method.getSubSignature());
        writer.endNode();
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		try {
            reader.moveDown();
            String className = reader.getValue();
            reader.moveUp();
            reader.moveDown();
            String methodSubSignature = reader.getValue();
            reader.moveUp();

			SootMethod sootMethod = SootUtils.getMethod(className, methodSubSignature);
			
			return sootMethod;
		} catch (RuntimeException e) {
			throw new ConversionException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return SootMethod.class.isAssignableFrom(clazz);
	}
}
