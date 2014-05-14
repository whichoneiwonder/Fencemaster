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
	protected int turnNum;
	
	Random randNum = new Random();
	
	@Override
public int getWinner() {
		
		//Win Checking Booleans
		boolean blackWins = false;
		boolean whiteWins = false;
		boolean tripod =false;
		boolean loop =false;
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
		turnNum = 1;
		
		return 0;
	}

	@Override
	public Move makeMove() {
		int row, col;
		Move nextMove = new Move();
		Cell target;
		
		//random move generator
		
		nextMove.IsSwap = false;
		nextMove.P = this.playerNum;
		
		
		if(!currentBoard.getBorderCellList(playerNum).isEmpty() ){
			target = currentBoard.getBorderCellList(playerNum).get(
					randNum.nextInt(
							currentBoard.getBorderCellList(playerNum).size()));
			nextMove.Col = target.col;
			nextMove.Row = target.row;
			currentBoard.addMoveToBoard(nextMove);
			return nextMove;
		}
		
		while(true){
			row = randNum.nextInt(2* currentBoard.getDimension()-1);
			col = randNum.nextInt(2* currentBoard.getDimension()-1) ;
			
			if(DEBUG){
				System.out.println("Player" + playerNum +
						" trying row " + row + " and col " + col);
			}
			
			if((target =currentBoard.getCell(row, col)) != null ){
				if (target.getState() == EMPTY){
					nextMove.Col=col;
					nextMove.Row = row;
					//REMEMBER TO CHANGE OUR BOARD STATE
					currentBoard.addMoveToBoard(nextMove);
					return nextMove;
				}
			}
		}
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
		currentBoard.addMoveToBoard(m);
		opponentFirstMove = false;
		return 0;
	}

	@Override
	public void printBoard(PrintStream output) {
		currentBoard.printBoard(output);

	}

}
