package ar.uba.dc.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class List implements Collection{

	Cell head;
	public int size = 0;

	/**
	 * @return 
	 * @temporal: 0
	 * @residual: 1
	 */
	public boolean add(Object e) {
		this.head = new Cell(e, this.head);
		this.size++;
		return true;
	}
	
	public void addAll(List l) {
		Iterator it = l.iterator();
		while(it.hasNext())
		{
			Object o = it.next();
			this.add(o);
		}
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

	@Override
	public void forEach(Consumer action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeIf(Predicate filter) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Spliterator spliterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream stream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream parallelStream() {
		// TODO Auto-generated method stub
		return null;
	}
}
