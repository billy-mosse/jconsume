package ar.uba.dc.tools.instrumentation.resource.tracker.runtime;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
@Ignore

public class HeapJVMTITest {
	
	static Object object;
	
	@Test 
	public void setTag() {
		
		/*object = new Object();
		HeapTracker.track(object);*/
		this.createInteger();
		Assert.assertEquals(0, HeapTracker.countReachableObjects());	
	}
	
	public void createInteger() {
		Integer integer = new Integer(1);
		HeapTracker.track(integer);
		Integer integer2 = new Integer(1);
		HeapTracker.track(integer2);
		ObjectContainer container1 = new ObjectContainer(integer2);
		HeapTracker.track(container1);
		ObjectContainer container2 = new ObjectContainer(integer2);
		HeapTracker.track(container2);
		Assert.assertEquals(4, HeapTracker.countReachableObjects());
	}

	class ObjectContainer {
		
		private Object object;

		public ObjectContainer(Object object) {
			super();
			this.object = object;
		}

		public Object getObject() {
			return object;
		}

		public void setObject(Object object) {
			this.object = object;
		}
		
		
	}
}
