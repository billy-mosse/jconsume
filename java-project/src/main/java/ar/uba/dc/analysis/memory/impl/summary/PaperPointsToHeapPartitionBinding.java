package ar.uba.dc.analysis.memory.impl.summary;

public class PaperPointsToHeapPartitionBinding {
	private PaperPointsToHeapPartition callee_hp;
	public PaperPointsToHeapPartition getCallee_hp() {
		return callee_hp;
	}

	public void setCallee_hp(PaperPointsToHeapPartition callee_hp) {
		this.callee_hp = callee_hp;
	}

	public PaperPointsToHeapPartition getCaller_hp() {
		return caller_hp;
	}

	public void setCaller_hp(PaperPointsToHeapPartition caller_hp) {
		this.caller_hp = caller_hp;
	}

	private PaperPointsToHeapPartition caller_hp;
	
	public PaperPointsToHeapPartitionBinding(PaperPointsToHeapPartition callee_hp, PaperPointsToHeapPartition caller_hp)
	{
		this.callee_hp = callee_hp;	
		this.caller_hp = caller_hp;
	}

}
