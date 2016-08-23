package ar.uba.dc.analysis.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;


@SuppressWarnings("serial")
class CycleException extends Exception
{
      //Parameterless Constructor
      public CycleException() {}

      //Constructor that accepts a message
      public CycleException(String message)
      {
         super(message);
      }
}


public class IRGraph {
	  static class Node{
	    public final IntermediateRepresentationMethod ir_method;
	    public final HashSet<Edge> inEdges;
	    public final HashSet<Edge> outEdges;
	    public Node(IntermediateRepresentationMethod ir_method) {
	      this.ir_method = ir_method;
	      inEdges = new HashSet<Edge>();
	      outEdges = new HashSet<Edge>();
	    }
	    public Node addEdge(Node node){
	      Edge e = new Edge(this, node);
	      outEdges.add(e);
	      node.inEdges.add(e);
	      return this;
	    }
	    @Override
	    public String toString() {
	      return this.ir_method.getName();
	    }
	  }

	  static class Edge{
	    public final Node from;
	    public final Node to;
	    public Edge(Node from, Node to) {
	      this.from = from;
	      this.to = to;
	    }
	    @Override
	    public boolean equals(Object obj) {
	      Edge e = (Edge)obj;
	      return e.from == from && e.to == to;
	    }
	  }
}