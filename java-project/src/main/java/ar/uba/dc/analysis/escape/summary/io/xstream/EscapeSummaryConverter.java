package ar.uba.dc.analysis.escape.summary.io.xstream;

import java.util.Set;

import soot.SootMethod;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.PointsToGraph;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.SootUtils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class EscapeSummaryConverter extends AbstractCollectionConverter implements Converter {

	public EscapeSummaryConverter(Mapper mapper) {
		super(mapper);
	}
	
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		EscapeSummary summary = (EscapeSummary) source;
		
		writer.addAttribute("className", summary.getTarget().getDeclaringClass().getName());
		writer.addAttribute("methodSignature", summary.getTarget().getSubSignature());
			
		writer.startNode("points-to-graph");
			context.convertAnother(summary.getPointsToGraph());
		writer.endNode();
		
		writer.startNode("mutated-fields");
			for (Node node : summary.getMutatedNodes()) {
				writer.startNode("node");
					writeItem(node, context, writer);
					writer.startNode("fields");
					for (String field : summary.getMutatedFieldsOf(node)) {
						writer.startNode("field");
							writer.setValue(field);
						writer.endNode();
					}
					writer.endNode();
				writer.endNode();
			}
		writer.endNode();
	}

	@SuppressWarnings("unchecked")
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		String className = reader.getAttribute("className");
		String methodSignature = reader.getAttribute("methodSignature");
		
		SootMethod method = SootUtils.getMethod(className, methodSignature);
		
		EscapeSummary summary = new EscapeSummary(method);
		
		context.put(PointsToGraphConverter.METHOD_UNDER_CONVERTION, method);
		
		reader.moveDown();
			summary.setPointsToGraph((PointsToGraph) context.convertAnother(summary, PointsToGraph.class));
		reader.moveUp();
		
		reader.moveDown();
			while (reader.hasMoreChildren()) {
				reader.moveDown();
					reader.moveDown();
						Node target = (Node) readItem(reader, context, summary);
					reader.moveUp();
					reader.moveDown();
						Set<String> fields = (Set<String>) readItem(reader, context, summary);
					reader.moveUp();
				reader.moveUp();
				summary.mutateField(target, fields);
			}
		reader.moveUp();
		
		return summary;
	}

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return EscapeSummary.class.isAssignableFrom(clazz);
	}
}
