package ar.uba.dc.analysis.common.code;

public interface StatementVisitor<T> {

	T visit(CallStatement stmt);
	T visit(NewStatement stmt);
	
}
