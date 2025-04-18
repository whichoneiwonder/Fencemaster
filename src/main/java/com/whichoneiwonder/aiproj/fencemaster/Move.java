package com.whichoneiwonder.aiproj.fencemaster;

public class Move implements Piece {
  public int P;
  public boolean IsSwap;
  public int Row;
  public int Col;

  public Move() {
    P = EMPTY;
    IsSwap = false;
    Row = -1;
    Col = -1;
  }

  public Move(int player, boolean isswap, int r, int c) {
    P = player;
    IsSwap = isswap;
    Row = r;
    Col = c;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Move [P=" + P + ", IsSwap=" + IsSwap + ", Row=" + Row + ", Col=" + Col + "]";
  }
}
