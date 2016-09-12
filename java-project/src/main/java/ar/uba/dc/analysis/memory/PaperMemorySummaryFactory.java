package ar.uba.dc.analysis.memory;

import ar.uba.dc.analysis.common.SummaryFactory;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.expression.ParametricExpressionFactory;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpressionFactory;
import ar.uba.dc.analysis.memory.impl.madeja.MemorySummaryImpl;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.analysis.memory.summary.MemorySummary;

public class PaperMemorySummaryFactory {


	protected BarvinokParametricExpressionFactory expressionFactory;
		
        public PaperMemorySummary initialSummary(IntermediateRepresentationMethod ir_method) {
                
                ParametricExpression initialTemporal = expressionFactory.constant(0L, ir_method.getMethodRequirements());
        		ParametricExpression initialMemoryRequirement = expressionFactory.constant(0L, ir_method.getMethodRequirements());
        		
        		
        		PaperMemorySummary summary =  new PaperMemorySummary(ir_method, ir_method.getRelevant_parameters(), initialTemporal, initialMemoryRequirement);
                return summary;
        }

		public BarvinokParametricExpressionFactory getExpressionFactory() {
			return expressionFactory;
		}

		public void setExpressionFactory(BarvinokParametricExpressionFactory expressionFactory) {
			this.expressionFactory = expressionFactory;
		}

	
}
