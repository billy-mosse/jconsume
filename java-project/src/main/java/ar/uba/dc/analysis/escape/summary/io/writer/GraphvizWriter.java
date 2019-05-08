package ar.uba.dc.analysis.escape.summary.io.writer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Local;
import soot.util.dot.DotGraph;
import soot.util.dot.DotGraphEdge;
import soot.util.dot.DotGraphNode;
import ar.uba.dc.analysis.common.SummaryWriter;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.StmtNode;
import ar.uba.dc.util.ConsoleException;
import ar.uba.dc.util.ConsoleUtils;
import ar.uba.dc.util.location.MethodLocationStrategy;

public class GraphvizWriter implements SummaryWriter<EscapeSummary> {

	private static int INFINITE = -1;
	
	private static Log log = LogFactory.getLog(GraphvizWriter.class);
	
	protected MethodLocationStrategy locationStrategy;
	
	protected int graphSizeLimit;
	
	protected boolean leaveSourceFile;
	
	public GraphvizWriter() {
		super();
	}

	@Override
	public void setMainClass(String mainClass) {
		throw new java.lang.UnsupportedOperationException();
		
	}

	public void write(EscapeSummary summary) {
		DotGraph dot = new DotGraph(summary.getTarget().toString());
		dot.setGraphLabel(summary.getTarget().toString());
		fillDotGraph(summary, dot);
		plot(dot, summary);
	}
	
	
	
	
	private void fillDotGraph(EscapeSummary summary, DotGraph out) {
		Map<Node, String> nodeId = new HashMap<Node, String>();
		int id = 0;
		
		Set<Node> escaping = summary.getEscaping();

		// add nodes
		for (Node n : summary.getNodes()) {
			String label = "N" + "_" + id;
			
			String label_to_show = n.toString();
			
			if(n.getClass().equals(StmtNode.class))
			{
				StmtNode stmtNode = (StmtNode) n;
				label_to_show += "(" + stmtNode.getLineNumber() + ")";
			}
				
			
			DotGraphNode node = out.drawNode(label);
			node.setLabel(label_to_show);
			if (!n.isInside()) {
				node.setStyle("dashed");
				node.setAttribute("color", "gray50");
			}
			if (summary.escapeGlobal(n)) {
				node.setAttribute("fontcolor", "red");
			}
			//hack
			if(escaping.contains(n))
				node.setAttribute("fontcolor", "blue");

			
			nodeId.put(n, label);
			id++;
		}

		// add edges
		for (Node src : summary.getEdgesSources()) {
			for (Edge e : summary.getEdgesOutOf(src)) { 
				DotGraphEdge edge = out.drawEdge(nodeId.get(e.getSource()), nodeId.get(e.getTarget()));
				edge.setLabel(e.getField());
				if (!e.isInside()) {
					edge.setStyle("dashed");
					edge.setAttribute("color", "gray50");
					edge.setAttribute("fontcolor", "gray40");
				}
			}
		}

		// add locals
		for (Local local : summary.getLocals()) {
			if (!summary.nodesPointedBy(local).isEmpty()) {
				String label = "L" + "_" + id;
				DotGraphNode node = out.drawNode(label);
				node.setLabel(local.toString());
				node.setShape("plaintext");
				for (Node dst : summary.nodesPointedBy(local)) {
					out.drawEdge(label, nodeId.get(dst));
				}
				id++;
			}
		}

		// ret
		if (!summary.getReturnedNodes().isEmpty()) {
			DotGraphNode node = out.drawNode("ret");
			node.setLabel("ret");
			node.setShape("plaintext");
			for (Node dst : summary.getReturnedNodes()) {
				out.drawEdge("ret", nodeId.get(dst));
			}
		}
	}

	protected void plot(DotGraph dot, EscapeSummary summary) {
		String location = locationStrategy.getLocation(summary.getTarget());
		log.debug("Location for summary: [" + location + "]");
		File srcFile = new File(location);
		
		if (!srcFile.getParentFile().exists()) {
			srcFile.getParentFile().mkdirs();
		}
		
		dot.plot(srcFile.getPath());

		if (graphSizeLimit == INFINITE || summary.getNodes().size() <= graphSizeLimit) {
			String command = "dot -Tgif " + srcFile.getAbsolutePath() + " -o " + srcFile.getAbsolutePath().replace(DotGraph.DOT_EXTENSION, ".gif");
			try {
				ConsoleUtils.execCommand(command, false);
				if (!leaveSourceFile) {
			//		FileUtils.deleteQuietly(srcFile);
				}
			} catch (ConsoleException e) {
				log.error("No fue posible generar el grafo [" + location + "]: " + e.getMessage());
				FileUtils.deleteQuietly(new File(srcFile.getAbsolutePath().replace(DotGraph.DOT_EXTENSION, ".gif")));
			}
		}
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
}
