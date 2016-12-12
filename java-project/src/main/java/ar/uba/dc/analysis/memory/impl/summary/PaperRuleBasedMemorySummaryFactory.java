package ar.uba.dc.analysis.memory.impl.summary;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import soot.RefLikeType;
import soot.SootMethod;
import ar.uba.dc.analysis.common.SummaryFactory;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import ar.uba.dc.analysis.common.method.information.RuleBasedMethodInformationProvider;
import ar.uba.dc.analysis.common.method.information.rules.Rule;
import ar.uba.dc.analysis.escape.graph.node.GlobalNode;
import ar.uba.dc.analysis.escape.graph.node.MethodNode;
import ar.uba.dc.analysis.escape.graph.node.PaperMethodNode;
import ar.uba.dc.analysis.escape.graph.node.PaperParamNode;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpressionFactory;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.barvinok.BarvinokSyntax;



import ar.uba.dc.analysis.memory.SymbolicCalculator;


public class PaperRuleBasedMemorySummaryFactory implements SummaryFactory<PaperMemorySummary, IntermediateRepresentationMethod> {
	
	private RuleBasedMethodInformationProvider informationProvider;
	private BarvinokParametricExpressionFactory expressionFactory;
	private BarvinokSyntax isccSyntax;
	private BarvinokSyntax omegaSyntax;
	private int sensitivity;
	private SymbolicCalculator symbolicCalculator;

	
	@SuppressWarnings("unchecked")
	@Override
	public PaperMemorySummary conservativeGraph(IntermediateRepresentationMethod method, boolean withEffect) {
		/*Rule rule = informationProvider.IR_findRule(method);
		ParametricExpression tempMemory = buildExpression(rule.getResources().getTemporal(), rule.getResources().getSyntax());
		ParametricExpression resMemory = buildExpression(rule.getResources().getResidual(), rule.getResources().getSyntax());
		ParametricExpression memReq = buildExpression(rule.getResources().getMemoryRequirement(), rule.getResources().getSyntax());
		
		Set<String> parameters = new TreeSet<String>(tempMemory.getParameters());
		parameters.addAll(resMemory.getParameters());
		
		PaperMemorySummary summary = new PaperMemorySummary(method, parameters, tempMemory, memReq);
		
		PointsToHeapPartition globHp = new PointsToHeapPartition(GlobalNode.node, false);
		
		PaperPointsToHeapPartition paperGlobHp = new PaperPointsToHeapPartition(globHp);
		
		summary.add(paperGlobHp, resMemory);

		// parameters & this escape globally
		Iterator it = method.getParameters().iterator();
		
		int i = 0;
		while (it.hasNext()) {
			IntermediateRepresentationParameter p = (IntermediateRepresentationParameter) it.next();
			
			if(p.isRefLikeType())
			{
				PaperPointsToHeapPartition paramHp = new PaperPointsToHeapPartition(new PaperParamNode(i), false);
				summary.add(paramHp, expressionFactory.constant(0L));
				summary.partitionEscapeGlobaly(paramHp);
			}
			
			i++;
		}

		// return value escapes globally
		if (method.isReturnRefLikeType())
			summary.returnPartition(paperGlobHp);
		
		return summary;*/ return null;
	}
	
	private ParametricExpression buildExpression(String value, String syntax) {
		BarvinokSyntax parser = omegaSyntax;
		if (syntax.equals("iscc")) {
			parser = isccSyntax;
		}
		return expressionFactory.polynomial(parser.parsePiecewiseQuasipolynomial(value));
	}


	@Override
	public PaperMemorySummary freshGraph(IntermediateRepresentationMethod ir_method) {
		Rule rule = informationProvider.IR_findRule(ir_method);
		ParametricExpression tempMemory = buildExpression(rule.getResources().getTemporal(), rule.getResources().getSyntax());
		ParametricExpression resMemory = buildExpression(rule.getResources().getResidual(), rule.getResources().getSyntax());
		ParametricExpression memReq = buildExpression(rule.getResources().getMemoryRequirement(), rule.getResources().getSyntax());
		
		Set<String> parameters = new TreeSet<String>(tempMemory.getParameters());
		parameters.addAll(resMemory.getParameters());
		
		PaperMemorySummary summary = new PaperMemorySummary(ir_method, parameters, tempMemory, memReq);
		
		
		
		if (ir_method.isReturnRefLikeType())
		{
			PaperPointsToHeapPartition methodHp = new SimplePaperPointsToHeapPartition(-1);
			summary.returnPartition((SimplePaperPointsToHeapPartition)methodHp);
			summary.add(methodHp, resMemory);
		}
		else
		{
			summary.setMemoryRequirement(symbolicCalculator.substract(memReq, resMemory));
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
	
	public void setSymbolicCalculator(SymbolicCalculator symbolicCalculator)
	{
		this.symbolicCalculator = symbolicCalculator;
	}
	
}
