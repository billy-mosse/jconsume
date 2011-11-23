package ar.uba.dc.barvinok.expression.operation;

public interface DomainUnifier {

	/**
	 * Unifica los dominios recibidos como parametros para devolver una nueva expresion que defina la conjuncion
	 * de ambos dominios.
	 * 
	 * Un dominio es una diyuncion de conjunciones. Es decir
	 * 
	 * (a and b and c) or (b and e and d) or (e and a) or a
	 * 
	 * @param constraints
	 * @param constraints2
	 * @return
	 */
	public String unify(String constraints, String constraints2);
}
