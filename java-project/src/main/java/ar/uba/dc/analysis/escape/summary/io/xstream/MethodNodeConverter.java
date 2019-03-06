package ar.uba.dc.analysis.escape.summary.io.xstream;

import java.util.Iterator;
import java.util.LinkedList;

import soot.SootMethod;
import ar.uba.dc.analysis.escape.graph.node.MethodNode;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class MethodNodeConverter extends AbstractNodeWithContextConverter implements Converter {

	private Mapper mapper;

	public MethodNodeConverter(Mapper mapper) {
		super(mapper);
		this.mapper = mapper;
	}

	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		MethodNode node = (MethodNode) source;
		writer.startNode("method-id");
		context.convertAnother(node.getId());
		writer.addAttribute(mapper.serializedMember(MethodNode.class, "omega"), Boolean.toString(node.isOmega()));
		writer.addAttribute(mapper.serializedMember(MethodNode.class, "fresh"), Boolean.toString(node.isFresh()));
		writer.endNode();
		writeContext(node.getContext(), context, writer);
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {


		String omega = reader.getAttribute(mapper.serializedMember(MethodNode.class, "omega"));
		String fresh = reader.getAttribute(mapper.serializedMember(MethodNode.class, "fresh"));
		
		
		reader.moveDown();
			SootMethod id = (SootMethod) readItem(reader, context, null);
		reader.moveUp();

		reader.moveDown();
			LinkedList<StatementId> statements = new LinkedList<StatementId>();
			Integer sensitivity = readContext(statements, reader, context);
		reader.moveUp();
		
		if (sensitivity == null) {
			sensitivity = CircularStack.INFINITE;
		}
		
		MethodNode ret = new MethodNode(id, sensitivity, omega.equals("true"), fresh.equals("true"));
		Iterator<StatementId> itStatementId = statements.descendingIterator();
		while (itStatementId.hasNext()) {
			ret.changeContext(itStatementId.next());
		}
		
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return MethodNode.class.isAssignableFrom(clazz);
	}
}
