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
	public Edge(int x, int y, int state, GameBoard parentBoard, int edgeNum){
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
	
	/**
	 * @return the minimum steps to an edge
	 */
	@Override
	public int getDistanceToEdge(int edgeNum){
		int addition = 0;
		if((edgeNum + 5)%6 == this.edgeNum || (edgeNum + 1)%6 == this.edgeNum){
			addition = 1;
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
