package ar.uba.dc.tools.instrumentation.resource.tracker.runtime;

public class HeapTracker {

	private static HeapJVMTI heapInterface;
	
	private static long seq = 0;
	
	public static long times = 0;
	
	static {
		heapInterface = new HeapJVMTI();
	}
	
	private static synchronized long next() {
		seq++;
		return seq;
	}
	
	public static synchronized long currentSeq() {
		return seq;
	}
	
	public static void track(Object object) {
		heapInterface.setTag(object, next(), -1);
	}
	
	public static void track(Object object, int length) {
		heapInterface.setTag(object, next(), length);
	}
	

	public static int countReachableObjects() {
		return countReachableObjects(-1,-1);
	}
	
	public static int countReachableObjects(long from, long to) {
		times++;
		return  heapInterface.countReachable(from,to);
		//return 0;
	}
}
