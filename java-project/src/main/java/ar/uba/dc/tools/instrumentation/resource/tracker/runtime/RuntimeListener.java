package ar.uba.dc.tools.instrumentation.resource.tracker.runtime;

public interface RuntimeListener {

		void enter(String methodName);
		
		void exit(String methodName);
		
		void exit();

		void newObjectEvent(Object object, String newStmt);

		void newArrayEvent(Object object, String newStmt, int size);
		
		void newArrayEvent(Object object, String newStmt, int firstDimension,int secondDimension);
		
		void newArrayEvent(Object object, String newStmt, int firstDimension,int secondDimension, int thirdDimension);
		
		void newObjectRegistrationEvent(String newStmt, String partition);

		void callEvent(String callStmt);
		
		void endCallEvent(String callStmt);
		
		void caughtException(String atMethod);

}
