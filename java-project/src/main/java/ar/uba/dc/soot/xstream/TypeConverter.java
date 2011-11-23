package ar.uba.dc.soot.xstream;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.AnySubType;
import soot.ArrayType;
import soot.RefType;
import soot.Type;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TypeConverter implements Converter {

	private static Log log = LogFactory.getLog(TypeConverter.class);
	
	@SuppressWarnings("unchecked")
	private Class[] methodParameters = new Class[] {};
	private Object[] methodArguments = new Object[] {};
	private List<String> packages = new ArrayList<String>();
	
	public TypeConverter() {
		packages.add("soot");
		packages.add("soot.baf");
		packages.add("soot.coffi");
		packages.add("soot.jimple.toolkits.typing.fast");
	}
	
	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return Type.class.isAssignableFrom(clazz);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,	MarshallingContext context) {
		String className = source.getClass().getSimpleName();
		className = className.substring(0, className.length() - "Type".length());
		writer.addAttribute("name", StringUtils.uncapitalize(className));
		
		if (source instanceof RefType) {
			RefType ref = (RefType) source;
			writer.addAttribute("to", ref.getClassName());
		} else if (source instanceof AnySubType) {
			AnySubType subType = (AnySubType) source;
			writer.startNode("base");
				context.convertAnother(subType.getBase());
			writer.endNode();
		} else if (source instanceof ArrayType) {
			ArrayType arrayType = (ArrayType) source;
			writer.addAttribute("numDimensions", Integer.toString(arrayType.numDimensions, 10));
			writer.startNode("base");
				context.convertAnother(arrayType.baseType);
			writer.endNode();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		Type result = null;
		String typeName = reader.getAttribute("name"); 
		
		if (typeName.equals("ref")) {
			String refTo = reader.getAttribute("to");
			result = RefType.v(refTo);
		} else if (typeName.equals("anySub")) {
			reader.moveDown();
				RefType baseType = (RefType) context.convertAnother(null, RefType.class);
			reader.moveUp();
			result = AnySubType.v(baseType);
		} else if (typeName.equals("array")) {
			int numDimensions = Integer.valueOf(reader.getAttribute("numDimensions"), 10);
			reader.moveDown();
				Type baseType = (Type) context.convertAnother(null, Type.class);
			reader.moveUp();
			result = ArrayType.v(baseType, numDimensions);
		} else {
			try {
				Class clazz = findClass(typeName);
				Method method = clazz.getMethod("v", methodParameters);
				result = (Type) method.invoke(null, methodArguments);
			} catch (Exception e) {
				log.error("Ocurrio un error al recuperar el tipo de datos [" + typeName + "]: " + e.getMessage(), e);
			}
		}
	
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Class findClass(String typeName) throws ClassNotFoundException {
		Class ret = null;
		String typeClass = StringUtils.capitalize(typeName) + "Type";
		
		Iterator<String> itPackage = packages.iterator();
		while (itPackage.hasNext() && ret == null) {
			String pkg = itPackage.next();
			try {
				ret = Class.forName(pkg + "." + typeClass);
			} catch (Exception e) { }
		}
		
		if (ret == null) {
			throw new ClassNotFoundException("No class found for type [" + typeName + "]");
		}
		
		return ret;
	}
}
