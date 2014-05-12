package aiproj.fencemaster.avnishj;

 

import java.io.PrintStream;

import aiproj.fencemaster.Move;
import aiproj.fencemaster.Piece;
import aiproj.fencemaster.Player;

public class JamesAvnish implements Player, Piece {
	
	boolean opponentFirstMove = true;
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
		turnNum = 1;
		
		return 0;
	}

	@Override
	public Move makeMove() {
		// TODO Auto-generated method stub
		return null;
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
			
			if(!opponentFirstMove || m.P != BLACK)){
				return INVALID;
			}
			if(targetCell.getState() != WHITE){
				return INVALID;
			}
			
		}
		
		else if(targetCell.getState != EMPTY){
			return INVALID;
		}
		//set the target cell as placed by the opponent 
		currentBoard.getCell(m.Row, m.Col).setState(m.P);
		opponentFirstMove = false;
		return 0;
	}

	@Override
	public void printBoard(PrintStream output) {
		currentBoard.printBoard(output);

	}

}
