package ar.uba.dc.analysis.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import ar.uba.dc.analysis.common.IRGraph.Edge;
import ar.uba.dc.analysis.common.IRGraph.Node;

public class IRGraphUtils {

	public static ArrayList<Node> getPseudoTopologicalOrder(Node[] allNodes) throws CycleException
	  {
		  ArrayList<Node> L = new ArrayList<Node>();

		    //S <- Set of all nodes with no incoming edges
		    HashSet<Node> S = new HashSet<Node>(); 
		    for(Node n : allNodes){
		      if(n.inEdges.size() == 0){
		        S.add(n);
		      }
		    }

		    //while S is non-empty do
		    while(!S.isEmpty()){
		      //remove a node n from S
		      Node n = S.iterator().next();
		      S.remove(n);

		      //insert n into L
		      L.add(n);

		      //for each node m with an edge e from n to m do
		      for(Iterator<Edge> it = n.outEdges.iterator();it.hasNext();){
		        //remove edge e from the graph
		        Edge e = it.next();
		        Node m = e.to;
		        it.remove();//Remove edge from n
		        m.inEdges.remove(e);//Remove edge from m

		        //if m has no other incoming edges then insert m into S
		        if(m.inEdges.isEmpty()){
		          S.add(m);
		        }
		      }
		    }
		    //Check to see if all edges are removed
		    boolean cycle = false;
		    for(Node n : allNodes){
		      if(!n.inEdges.isEmpty()){
		        cycle = true;
		        break;
		      }
		    }
		    if(cycle)
		    {
		    	throw new CycleException("El grafo tiene un ciclo");
		    }
		    else
		    {
		    	return L;
		    }
	  }
}
