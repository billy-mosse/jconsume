package ar.uba.dc.jolden.em3d;

/**
 * Java implementation of the <tt>em3d</tt> Olden benchmark.  This Olden
 * benchmark models the propagation of electromagnetic waves through
 * objects in 3 dimensions. It is a simple computation on an irregular
 * bipartite graph containing nodes representing electric and magnetic
 * field values.
 *
 * <p><cite>
 * D. Culler, A. Dusseau, S. Goldstein, A. Krishnamurthy, S. Lumetta, T. von
 * Eicken and K. Yelick. "Parallel Programming in Split-C".  Supercomputing
 * 1993, pages 262-273.
 * </cite>
 **/
public class Em3d
{
  /**
   * The number of nodes (E and H)
   **/
  private static int numNodes = 0;
  /**
   * The out-degree of each node.
   **/
  private static int numDegree = 0;
  /**
   * The number of compute iterations
   **/
  private static int numIter = 1;
  /**
   * Should we print the results and other runtime messages
   **/
  private static boolean printResult = false;
  /**
   * Print information messages?
   **/
  private static boolean printMsgs = false;

  /**
   * The main roitine that creates the irregular, linked data structure
   * that represents the electric and magnetic fields and propagates the
   * waves through the graph.
   * @param args the command line arguments
   **/
  public static final void main(String args[])
  {
  	mainOrig(args);
  }
  
  /**
   * 
   * @temporal 0
   * @residual 0
   */
  public static final void mainOrig(String args[])
  {
  	parseCmdLine(args);
  	mainParameters(numNodes, numDegree, printMsgs, printResult);
  }
  
  /**
   * 
   * ArrayCountSize - Add
   * @temporal 13 + (4+2*numDegree)*numNodes + 4*numNodes^2 : numDegree >= 1 && numNodes >= 2 && numIter >=1
   * @temporal 11 + (4+2*numDegree)*numNodes + 4*numNodes^2 : numDegree >= 1 && numNodes >= 2 && numIter <=0
   * @temporal 15 + 2*numDegree + 2*numNodes + 4*numNodes^2 : numDegree >= 1 && numNodes == 1 && numIter >=1
   * @temporal 13 + 2*numDegree + 2*numNodes + 4*numNodes^2 : numDegree >= 1 && numNodes == 1 && numIter <=0
   * @temporal 15 + 2*numDegree : numDegree >= 1 && numNodes <=0 && numIter >=1
   * @temporal 13 + 2*numDegree : numDegree >= 1 && numNodes <=0 && numIter <=0
   * @temporal 13 + 4*numNodes + 4*numNodes^2 : numDegree <= 0 && numNodes >=2 && numIter >=1
   * @temporal 11 + 4*numNodes + 4*numNodes^2 : numDegree <= 0 && numNodes >=2 && numIter <=0
   * @temporal 15 + 2*numNodes + 4*numNodes^2 : numDegree <= 0 && numNodes ==1 && numIter >=1
   * @temporal 13 + 2*numNodes + 4*numNodes^2 : numDegree <= 0 && numNodes ==1 && numIter <=0
   * @temporal 15 : numDegree <= 0 && numNodes <=0 && numIter >=1
   * @temporal 13 : numDegree <= 0 && numNodes <=0 && numIter <=0
   * @residual 1
   * 
   * ArrayCountSize - Lazy
   * @temporal 11 + (4+2*numDegree)*numNodes + 4*numNodes^2 : numDegree >= 1 && numNodes >= 2 && numIter >=1
   * @temporal 17 + 2*numDegree + 2*numNodes : numDegree >= 1 && numNodes == 1 && numIter >=1
   * @residual 1 : numDegree >= 1 && numNodes >= 1 && numIter >=1 
   *  
   * ArrayCountOne - Add
   * @temporal 15 + 8*numNodes : numNodes >= 2 && numIter >=1
   * @temporal 13 + 8*numNodes : numNodes >= 2 && numIter <=0
   * @temporal 19 + 4*numNodes : numNodes == 1 && numIter >=1
   * @temporal 17 + 4*numNodes : numNodes == 1 && numIter <=0
   * @temporal 19 : numNodes <= 0 && numIter >=1
   * @temporal 17 : numNodes <= 0 && numIter <=1
   * @residual 1
   * 
   * ArrayCountOne - Lazy
   * @temporal 13 + 8*numNodes : numNodes >= 2 && numIter >=1
   * @temporal 13 + 8*numNodes : numNodes >= 2 && numIter <=0
   * @temporal 17 + 4*numNodes : numNodes == 1 && numIter >=1
   * @temporal 17 + 4*numNodes : numNodes == 1 && numIter <=0
   * @temporal 17 : numNodes <= 0 && numIter >=1
   * @temporal 17 : numNodes <= 0 && numIter <=1
   * @residual 1
   */
  public static final void mainParameters(int numNodes, int numDegree, boolean printMsgs, boolean printResult)
  {
    if (printMsgs)
      System.out.println("Initializing em3d random graph...");
    long start0 = System.currentTimeMillis();
    BiGraph graph = BiGraph.create(numNodes, numDegree, printResult); //tempCall = 8 * numNodes  + 1 : numNodes>=2 ; 5 + 4*numNodes : numNodes ==1  ; 6 : numNodes <= 0, maxCall =8, residual =1

    long end0 = System.currentTimeMillis();

    // compute a single iteration of electro-magnetic propagation
    ///* Comentado para ahorrar trabajo a daikon
    if (printMsgs)
      System.out.println("Propagating field values for " + numIter +
			 " iteration(s)..."); //tempLocal = 1
    long start1 = System.currentTimeMillis();
    for (int i = 0; i < numIter; i++) { 
      graph.compute(numNodes);  //tempCall = 0, maxCall =2(condominio) , residual =0 
    }
    long end1 = System.currentTimeMillis();

    // print current field values
    if (printResult)
      System.out.println(graph); 

    if (printMsgs) {
      System.out.println("EM3D build time " + (end0 - start0)/1000.0); //tempLocal = 1
      System.out.println("EM3D compute time " + (end1 - start1)/1000.0); //tempLocal = 1
      System.out.println("EM3D total time " + (end1 - start0)/1000.0); //tempLocal = 1
    }
    //*/
    System.out.println("Done!");

  	
  }


  /**
   * Parse the command line options.
   * @param args the command line options.
   * 
   * @temporal 3  
   * @residual 3
   */ 
  private static final void parseCmdLine(String args[])
  {
    int i = 0;
    String arg;

    while (i < args.length && args[i].startsWith("-")) {
      arg = args[i++];

      // check for options that require arguments
      if (arg.equals("-n")) {
        if (i < args.length) {
          numNodes = new Integer(args[i++]).intValue(); //tempLocal = 1
        } else throw new Error("-n requires the number of nodes"); //residual = 1
      } else if (arg.equals("-d")) {
	if (i < args.length) {
	  numDegree = new Integer(args[i++]).intValue();  //tempLocal = 1
	} else throw new Error("-d requires the out degree"); //residual = 1
      } else if (arg.equals("-i")) {
	if (i < args.length) {
	  numIter = new Integer(args[i++]).intValue();  //tempLocal = 1
	} else throw new Error("-i requires the number of iterations");  //residual = 1
      } else if (arg.equals("-p")) {
        printResult = true;
      } else if (arg.equals("-m")) {
        printMsgs = true;
      } else if (arg.equals("-h")) {
	usage();  //maxCall = 7 (Deberia dar 0 cuando cambioe el prinln)
      }
    }
    if (numNodes == 0 || numDegree == 0) usage();  //maxCall = 7 (Deberia dar 0 cuando cambioe el prinln)
  }

  /**
   * The usage routine which describes the program options.
   * 
   * @temporal 0 
   * @residual 0
   */
  private static  final void usage()
  {
    System.out.println("usage: java Em3d -n <nodes> -d <degree> [-p] [-m] [-h]");
    System.out.println("    -n the number of nodes");
    System.out.println("    -d the out-degree of each node");
    System.out.println("    -i the number of iterations");
    System.out.println("    -p (print detailed results)");
    System.out.println("    -m (print informative messages)");
    System.out.println("    -h (this message)");
    System.exit(0);
  }

}
