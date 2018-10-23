package ar.uba.dc.jolden.mst;

import ar.uba.dc.annotations.InstrumentationSiteInvariant;
import ar.uba.dc.annotations.InstrumentationSiteInvariantList;

/**
 * A class that represents a vertex in a graph.  We maintain a linked list
 * representation of the vertices.
 **/
class Vertex
{
  /**
   * The minimum distance value for the node
   **/
  int    mindist;
  /**
   * The next vertex in the graph.
   **/
  Vertex next;
  /**
   * A hashtable containing all the connected vertices.
   **/
  Hashtable neighbors;

  /**
   * Create a vertex and initialize the fields.  
   * @param n the next element
   * 
   * ArrayCountSize
   * @temporal 0
   * @residual 1 + numvert  : numvert >=1 ; 1 : numvert <= 0
   * 
   * ArrayCountOne
   * @temporal 0
   * @residual 2
   */
  
  @InstrumentationSiteInvariantList(invariants={
			@InstrumentationSiteInvariant(
			isCallSite=true,
			index=1,
			constraints={"4*$i0==numvert"},
		    newRelevantParameters={ }, newInductives = {  }, newVariables = {  })}
	)
  Vertex(Vertex n, int numvert)
  {
    mindist = 9999999;
    next = n;
    
    //No se da cuenta del /4, pero si que numvert/4 < numvert,
    //asi que el consumo da numvert -1, que sumado al new de next, da numvert
    
    neighbors = new Hashtable(numvert/4); //residual = 1 (porNEW) , residual = 1 (porCallConstructor Hashtable) (numvert ArrayCountSize)
  }

  /**
   * @temporal 0
   * @residual 0
   */
  public int mindist()
  {
    return mindist;
  }

  /**
   * @temporal 0
   * @residual 0
   */
  public void setMindist(int m)
  {
    mindist = m;
  }

  /**
   * @temporal 0
   * @residual 0
   */
  public Vertex next()
  {
    return next;
  }

  /**
   * @temporal 0
   * @residual 0
   */
  public void setNext(Vertex v)
  {
    next = v;
  }

  /**
   * @temporal 0
   * @residual 0
   */
  public Hashtable neighbors()
  {
    return neighbors;
  }

}
