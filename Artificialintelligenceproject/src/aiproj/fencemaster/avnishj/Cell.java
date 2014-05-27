package aiproj.fencemaster.avnishj;



/** Super class for cells, which has attributes and
 * methods for a Center type cell
 */
public class Cell implements aiproj.fencemaster.Piece{
	//Row Position on Board
	protected int row;
	
	//Column Position on Board
	protected int col;
	
	//Array of Cells this cell is connected to
	protected Cell [] links = new Cell[6];
	
	//State of the Cell
	protected int state;
	
	//
	
	
	protected GameBoard parentBoard;

	/** Constructor which sets the location and state
	 *  of the cell based on the inputs
	 *  
	 *  @param x = Row Location
	 * @param y = Col Location
	 * @param state = String value of what state Cell is in
	 * @param parentBoard TODO
	 */
	public Cell(int x, int y, int state, GameBoard parentBoard){
		setRow(x);
		setCol(y);
		setState(state);
		setParentBoard(parentBoard);
		//add to the parent Board's list of empty Cells
		getParentBoard().cellList.get(EMPTY).add(this);
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
	protected int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	protected void setState(int state) {
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
	
	public int [] getEdgeDistances(){
		int [] edgeD = new int [6];
		
		for (int i = 0 ; i < edgeD.length; i++){
			edgeD[i] = getDistanceToEdge(i);
		}
		
		return edgeD;
	}
	
	public int getMinimumInEdgeList(){
		int min = parentBoard.getDimension();
		int [] list = getEdgeDistances();
		
		for (int i = 0; i < list.length; i++){
			
			if(list[i] < min){
				min = list[i];
			}
			
		}
		
		return min;
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
	
	public boolean joinsTwoChains(int player){
		int count = 0;
		
		for(int i = 0; i <6; i++){

			if(links[i] != null && links[i].state == player &&
					(links[(i+1)%6] == null || links[(i+1)%6].state != player )){
				count++;
				i++;
			}
		}
		if (count >= 2){
			
			return true;
		}
		else{
			return false;
		}
	}
	
	
}
