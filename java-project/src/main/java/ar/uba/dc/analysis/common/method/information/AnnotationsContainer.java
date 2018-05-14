package ar.uba.dc.analysis.common.method.information;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;

public class AnnotationsContainer {
	
	public AnnotationsContainer()
	{
		this.annotations = new HashMap<String, Annotation>();
	}
	
	public Map<String, Annotation> annotations; //key=method signature
	//creo que ya tenia una manera de obtener la firma en otro lado
}
