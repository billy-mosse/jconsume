package ar.uba.dc.analysis.escape.summary.io.xstream;

import ar.uba.dc.analysis.escape.graph.Cache;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class EdgeConverter extends AbstractCollectionConverter implements Converter {

	public EdgeConverter(Mapper mapper) {
		super(mapper);
	}
	
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		Edge edge = (Edge) source;

		writer.addAttribute(super.mapper().serializedMember(Edge.class, "field"), edge.getField());
		writer.addAttribute(super.mapper().serializedMember(Edge.class, "inside"), Boolean.toString(edge.isInside()));
		writer.startNode(super.mapper().serializedMember(Edge.class, "source"));
			writeItem(edge.getSource(), context, writer);
		writer.endNode();
		writer.startNode(super.mapper().serializedMember(Edge.class, "target"));
		writeItem(edge.getTarget(), context, writer);
		writer.endNode();	
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		String field = reader.getAttribute(super.mapper().serializedMember(Edge.class, "field"));
		Boolean inside = Boolean.valueOf(reader.getAttribute(super.mapper().serializedMember(Edge.class, "inside")));
		
		reader.moveDown();
			reader.moveDown();
				Node source = (Node) readItem(reader, context, null);
			reader.moveUp();
		reader.moveUp();
		reader.moveDown();
			reader.moveDown();
				Node target = (Node) readItem(reader, context, null);
			reader.moveUp();
		reader.moveUp();
		
		return Cache.cacheEdge(new Edge(source, field, target, inside));
	}

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return Edge.class.isAssignableFrom(clazz);
	}
}
