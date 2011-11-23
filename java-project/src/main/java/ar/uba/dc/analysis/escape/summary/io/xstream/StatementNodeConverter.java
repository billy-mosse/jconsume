package ar.uba.dc.analysis.escape.summary.io.xstream;

import java.util.Iterator;
import java.util.LinkedList;

import ar.uba.dc.analysis.escape.graph.node.StmtNode;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class StatementNodeConverter extends AbstractNodeWithContextConverter implements Converter {

	public StatementNodeConverter(Mapper mapper) {
		super(mapper);
	}

	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		StmtNode node = (StmtNode) source;
		writer.addAttribute(super.mapper().serializedMember(StmtNode.class, "inside"), Boolean.toString(node.isInside()));
		writer.startNode("statement-id");
			context.convertAnother(node.getId());
		writer.endNode();
		writeContext(node.getContext(), context, writer);
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		Boolean inside = Boolean.valueOf(reader.getAttribute(super.mapper().serializedMember(StmtNode.class, "inside")));
		reader.moveDown();
			StatementId id = (StatementId) readItem(reader, context, new StmtNode(null, inside, 0));
		reader.moveUp();
		
		reader.moveDown();
			LinkedList<StatementId> statements = new LinkedList<StatementId>();
			Integer sensitivity = readContext(statements, reader, context);
		reader.moveUp();
		
		if (sensitivity == null) {
			sensitivity = CircularStack.INFINITE;
		}
		
		StmtNode ret = new StmtNode(id, inside, sensitivity);
		Iterator<StatementId> itStatementId = statements.descendingIterator();
		while (itStatementId.hasNext()) {
			ret.changeContext(itStatementId.next());
		}
		
		return ret;
	}

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return StmtNode.class.isAssignableFrom(clazz);
	}
}
