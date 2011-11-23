package ar.uba.dc.soot;


/**
 * Simple box class that encapsulates a reference to a type T. 
 */
public class Box<T> {

	private T value;

	public Box(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}

	public int hashCode() {
		return value.hashCode(); 
	}

	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (!(o instanceof Box)) return false;
		Box b = (Box) o; 
	    return value.equals(b.value); 
	}	
}


