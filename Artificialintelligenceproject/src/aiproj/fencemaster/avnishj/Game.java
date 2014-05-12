package aiproj.fencemaster.avnishj;
import java.util.Scanner;
/** Main class for the Game
 */
public class Game implements aiproj.fencemaster.Piece {
	
	private static boolean DEBUG = false;
	
	
	/** Main method that handles initialisation of the board, 
	 * input and state checking.
	 * @param args -- Command Line Arguements
	 */
	public static void main(String [] args){
		Scanner boardStateScanner = new Scanner(System.in);
		int row, col;
		int dimension;
		
		/* Error Handling for if the input is in the wrong format */
		if(! boardStateScanner.hasNextInt()){
			System.out.println("Error: input must start with the dimension of the board");
			System.exit(1);
		}
		
		//Can assume at this stage that the dimension value is the
		//first line in the input
		dimension = boardStateScanner.nextInt();
		
		//Creates the GameBoard object
		GameBoard board = new GameBoard(dimension);
		

		for(row = 0; row < 2*dimension-1; row++){
			for(col = 0 ; col < 2*dimension -1; col++){
				
				//Skips over null spaces
				if(board.getBoard() [row][col] !=null){
					if(! boardStateScanner.hasNext()){
						System.err.println("Error: input must match size of board");
						System.exit(1);
					}
					//Set state according to input
					String nextState = boardStateScanner.next();
					if (nextState.equals("-") ){
						board.setCellState(row, col, EMPTY);
					}
					else if (nextState.equals("B")){
						board.setCellState(row, col, BLACK);
					} 
					else if (nextState.equals("W")){
						board.setCellState(row, col, WHITE);
					}
					else{
						System.err.println("Wrong Input: " + nextState);
						System.exit(1);
					}
				}

			}
		}
		
		//Win Checking Booleans
		boolean blackWins = false;
		boolean whiteWins = false;
		boolean tripod =false;
		boolean loop =false;
		boolean blankSpotsOnBoard = board.checkForBlank();
		
		if (Game.DEBUG)
			board.printBoard();
		
		//Checks if Black has won by a tripod
		if(board.checkTripod(BLACK)){
			blackWins = true;
			tripod = true;
		}
		
		//Checks if White has won by a tripod
		if(board.checkTripod(WHITE)){
			whiteWins = true;
			tripod = true;
		}
		
		//Checks if Black has won by a Loop
		if(board.checkLoop(BLACK)){
			blackWins = true;
			loop = true;
		}
		
		//Checks if White has won by a loop
		if(board.checkLoop(WHITE)){
			whiteWins = true;
			loop = true;
		}
		
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
		}else{
			System.out.println("None");
			System.out.println("Nil");
		}
		
		
		
		
		return;
		
	}
	
	

}
