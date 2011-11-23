package ar.uba.dc.jolden.em3d;
import java.util.Enumeration;
import java.util.Random;

/**
 * This class implements nodes (both E- and H-nodes) of the EM graph. Sets
 * up random neighbors and propagates field values among neighbors.
 */
final class Node
{
  /**
   * The value of the node.
   **/
  double value;
  /**
   * The next node in the list.
   **/
  private Node next;
  /**
   * Array of nodes to which we send our value.
   **/
  Node[] toNodes;
  /**
   * Array of nodes from which we receive values.
   **/
  Node[] fromNodes;
  /**
   * Coefficients on the fromNodes edges
   **/
  double[] coeffs;
  /**
   * The number of fromNodes edges
   **/
  int fromCount;
  /**
   * Used to create the fromEdges - keeps track of the number of edges that have
   * been added
   **/
  int fromLength;

  /**
   * A random number generator.
   **/
  private static Random rand;

  /**
   * Initialize the random number generator
   * 
   * @temporal 0
   * @residual 1
   */
  public static void initSeed(long seed)
  {
    rand = new Random(seed);
  }

  /**
   * Constructor for a node with given `degree'.   The value of the
   * node is initialized to a random value.
   * 
   * ArrayCountSize
   * @temporal 0
   * @residual degree : degree >=1
   * 
   * ArrayCountOne
   * @temporal 0
   * @residual 1
   */
  Node(int degree)
  {
    value = rand.nextDouble();
    // create empty array for holding toNodes
    toNodes = new Node[degree]; //residual = 1 (degree : degree >=1 ArrayCountSize)

    next = null;
    fromNodes = null;
    coeffs = null;
    fromCount = 0;
    fromLength = 0;
  }

  /**
   * Create the linked list of E or H nodes.  We create a table which is used
   * later to create links among the nodes.
   * @param size the no. of nodes to create
   * @param degree the out degree of each node
   * @return a table containing all the nodes.
   * 
   * ArrayCountSize
   * @temporal 0
   * @residual (2+degree)*size : degree >=1 && size>=2
   * @residual 1 + degree + size : size==1 && degree >=1 
   * 
   * ArrayCountOne
   * @temporal 0
   * @residual 2*size + 1 : size >= 2 ; 3 : size <= 1
   */
  static Node[] fillTable(int size, int degree)
  {
    Node[] table = new Node[size]; //residual = 1 (size : size>=1 ArrayCountSize)

    Node prevNode = new Node(degree);  //residual = 2 (1 new, 1 call) (degree + 1 : degree >= 2 ; 1: degree<=1 ArrayCountSize) 
    table[0] = prevNode;
    for (int i = 1; i < size; i++) {
      Node curNode = new Node(degree);  //residual = 2*(size - 1) : size >= 2 (mitad new, mitadl call) (Muchos casos, pero da OK ArrayCountSize)
      table[i] = curNode;
      prevNode.next = curNode;
      prevNode = curNode;
    }
    return table;
  }

  /**
   * Create unique `degree' neighbors from the nodes given in nodeTable.
   * We do this by selecting a random node from the give nodeTable to
   * be neighbor. If this neighbor has been previously selected, then
   * a different random neighbor is chosen.
   * @param nodeTable the list of nodes to choose from.
   * 
   * 
   * @temporal 0
   * @residual 0
   */
  void makeUniqueNeighbors(Node[] nodeTable)
  {
    for (int filled = 0; filled < toNodes.length; filled++) {
      int k;
      Node otherNode;

      do {
	// generate a random number in the correct range
	int index = rand.nextInt();
	if (index < 0) index = -index;
	index = index % nodeTable.length;

	// find a node with the random index in the given table
	otherNode = nodeTable[index];

	for (k = 0; k < filled; k++) {
	  if (otherNode == toNodes[filled]) break;
	}
      } while (k < filled);

      // other node is definitely unique among "filled" toNodes
      toNodes[filled] = otherNode;

      // update fromCount for the other node
      otherNode.fromCount++;
    }
  }

  /**
   * Allocate the right number of FromNodes for this node. This
   * step can only happen once we know the right number of from nodes
   * to allocate. Can be done after unique neighbors are created and known.
   *
   * It also initializes random coefficients on the edges.
   * 
   * ArrayCountSize
   * @temporal 0
   * @residual 2*fromCount: fromCount>=1
   * 
   * ArrayCountOne
   * @temporal 0
   * @residual 2
   */
  void makeFromNodes()
  {
    fromNodes = new Node[fromCount]; // nodes fill be filled in later, residual = 1 (fromCount : fromCount >=1 ArrayCountSize)
    coeffs = new double[fromCount]; //residual = 1 (fromCount : fromCount >=1 ArrayCountSize)
  }

  /**
   * Fill in the fromNode field in "other" nodes which are pointed to
   * by this node.
   * 
   * @temporal 0
   * @residual 0
   */
  void updateFromNodes()
  {
    for (int i = 0; i < toNodes.length; i++) {
      Node otherNode = toNodes[i];
      int count = otherNode.fromLength++;
      otherNode.fromNodes[count] = this;
      otherNode.coeffs[count] = rand.nextDouble();
    }
  }

  /**
   * Get the new value of the current node based on its neighboring
   * from_nodes and coefficients.
   * 
   * @temporal 0
   * @residual 0
   */
  void computeNewValue()
  {
    for (int i = 0; i < fromCount; i++) {
      value -= coeffs[i] * fromNodes[i].value;
    }
  }

  /**
   * Return an enumeration of the nodes.
   * @return an enumeration of the nodes.
   * 
   * @temporal 0
   * @residual 1
   */
  @SuppressWarnings("unchecked")
Enumeration elements()
  {
    // a local class that implements the enumeration
    class Enumerate implements Enumeration {
      private Node current;
      public Enumerate() { this.current = Node.this; } //temporal = 0, residual = 0
      public boolean hasMoreElements() { return (current != null); } //temporal = 0, residual = 0
      public Object nextElement() { //temporal = 0, residual = 0
	Object retval = current;
	current = current.next;
	return retval;
      }
    }
    return new Enumerate();
  }

  /**
   * Override the toString method to return the value of the node.
   * @return the value of the node.
   * 
   * ESTA DANDO
   * @temporal 4 
   * @residual 3
   *  
   * DEBERIA DAR
   * @temporal 3 
   * @residual 0
   * 
   */
  public String toString()
  {
    return "value " + value + ", from_count " + fromCount;
  }

}
