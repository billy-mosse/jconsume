package ar.uba.dc.jolden.mst;

/**
 * A Java implementation of the <tt>mst</tt> Olden benchmark.  The Olden
 * benchmark computes the minimum spanning tree of a graph using
 * Bentley's algorithm.
 * <p><cite>
 * J. Bentley. "A Parallel Algorithm for Constructing Minimum Spanning Trees"
 * J. of Algorithms, 1:51-59, 1980.
 * </cite>
 * <p>
 * As with the original C version, this one uses its own implementation
 * of hashtable.
 **/
public class MST
{
  /**
   * The number of vertices in the graph.
   **/
  private static int vertices = 0;
  /**
   * Set to true to print the final result.
   **/
  private static boolean printResult = false;
  /**
   * Set to true to print information messages and timing values
   **/
  private static boolean printMsgs = false;

  /**
   * ArrayCountSize
   * @temporal 2*numVert + 7 : numvert >= 1 ; 6 numvert <= 0
   * @residual 3*numvert^2 + 2 * numVert +1 : numvert >= 1; 1 : numvert <= 0
   */
  public static void main(String args[])
  {
  	mainOrig(args);
  }
  
  /**
   * 
   * ArrayCountSize
   * @temporal 2*numVert + 7 : numvert >= 1 ; 6 numvert <= 0
   * @residual 3*numvert^2 + 2 * numVert +1 : numvert >= 1; 1 : numvert <= 0
   */
  public static void mainOrig(String args[])
  {
      	parseCmdLine(args);
      	mainParameters(vertices,printMsgs, printResult);
  }
  
  /**
   * (esto creo ya no vale mas)Aca somos "peores" q martin xq en realidad somos mas finos. Captura cosas q no deberia el.
   * ArrayCountSize
   * @temporal 2*numVert + 6 : numvert >= 1 ; 6 numvert <= 0
   * @residual 3*numvert^2 + 2 * numVert : numvert >= 1
   * 
   * ArrayCountOne
   * @temporal numVert + 7 : numvert >= 1 ; 7 numvert <= 0
   * @residual 2*numvert^2 + 3 * numVert : numvert >= 1 
   */
  public static void mainParameters(int pVertices, boolean pPrintMsgs, boolean pPrintResult)
  {
	  		if (pPrintMsgs)
	  			System.out.println("Making graph of size " + pVertices); //tempLocal = 1
		    
		    long start0 = System.currentTimeMillis();
		    Graph graph = new Graph(vertices); //tempLocal = 1, tempCall = 2*numvert^2 + 3 * numVert + 1
		    long end0 = System.currentTimeMillis();
		      
		    if (pPrintMsgs)
		      System.out.println("About to compute MST");
		    long start1 = System.currentTimeMillis();
		    int dist = computeMST(graph, pVertices); //maxCall = numvert
		    long end1 = System.currentTimeMillis();
		    
		    if (pPrintResult || pPrintMsgs)
		    	System.out.println("MST has cost "+ dist); //tempLocal = 1
		
		    if (pPrintMsgs) {
		    	System.out.println("Build graph time "+ (end0 - start0)/1000.0); //tempLocal = 1
		    	System.out.println("Compute time " + (end1 - start1)/1000.0); //tempLocal = 1
		    	System.out.println("Total time " + (end1 - start0)/1000.0); //tempLocal = 1
		    }
		
		    System.out.println("Done!");
}

  /**
   * The method to compute the minimum spanning tree
   * @param graph the graph data structure 
   * @param numvert the number of vertices in the graph
   * @return the minimum spanning tree cost
   * 
   * @temporal numvert : numvert>=1
   * @residual 0
   */
  static int computeMST(Graph graph, int numvert)
  {
    int cost=0;

    // Insert first node
    Vertex inserted = graph.firstNode(); //maxCall = 0, tempCall = 0, residual = 0
    Vertex tmp = inserted.next(); //maxCall = 0, tempCall = 0, residual = 0
    MyVertexList = tmp; 
    numvert--;

    // Annonunce insertion and find next one
    while (numvert != 0) {
      //System.out.println("numvert= " +numvert);
      BlueReturn br = doAllBlueRule(inserted); //tempCall = numvert : numvert>=1
      inserted = br.vert(); //maxCall = 0, tempCall = 0, residual = 0
      int dist = br.dist(); //maxCall = 0, tempCall = 0, residual = 0
      numvert--;
      cost += dist;
    }
    return cost;
  }

  /**
   * 
   * @temporal 0
   * @residual 1
   */
  private static BlueReturn BlueRule(Vertex inserted, Vertex vlist)
  {
    BlueReturn retval = new BlueReturn(); //residual = 1

    if (vlist == null) {
      retval.setDist(999999); //maxCall = 0, tempCall = 0, residual = 0
      return retval;
    }

    Vertex prev = vlist;
    retval.setVert(vlist); //maxCall = 0, tempCall = 0, residual = 0
    retval.setDist(vlist.mindist()); //maxCall = 0, tempCall = 0, residual = 0
    Hashtable hash = vlist.neighbors(); //maxCall = 0, tempCall = 0, residual = 0
    Object o = hash.get(inserted); //maxCall = 0, tempCall = 0, residual = 0
    if (o != null) {
      int dist = ((Integer)o).intValue(); //maxCall = 0, tempCall = 0, residual = 0
      if (dist < retval.dist()) {
	vlist.setMindist(dist); //maxCall = 0, tempCall = 0, residual = 0
	retval.setDist(dist); //maxCall = 0, tempCall = 0, residual = 0
      }
    } else 
      System.out.println("Not found"); //residual = 0
    
    int count = 0;
    // We are guaranteed that inserted is not first in list
    for (Vertex tmp = vlist.next(); tmp != null; prev = tmp, tmp = tmp.next()) { //maxCall = 0, tempCall = 0, residual = 0
      count++;
      if (tmp == inserted) {
	Vertex next = tmp.next(); //maxCall = 0, tempCall = 0, residual = 0
	prev.setNext(next); //maxCall = 0, tempCall = 0, residual = 0
      }	else {
	hash = tmp.neighbors(); //maxCall = 0, tempCall = 0, residual = 0
	int dist2 = tmp.mindist(); //maxCall = 0, tempCall = 0, residual = 0
	o = hash.get(inserted); //maxCall = 0, tempCall = 0, residual = 0
	if (o != null) {
	  int dist = ((Integer)o).intValue(); //maxCall = 0, tempCall = 0, residual = 0
	  if (dist < dist2) {
	    tmp.setMindist(dist); //maxCall = 0, tempCall = 0, residual = 0
	    dist2 = dist;
	  }
	} else 
	 // System.out.println("Not found");

	if (dist2 < retval.dist()) {
	  retval.setVert(tmp); //maxCall = 0, tempCall = 0, residual = 0
	  retval.setDist(dist2); //maxCall = 0, tempCall = 0, residual = 0
	}
      } // else
    } // for
    return retval;
  }
	
  private static Vertex MyVertexList = null;

  /**
   * (Confirmar)
   * @temporal 0
   * @residual 1
   */
  private static BlueReturn doAllBlueRule(Vertex inserted)
  {
    if (inserted == MyVertexList)
      MyVertexList = MyVertexList.next(); //maxCall = 0, tempCall = 0, residual = 0 
    BlueReturn b = BlueRule(inserted, MyVertexList); //maxCall = 0, tempCall = 0, residual = 1 
    return b;
  }

  /**
   * Parse the command line options.
   * @param args the command line options.
   * 
   * @temporal 1
   * @residual 1
   */
  private static final void parseCmdLine(String args[])
  {
    int i = 0;
    String arg;

    while (i < args.length && args[i].startsWith("-")) {
      arg = args[i++];

      if (arg.equals("-v")) {
	if (i < args.length) {
	  vertices = new Integer(args[i++]).intValue(); //residual = 1 (esto se termina correindo 1 vez, es 1 caso del while nomas)
	} else throw new RuntimeException("-v requires the number of vertices"); //residual = 1 (esto se termina correindo 1 vez, es 1 caso del while nomas)
      } else if (arg.equals("-p")) {
	printResult = true;
      } else if (arg.equals("-m")) {
	printMsgs = true;
      } else if (arg.equals("-h")) {
	usage(); //maxCall = 0, tempCall = 0, residual = 0
      }
    }
    if (vertices == 0) usage(); //maxCall = 0, tempCall = 0, residual = 0 
  }

  /**
   * The usage routine which describes the program options.
   * 
   * @temporal 0
   * @residual 0
   */
  private static final void usage()
  {
    System.err.println("usage: java MST -v <levels> [-p] [-m] [-h]");
    System.err.println("    -v the number of vertices in the graph");
    System.err.println("    -p (print the result>)");
    System.err.println("    -m (print informative messages)");
    System.err.println("    -h (this message)");
    System.exit(0);
  }

}

/**
 * Not really sure what this is for?
 **/
class BlueReturn
{
  private Vertex vert;
  private int dist;
  
  /**
   * @temporal 0
   * @residual 0
   */
  public Vertex vert()
    {
      return vert;
    }
  
  /**
   * @temporal 0
   * @residual 0
   */
  public void setVert(Vertex v)
    {
      vert = v;
    }

  /**
   * @temporal 0
   * @residual 0
   */
  public int dist()
    {
      return dist;
    }

  /**
   * @temporal 0
   * @residual 0
   */
  public void setDist(int d)
    {
      dist = d;
    }

}
