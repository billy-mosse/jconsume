package ar.uba.dc.jolden.bisort;

/**
 * A class that represents a value to be sorted by the <tt>BiSort</tt>
 * algorithm.  We represents a values as a node in a binary tree.
 **/
class Value
{
  private int value;
  private Value left;
  private Value right;

  static final boolean FORWARD = false;
  static final boolean BACKWARD = true;

  // These are used by the Olden benchmark random no. generator
  private static final int CONST_m1 = 10000;
  private static final int CONST_b = 31415821;
  static final int RANGE = 100;

  /**
   * Constructor for a node representing a value in the bitonic sort tree.
   * @param v the integer value which is the sort key
   * 
   * @temporal 0
   * @residual 0
   */
  Value(int v)
  {
    value = v;
    left = right = null;
  }

  /**
   * Create a random tree of value to be sorted using the bitonic sorting algorithm.
   *
   * @param size the number of values to create.
   * @param seed a random number generator seed value
   * @return the root of the (sub) tree.
   * 
   * DEberia especificar a mano el create y decir q da (s/2) - 1
   * @temporal 0
   * @residual s-1 : s > 1 
   */
    
  static Value createTree(int tree_size, int seed) {
    if (tree_size > 1) {
      seed = random(seed);
      int next_val = seed % RANGE;

      Value retval = new Value(next_val); //residual = 1 
      retval.left = createTree(tree_size/2, seed); //residual = (s/2)-1 : s > 1
      retval.right = createTree(tree_size/2, skiprand(seed, tree_size+1)); //residual = (s/2)-1 : s > 1
      return retval;
    } else {
      return null;
    }
  }

  /**
   * Perform a bitonic sort based upon the Bilardi and Nicolau algorithm.
   *
   * @param spr_val the "spare" value in the algorithm.
   * @param direction the direction of the sort (forward or backward)
   * @return the new "spare" value.
   * 
   * DEBERIA ESPECIFICAR A mano el BiSOrt y decir q tiene 0 de residual
   * @temporal 0
   * @residual 0
   */
  int bisort(int spr_val, boolean direction)
  {
    if (left == null) {
      if ((value > spr_val) ^ direction) {
	int tmpval = spr_val;
	spr_val = value;
	value = tmpval;
      }
    } else {
      int val = value;
      value = left.bisort(val, direction); //maxCall = 0, tempCall = 0, residual = 0
      boolean ndir = !direction;
      spr_val = right.bisort(spr_val, ndir); //maxCall = 0, tempCall = 0, residual = 0
      spr_val = bimerge(spr_val, direction); //maxCall = 0, tempCall = 0, residual = 0
    }
    return spr_val;
  }

  /**
   * Perform the merge part of the bitonic sort.  The merge part does
   * the actualy sorting.
   * @param spr_val the "spare" value in the algorithm.
   * @param direction the direction of the sort (forward or backward)
   * @return the new "spare" value
   * 
   * DEBERIA ESPECIFICAR a mano eL BiMerge y decir q tiene 0 de reisudla y demas
   * @temporal 0
   * @residual 0
   */
  int bimerge(int spr_val, boolean direction)
  {
    int   rv = value;
    Value pl = left;
    Value pr = right;

    boolean rightexchange = (rv > spr_val) ^ direction;
    if (rightexchange) {
      value = spr_val;
      spr_val = rv;
    }

    while (pl != null) {
      int   lv  = pl.value;
      Value pll = pl.left;
      Value plr = pl.right;
      rv        = pr.value;
      Value prl = pr.left;
      Value prr = pr.right;

      boolean elementexchange = (lv > rv) ^ direction;
      if (rightexchange) {
	if (elementexchange) {
	  pl.swapValRight(pr); //maxCall = 0, tempCall = 0, residual = 0
	  pl = pll;
	  pr = prl;
	} else {
	  pl = plr;
	  pr = prr;
	}
      } else {
	if (elementexchange) {
	  pl.swapValLeft(pr);  //maxCall = 0, tempCall = 0, residual = 0
	  pl = plr;
	  pr = prr;
	} else {
	  pl = pll;
	  pr = prl;
	}
      }
    }

    if (left != null) {
      value = left.bimerge(value, direction);  //maxCall = 0, tempCall = 0, residual = 0
      spr_val = right.bimerge(spr_val, direction);  //maxCall = 0, tempCall = 0, residual = 0
    }
    return spr_val;
  }

  /**
   * Swap the values and the right subtrees.
   * @param n the other subtree involved in the swap.
   * 
   * @temporal 0
   * @residual 0
   */
  void swapValRight(Value n)
  {
    int   tmpv = n.value;
    Value tmpr = n.right;

    n.value = value;
    n.right = right;

    value = tmpv;
    right = tmpr;
  }

  /**
   * Swap the values and the left subtrees.
   * @param n the other subtree involved in the swap.
   * @temporal 0
   * @residual 0
   */ 
  void swapValLeft(Value n)
  {
    int   tmpv = n.value;
    Value tmpl = n.left;

    n.value = value;
    n.left  = left;

    value = tmpv;
    left  = tmpl;
  }

  /**
   * Print out the nodes in the binary tree in infix order.
   * 
   * DEBERIA ESPECIFICARLO a MAno Y DECIR Q INORDER TIENE RESIDUAL 0 Y listo
   * @temporal 0
   * @residual 0
   */
  void inOrder()
  {
    if (left != null)
      left.inOrder();   //maxCall = 0, tempCall = 0, residual = 0
    System.out.println(value + " " + hashCode()); //maxCall = 0, tempCall = 0, residual = 0
    if (right != null)
      right.inOrder();  //maxCall = 0, tempCall = 0, residual = 0
  }


  /**
   * A random generator.  The original Olden benchmark uses its
   * own random generator.  We use the same one in the Java version.
   * @return the next random number in the sequence.
   * 
   * @temporal 0
   * @residual 0
   */
  private static int mult(int p, int q)
  {
    int p1 = p/CONST_m1;
    int p0 = p%CONST_m1;
    int q1 = q/CONST_m1;
    int q0 = q%CONST_m1;
    return (((p0*q1+p1*q0) % CONST_m1) * CONST_m1+p0*q0);
  }

  /**
   * A routine to skip the next <i>n</i> random numbers.
   * @param seed the current random no. seed
   * @param n the number of numbers to skip
   * 
   * @temporal 0
   * @residual 0
   */
  private static int skiprand(int seed, int n)
  {
    for (; n != 0; n--) seed = random(seed);
    return seed;
  }

  /**
   * Return a random number based upon the seed value.
   * @param seed the random number seed value
   * @return a random number based upon the seed value.
   * 
   * @temporal 0
   * @residual 0
   */
  static int random(int seed)
  {
    int tmp = mult(seed, CONST_b) + 1;
    return tmp;
  }
}

