package ar.uba.dc.analysis.escape.summary.io.xstream;

import ar.uba.dc.analysis.escape.graph.Cache;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class ParamNodeConverter implements Converter {

	private Mapper mapper;
	
	public ParamNodeConverter(Mapper mapper) {
		this.mapper = mapper;
	}

	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		ParamNode node = (ParamNode) source;
		writer.addAttribute(mapper.serializedMember(ParamNode.class, "id"), Integer.toString(node.getIndex(), 10));
		writer.addAttribute(mapper.serializedMember(ParamNode.class, "omega"), Boolean.toString(node.isOmega()));
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		String idx = reader.getAttribute(mapper.serializedMember(ParamNode.class, "id"));
		String  t = mapper.serializedMember(ParamNode.class, "omega");
		String omega = reader.getAttribute(t);
		
		//TODO chequear que este bien escrito
		return Cache.cacheNode(new ParamNode(Integer.valueOf(idx), omega.equals("true")));
	}

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return ParamNode.class.isAssignableFrom(clazz);
	}	
}
