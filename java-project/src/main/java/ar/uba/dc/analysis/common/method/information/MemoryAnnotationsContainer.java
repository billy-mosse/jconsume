package ar.uba.dc.analysis.common.method.information;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;

public class MemoryAnnotationsContainer {
	
	public MemoryAnnotationsContainer()
	{
		this.annotations = new HashMap<String, MemoryAnnotation>();
	}
	
	public Map<String, MemoryAnnotation> annotations; //key=method signature
	//creo que ya tenia una manera de obtener la firma en otro lado
}
