package aiproj.fencemaster.avnishj;
/** Class for the Corner Cell which is a sub-class
 * of Cell. Inherits all attributes and methods
 */
public class Corner extends Cell {
	

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
	public Corner(int x, int y, String state, GameBoard parentBoard, int edgeNum){
		super(x, y, state, parentBoard);
		
		/*As Cell is a corner piece, 3 of its edges will be
		  null. We can set the respective links with the corresponding
		  edge side of the cell to null by knowing which side of
		  the board we are on, denoted by edgeNum */
		setLink(edgeNum,null);
		setLink((edgeNum+1)%6,null);
		setLink((edgeNum+5)%6,null);
	}

	/**
	 * @return the type of Cell it is
	 */
	@Override
	public String toString() {
		return "Corner";
	}
	
	
}

