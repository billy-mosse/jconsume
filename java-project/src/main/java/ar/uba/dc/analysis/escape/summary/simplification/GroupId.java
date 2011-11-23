package ar.uba.dc.analysis.escape.summary.simplification;

import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

public class GroupId {

	private Node src;
	private String field;
	private boolean isEdge;
	private boolean isTargetInside;
	private CircularStack<StatementId> targetContext;
	
	public GroupId(Node src, String field, boolean isEdge, boolean isTargetInside, CircularStack<StatementId> targetContext) {
		super();
		this.src = src;
		this.field = field;
		this.isEdge = isEdge;
		this.isTargetInside = isTargetInside;
		this.targetContext = targetContext;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + (isEdge ? 1231 : 1237);
		result = prime * result + (isTargetInside ? 1231 : 1237);
		result = prime * result + ((src == null) ? 0 : src.hashCode());
		result = prime * result
				+ ((targetContext == null) ? 0 : targetContext.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof GroupId)) {
			return false;
		}
		GroupId other = (GroupId) obj;
		if (field == null) {
			if (other.field != null) {
				return false;
			}
		} else if (!field.equals(other.field)) {
			return false;
		}
		if (isEdge != other.isEdge) {
			return false;
		}
		if (isTargetInside != other.isTargetInside) {
			return false;
		}
		if (src == null) {
			if (other.src != null) {
				return false;
			}
		} else if (!src.equals(other.src)) {
			return false;
		}
		if (targetContext == null) {
			if (other.targetContext != null) {
				return false;
			}
		} else if (!targetContext.equals(other.targetContext)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "GroupId [field=" + field + ", isEdge=" + isEdge
				+ ", isTargetInside=" + isTargetInside + ", src=" + src
				+ ", targetContext=" + targetContext + "]";
	}

	public Node getSrc() {
		return src;
	}

	public String getField() {
		return field;
	}

	public boolean isEdge() {
		return isEdge;
	}

	public boolean isTargetInside() {
		return isTargetInside;
	}

	public CircularStack<StatementId> getTargetContext() {
		return targetContext;
	}

}
