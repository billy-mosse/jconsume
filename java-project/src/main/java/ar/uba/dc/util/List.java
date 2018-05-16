package ar.uba.dc.util;

import java.util.Iterator;

public class List {

	CellE head;
	public int size = 0;

	/**
	 * @temporal: 0
	 * @residual: 1
	 */
	public void add(Object e) {
		this.head = new CellE(e, this.head);
		this.size++;
	}

	/**
	 * @temporal: 0
	 * @residual: 1
	 */
	@SuppressWarnings("unchecked")
	public Iterator iterator() {
		return new ListItrE(this.head);
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