package ar.uba.dc.jolden.bisort;

/**
 * A Java implementation of the <tt>bisort</tt> Olden benchmark.  The Olden
 * benchmark implements a Bitonic Sort as described in :
 * <p><cite>
 * G. Bilardi and A. Nicolau, "Adaptive Bitonic Sorting: An optimal parallel
 * algorithm for shared-memory machines." SIAM J. Comput. 18(2):216-228, 1998.
 * </cite>
 * <p>
 * The benchmarks sorts N numbers where N is a power of 2.  If the user provides
 * an input value that is not a power of 2, then we use the nearest power of
 * 2 value that is less than the input value.
 **/
public class BiSort
{
  /**
   * The number of values to sort.
   **/
  private static int size = 0;

  /**
   * Print information messages
   **/
  private static boolean printMsgs = false;
  /**
   * Print the tree after each step
   **/
  private static boolean printResults = false;

  /**
   * The main routine which creates a tree and sorts it a couple of times.
   * @param args the command line arguments
   **/
  public static void main(String args[])
  {
  	mainOrig(args);
  }
  
  public static void mainOrig(String args[])
  {
	  parseCmdLine(args);
	  mainParameters(size, printMsgs, printResults);
  }

  /**
   * 
   * ???
   * @temporal 0
   * @residual 0
   */
  public static final void mainParameters(int size, boolean printMsgs, boolean printResults)
  {
   

    if (printMsgs)
      System.out.println("Bisort with " + size + " values");

    long start2 = System.currentTimeMillis();
    Value tree = Value.createTree(size, 12345768);  //maxCall =0 , tempCall =s-1 : s>1 
    long end2 = System.currentTimeMillis();
    int sval = Value.random(245867) % Value.RANGE; //maxCall = 0, tempCall = 0

    if (printResults) {
      tree.inOrder();  //maxCall =0, tempCall =0 
      System.out.println(sval);
    }

    if (printMsgs)
      System.out.println("BEGINNING BITONIC SORT ALGORITHM HERE");

    long start0 = System.currentTimeMillis();
    sval = tree.bisort(sval, Value.FORWARD);  //maxCall =0, tempCall =0 
    long end0 = System.currentTimeMillis();

    if (printResults) {
      tree.inOrder();  //maxCall = 0, tempCall = 0
      System.out.println(sval);
    }

    long start1 = System.currentTimeMillis();
    sval = tree.bisort(sval, Value.BACKWARD);  //maxCall =0, tempCall =0
    long end1 = System.currentTimeMillis();

    if (printResults) {
      tree.inOrder();  //maxCall =0, tempCall =0
      System.out.println(sval);
    }

    if (printMsgs) {
      System.out.println("Creation time: " + (end2 - start2)/1000.0);
      System.out.println("Time to sort forward = " + (end0 - start0)/1000.0);
      System.out.println("Time to sort backward = " + (end1 - start1)/1000.0);
      System.out.println("Total: " + (end1 - start0)/1000.0);
    }
    System.out.println("Done!");
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

      // check for options that require arguments
      if (arg.equals("-s")) {
	if (i < args.length) {
	  size = new Integer(args[i++]).intValue(); //temporal = 1
	} else {
	  throw new Error("-l requires the number of levels"); //residual = 1
	}
      } else if (arg.equals("-m")) {
	printMsgs = true;
      } else if (arg.equals("-p")) {
	printResults = true;
      } else if (arg.equals("-h")) {
	usage(); //temporal  = 0, residual = 0
      }
    }
    if (size == 0) usage(); //temporal  = 0, residual = 0
  }

  /**
   * The usage routine which describes the program options.
   * 
   * @temporal 0
   * @residual 0
   */
  private static final void usage()
  {
    System.err.println("usage: java BiSort -s <size> [-p] [-i] [-h]");
    System.err.println("    -s the number of values to sort");
    System.err.println("    -m (print informative messages)");
    System.err.println("    -p (print the binary tree after each step)");
    System.err.println("    -h (print this message)");
    System.exit(0);
  }

}
