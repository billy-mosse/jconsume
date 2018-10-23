package ar.uba.dc.analysis.common.method.information;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.method.information.rules.Rule;
import ar.uba.dc.analysis.memory.PaperInterproceduralAnalysis;
import ar.uba.dc.analysis.memory.expression.ParametricExpression;
import ar.uba.dc.analysis.memory.impl.BarvinokParametricExpression;
import ar.uba.dc.analysis.memory.impl.madeja.PaperMemorySummary;
import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;
import ar.uba.dc.barvinok.expression.QuasiPolynomial;

//TODO cambiar el nombre
public class MethodAnnotationsHelper {

	private static Log log = LogFactory.getLog(MethodAnnotationsHelper.class);

	private String mainClass;
	
	//Por ahora voy a asumir que son memory summaries.
	private AnnotationsContainer annotationsContainer;
	
	public MethodAnnotationsHelper(String mainClass) {
		this.setMainClass(mainClass);
	}

	public MethodAnnotationsHelper(AnnotationsContainer annotationsContainer) {
		this.setAnnotationsContainer(annotationsContainer);
	}

	public String getMainClass() {
		return mainClass;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}
	
	public String getRelativePathFile()
	{
		return null;
	}
	
	
	public PaperMemorySummary get(IntermediateRepresentationMethod ir_method)
	{
		Annotation annotation;
		try
		{
			if(this.annotationsContainer != null)
			{
				annotation = this.annotationsContainer.annotations.get(ir_method.toString());
			
				if(annotation != null)
				{
					QuasiPolynomial q = new QuasiPolynomial("0");
					PiecewiseQuasipolynomial p = new PiecewiseQuasipolynomial();
					p.add(q);
					ParametricExpression temp = new BarvinokParametricExpression(p);
					PaperMemorySummary summary = new PaperMemorySummary(ir_method, temp, new BarvinokParametricExpression(annotation.consumption));
					
					return summary;
				}
			}
		}
		catch(java.lang.NullPointerException e )
		{
			log.error(e.getMessage(), e);
			System.exit(1);
		}
		return null;
		
	}

	public AnnotationsContainer getAnnotationsContainer() {
		return annotationsContainer;
	}

	public void setAnnotationsContainer(AnnotationsContainer annotationsContainer) {
		this.annotationsContainer = annotationsContainer;
	}
	

	
	
}
