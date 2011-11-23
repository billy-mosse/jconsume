package ar.uba.dc.analysis.escape.summary.simplification;


public class BooleanGroupIdDecorator extends GroupId {

	private GroupId target;
	private boolean value; 
	
	public BooleanGroupIdDecorator(GroupId target, boolean value) {
		super(target.getSrc(), target.getField(), target.isEdge(), target.isTargetInside(), target.getTargetContext());
		this.target = target;
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = (value ? 1231 : 1237);
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof BooleanGroupIdDecorator)) {
			return false;
		}
		BooleanGroupIdDecorator other = (BooleanGroupIdDecorator) obj;
		if (value != other.value) {
			return false;
		}
		if (target == null) {
			if (other.target != null) {
				return false;
			}
		} else if (!target.equals(other.target)) {
			return false;
		}
		return true;
	}
}
