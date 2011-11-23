package ar.uba.dc.invariant.spec.compiler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ar.uba.dc.analysis.memory.code.CallStatement;
import ar.uba.dc.analysis.memory.code.NewStatement;
import ar.uba.dc.analysis.memory.code.Statement;
import ar.uba.dc.analysis.memory.code.StatementVisitor;
import ar.uba.dc.barvinok.expression.DomainSet;

/**
 * Resultado de compilar una especificacion. Permite acceder facilmente a los
 * invariantes de un metodo
 * 
 * @author testis
 */
public class CompiledMethodInvariantProvider implements StatementVisitor<DomainSet> {

	private Map<String, DomainSet> callInvariants = new HashMap<String, DomainSet>(); // offset -> invariant
	private Map<String, DomainSet> creationInvariants = new HashMap<String, DomainSet>(); // offset -> invariant
	
	public DomainSet getInvariant(Statement stmt) {
		DomainSet invariant = stmt.apply(this);
		
		if (invariant == null) {
			invariant = new DomainSet(StringUtils.EMPTY);
		}
		
		return invariant;
	}
	
	public DomainSet visit(CallStatement stmt) {
		return callInvariants.get(stmt.getCounter());
	}

	public DomainSet visit(NewStatement stmt) {
		return  creationInvariants.get(stmt.getCounter());
	}
	
}
