package ar.uba.dc.analysis.memory.code;

public interface StatementVisitor<T> {

	T visit(CallStatement stmt);
	T visit(NewStatement stmt);
	
}
