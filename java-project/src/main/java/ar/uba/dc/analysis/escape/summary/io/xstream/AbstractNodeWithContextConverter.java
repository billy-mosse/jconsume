package ar.uba.dc.analysis.escape.summary.io.xstream;

import java.util.Iterator;
import java.util.LinkedList;

import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public abstract class AbstractNodeWithContextConverter extends AbstractCollectionConverter implements Converter {

	public AbstractNodeWithContextConverter(Mapper mapper) {
		super(mapper);
	}

	protected void writeContext(CircularStack<StatementId> source, MarshallingContext context, HierarchicalStreamWriter writer) {
		writer.startNode("context");
		if (source.maxSize() != CircularStack.INFINITE) {
			writer.addAttribute("sensitivity", Integer.toString(source.maxSize(), 10));
		}

		Iterator<StatementId> itContext = source.iterator();
		LinkedList<StatementId> list = new LinkedList<StatementId>();
		while (itContext.hasNext()) {
			list.add(itContext.next());
		}
		itContext = list.descendingIterator();
		while (itContext.hasNext()) {
			writeItem(itContext.next(), context, writer);
		}
		writer.endNode();
	}
	
	protected Integer readContext(LinkedList<StatementId> statements, HierarchicalStreamReader reader, UnmarshallingContext context) {
		Integer sensitivity = null;
		String temp = reader.getAttribute("sensitivity");
		
		if (temp != null) {
			sensitivity = Integer.valueOf(temp);
		
			//HACK. Puse este while dentro del IF
			while (reader.hasMoreChildren()) {
				reader.moveDown();
					statements.add((StatementId) readItem(reader, context, null));
				reader.moveUp();
			}
		
		}
		
		return sensitivity;
	}	

}
