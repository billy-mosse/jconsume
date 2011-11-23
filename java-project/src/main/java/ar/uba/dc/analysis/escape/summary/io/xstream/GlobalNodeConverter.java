package ar.uba.dc.analysis.escape.summary.io.xstream;

import ar.uba.dc.analysis.escape.graph.node.GlobalNode;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class GlobalNodeConverter implements Converter {
	// private Mapper mapper;
	
	public GlobalNodeConverter(Mapper mapper) {
		//this.mapper = mapper;
	}

	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		//String name = mapper.serializedClass(source.getClass());
		//writer.startNode(name);
		//writer.endNode();
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return GlobalNode.node;
	}

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return GlobalNode.class.isAssignableFrom(clazz);
	}	
}
