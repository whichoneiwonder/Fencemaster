package aiproj.fencemaster.avnishj;

/** Super class for cells, which has attributes and
 * methods for a Center type cell
 */
public class Cell {
	//Row Position on Board
	protected int row;
	
	//Column Position on Board
	protected int col;
	
	//Array of Cells this cell is connected to
	protected Cell [] links = new Cell[6];
	
	//State of the Cell
	protected String state;
	
	protected GameBoard parentBoard;

	/** Constructor which sets the location and state
	 *  of the cell based on the inputs
	 *  
	 *  @param x = Row Location
	 * @param y = Col Location
	 * @param state = String value of what state Cell is in
	 * @param parentBoard TODO
	 */
	public Cell(int x, int y, String state, GameBoard parentBoard){
		setRow(x);
		setCol(y);
		setState(state);
		setParentBoard(parentBoard);
	}
	
	
	
	/**
	 * @return the parentBoard
	 */
	protected GameBoard getParentBoard() {
		return parentBoard;
	}



	/**
	 * @param parentBoard the parentBoard to set
	 */
	protected void setParentBoard(GameBoard parentBoard) {
		this.parentBoard = parentBoard;
	}



	/**
	 * @return the row
	 */
	protected int getRow() {
		return row;
	}

	/**
	 * @param xpos the row to set
	 */
	protected void setRow(int xpos) {
		row = xpos;
	}

	/**
	 * @return the col
	 */
	protected int getCol() {
		return col;
	}

	/**
	 * @param ypos the col to set
	 */
	protected void setCol(int ypos) {
		col = ypos;
	}

	
	/**
	 * @return the array of all links
	 */
	protected Cell[] getAllLinks() {
		return links;
	}
	/**
	 * @param sets the newLink Cell to the edge number
	 * denoted by the index
	 */
	protected void setLink(int index, Cell newLink ){
		this.links[index] = newLink;
	}
	
	/**
	 * @return the Cell of a specific link
	 */
	protected Cell getLink(int index){
		return this.links[index];
	}
	/**
	 * @return the state
	 */
	protected String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	protected void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the type of Cell it is
	 */
	@Override
	public String toString() {
		return "Center";
	}
	
	/**
	 * Creates the links of the cell with the indices
	 * being the respective edge number and the value
	 * being the Cell reference
	 */
	public void createLinks(){
		links[0] = parentBoard.getCell(row-1, col-1);
		links[1] = parentBoard.getCell(row-1, col  );
		links[2] = parentBoard.getCell(row  , col+1);
		links[3] = parentBoard.getCell(row+1, col+1);
		links[4] = parentBoard.getCell(row+1, col  );
		links[5] = parentBoard.getCell(row  , col-1);
	}
	
	public int getDistanceToEdge(int edgeNum){
		switch(edgeNum){
			case 0:
				return this.row;
			case 1:
				return parentBoard.getDimension() - 1 + this.row - this.col;
			case 2:
				return (2*parentBoard.getDimension() - 2) - this.col;
			case 3:
				return (2*parentBoard.getDimension() - 2) - this.row;
			case 4:
				return (parentBoard.getDimension() - 1) + this.col - this.row;
			case 5:
				return this.col;
		}
		return 0;
		
	}
	
	
}
