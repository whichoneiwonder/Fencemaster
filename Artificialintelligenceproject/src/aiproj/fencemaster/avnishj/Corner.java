package aiproj.fencemaster.avnishj;
/** Class for the Corner Cell which is a sub-class
 * of Cell. Inherits all attributes and methods
 */
public class Corner extends Cell {
	
	private int cornerNum;

	/** Constructor which sets the location and state
	 *  of the Corner cell based on the inputs
	 *  
	 *  @param x = Row Location
	 * @param y = Col Location
	 * @param state = String value of what state Cell is in
	 * @param parentBoard TODO
	 * @param edgeNum = edgeNumber of the board
	 *  			
	 */
	public Corner(int x, int y, int state, GameBoard parentBoard, int cornerNumber){
		super(x, y, state, parentBoard);
		
		/*As Cell is a corner piece, 3 of its edges will be
		  null. We can set the respective links with the corresponding
		  edge side of the cell to null by knowing which side of
		  the board we are on, denoted by edgeNum */
		cornerNum = cornerNumber;
		setLink(cornerNum,null);
		setLink((cornerNum+1)%6,null);
		setLink((cornerNum+5)%6,null);
	}

	/**
	 * @return the type of Cell it is
	 */
	@Override
	public String toString() {
		return "Corner";
	}
	
	
	/**
	 * @return the minimum steps to an edge
	 */
	@Override
	public int getDistanceToEdge(int edgeNum){
		int addition = 1;
		if((edgeNum + 3)%6 == this.cornerNum || (edgeNum + 4)%6 == this.cornerNum){
			addition = 0;
		}
		int returnValue=0; 
		switch(edgeNum){
			case 0:
				returnValue = this.getRow();
			case 1:
				returnValue = parentBoard.getDimension() - 1 + this.getRow() - this.getCol();
			case 2:
				returnValue = (2*parentBoard.getDimension() - 2) - this.getCol();
			case 3:
				returnValue = (2*parentBoard.getDimension() - 2) - this.getRow();
			case 4:
				returnValue = (parentBoard.getDimension() - 1) + this.getCol() - this.getRow();
			case 5:
				returnValue = this.col;
		}
		return returnValue + addition;
		
	}
	
	
}

