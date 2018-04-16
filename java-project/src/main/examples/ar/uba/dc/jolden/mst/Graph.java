package ar.uba.dc.jolden.mst;


/**
 * A class that represents a graph data structure.
 **/
class Graph
{
  /**
   * List of vertices in the graph.
   **/
  private Vertex[] nodes;

  // parameters for the random number generater
  private final static int CONST_m1 = 10000;
  private final static int CONST_b = 31415821;
  private final static int RANGE = 2048;

  /**
   * Create a graph.
   * @param numvert the number of vertices in the graph
   *
   * ArrayCountSize
   * @temporal 0
   * @residual 2*numvert^2 + 4 * numVert
   *
   *ArrayCountOne
   * @temporal 0
   * @residual 2*numvert^2 + 3 * numVert + 1 : numvert >= 1 ; 1 numvert <= 0
   */
  public Graph(int numvert) 
  {
	nodes = new Vertex[numvert]; //residual = 1  (numvert : numvert >= 1 ArrayCountSize)
    Vertex v = null;
    // the original C code creates them in reverse order 
    for (int i=numvert-1; i>=0; i--) {
      Vertex tmp = nodes[i] = new Vertex(v, numvert); //residual = numvert *3: numvert>=1 (1 del new + 2 del call a vertex) (ArrayCountSize) 
      v = tmp;
    }
    addEdges(numvert); //maxCall = 0 , tempCall = 0, residual = 2*numvert^2 : numvert>=1
  }

  /**
   * Create a graph.  This is just another method for
   * creating the graph data structure. 
   * @param numvert the size of the graph
   *
   *ArrayCountSize
   * @temporal 0
   * @residual 3*numvert^2 + 3 * numVert : numvert >=1
   *
   *ArrayCountOne
   * @temporal 0
   * @residual 2*numvert^2 + 3 * numVert + 1 : numvert >= 1 ; 1 numvert <= 0
   */ 
  public void createGraph(int numvert)
  {
    nodes = new Vertex[numvert];  //residual = 1 (numvert : numvert >= ArrayCountSize)
    Vertex v = null;
    // the original C code creates them in reverse order 
    for (int i=numvert-1; i>=0; i--) {
      Vertex tmp = nodes[i] = new Vertex(v, numvert); //residual = numvert *3: numvert>=1 (1 del new + 2 del call a vertex) (numvert^2 + 2*numVert arrayCountSize)
      v = tmp;
    }

    addEdges(numvert); //maxCall = 0 , tempCall = 0, residual = 2*numvert^2 : numvert>=1 
  }

  /**
   * Return the first node in the graph.
   * @return the first node in the graph.
   * 
   * @temporal 0
   * @residual 0
   */
  public Vertex firstNode()
  {
    return nodes[0];
  }



  //__r1__f__array__f__size se bindea con this_init__f__array__f__size



  /**
   * Add edges to the graph.  Edges are added to/from every node
   * in the graph and a distance is computed for each of them.
   * @param numvert the number of nodes in the graph
   *
   * @temporal 0
   * @residual 2*numvert^2 : numvert>=1
   */
  private void addEdges(int numvert) 
  {
    int count1 = 0;

    for (Vertex tmp = nodes[0]; tmp != null; tmp = tmp.next()) {  //residual =2*numvert^2 : numvert>=1 
      Hashtable hash = tmp.neighbors(); //maxCall = 0 , tempCall= 0 , residual =0 
      for (int i = 0; i < numvert; i++) { //residual =2*numvert : numvert>=1
		if (i != count1) {
		  int dist = computeDist(i, count1, numvert); //maxCall = 0 , tempCall=0  , residual =0 
		  hash.put(nodes[i], new Integer(dist)); //residual =1   (NEW)  
												 //residual =1   (PUT)
		}
      }
      count1++;
    }
  }

  /**
   * Compute the distance between two edges.  A random number generator
   * is used to compute the distance.
   * 
   * @temporal 0
   * @residual 0
   */
  private int computeDist(int i, int j, int numvert) 
  {
    int less, gt;
    if (i < j) {
      less = i; gt = j;
    } else {
      less = j; gt = i;
    }
    return (random(less * numvert + gt) % RANGE) + 1;
  }

  /**
   * @temporal 0
   * @residual 0
   */
  private static int mult(int p, int q) 
  {   
    int p1, p0, q1, q0;

    p1=p/CONST_m1; p0=p%CONST_m1;
    q1=q/CONST_m1; q0=q%CONST_m1;
    return (((p0*q1+p1*q0) % CONST_m1)*CONST_m1+p0*q0);
  }

  /**
   * @temporal 0
   * @residual 0
   */
  private static int random(int seed) 
  {
    return mult(seed, CONST_b) + 1;
  }

} 
