package ar.uba.dc.tools.instrumentation.resource.tracker.runtime;

public class HeapJVMTI {

	static {
		System.loadLibrary("HeapJVMTI");
	}
	
	public native void setTag(Object object, long tag, int length);
	
	//public native int countTagged();
	
	public native int countReachable(long from, long to);
}
