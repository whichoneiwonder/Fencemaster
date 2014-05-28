package aiproj.fencemaster.avnishj;
import java.io.PrintStream;

import java.util.ArrayList;

import aiproj.fencemaster.Move;

/** Main class for the GameBoard
 */
public class GameBoard implements aiproj.fencemaster.Piece{
	
	private int dimension;
	public final int SCALE = 20;
	
	//Board implementation as a 2-D Array
	protected Cell [][] board;
	protected ArrayList<Edge> edgeList;
	protected ArrayList<ArrayList<Cell>> cellList;
	protected ArrayList<ArrayList<Cell>> borderCellList;
	protected boolean edges[][];
	protected ArrayList<Move> previousMoves;
	/** Constructor which creates the GameBoard given the dimension n
	 *  
	 *  @param n = dimension of the board
	 *  			
	 */
	public GameBoard(int n){
		previousMoves = new ArrayList<Move>();
		cellList = new ArrayList<ArrayList<Cell>>(3);
		borderCellList = new ArrayList<ArrayList<Cell>>(3);
		for(int i =0; i < 3; i++){
			cellList.add(i, new ArrayList<Cell>());
			borderCellList.add(i, new ArrayList<Cell>());
		}
		
		setDimension(n);
		board = new Cell [2*getDimension() - 1] [2*getDimension() - 1];
		//Static ArrayList that assists with Tripod Searching
		edgeList = new ArrayList<Edge>();
		createGameBoard();
		edges = new boolean[3][6];
		for (boolean[] list: edges){
			for(boolean item: list){
				item = false;
				if(item);
			}
		
		}
		for(Cell[] row: board){
			for (Cell cell : row){
				if(cell != null){
					cellList.get(EMPTY).add(cell);
				}
			}	
		}
		
	}
	
	/**Copy constructor takes a previous board state and a move to create a child state
	 * 
	 * @param parent : theprevisous state of the board 
	 * @param m the move to be added
	 */
	public GameBoard(GameBoard parent, Move m){
		previousMoves = new ArrayList<Move>();


		cellList = new ArrayList<ArrayList<Cell>>(3);
		borderCellList = new ArrayList<ArrayList<Cell>>(3);
		for(int i =0; i < 3; i++){
			cellList.add(i, new ArrayList<Cell>());
			borderCellList.add(i, new ArrayList<Cell>());
		}
		
		setDimension(parent.dimension);
		board = new Cell [2*getDimension() - 1] [2*getDimension() - 1];
		//Static ArrayList that assists with Tripod Searching
		edgeList = new ArrayList<Edge>();
		createGameBoard();
		edges = new boolean[3][6];
		for (boolean[] list: edges){
			for(boolean item: list){
				item = false;
				if(item);
			}
		
		}
		
		for(Move mv : parent.previousMoves){
			addMove(mv);
		}
		
		for(Cell[] row: board){
			for (Cell cell : row){
				if(cell != null){
					cellList.get(EMPTY).add(cell);
				}
			}	
		}
		
			addMove(m);	
		
	}

	/**
	 * @return the dimension
	 */
	protected int getDimension() {
		return dimension;
	}
	/** Creates the top half of the game board
	 *  From row 0 to row (Dimension - 1)
	 *  **/
	protected void createTopHalf(){
		int cornerNum = 0;
		
		//Counter for the creation of null spaces after the row is initialised
		int counter = getDimension();
		
		for (int row = 0; row < getDimension() - 1; row++){
			for (int col = 0; col < 2*getDimension() - 1; col++){
				
				//First Row so it has corner and edge pieces
				if (row == 0){
					
					//Creates the null spaces
					if (col >= counter){
						board[row][col] = null;
					}
					else{
						//Creates the Corner Pieces based on which Corner it is on
						//cornerNum is used to assist the GameBoard in creating links
						//so it knows which indices to set to null as connections
						if (col == 0 || col == counter - 1){
							board[row][col] = new Corner(row, col, EMPTY, this, cornerNum);
							cornerNum++;
						}
						//Creates the rest of the edge pieces in the first row
						else{
							board[row][col] = new Edge(row, col, EMPTY, this, 0);
						}
					}
				}// end first row
				
				//Every other row in the top half of the board 
				//starts and ends with an edge
				// and has center pieces in the middle
				else{
					
					//Creates the null spaces
					if (col >= counter){
						board[row][col] = null;
					}
					
					else{
						//First entry in the row is an edge piece
						if (col == 0){
							board[row][col] = new Edge(row, col, EMPTY, this, 5);
						}
						//Last valid entry in the row is an edge piece
						else if (col == counter - 1){
							board[row][col] = new Edge(row, col, EMPTY, this, 1);
						}
						//Rest are generic Center Cell objects
						else{
							board[row][col] = new Cell(row, col, EMPTY, this);
						}
					}
					
				}
			}//end inner for loop
			counter++;
		}//end outer for loop

	}
	
	/** Creates the middle and longest row
	 *  Starts and ends with 2 corner cells and the rest are Center pieces
	 *  **/
	protected void createMiddle(){
		int row = getDimension() - 1;
		//Starts and Ends the Row with 2 Corner Cells as it is the longest row
		board [row] [0] = new Corner(row, 0, EMPTY, this, 5);
		board [row] [2*getDimension() - 2] = 
				new Corner(row, 2*getDimension() - 2, EMPTY, this, 2);
		
		//Rest are initialised as Center Cells
		for (int col = 1; col < 2*getDimension() - 2; col++){
			board [row] [col] = new Cell(row, col, EMPTY, this);
		}
	}
	
	/** Creates the top half of the game board
	 *  From row Dimension to the last row
	 *  **/
	protected void createBottomHalf(){
		
		//Counter that counts the number of null spaces needed
		int nullCounter = 1;
		
		//Creates all rows except the last row as these rows begin with an edge
		//and have center pieces in the middle
		for (int row = getDimension(); row < 2*getDimension() - 2; row++){
			for (int col = 0; col < 2*getDimension() - 1; col++){
				
				//Creates the Null Spaces
				if (col < nullCounter){
					board[row][col] = null;
				}
				else{
					//Last entry in the board row is an edge piece
					if (col == 2*getDimension()-2){
						board[row][col] = new Edge(row, col, EMPTY, this, 2);
					}
					//First entry of the row in the board is an edge piece
					else if (col == nullCounter){
						board[row][col] = new Edge(row, col, EMPTY, this, 4);
					}
					//Everything else is a generic Cell
					else{
						board[row][col] = new Cell(row, col, EMPTY, this);
					}
				}//end inner else	
			}//end inner for loop
			nullCounter++;
		}//end outer for loop
		
		//Creates Cells for the Last Row
		int row = 2*getDimension()-2;
		for (int col = 0; col < 2*getDimension()-1; col++ ){
			//Creates Null Spaces
			if(col < getDimension()-1){
				board[row][col] = null;
			}
			//First entry in the board is an Corner piece
			else if (col == getDimension()-1){
				board[row][col] = new Corner(row, col, EMPTY, this, 4);
			}
			//Last entry in the board is an Corner piece
			else if (col == 2*getDimension() -2){
				board[row][col] = new Corner(row, col, EMPTY, this, 3);
			}
			//Everything else is an Edge Piece
			else{
				board[row][col] = new Edge(row, col, EMPTY, this, 3);
			}
				
		}
		
	}
	/** Creates the links for all the cells in the game board
	 *  **/
	protected void createLinks(){
		for (int row = 0; row < 2*dimension - 1; row++){
			for (int col = 0; col < 2*dimension - 1; col++){
				//Skips over Null spaces
				if (board[row] [col] != null){
					board[row] [col].createLinks();
				}
			}
		}
	}
	/** Creates the whole game board by calling the respective functions
	 *  **/
	protected void createGameBoard(){
		createTopHalf();
		createMiddle();
		createBottomHalf();
		createLinks();
	}
	
	/**
	 * @param dimension the dimension to set
	 */
	protected void setDimension(int dim) {
		dimension = dim;
	}
	
	/** Used for Debugging Purposes
	 * 
	 * Prints the GameBoard
	 *  **/
	protected void printBoard(){
		for (int row = 0; row < board.length; row++){
			for (int col = 0; col < board[0].length; col++){
				if (board [row] [col] != null)
					System.out.print(board [row] [col].getState() + "\t");
				else
					System.out.print("null\t");
			}
			System.out.println();
		}
	}
	
	protected void printBoard(PrintStream output){
		
		int spaces=0;
		for (int row = 0; row < board.length; row++){
			if( row < getDimension() ){
				spaces = getDimension()-1 -row;
			}
			else if (row >= getDimension()){
				spaces = row - getDimension()+1 ;
			}
			while(spaces > 0){
				System.out.print(' ');
				spaces--;
			}
			
			for (int col = 0; col < board[0].length; col++){
				if (board [row] [col] != null)
					output.print(board [row] [col].getState() + " ");
				
			}
			output.print("\n");
		}
	}
	
	/** Method that sets the state of a cell in the GameBoard
	 * given the (row, col) positions and the desired state
	 *  
	 *  @param x = Row Location
	 *  @param y = Col Location
	 *  @param state = String value of what state Cell is in
	 *  
	 **/  
	protected void setCellState(int x, int y, int state){
		board[x][y].setState(state);
	}
	protected int getCellState(int x, int y){
		return board[x][y].state;
	}
	
	
	protected void setCellState(int x, int y, String state){
		if(state.equals("W")){
				board[x][y].setState(WHITE);
		} else if(state.equals("B")){
			board[x][y].setState(BLACK);
		} else if(state.equals("-")){
			board[x][y].setState(EMPTY);
		} 
		
		
		
	}

	/**
	 * @return the board
	 */
	protected Cell[][] getBoard() {
		return board;
	}

	/**
	 * @param board the board to set
	 */
	protected void setBoard(Cell[][] board) {
		this.board = board;
	}
	
	/** Returns the Cell reference given the (row, col) position
	 *  
	 *  @param x = Row Location
	 *  @param y = Col Location
	 **/ 
	protected Cell getCell(int x,int y){
		//Checks if the x and y positions are valid
		//If not, returns null
		//Error Handling for ArrayIndexOutOfBounds Exception
		if (x < 0 || x > 2*dimension - 2 || y < 0 || y > 2*dimension - 2){
			return null;
		}
		if(board[x][y] != null){
			return board[x][y];
		}
		return null;
	}
	/** Used for Debugging Purposes
	 * 
	 * Prints the static ArrayList edgeList consisting of all edge pieces
	 *  **/
	protected void printEdgeList(){
		System.out.println("There are " + edgeList.size() + " edges");
		for (int i = 0; i < edgeList.size(); i++){
			System.out.println("(" + edgeList.get(i).getRow() + ", " + edgeList.get(i).getCol() + ")");
		}
	}
	/** Used for Debugging Purposes
	 * 
	 * Prints the Cell position of all the connecting cells of a given cell
	 * 
	 * @param o = Array of Connecting Cell references
	 *  **/
	protected void printGetLinks(Cell [] o){
		for (int i = 0; i < o.length; i++){
			if (o[i] == null) {
				System.out.println("Connection " + i + ": Null");
			}
			else{
				System.out.println("Connection " + i + ": ("+o[i].getRow() + ", " + o[i].getCol() + ")");
			}
		}
	}
	
	public boolean checkForBlank(){
		
		for(int row = 0; row < 2*getDimension()-1; row++){
			for(int col = 0 ; col < 2*getDimension() -1; col++){
				if(this.getCell(row, col) != null &&
						this.getCell(row, col).getState() == 0){
					return true;
				}	
			}
		}

		return false;
	}
	
	
	/**
	 * Handles the checking if a tripod is present in the GameBoard
	 * through DFS and a stack abstract data structure implemented in
	 * an ArrayList
	 * 
	 * @param colour = the type of state we are checking the tripod
	 * 					win state for that colour
	 * @return true if tripod exists, false if not
	 */
	protected boolean checkTripod(int colour){
		Cell current;
		
		//Stack Abstract Data Structure as an ArrayList
		ArrayList<Cell> queue = new ArrayList<Cell>();
		
		//Data Structure to keep track of all visited nodes
		ArrayList<Cell> visitedNodes = new ArrayList<Cell>();
		
		//Data Structure to check that a minimum of 3 unique edges have been
		//reached
		ArrayList<Integer> foundEdge = new ArrayList<Integer>();
		
		
		for (int i = 0; i < edgeList.size(); i++){
			//Clears and resets the ArrayList
			foundEdge.clear();
			//Starts the search point for the tripod from an edge
			current = edgeList.get(i);
			
			//If the edge piece is of the right colour, add it to visitedNodes
			//else, as the queue is initially empty, move to the next edge piece
			if (current.getState() ==(colour)){
				visitedNodes.add(current);
				//Adds the search point of the edge to the foundEdge list
				//Now proceeds to find the other 2 unique edges
				foundEdge.add(edgeList.get(i).getEdgeNum());
				
				
				//Perform Depth - First - Search
				for (int j = 0; j < 6; j++){
					
					//If the current node has connections that are of the same colour
					//that has not been visited before or is not part of the queue
					//add it to the queue
					if (current.getAllLinks()[j] != null 
							&& current.getAllLinks()[j].getState() == colour
							&& !visitedNodes.contains(current.getAllLinks()[j])
							&& !queue.contains(current.getAllLinks()[j])) {

						queue.add(0, current.getAllLinks()[j]);
						
					}
				}
				
				//While there are cells in the Queue
				while(!queue.isEmpty()){
					//Remove the first cell in the queue and add it to visitedNodes
					current = queue.get(0);
					visitedNodes.add(current);
					queue.remove(0);
					
					//If we find an edge cell on a unique edge number that is not in
					//the foundEdge ArrayList
					if (current.toString().equals("Edge")
						&& !foundEdge.contains(((Edge) current).getEdgeNum() )){
						//If this is the second Edge, we still have one more to go
						//Add it to the foundEdge ArrayList and keep searching
						if (foundEdge.size() <2){
							foundEdge.add(((Edge) current).getEdgeNum() );
						}
						//We have found 3 unique edges, hence a Tripod!
						else
							return true;
						
					}
					for (int j = 0; j < 6; j++){
						//for each adjacent cell, if it's not null, not the right color 
						//and not  already visited, then add it to the queue
						if (current.getAllLinks()[j] != null 
								&& current.getAllLinks()[j].getState() == (colour)
								&& !visitedNodes.contains(current.getAllLinks()[j])
								&& !queue.contains(current.getAllLinks()[j])) {
							// add to the front of the queue
							queue.add(0, current.getAllLinks()[j]);
						}
					}
				}
				
			}
		}
		
		return false;
	}

	/**
	 * Handles the checking if a loop is present in the GameBoard
	 * through DFS and a stack abstract data structure implemented in
	 * an ArrayList
	 * 
	 * @param colour = the type of state we are checking the tripod
	 * 					win state for that colour
	 * @return true if loop exists, false if not
	 */
	protected boolean checkLoop(int colour){
		
		//Stack Abstract Data Structure as an ArrayList
		ArrayList<Cell> queue = new ArrayList<Cell>();
		
		//Array List Data Structure that holds all cells that 
		//are connected to or can indirectly access an Edge
		ArrayList<Cell> canReachEdge = new ArrayList<Cell>();
		
		//Data Structure to keep track of all visited nodes
		ArrayList<Cell> visitedNodes = new ArrayList<Cell>();
		
		Cell current, next;
		boolean checkNext = false;
		
		for(int row = 0; row < 2*getDimension()-1; row++){
			for(int col=0; col < 2*getDimension()-1; col++){
				current = board[row][col];
				checkNext = false;
				
				//If the current cell is of type Center, is of the opposite colour 
				//of the loop and is not in canReachEdge or visitedNodes, start
				//searching from here
				if(current !=null
						&& current.toString().equals("Center")
						&& !(current.getState() == colour )
						&& !canReachEdge.contains(current)
						&& !visitedNodes.contains(current)){
					//ie: not a corner or edge
					
					//Add the current node to visitedNodes
					visitedNodes.add(current);
					
					for(int i = 0; i < 6; i++){
						//Checks links of the cell
						next = current.getLink(i);
						
						//If the cell is of the opposite colour
						if (!(next.getState() == colour)){
							
							//If the cell is an edge or a corner or is in canReachEdge
							//the searching piece is not enclosed within a boundary
							if(!next.toString().equals("Center")
									|| canReachEdge.contains(next)){
								
								//Add all the cells in the queue and visitedNodes
								//(which are indirectly linked to each other)
								//to canReachEdge
								canReachEdge.addAll(queue);
								queue.clear();
								canReachEdge.addAll(visitedNodes);
								visitedNodes.clear();
								checkNext = true;
								break; 
								
							//if the linking cell is not in visitedNodes and is another center piece,
							//add it to the front of the stack	
							}else if(!visitedNodes.contains(next)){
								queue.add(0,next);
							}
						}	
					}//ends the initial loading of the queue
					
					//While the queue is not empty
					while(!queue.isEmpty()){
						
						//Remove the first element off the stack and add it to visitedNodes
						current = queue.get(0);
						queue.remove(0);
						visitedNodes.add(current);
						
						//iterate over the links of each cell
						for(int i = 0; i < 6; i++){
							next = current.getLink(i);
							
							//the Cell is of the opposite colour
							if (!(next.getState() == colour)){
								
								//If the cell is an edge or a corner or is in canReachEdge
								//the searching piece is not enclosed within a boundary
								if(!next.toString().equals("Center")
										|| canReachEdge.contains(next)){
									
									//Add all the cells in the queue and visitedNodes
									//(which are indirectly linked to each other)
									//to canReachEdge
									canReachEdge.addAll(queue);
									queue.clear();
									canReachEdge.addAll(visitedNodes);
									visitedNodes.clear();
									checkNext = true;
									break;
									
								//if the linking cell is not in visitedNodes and is another center piece,
								//add it to the front of the stack		
								}else if(!visitedNodes.contains(next)){
									queue.add(0,next);
									
								}

							}
							
						}
					}
					//if checkNext is equals to false, which means that the queue has
					//already been initialised to some values and is now empty
					//There is a loop!
					if(!checkNext) 
						return true;	
					
				}
				
			}
		}
		
		
		
		return false;
	}
	/**
	 * 
	 * @param mv the move to be added to the board
	 */
	protected void addMove(Move mv){
		previousMoves.add(mv);
		Cell target = getCell(mv.Row, mv.Col);
		if(mv.IsSwap){
			//if it's a swap, remove the preciously added things
			cellList.get(mv.P%2 +1).clear();
			borderCellList.get(mv.P%2 +1).clear();
			for (boolean[] edge: edges){
				for( boolean marker: edge){
					marker = false;
					if(marker);
				}
			}
		}
		//update tracking arraylists
		setCellState(mv.Row, mv.Col, mv.P);
		
		cellList.get(EMPTY).remove(target);
		borderCellList.get(WHITE).remove(target);
		borderCellList.get(BLACK).remove(target);
		cellList.get(mv.P).add(target);
		
		
		
		
		if (target.toString().equals("Edge")){
			edges[mv.P][((Edge)target).getEdgeNum()]= true;
		}
		
		//expand the border space of the moving player
		for(Cell space: getCell(mv.Row, mv.Col).getAllLinks()){
			if( space != null && space.state == EMPTY &&
					!borderCellList.get(mv.P).contains(space)){
				borderCellList.get(mv.P).add(space);
			}
		}
		
	}
	
	
	
	protected ArrayList<Cell> getCellList(int player){
		if(player == EMPTY || player == WHITE || player == BLACK){
			return cellList.get(player);
		}
		
		return null;
	}
	
	protected ArrayList<Cell> getBorderCellList(int playerNum){
		if(playerNum == WHITE || playerNum == BLACK){
			return borderCellList.get(playerNum);
		}
		
		return null;
	}
	
	protected Cell getSmallestCell(ArrayList<Cell> list, int edgeNum){
		Cell minCell = list.get(0);
		int min = minCell.getDistanceToEdge(edgeNum);
		
		for(int i = 1; i < list.size(); i++){
			if (list.get(i).getDistanceToEdge(edgeNum) < min){
				min = list.get(i).getDistanceToEdge(edgeNum);
				minCell = list.get(i);
			}
		}
		
		return minCell;
		
	}
	
	protected ArrayList<Cell> addToPriorityQueue(ArrayList<Cell> pq, Cell current, int edgeNum, int playerNum){
		
		int [] listingOrder = {edgeNum, (edgeNum+1)%6, (edgeNum+5)%6, (edgeNum+2)%6, 
		                          (edgeNum+4)%6, (edgeNum+3)%6};
		
		for (int i = listingOrder.length - 1; i >= 0; i--){
			Cell next = current.getLink(listingOrder[i]);
			
			if (next != null && next.getState() != ((playerNum%2)+1) && !pq.contains(next) ){
				pq.add(0,next);
			}
		}
		
		return pq;
	}
	
	protected double djikstra(int playerNum, int edgeNum){
		
	ArrayList<Cell> pq = new ArrayList<Cell>();

		Cell smallest = getSmallestCell(this.getCellList(playerNum), edgeNum);
		pq = addToPriorityQueue(pq, smallest, edgeNum, playerNum);
		double counter = 1;
		while(!pq.isEmpty() && counter <= SCALE * this.getDimension() ){
			//checking first list
			Cell check = pq.get(0);
			if(check.toString().equals("Edge") && ((Edge)check).getEdgeNum()== edgeNum){
				return counter;
			}
			else{
				pq = addToPriorityQueue(pq, check, edgeNum, playerNum);
				pq.remove(pq.indexOf(check));
				counter++;
			}
		}
		
		
		return counter;
		
	}
	protected double eval(int playerNum){
		double connectedEdges =0;
		double [] heuristic = new double [6];
		double min = Double.MAX_VALUE;
		
		for (int i = 0; i < heuristic.length; i++){
			if (!edges[playerNum][i]){
				heuristic[i] = djikstra(playerNum, i);
				
				if ( heuristic[i] < min){
					min = heuristic[i];
				}
			}else connectedEdges++;
				
		}
		
		return 101*connectedEdges + (100/(min));
	}

}
	

