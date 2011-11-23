package ar.uba.dc.analysis.memory.impl.summary;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import soot.RefLikeType;
import soot.SootMethod;
import ar.uba.dc.analysis.common.SummaryFactory;
import ar.uba.dc.analysis.common.method.information.RuleBasedMethodInformationProvider;
import ar.uba.dc.analysis.common.method.information.rules.Rule;
import ar.uba.dc.analysis.escape.graph.node.GlobalNode;
import ar.uba.dc.analysis.escape.graph.node.MethodNode;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpressionFactory;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.barvinok.BarvinokSyntax;

public class RuleBasedMemorySummaryFactory implements SummaryFactory<MemorySummary> {
	
	private RuleBasedMethodInformationProvider informationProvider;
	private BarvinokParametricExpressionFactory expressionFactory;
	private BarvinokSyntax isccSyntax;
	private BarvinokSyntax omegaSyntax;
	private int sensitivity;
	
	@SuppressWarnings("unchecked")
	@Override
	public MemorySummary conservativeGraph(SootMethod method, boolean withEffect) {
		Rule rule = informationProvider.findRule(method);
		ParametricExpression tempMemory = buildExpression(rule.getResources().getTemporal(), rule.getResources().getSyntax());
		ParametricExpression resMemory = buildExpression(rule.getResources().getResidual(), rule.getResources().getSyntax());
		
		Set<String> parameters = new TreeSet<String>(tempMemory.getParameters());
		parameters.addAll(resMemory.getParameters());
		EscapeBasedMemorySummary summary = new EscapeBasedMemorySummary(method, parameters, tempMemory);
		
		PointsToHeapPartition globHp = new PointsToHeapPartition(GlobalNode.node, false);
		summary.add(globHp, resMemory);

		// parameters & this escape globally
		Iterator it = method.getParameterTypes().iterator();
		int i = 0;
		while (it.hasNext()) {
			if (it.next() instanceof RefLikeType) {
				PointsToHeapPartition paramHp = new PointsToHeapPartition(new ParamNode(i), false);
				summary.add(paramHp, expressionFactory.constant(0L));
				summary.partitionEscapeGlobaly(paramHp);
			}
			i++;
		}

		// return value escapes globally
		if (method.getReturnType() instanceof RefLikeType)
			summary.returnPartition(globHp);
		
		return summary;
	}
	
	private ParametricExpression buildExpression(String value, String syntax) {
		BarvinokSyntax parser = omegaSyntax;
		if (syntax.equals("iscc")) {
			parser = isccSyntax;
		}
		return expressionFactory.polynomial(parser.parsePiecewiseQuasipolynomial(value));
	}

	@Override
	public MemorySummary freshGraph(SootMethod method) {
		Rule rule = informationProvider.findRule(method);
		ParametricExpression tempMemory = buildExpression(rule.getResources().getTemporal(), rule.getResources().getSyntax());
		ParametricExpression resMemory = buildExpression(rule.getResources().getResidual(), rule.getResources().getSyntax());
		
		Set<String> parameters = new TreeSet<String>(tempMemory.getParameters());
		parameters.addAll(resMemory.getParameters());
		EscapeBasedMemorySummary summary = new EscapeBasedMemorySummary(method, parameters, tempMemory);
		
		if (method.getReturnType() instanceof RefLikeType) {
			PointsToHeapPartition methodHp = new PointsToHeapPartition(new MethodNode(method, sensitivity), false);
			summary.returnPartition(methodHp);
			summary.add(methodHp, resMemory);
		}
		
		return summary;
	}

	public void setInformationProvider(RuleBasedMethodInformationProvider informationProvider) {
		this.informationProvider = informationProvider;
	}

	public void setExpressionFactory(
			BarvinokParametricExpressionFactory expressionFactory) {
		this.expressionFactory = expressionFactory;
	}

	public void setIsccSyntax(BarvinokSyntax isccSyntax) {
		this.isccSyntax = isccSyntax;
	}

	public void setOmegaSyntax(BarvinokSyntax omegaSyntax) {
		this.omegaSyntax = omegaSyntax;
	}

	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}
}
