package ar.uba.dc.analysis.memory.impl.summary.io.writer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.util.dot.DotGraph;
import soot.util.dot.DotGraphEdge;
import soot.util.dot.DotGraphNode;
import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpression;
import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedMemorySummary;
import ar.uba.dc.analysis.memory.impl.summary.PointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.PointsToHeapPartitionEdge;
import ar.uba.dc.analysis.memory.impl.summary.io.graphviz.GraphvizPainter;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.analysis.memory.summary.ResidualSummarizer;
import ar.uba.dc.util.ConsoleException;
import ar.uba.dc.util.ConsoleUtils;
import ar.uba.dc.util.location.MethodLocationStrategy;

public class GraphvizWriter implements SummaryWriter<EscapeBasedMemorySummary> {

	private static int INFINITE = -1;
	
	private static Log log = LogFactory.getLog(GraphvizWriter.class);
	
	protected MethodLocationStrategy locationStrategy;
	
	protected ParametricExpressionFactory expressionFactory;
	
	protected ResidualSummarizer<MemorySummary, ParametricExpression> residualSummarizer;
	
	protected int graphSizeLimit;
	
	protected boolean leaveSourceFile;

	protected boolean showResidualMemorySummarized;
	
	protected GraphvizPainter painter;
	
	protected boolean glossOver;
	
	public void write(EscapeBasedMemorySummary summary) {
		DotGraph dot = new DotGraph(summary.getTarget().toString());
		dot.setGraphLabel(summary.getTarget().toString());
		fillDotGraph(summary, dot);
		plot(dot, summary);
	}
	
	@Override
	public void setMainClass(String mainClass) {
		throw new java.lang.UnsupportedOperationException();
		
	}

	
	private void fillDotGraph(EscapeBasedMemorySummary summary, DotGraph out) {
		Map<PointsToHeapPartition, String> nodeId = new HashMap<PointsToHeapPartition, String>();
		int id = 0;

		if (glossOver) {
			painter.init();
		}
		
		// add nodes
		for (PointsToHeapPartition hp : summary.getAllPartitions()) {
			Node n = hp.getNode();
			String label = "N" + "_" + id;
			DotGraphNode node = out.drawNode(label);
			ParametricExpression residual = summary.getResidual(hp);
			
			if (residual == null) {
				residual = expressionFactory.constant(0L);
			}
			
			node.setLabel(n.toString() + " (" + toString(residual) + ")");
			
			if (!n.isInside()) {
				node.setStyle("dashed");
				node.setAttribute("color", "gray50");
			} else {
				if (glossOver) {
					String color = painter.getColor(n);
					String fillcolor = painter.getFillColor(n);
					if (StringUtils.isNotBlank(color) && StringUtils.isNotBlank(fillcolor)) {
						node.setAttribute("penwidth", "5");			   	// Tener un borde mas grueso
						node.setAttribute("style", "filled");		   	// El nodo se dibuja con fondo
						node.setAttribute("fillcolor", fillcolor); 		// Background
						node.setAttribute("color", color);		 		// Color del borde
					}
				}
			}
			
			if (summary.isEscapeGlobal(hp)) {
				node.setAttribute("fontcolor", "red");
			}
			nodeId.put(hp, label);
			id++;
		}

		// add edges
		for (PointsToHeapPartition src : summary.getEdgesSources()) {
			for (PointsToHeapPartitionEdge e : summary.getEdgesOutOf(src)) { 
				DotGraphEdge edge = out.drawEdge(nodeId.get(e.getSource()), nodeId.get(e.getTarget()));
				edge.setLabel(e.getField());
				if (!e.isInside()) {
					edge.setStyle("dashed");
					edge.setAttribute("color", "gray50");
					edge.setAttribute("fontcolor", "gray40");
				}
			}
		}

		// ret
		if (!summary.getReturnedPartitions().isEmpty()) {
			DotGraphNode node = out.drawNode("ret");
			node.setLabel("ret");
			node.setShape("plaintext");
			for (PointsToHeapPartition dst : summary.getReturnedPartitions()) {
				out.drawEdge("ret", nodeId.get(dst));
			}
		}
		
		DotGraphNode node = out.drawNode("memoTemp");
		node.setShape("plaintext");
		//node.setLabel("Tmp: " + toString(summary.getTemporal()));
		
		if (showResidualMemorySummarized) {
			log.debug("Build summary for residual memory of method [" + summary.getTarget() + "]");
			ParametricExpression[] residualExpressions = new ParametricExpression[summary.getResidualPartitions().size()];
			
			int idx = 0;
			for (HeapPartition residualPartition : summary.getResidualPartitions()) {
				residualExpressions[idx] = summary.getResidual(residualPartition);
				idx++;
			}
			
			ParametricExpression residual = residualSummarizer.getResidual(summary); 
			
			node = out.drawNode("memoRes");
			node.setShape("plaintext");
			node.setLabel("Res: " + toString(residual));
		}
	}

	protected void plot(DotGraph dot, EscapeBasedMemorySummary summary) {
		String location = locationStrategy.getLocation(summary.getTarget());
		log.debug("Location for summary: [" + location + "]");
		File srcFile = new File(location);
		
		if (!srcFile.getParentFile().exists()) {
			srcFile.getParentFile().mkdirs();
		}
		
		dot.plot(srcFile.getPath());

		if (graphSizeLimit == INFINITE || summary.getAllPartitions().size() <= graphSizeLimit) {
			String command = "dot -Tgif " + srcFile.getAbsolutePath() + " -o " + srcFile.getAbsolutePath().replace(DotGraph.DOT_EXTENSION, ".gif");
			try {
				ConsoleUtils.execCommand(command, false);
				if (!leaveSourceFile) {
					FileUtils.deleteQuietly(srcFile);
				}
			} catch (ConsoleException e) {
				log.error("No fue posible generar el grafo [" + location + "]: " + e.getMessage());
				FileUtils.deleteQuietly(new File(srcFile.getAbsolutePath().replace(DotGraph.DOT_EXTENSION, ".gif")));
			}
		}
	}
	
	protected String toString(ParametricExpression value) {
		String result = null;
		
		if (value instanceof BarvinokParametricExpression) {
			BarvinokParametricExpression barvinokValue = (BarvinokParametricExpression) value;
			result = barvinokValue.asString();
		} else {
			result = value.toString();
		}
		
		return result;
	}
	
	public MethodLocationStrategy getLocationStrategy() {
		return locationStrategy;
	}

	public void setLocationStrategy(MethodLocationStrategy locationStrategy) {
		this.locationStrategy = locationStrategy;
	}

	public void setGraphSizeLimit(int graphSizeLimit) {
		this.graphSizeLimit = graphSizeLimit;
	}

	public void setLeaveSourceFile(boolean leaveSourceFile) {
		this.leaveSourceFile = leaveSourceFile;
	}
	
	public void setExpressionFactory(ParametricExpressionFactory expressionFactory) {
		this.expressionFactory = expressionFactory;
	}

	public void setResidualSummarizer(ResidualSummarizer<MemorySummary, ParametricExpression> residualSummarizer) {
		this.residualSummarizer = residualSummarizer;
	}

	public void setShowResidualMemorySummarized(boolean showResidualMemorySummarized) {
		this.showResidualMemorySummarized = showResidualMemorySummarized;
	}

	public void setGlossOver(boolean glossOver) {
		this.glossOver = glossOver;
	}

	public void setPainter(GraphvizPainter painter) {
		this.painter = painter;
	}

	
}
