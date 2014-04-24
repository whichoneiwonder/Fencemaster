package aiproj.fencemaster;

import java.io.PrintStream;


/*   
 *   Player Interface:
 *      Includes basic functions required by referee 
 *
 *   @author msalehi
 */
public interface Player{
	/* This function when called by referee should return the winner
	 *  
	 */
	public int getWinner();

	/* Function called by referee to initialize the player.
	 *  Return 0 for successful initialization and -1 for failed one.
	 */
	public int init(int n, int p);
	
	/* Function called by referee to request a move by the player.
	 *  Return object of class Move
	 */
	public Move makeMove();
	
	/* Function called by referee to inform the player about the opponent's move
	 *  Return -1 if the move is illegal otherwise return 0
	 */
	public int opponentMove(Move m);
	
	/* Function called by referee to get the board configuration in String format
	 * from player 
	 */
	public void printBoard(PrintStream output);
}
