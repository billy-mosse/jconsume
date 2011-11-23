package ar.uba.dc.tools.instrumentation.resource.tracker.runtime;

public class ListenerFacade {
	
	static private final String DEFAULT_LISTENER_CLASS = "ar.uba.dc.tools.instrumentation.resource.tracker.runtime.ResourceTrackerListener";
	
	static private final String LISTER_CLASS_PROPERTY = "tracker.listener.class";
	
	static private RuntimeListener listener;
	
	static public void register(RuntimeListener listener) {
		ListenerFacade.listener = listener;
	}
	
	static {
		RuntimeListener listener;
		try {
			String listenerClass = System.getProperty(LISTER_CLASS_PROPERTY, DEFAULT_LISTENER_CLASS);
			listener = (RuntimeListener) Class.forName(listenerClass).newInstance();
			register(listener);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	
	static public void newObjectRegistrationEvent(String newStmt, String partition) {
		listener.newObjectRegistrationEvent(newStmt, partition);
	}
	
	static public void enter(String methodName) {
		listener.enter(methodName);
	}
	
	static public void exit(String methodName) {
		listener.exit(methodName);
	}
	
	static public void exit() {
		listener.exit();
	}
	
	static public void newObjectEvent(Object object, String newStmt) {
		listener.newObjectEvent(object,newStmt);
	}
	

	static public void newArrayEvent(Object object, String newStmt, int size) {
		listener.newArrayEvent(object,newStmt, size);
	}
	
	static public void newArrayEvent(Object object, String newStmt, int fistDimension, int secondDimension) {
		//System.out.println(object);
		listener.newArrayEvent(object, newStmt, fistDimension, secondDimension);
	}
	
	static public void newArrayEvent(Object object, String newStmt, int fistDimension, int secondDimension, int thirdDimension) {
	//	System.out.println(object);
		listener.newArrayEvent(object, newStmt, fistDimension, secondDimension, thirdDimension);
	}
	
	static public void callEvent(String callStmt) {
		listener.callEvent(callStmt);
	}
	
	static public void endCallEvent(String callStmt) {
		listener.endCallEvent(callStmt);
	}
	
	static public void caughtExceptionEvent(String atMethod) {
		listener.caughtException(atMethod);
	}

}
