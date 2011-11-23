package ar.uba.dc.analysis.escape.summary.io.xstream;

import java.util.Set;

import soot.Local;
import soot.SootMethod;
import soot.Type;
import ar.uba.dc.analysis.escape.PointsToGraph;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.SootUtils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class PointsToGraphConverter extends AbstractCollectionConverter implements Converter {

	public static final String METHOD_UNDER_CONVERTION = "methodUnderConvertion";
	
	public PointsToGraphConverter(Mapper mapper) {
		super(mapper);
	}
	
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		PointsToGraph ptg = (PointsToGraph) source;
		
		writer.startNode("graph");
			marshalGraph(ptg, writer, context);
		writer.endNode();	
	
		marshalVariables(ptg, writer, context);
		marshalReturnedNodes(ptg, writer, context);
		marshalGlobalEscapeNodes(ptg, writer, context);
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		PointsToGraph ptg = new PointsToGraph();

		// Procesamos el grafo
		reader.moveDown();
			unmarshalGraph(ptg, reader, context);
		reader.moveUp();
		// Los parametros se levantaron solos al levantar el grafo
		// Procesamos las variables
		unmarshalVariables(ptg, reader, context);
		// Procesamos los nodos retornados
		unmarshalReturnedNodes(ptg, reader, context);
		// Procesamos los nodos que escaparon globalmente
		unmarshalGlobalEscapeNodes(ptg, reader, context);
		
		return ptg;
	}

	
	private void marshalGraph(PointsToGraph ptg, HierarchicalStreamWriter writer, MarshallingContext context) {
		writer.startNode("nodes");
			marshalNodes(ptg, writer, context);
		writer.endNode();
		writer.startNode("edges");
			marshalEdges(ptg, writer, context);
		writer.endNode();
	}

	private void unmarshalGraph(PointsToGraph ptg, HierarchicalStreamReader reader, UnmarshallingContext context) {
		reader.moveDown();
			unmarshalNodes(ptg, reader, context);
		reader.moveUp();
		reader.moveDown();
			unmarshalEdges(ptg, reader, context);
		reader.moveUp();		
	}
	
	private void marshalNodes(PointsToGraph ptg, HierarchicalStreamWriter writer, MarshallingContext context) {
		for (Node node : ptg.getNodes()) {
			writeItem(node, context, writer);
		}
	}

	private void unmarshalNodes(PointsToGraph ptg, HierarchicalStreamReader reader, UnmarshallingContext context) {
		while (reader.hasMoreChildren()) {
            reader.moveDown();
            	ptg.add((Node) readItem(reader, context, ptg));
            reader.moveUp();
        }
	}
	
	private void marshalEdges(PointsToGraph ptg, HierarchicalStreamWriter writer, MarshallingContext context) {
		for (Node node : ptg.getEdgesSources()) {
			for (Edge edge : ptg.edgesOutOf(node)) {
				writeItem(edge, context, writer);
			}
		}
	}
	
	private void unmarshalEdges(PointsToGraph ptg, HierarchicalStreamReader reader, UnmarshallingContext context) {
		while (reader.hasMoreChildren()) {
            reader.moveDown();
            	Edge edge = (Edge) readItem(reader, context, ptg);
            	ptg.relate(edge.getSource(), edge.getTarget(), edge.getField(), edge.isInside());
            reader.moveUp();
        }
	}

	private void marshalVariables(PointsToGraph ptg, HierarchicalStreamWriter writer, MarshallingContext context) {
		writer.startNode("variables");
			for (Local local : ptg.getLocals()) {
				writer.startNode("variable");
					writer.startNode("name");
						writer.setValue(local.getName());
					writer.endNode();
					writer.startNode("type");
						context.convertAnother(local.getType());
					writer.endNode();
					writer.startNode("points-to");
						context.convertAnother(ptg.getNodesPointedBy(local));
					writer.endNode();
				writer.endNode();
			}
		writer.endNode();
	}
	
	@SuppressWarnings("unchecked")
	private void unmarshalVariables(PointsToGraph ptg, HierarchicalStreamReader reader, UnmarshallingContext context) {
		SootMethod methodUnderConversion = (SootMethod) context.get(METHOD_UNDER_CONVERTION);
		
		reader.moveDown();
			while (reader.hasMoreChildren()) {
				reader.moveDown();
					reader.moveDown();
						String localName = reader.getValue();
					reader.moveUp();
					reader.moveDown();
						Type localType = (Type) readItem(reader, context, null);
					reader.moveUp();
					reader.moveDown();
						Set<Node> nodes = (Set<Node>) readItem(reader, context, null);
					reader.moveUp();
					Local local = SootUtils.findLocal(methodUnderConversion, localName, localType);
					ptg.strongUpdate(local, nodes);
				reader.moveUp();
			}
		reader.moveUp();
	}

	private void marshalReturnedNodes(PointsToGraph ptg, HierarchicalStreamWriter writer, MarshallingContext context) {
		writer.startNode("returned");
			context.convertAnother(ptg.getReturnedNodes());
		writer.endNode();
	}

	@SuppressWarnings("unchecked")
	private void unmarshalReturnedNodes(PointsToGraph ptg, HierarchicalStreamReader reader, UnmarshallingContext context) {
		reader.moveDown();
			Set<Node> nodes = (Set<Node>) readItem(reader, context, null);
			ptg.returnNodes(nodes);
		reader.moveUp();
	}
	
	private void marshalGlobalEscapeNodes(PointsToGraph ptg, HierarchicalStreamWriter writer, MarshallingContext context) {
		writer.startNode("escape-globaly");
			context.convertAnother(ptg.getEscapeGlobaly());
		writer.endNode();
	}
	
	@SuppressWarnings("unchecked")
	private void unmarshalGlobalEscapeNodes(PointsToGraph ptg, HierarchicalStreamReader reader, UnmarshallingContext context) {
		reader.moveDown();
			Set<Node> nodes = (Set<Node>) readItem(reader, context, null);
			ptg.escapeGlobal(nodes);
		reader.moveUp();		
	}

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return PointsToGraph.class.isAssignableFrom(clazz);
	}	
}
