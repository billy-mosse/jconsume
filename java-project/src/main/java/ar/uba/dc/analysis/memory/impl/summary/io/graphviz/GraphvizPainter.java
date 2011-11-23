package ar.uba.dc.analysis.memory.impl.summary.io.graphviz;

import ar.uba.dc.analysis.escape.graph.Node;

public interface GraphvizPainter {

	void init();

	String getFillColor(Node node);

	String getColor(Node node);

}
