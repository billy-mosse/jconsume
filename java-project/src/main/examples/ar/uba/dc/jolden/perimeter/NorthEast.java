package ar.uba.dc.jolden.perimeter;

/**
 * A class that represents the North East quadrant of the image.
 **/
class NorthEast implements Quadrant
{

  /**
   * Return true iff this quadrant is adjacent to the boundary
   * of an image in the given direction.
   * @param direction the image boundary
   * @return true if the quadrant is adjacent, false otherwise.
   **/
  public boolean adjacent(int direction)
  {
    return (direction == QuadTreeNode.NORTH || direction == QuadTreeNode.EAST);
  }

  /**
   * Return the quadrant of a block of equal size that is
   * adjacent to the given side of this quadrant.
   * @param direction the image boundary
   * @return the reflected quadrant
   **/
  public Quadrant reflect(int direction)
  {
    if (direction == QuadTreeNode.WEST || direction == QuadTreeNode.EAST) {
      return cNorthWest;
    }
    return cSouthEast;
  }

  /**
   * Return the child that represents this quadrant of the given
   * node.
   * @param node the node that we want the child from.
   * @return the child node representing this quadrant
   **/
  public QuadTreeNode child(QuadTreeNode node)
  {
    return node.getNorthEast();
  }

}

