package aiproj.fencemaster.avnishj;
/** Class for the Edge Cell which is a sub-class
 * of Cell. Inherits all attributes and methods
 */
public class Edge extends Cell {
	
	//Edge Number with respect to the board
	private int edgeNum;
	
	/** Constructor which sets the location and state
	 *  of the Edge cell based on the inputs
	 *  
	 *  @param x = Row Location
	 * @param y = Col Location
	 * @param state = String value of what state Cell is in
	 * @param parentBoard 
	 * @param edgeNum = edgeNumber of the board
	 *  			
	 */
	public Edge(int x, int y, String state, GameBoard parentBoard, int edgeNum){
		super( x, y, state, parentBoard);
		setEdgeNum(edgeNum);
		
		//Appends the cell to the static ArrayList edgeList
		//Assists in the searching of Tripods
		parentBoard.edgeList.add(this);
		
	}

	/**
	 * @return the edgeNum
	 */
	protected int getEdgeNum() {
		return edgeNum;
	}

	/**
	 * @param edgeNum the edgeNum to set
	 */
	protected void setEdgeNum(int edgeNum) {
		this.edgeNum = edgeNum;
	}
	/**
	 * @return the type of Cell it is
	 */
	@Override
	public String toString() {
		return "Edge";
	}
	
	
}
