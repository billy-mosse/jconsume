package ar.uba.dc.analysis.common.intermediate_representation;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import ar.uba.dc.analysis.common.Line;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.ContainerNode;
import ar.uba.dc.analysis.escape.graph.node.MethodNode;
import ar.uba.dc.analysis.escape.graph.node.PaperNodeUtils;
import ar.uba.dc.analysis.escape.graph.node.StmtNode;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.RichPaperPointsToHeapPartition;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.util.collections.CircularStack;
import soot.RefLikeType;
import soot.SootMethod;

public class IntermediateRepresentationMethod {

	protected String name;

	// protected String integer_name;

	protected Set<IntermediateRepresentationParameter> parameters;

	protected IntermediateRepresentationMethodBody body;

	protected String returnType;

	protected long order;

	protected Set<String> relevant_parameters;

	protected DomainSet methodRequirements;

	protected boolean isReturnRefLikeType;

	protected int number;
	protected String declaration;

	protected String subSignature;

	public void setSubSignature(String subSignature) {
		this.subSignature = subSignature;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getDeclaration() {
		return declaration;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}

	public boolean isReturnRefLikeType() {
		return this.isReturnRefLikeType;
	}

	public void setIsReturnRefLikeType(boolean isReturnRefLikeType) {
		this.isReturnRefLikeType = isReturnRefLikeType;
	}

	protected Set<PaperPointsToHeapPartition> escapeNodes;

	private String declaringClass;

	protected String class_owner;

	protected Set<PaperPointsToHeapPartition> nodes;

	public Set<PaperPointsToHeapPartition> getNodes() {
		return nodes;
	}

	public void setNodes(Set<PaperPointsToHeapPartition> nodes) {
		this.nodes = nodes;
	}

	public void setEscapeNodes(Set<PaperPointsToHeapPartition> escapeNodes) {
		this.escapeNodes = escapeNodes;
	}

	public IntermediateRepresentationMethod() {

	}

	private static Log log = LogFactory.getLog(IntermediateRepresentationMethod.class);

	// protected SootMethod target;

	private void setParametersFromSootMethod(SootMethod target) {
		this.parameters = new TreeSet<IntermediateRepresentationParameter>();
		Set<IntermediateRepresentationParameter> s = SootUtils.getParameters(target, true);
		for (IntermediateRepresentationParameter p : s) {
			this.parameters.add(p);
		}

	}

	public String getReturnType() {
		return this.returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public IntermediateRepresentationMethod(SootMethod m, long order) {
		this.order = order;

		log.debug("____Construyendo " + m.toString());

		this.name = m.getName();
		this.setParametersFromSootMethod(m);
		this.returnType = m.getReturnType().toString();
		this.declaringClass = m.getDeclaringClass().toString();

		this.isReturnRefLikeType = m.getReturnType() instanceof RefLikeType;

	}

	public long getOrder() {
		return order;
	}

	public void setOrder(long order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IntermediateRepresentationMethodBody getBody() {
		return body;
	}

	public void setBody(IntermediateRepresentationMethodBody body) {
		this.body = body;
	}

	public Set<IntermediateRepresentationParameter> getParameters() {
		return parameters;
	}

	// TODO: creeria que esto esta mal porque mueren al final. Probemos.
	public void setParameters(Set<IntermediateRepresentationParameter> parameters) {
		this.parameters = parameters;
	}

	public Set<String> getRelevant_parameters() {
		return relevant_parameters;
	}

	public void setRelevant_parameters(Set<String> relevant_parameters) {
		this.relevant_parameters = relevant_parameters;
	}

	public DomainSet getMethodRequirements() {
		return methodRequirements;
	}

	public void setMethodRequirements(DomainSet methodRequirements) {
		this.methodRequirements = methodRequirements;
	}

	@Override
	public String toString() {
		return this.declaringClass + "." + this.name;
	}

	public String toHumanReadableString() {
		StringBuffer sbf = new StringBuffer();

		// StringBuffer contents
		sbf.append(this.getMethodSignature());

		sbf.append(System.getProperty("line.separator"));

		sbf.append("Integer signature: " + this.processMethodIntegerSignature());

		if (this.methodRequirements != null) {
			sbf.append(System.getProperty("line.separator"));

			sbf.append("Method requirements: " + this.methodRequirements.toHumanReadableString());
		}

		sbf.append(System.getProperty("line.separator"));

		sbf.append("Escape info: {");

		String s = Joiner.on(", ").skipNulls().join(this.escapeNodes);

		sbf.append(s + "}");

		// new line

		Set<Line> lines = this.body.getLines();
		for (Line line : lines) {
			sbf.append(System.getProperty("line.separator"));
			// los NEW ya se duplicaron antes
			// TODO: duplicar los new
			sbf.append(line.toHumanReadableString());
		}
		// second line
		return sbf.toString();

	}

	public String processMethodIntegerSignature() {
		String s = (this.relevant_parameters != null ? Joiner.on(", ").skipNulls().join(this.relevant_parameters) : "");

		return this.getReturnType() + " " + this.getName() + String.format("(%s)", s);
	}

	public String getMethodSignature() {
		String s = (this.getParameters() != null ? Joiner.on(", ").skipNulls().join(
				Iterables.transform(this.getParameters(), new Function<IntermediateRepresentationParameter, String>() {
					public String apply(IntermediateRepresentationParameter parameter) {
						return parameter.getType() + " " + parameter.getName();
					}
				}

				)) : "");

		return this.getReturnType() + " " + this.getName() + String.format("(%s)", s);
	}

	public String getFullName() {
		return this.declaringClass + "." + this.name;
	}

	// Esto no esta soportado para tamaño maximo del CircularStack todavia
	public void setNodesInfo(EscapeSummary escapeSummary, Map<Node, Integer> numbers) {
		Set<Node> escaping = escapeSummary.getEscaping();
		this.escapeNodes = new TreeSet<PaperPointsToHeapPartition>();

		// Podria hacer una funcion asi no escribo las cosas 2 veces

		Set<Node> allNodes = escapeSummary.getNodes();

		TreeSet<Node> allOrderedNodes = new TreeSet<Node>(allNodes);
		TreeSet<Node> allOrderedEscapingNodes = new TreeSet<Node>(escaping);
				
		this.nodes = new TreeSet<PaperPointsToHeapPartition>();

		for (Node n : allOrderedNodes) {

			CircularStack<String> ir_context = PaperNodeUtils.getIrContext(n);

			String belongsTo = this.getFullName();

			if (n instanceof StmtNode || n instanceof MethodNode || n instanceof ContainerNode)
				belongsTo = n.belongsTo().getDeclaringClass() + "." + n.belongsTo().getName();

			// TODO: Cada hp tiene un nodo en este rinard. En madeja no!!!
			numbers.put(n, RichPaperPointsToHeapPartition.counter);

			this.nodes.add(new RichPaperPointsToHeapPartition(n, ir_context, belongsTo,
					RichPaperPointsToHeapPartition.counter));

			RichPaperPointsToHeapPartition.counter += 1;
		}

		for (Node n : allOrderedEscapingNodes) {

			CircularStack<String> ir_context = PaperNodeUtils.getIrContext(n);

			String belongsTo = this.getFullName();

			if (n instanceof StmtNode || n instanceof MethodNode || n instanceof ContainerNode)
				belongsTo = n.belongsTo().getDeclaringClass() + "." + n.belongsTo().getName();

			Integer counter_number = new Integer(0);

			// TODO: podria hacer las dos cosas juntas
			if (numbers.containsKey(n)) {
				counter_number = numbers.get(n);
			} else {
				counter_number = RichPaperPointsToHeapPartition.counter;
			}

			this.escapeNodes.add(new RichPaperPointsToHeapPartition(n, ir_context, belongsTo, counter_number));
			RichPaperPointsToHeapPartition.counter = RichPaperPointsToHeapPartition.counter + 1;
		}

		RichPaperPointsToHeapPartition.counter = 0;

		log.debug("Nodos armados");

	}

	public Set<PaperPointsToHeapPartition> getEscapeNodes() {
		return this.escapeNodes;
	}

	public String getDeclaringClass() {
		return declaringClass;
	}

	public void setDeclaringClass(String declaringClass) {
		this.declaringClass = declaringClass;
	}
}
