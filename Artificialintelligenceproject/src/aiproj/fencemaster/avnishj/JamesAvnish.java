package aiproj.fencemaster.avnishj;

 

import java.io.PrintStream;

import aiproj.fencemaster.Move;
import aiproj.fencemaster.Piece;
import aiproj.fencemaster.Player;

public class JamesAvnish implements Player, Piece {
	
	GameBoard currentBoard;
	int playerNum;
	int turnNum;
	
	
	@Override
	public int getWinner() {
		
		// TODO Auto-generated method stub
		
		return 0;
	}

	@Override
	public int init(int n, int p) {
		currentBoard = new GameBoard(n);
		playerNum = p;
		
		
		return 0;
	}

	@Override
	public Move makeMove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int opponentMove(Move m) {
		
		
		
		return 0;
	}

	@Override
	public void printBoard(PrintStream output) {
		currentBoard.printBoard(output);

	}

}
