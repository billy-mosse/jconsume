package ar.uba.dc.soot;

import ar.uba.dc.analysis.escape.graph.node.StmtNode;
import soot.SootMethod;
import soot.jimple.Stmt;

/**
 * Id de un {@link StmtNode}. Necesitamos el {@link Stmt} de soot al cual esta asociado el nodo
 * junto con el {@link SootMethod} al cual pertenece el statement. Esto es para poder guardar el summary
 * como XML.
 * 
 * @author testis
 */
public class StatementId {

	/** Method in which statement that created the node was executed **/
	private SootMethod methodOfId;
	
    /** Statement that created the node */
    private Stmt id;
	
    public StatementId(SootMethod method, Stmt stmt) {
    	this.methodOfId = method;
    	this.id = stmt;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof StatementId)) {
			return false;
		}
		
		StatementId ni = (StatementId) obj;
		return methodOfId.equals(ni.methodOfId) && id.equals(ni.id);
	}

	@Override
	public int hashCode() {
		return methodOfId.hashCode() + id.hashCode();
	}

	@Override
	public String toString() {
		return id.toString();
	}

	public SootMethod getMethodOfId() {
		return methodOfId;
	}

	public Stmt getId() {
		return id;
	}
}
