package ar.uba.dc.rinard;

import java.util.Iterator;

public class List {

	Cell head;
	private int size = 0;

	/**
	 * @temporal: 0
	 * @residual: 1
	 */
	public void add(Object e) {
		this.head = new Cell(e, this.head);
		this.size++;
	}

	/**
	 * @temporal: 0
	 * @residual: 1
	 */
	@SuppressWarnings("unchecked")
	public Iterator iterator() {
		return new ListItr(this.head);
	}

	public int size() {
		return this.size;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		for (Iterator it = this.iterator(); it.hasNext();) {
			buffer.append(it.next().toString());
			if(it.hasNext())
				buffer.append(",");
		}
		buffer.append("}");
		return buffer.toString();
	}
}