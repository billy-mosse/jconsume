package ar.uba.dc.analysis.automaticinvariants.inductives;

import java.util.List;
import soot.Value;
import soot.jimple.Stmt;

public interface InductivesFilter {
	public List liveVars(List lives);
	public List getDerivedVars(Value l,List lives);
	public List getInductivesBefore(Stmt s);
	public List getInductivesAfter(Stmt s);
}
