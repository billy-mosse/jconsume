package ar.uba.dc.jolden.em3d;

import java.util.Enumeration;

//a former local (inner) class that implements the enumeration, now converted to a separate class so that it can be processed
public class Enumerate implements Enumeration {
  private Node current;
  public Enumerate(Node node) { this.current = node; } //temporal = 0, residual = 0
  public boolean hasMoreElements() { return (current != null); } //temporal = 0, residual = 0
  public Object nextElement() { //temporal = 0, residual = 0
	Object retval = current;
	current = current.next;
	return retval;
  }
}