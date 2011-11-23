package ar.uba.dc.util.collections;

import java.util.Iterator;
import java.util.Stack;


/**
 * CircularStack es una implementacion de Stack a la que se le puede indicar un tamaño maximo.
 * En caso de tenerlo y de encontrarse llena, al insertar un nuevo elemento, se elimina del stack
 * al elemento mas antiguo.
 * 
 * @author testis
 */
public class CircularStack<E> implements Cloneable {

	/** Serialization version */
	private static final long serialVersionUID = 1L;
	
	/** Constante para indicar cuando la capacidad del stack es infinita **/
	public static final int INFINITE = -1;
	
	/** Capacidad del stack */
	protected int maxElements;
	
	protected Stack<E> stack;
	
	/**
     * Constructor que crea un stack con capacidad infinita.
     */
	public CircularStack() {
		this(INFINITE);
    }
	
	/**
     * Constructor que posee la capacidad maxima especificada.
     * 
     * @param maxElements  capacidad del stack (no puede ser modificada)
     */
	public CircularStack(int maxElements) {
		super();
		this.maxElements = maxElements;
		this.stack = new Stack<E>();
		if (maxElements != INFINITE) {
			this.stack.ensureCapacity(maxElements);
		}
	}
	
	/**
     * Constructor que crea un stack a partir del stack especificada.
     * La capacidad del stack construido es tomada en base a la del stack especificado
     * 
     * @param circularStack  el stack a copiar, no debe ser null
     */
	public CircularStack(CircularStack<E> circularStack) {
		this(circularStack.maxElements);
        this.stack.addAll(circularStack.stack);
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof CircularStack)) return false;
		
		CircularStack s = (CircularStack) o;
		
		return maxElements == s.maxElements && stack.equals(s.stack);
	}

	@Override
	public int hashCode() {
		return stack.hashCode() + maxElements;
	}

	@Override
	public String toString() {
		return stack.toString();
	}

	/**
     * Si el stack esta a su maxima capacidad, el elemento mas antiguo del stack es eliminado antes de 
     * agregar el elemento especificado.
     *
     * @param element elementoa  agregar
     * @return true, always
     */
	public synchronized E push(E element) {
		if (maxElements == 0) {
			return element;
		}
		
		if (isFull()) {
			stack.removeElementAt(0);
		}

		return stack.push(element);
	}
	
	public E pop() {
		return stack.pop();
	}
	
	public E peek() {
		return stack.peek();
	}
	
	public boolean empty() {
		return stack.empty();
	}
	
	public int search(Object o) {
		return stack.search(o);
	}
	
	public boolean isFull() {
		return (maxElements != INFINITE && stack.size() == maxElements);	
	}
	
	public int size() {
		return stack.size();
	}
	
	public Iterator<E> iterator() {
		return stack.iterator();
	}

	public int maxSize() {
		return maxElements;
	}
	
	public CircularStack<E> clone() {
		return new CircularStack<E>(this);
	}

	public CircularStack<E> clear() {
		stack.clear();
		return this;
	}
}
