package ar.uba.dc.analysis.escape.summary.io.xstream;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import soot.SootMethod;

import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.ContainerNode;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class ContainerNodeConverter extends AbstractNodeWithContextConverter implements Converter {

	public ContainerNodeConverter(Mapper mapper) {
		super(mapper);
	}

	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		ContainerNode node = (ContainerNode) source;
		writer.addAttribute(super.mapper().serializedMember(ContainerNode.class, "inside"), Boolean.toString(node.isInside()));
		writer.startNode("id");
			context.convertAnother(node.getId());
		writer.endNode();
		writeContext(node.getContext(), context, writer);
		writer.startNode("belongsTo");
			context.convertAnother(node.belongsTo());
		writer.endNode();
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		Boolean inside = Boolean.valueOf(reader.getAttribute(super.mapper().serializedMember(ContainerNode.class, "inside")));
		ContainerNode dummy = new ContainerNode(null, inside, null, null);
		Set<Node> id = new HashSet<Node>();
		reader.moveDown();
			while (reader.hasMoreChildren()) {
				reader.moveDown();
					id.add((Node) readItem(reader, context, dummy));
				reader.moveUp();
			}
		reader.moveUp();
		
		reader.moveDown();
			LinkedList<StatementId> statements = new LinkedList<StatementId>();
			Integer sensitivity = readContext(statements, reader, context);
		reader.moveUp();
		
		reader.moveDown();
			SootMethod belongsTo = (SootMethod) readItem(reader, context, dummy);
		reader.moveUp();
		
		if (sensitivity == null) {
			sensitivity = CircularStack.INFINITE;
		}
		
		ContainerNode ret = new ContainerNode(id, inside, new CircularStack<StatementId>(sensitivity), belongsTo);
		Iterator<StatementId> itStatementId = statements.descendingIterator();
		while (itStatementId.hasNext()) {
			ret.changeContext(itStatementId.next());
		}
		
		return ret;
	}

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return ContainerNode.class.isAssignableFrom(clazz);
	}
}
