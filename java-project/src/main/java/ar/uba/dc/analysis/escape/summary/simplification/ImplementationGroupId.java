package ar.uba.dc.analysis.escape.summary.simplification;

import soot.SootClass;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

public class ImplementationGroupId extends GroupId {

	private Stmt call;
	private SootClass implementation;
	
	public ImplementationGroupId(Node src, String field, boolean isEdge, boolean isTargetInside, CircularStack<StatementId> targetContext, Stmt call, SootClass implementation) {
		super(src, field, isEdge, isTargetInside, targetContext);
		this.call = call;
		this.implementation = implementation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((call == null) ? 0 : call.hashCode());
		result = prime * result
				+ ((implementation == null) ? 0 : implementation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ImplementationGroupId)) {
			return false;
		}
		ImplementationGroupId other = (ImplementationGroupId) obj;
		if (call == null) {
			if (other.call != null) {
				return false;
			}
		} else if (!call.equals(other.call)) {
			return false;
		}
		if (implementation == null) {
			if (other.implementation != null) {
				return false;
			}
		} else if (!implementation.equals(other.implementation)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ImplementationGroupId [call=" + call + ", implementation="
				+ implementation + ", getField()=" + getField() + ", getSrc()="
				+ getSrc() + ", getTargetContext()=" + getTargetContext()
				+ ", isEdge()=" + isEdge() + ", isTargetInside()="
				+ isTargetInside() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + "]";
	}

	public Stmt getCall() {
		return call;
	}

	public SootClass getImplementation() {
		return implementation;
	}
	
}
