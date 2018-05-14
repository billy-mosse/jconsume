package ar.uba.dc.analysis.common.method.information;

import ar.uba.dc.barvinok.expression.PiecewiseQuasipolynomial;

//podria tener mas cosas, como ser puro y eso
public class Annotation {
	public Annotation(PiecewiseQuasipolynomial consumption) {
		this.consumption = consumption;
	}

	public PiecewiseQuasipolynomial consumption;
}
