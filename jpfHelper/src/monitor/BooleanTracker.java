package monitor;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public  class BooleanTracker {
	private static Map<Integer,Integer> trackedObjs = new HashMap<Integer,Integer>();
	private static String method = "";
	public static boolean tracked = false;
	public static boolean counting = false;
	public static int iterations = 10;
//	private static String methodToAnalyze="<javazoom.jl.player.jlp: void play()>";
	private static String methodToAnalyze="<ar.uba.dc.ismm.MotivatingExample: void main(java.lang.String[])>";
	//private String methodToAnalyze;
	//private static BooleanTracker singleton = new BooleanTracker();

	public static void track() {
		// tracked = true;	
	}
	public static void untrack() {
		tracked = false;
	}
	public static boolean isTracked()
	{
		return tracked;
	}
	
	public static boolean isCounting()
	{
		return counting;
	}

//	public static boolean isTrackedAndAllocated(Object o)
//	{
//		boolean res = trackedObjs.contains(o); 
//		return res;
//	}
//	public static void follow(Object o)
//	{
//		trackedObjs.add(o);
//	}
//	public static void unFollow(Object o)
//	{
//		trackedObjs.remove(o);
//	}
	public static boolean isTrackedAndAllocated(int oRef)
	{
		boolean res = trackedObjs.keySet().contains(oRef); 
		return res;
	}

	public static void follow(int oRef)
	{
		trackedObjs.put(oRef, 1);
	}
	
	public static void follow(int oRef, int size)
	{
		trackedObjs.put(oRef, size);
	}
	
	public static void follow(int oRef, int size1, int size2)
	{
		trackedObjs.put(oRef, size1*size2);
	}


	public static void unFollow(int oRef)
	{
		trackedObjs.remove(oRef);
	}
	
	public static void clear()
	{
		trackedObjs.clear();
	}
	public static int live()
	{
		return trackedObjs.size();
	}
	public static Iterator<Integer> trackedObjects()
	{
		return trackedObjs.keySet().iterator();
	}
	
	public static int getCount(int objRef) {
		Integer count = trackedObjs.get(objRef);
		if(count!=null) return count.intValue();
		return 0;
	}


	
	public static void start()
	{
	}
	public static void stop()
	{
	}
	
	public static void setMethod(String m)
	{
		//System.setProperty("METHOD_TO_ANALYZE", m);
		methodToAnalyze = m;
	}
	public static String getMethod() {
		//String methodToAnalyze = System.getProperty("METHOD_TO_ANALYZE");  
		return methodToAnalyze;
	}
	
	public static boolean isMethodUnderAnalysis(String m)
	{
		boolean res=true;
		//res = m.indexOf("BooleanTracker")==-1;
		return res && (method.length()==0 || method.equals(m));
	}
	
	
	
	public static void enter(String s)
	{
//		System.out.println("En:"+s+ " "+getMethod());

		
//		if(getMethod().equals(s)) {
//			start();
//		}
	}
	
	

	public static void exit(String s)
	{
//		System.out.println("Ex:"+s);
		
//		if(getMethod().equals(s))
//			stop();
	}

	public static void exit()
	{
		System.out.println("Exit:");
	}
	
	public static void caughtExceptionEvent(String s)
	{
		System.out.println("Exception Event!:"+s);		
	}
	
	
}
