package aiproj.fencemaster.avnishj;

 
import java.util.Random;
import java.io.PrintStream;

import aiproj.fencemaster.Move;
import aiproj.fencemaster.Piece;
import aiproj.fencemaster.Player;

public class JamesAvnish implements Player, Piece {
	
	private static final boolean DEBUG = false;
	protected boolean opponentFirstMove = true;
	protected GameBoard currentBoard;
	protected int playerNum;
	protected int enemyNum;
	protected int turnNum;
	protected final int MAXDEPTH = 5;
	
	
	protected Random randNum;
	
	@Override
public int getWinner() {
		
		//Win Checking Booleans
		boolean blackWins = false;
		boolean whiteWins = false;
		boolean tripod = false;
		boolean loop = false;
		boolean blankSpotsOnBoard = currentBoard.checkForBlank();
		
		if (DEBUG)
			currentBoard.printBoard();
		
		//Checks if Black has won by a tripod
		if(currentBoard.checkTripod(BLACK)){
			blackWins = true;
			tripod = true;
		}
		
		//Checks if White has won by a tripod
		if(currentBoard.checkTripod(WHITE)){
			whiteWins = true;
			tripod = true;
		}
		
		//Checks if Black has won by a Loop
		if(currentBoard.checkLoop(BLACK)){
			blackWins = true;
			loop = true;
		}
		
		//Checks if White has won by a loop
		if(currentBoard.checkLoop(WHITE)){
			whiteWins = true;
			loop = true;
		}
		
		printDebugWinnerStates(blackWins, whiteWins, tripod, loop, blankSpotsOnBoard);
		
		if(blackWins)
			return BLACK;
		
		if(whiteWins)
			return WHITE;
		
		if(blankSpotsOnBoard)
			return INVALID;
		
		return EMPTY;
	}

	public void printDebugWinnerStates(boolean blackWins, boolean whiteWins, 
			boolean tripod, boolean loop, boolean blankSpotsOnBoard){
		
		//Handles Printing given boolean values
		
		//If Both Players have Winning States
		if (whiteWins && blackWins || !blankSpotsOnBoard){
			System.out.println("Draw");
			if (tripod && loop){
				System.out.println("Both");
			}
			else{
				if (tripod)
					System.out.println("Tripod");
				else if (!blankSpotsOnBoard){
					System.out.println("Nil");
				}else
					System.out.println("Loop");
				}
			}
				
		//If White has won
		else if (whiteWins){
			System.out.println("White");
			if (tripod && loop){
				System.out.println("Both");
			}
			else{
				if (tripod)
					System.out.println("Tripod");
				else
					System.out.println("Loop");
			}
		}
				
		else if (blackWins){
			System.out.println("Black");
			if (tripod && loop){
				System.out.println("Both");
			}
			else{
				if (tripod)
					System.out.println("Tripod");
				else
					System.out.println("Loop");
				}
		}
		else{
			System.out.println("None");
			System.out.println("Nil");
		}
		
		
	}

	@Override
	public int init(int n, int p) {
		currentBoard = new GameBoard(n);
		playerNum = p;
		enemyNum = p%2+1;
		turnNum = 1;
		randNum = new Random();
		
		return 0;
	}

	@Override
	public Move makeMove() {
		
		Move nextMove = new Move();
		turnNum++;
		if(turnNum <3){
			nextMove.IsSwap = false;
			nextMove.P = playerNum;
			if(currentBoard.getCellState(1, 1)== enemyNum){
				nextMove.Col = 1;
				nextMove.Row = currentBoard.getDimension()-1;
				
						
			}else{
				nextMove.Row=1;
				nextMove.Col=1;
			}
			addMoveToBoard(nextMove);
			return nextMove;
		}
		
		nextMove = miniMax(MAXDEPTH);
		
		if(nextMove == null){
			System.err.println("random move...");
			nextMove = new Move();
			nextMove.IsSwap = false;
			nextMove.P = this.playerNum;
			Cell target;
			target = null;
			target = currentBoard.getCellList(EMPTY).get(
					randNum.nextInt(
						currentBoard.getCellList(EMPTY).size()));
			
				
			nextMove.Col = target.col;
			nextMove.Row = target.row;
			
		}
			
		addMoveToBoard(nextMove);
		System.out.flush();
		System.err.println("Board Evaluation: " + 
				currentBoard.eval(playerNum));
		System.err.flush();
		return nextMove;
		
		
	}

	@Override
	public int opponentMove(Move m) {
		
		Cell targetCell = currentBoard.getCell(m.Row, m.Col);
		if(m.P == playerNum){
			return INVALID;
		}else if (m.P > BLACK || m.P < WHITE){
			return INVALID;
		}
		if(targetCell == null){
			return INVALID;
		}
		
		 
		if(m.IsSwap){
			
			if(!opponentFirstMove || m.P != BLACK){
				return INVALID;
			}
			if(targetCell.getState() != WHITE){
				return INVALID;
			}
			
		}
		
		else if(targetCell.getState() != EMPTY){
			return INVALID;
		}
		//set the target cell as placed by the opponent 
		
		turnNum++;
		addMoveToBoard(m);
		opponentFirstMove = false;
		return 0;
	}

	@Override
	public void printBoard(PrintStream output) {
		currentBoard.printBoard(output);

	}
	public void addMoveToBoard(Move m){
		
		currentBoard.addMove(m);
	}

	public Move miniMax(int depth){
		double maxVal = Double.MIN_VALUE;
		double minVal;
		Move m;
		Move bestMove = null;
		
		for(Cell target : currentBoard.borderCellList.get(playerNum)){
			m = new Move(playerNum , false, target.row, target.col);
			//System.out.println(m);
			minVal =  minMove(new GameBoard(currentBoard, m), depth -1 );
			//System.out.println("MinVal: " + minVal);
			if (maxVal <= minVal){
				maxVal = minVal;
				bestMove = m;
			}
			
		}
		return bestMove;
	}
	
	protected double minMove (GameBoard parentBoard, int depth){
		
		if(parentBoard.checkTripod(playerNum)|| parentBoard.checkLoop(playerNum)){
			return Double.MAX_VALUE;
		}
		if (depth <=0 ){
			double eval = parentBoard.eval(playerNum);
			//System.out.println("MinMove() Eval at depth " + depth + " eval: " + eval);
			return eval - parentBoard.eval(enemyNum);
		}
		
		
		double minVal = Double.MAX_VALUE;
		double maxVal;
		Move m;
		for(Cell target : parentBoard.borderCellList.get(enemyNum)){
			m = new Move(enemyNum , false, target.row, target.col);
			if (minVal > (maxVal =  maxMove(new GameBoard(parentBoard, m),depth-1))){
				minVal = maxVal;
			}
			
			
		}
		return minVal;
	}
		
		protected double maxMove (GameBoard parentBoard, int depth){
			if(parentBoard.checkTripod(enemyNum) || parentBoard.checkLoop(enemyNum)){
				return Double.MIN_VALUE;
			}
			
			
			if (depth <=0 ){
				double eval = parentBoard.eval(playerNum);
				//System.out.println("MaxMove() Eval at depth " + depth + " eval: " + eval);
				return eval - parentBoard.eval(enemyNum);
			}
			
			
			double maxVal = Double.MIN_VALUE;
			double minVal;
			Move m;
			for(Cell target : parentBoard.borderCellList.get(playerNum)){
				m = new Move(playerNum , false, target.row, target.col);
				minVal =  minMove(new GameBoard(parentBoard, m), depth -1 );
				//System.out.println("MaxMove() MinVal: " + minVal);
				if (maxVal > minVal){
					maxVal = minVal;
				}
				
			}
		
		
		
		return maxVal;
		
	}




	


}