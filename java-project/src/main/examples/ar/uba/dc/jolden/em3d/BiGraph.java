package ar.uba.dc.jolden.em3d;
import java.util.Enumeration;

import ar.uba.dc.annotations.InstrumentationSiteInvariant;
import ar.uba.dc.annotations.InstrumentationSiteInvariantList;



/**
 * A class that represents the irregular bipartite graph used in
 * EM3D.  The graph contains two linked structures that represent the
 * E nodes and the N nodes in the application.
 **/
final class BiGraph
{
  /**
   * Nodes that represent the electrical field.
   **/
  Node eNodes;
  /**
   * Nodes that represent the magnetic field.
   **/
  Node hNodes;

  /**
   * Construct the bipartite graph.
   * @param e the nodes representing the electric fields
   * @param h the nodes representing the magnetic fields
   * 
   * @temporal 0
   * @residual 0
   */ 
  BiGraph(Node e, Node h)
  {
    eNodes = e;
    hNodes = h;
  }

  //16 20
  /**
   * Create the bi graph that contains the linked list of
   * @param numNodes the number of nodes to create
   * e and h nodes.
   * @param numDegree the out-degree of each node
   * @param verbose should we print out runtime messages
   * @return the bi graph that we've created.
   *
   * ArrayCountSize 
   * @temporal 6 + 2*numNodes: numNodes >=1 && numDegree >=1 
   * @residual 2 + (2 + 2*numDegree)*numNodes  + 4*numNodes^2 : numDegree >= 1 && numNodes >=2
   * @residual 4 + 2*numDegree  + 4*numNodes^2: numDegree >= 1 && numNodes ==1
   * 
   * ArrayCountOne
   * @temporal  8 
   * @residual 8 * numNodes  + 2 : numNodes>=2 ; 6 + 4*numNodes : numNodes ==1  ; 6 : numNodes <= 0
   */ 
  
  @SuppressWarnings("unchecked")
  @InstrumentationSiteInvariantList(invariants={
			@InstrumentationSiteInvariant(
					isCallSite=true,
					index=16,
					constraints={"n1.fromCount == numDegree"},
				    newRelevantParameters={}, newInductives = {  }, newVariables = {  }),
		    @InstrumentationSiteInvariant(
				isCallSite=true,
				index=20,
				constraints={"n1.fromCount == numDegree"},
			    newRelevantParameters={}, newInductives = {  }, newVariables = {  })
				    
  }
	)  
static BiGraph create(int numNodes, int numDegree, boolean verbose)
  {
    Node.initSeed(783); //residual = 1
    
    // making nodes (we create a table)
    if (verbose) System.out.println("making nodes (tables in orig. version)");
    Node[] hTable = Node.fillTable(numNodes, numDegree); //residual = 2*numNodes : numNodes > 1 ; 2 : numNodes <= 1 , tempCall = 1
    Node[] eTable = Node.fillTable(numNodes, numDegree); //residual = 2*numNodes : numNodes > 1 ; 2 : numNodes <= 1 , tempCall = 1

    Node hTable0 = hTable[0];
    Node eTable0 = eTable[0];
    
    // making neighbors
    if (verbose) System.out.println("updating from and coeffs");
    for (Enumeration e1 = hTable0.elements(); //maxCall = 0, tempCall = 1
    	e1.hasMoreElements(); ) {
      Node n1 = (Node) e1.nextElement(); //maxCall = 0, tempCal = 0
      n1.makeUniqueNeighbors(eTable);  //maxCall = 0, tempCal = 0
    }
    for (Enumeration e2 = eTable0.elements(); //maxCall = 0, tempCall = 1 
          e2.hasMoreElements(); ) {
      Node n2= (Node) e2.nextElement();  //maxCall = 0, tempCal = 0
      n2.makeUniqueNeighbors(hTable);  //maxCall = 0, tempCal = 0
    }

    // Create the fromNodes and coeff field
    if (verbose) System.out.println("filling from fields");
    for (Enumeration e3 = hTable0.elements(); //maxCall = 0, tempCall = 1 
        e3.hasMoreElements(); ) {
      Node n3 = (Node) e3.nextElement();  //maxCall = 0, tempCal = 0
      
      n3.makeFromNodes(); //residual = 2*numNodes : numNodes >= 2 ; 2 numNodes == 1
    }
    for (Enumeration e4 = eTable0.elements(); //maxCall = 0, tempCall = 1 
        e4.hasMoreElements(); ) {
      Node n4 = (Node) e4.nextElement();  //maxCall = 0, tempCal = 0
      n4.makeFromNodes(); //residual = 2*numNodes : numNodes >= 2 ; 2 numNodes == 1
    }

    // Update the fromNodes
    for (Enumeration e5 = hTable0.elements(); //maxCall = 0, tempCall = 1 
       e5.hasMoreElements(); ) {
      Node n5 = (Node) e5.nextElement();  //maxCall = 0, tempCal = 0
      n5.updateFromNodes(); //maxCall = 0, tempCal = 0
    }
    for (Enumeration e6 = eTable0.elements(); //maxCall = 0, tempCall = 1 
       e6.hasMoreElements(); ) {
      Node n6 = (Node) e6.nextElement();  //maxCall = 0, tempCal = 0
      n6.updateFromNodes(); //maxCall = 0, tempCal = 0
    }

    
    BiGraph g = new BiGraph(eTable0, hTable0); //residual = 1
    return g;
  }

  /**
   * Update the field values of e-nodes based on the values of
   * neighboring h-nodes and vice-versa.
   * 
   * @temporal 2 : numNodes >=1
   * @residual 0
   */ 
  @SuppressWarnings("unchecked")
  
  //creo que deberia poder poner -1 en el index si solo quiero agregar un relevant parameter
  //TODO hacer
  @InstrumentationSiteInvariantList(invariants={
			@InstrumentationSiteInvariant(
					isCallSite=true,
					index=0,
					constraints={"numNodes_init == cont_$r1"},
				    newRelevantParameters={"numNodes_init"}, newInductives = {  }, newVariables = {  })}
	)  
void compute(int numNodes)
  {
	 //con un println anda joya. Me parece que si hay contadores tengo que agregar artificialmente a los relevantparameters?
	  //hacer experimento y preguntarle a diego!
	  //System.out.println(numNodes);
	  //confirmado!
    for (Enumeration e = eNodes.elements(); e.hasMoreElements(); ) { //maxCall = 0, tempCall = 1
      Node n = (Node) e.nextElement(); //maxCall = 0, tempCall = 0
      n.computeNewValue(); //maxCall = 0, tempCall = 0
     /* System.out.println("Hola");
      System.out.println(eNodes.fromCount);
      System.out.println(eNodes.fromLength);
      System.out.println(eNodes.value);
      System.out.println(eNodes.fromNodes.length);
      System.out.println(eNodes.toNodes.length);
      System.out.println(i);
      System.out.println("_____________________________");*/
    }
    for (Enumeration e = hNodes.elements(); e.hasMoreElements(); ) { //maxCall = 0, tempCall =  1
      Node n = (Node) e.nextElement(); //maxCall = 0, tempCall = 0 
      n.computeNewValue(); //maxCall = 0, tempCall = 0

     /* System.out.println("Hola2");
      System.out.println(hNodes.fromCount);
      System.out.println(hNodes.fromLength);
      System.out.println(hNodes.value);
      System.out.println(hNodes.fromNodes.length);
      System.out.println(hNodes.toNodes.length);
      System.out.println(i);
      System.out.println("_____________________________");*/
    }
  }

  /**
   * Override the toString method to print out the values of the e and h nodes.
   * @return a string contain the values of the e and h nodes.
   * 
   * ???
   * @temporal 0
   * @residual 0
   */
  @SuppressWarnings("unchecked")
public String toString()
  {
    StringBuffer retval = new StringBuffer();
    for (Enumeration e = eNodes.elements(); e.hasMoreElements(); ) {
      Node n = (Node) e.nextElement();
      retval.append("E: " + n + "\n");
    }

    for (Enumeration e = hNodes.elements(); e.hasMoreElements(); ) {
      Node n = (Node) e.nextElement();
      retval.append("H: " + n + "\n");
    }
    return retval.toString();
  }

}
