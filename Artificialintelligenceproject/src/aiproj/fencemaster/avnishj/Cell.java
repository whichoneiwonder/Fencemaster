package aiproj.fencemaster.avnishj;

/** Super class for cells, which has attributes and
 * methods for a Center type cell
 */
public class Cell {
	//Row Position on Board
	private int row;
	
	//Column Position on Board
	private int col;
	
	//Array of Cells this cell is connected to
	private Cell [] links = new Cell[6];
	
	//State of the Cell
	private String state;
	
	private GameBoard parentBoard;

	/** Constructor which sets the location and state
	 *  of the cell based on the inputs
	 *  
	 *  @param x = Row Location
	 * @param y = Col Location
	 * @param state = String value of what state Cell is in
	 * @param parentBoard TODO
	 */
	public Cell(int x, int y, String state, GameBoard parentBoard){
		setXpos(x);
		setYpos(y);
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
	 * @return the xpos
	 */
	protected int getXpos() {
		return row;
	}

	/**
	 * @param xpos the xpos to set
	 */
	protected void setXpos(int xpos) {
		row = xpos;
	}

	/**
	 * @return the ypos
	 */
	protected int getYpos() {
		return col;
	}

	/**
	 * @param ypos the ypos to set
	 */
	protected void setYpos(int ypos) {
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
	
	
}
