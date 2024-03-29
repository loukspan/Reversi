import java.util.ArrayList;
import java.util.Random;

public class GamePlayer{
    //Variable that holds the maximum depth the MiniMax algorithm will reach for this player
	private int maxDepth;
    //Variable that holds which letter this player controls
	private int playerLetter;
	
	public GamePlayer(){
		maxDepth = 2;
		playerLetter = Board.W;
	}
	
	public GamePlayer(int maxDepth, int playerLetter){
		this.maxDepth = maxDepth;
		this.playerLetter = playerLetter;
	}
	
    //Initiates the MiniMax algorithm
	public Move MiniMax(Board board){
        //If the W plays then it wants to MAXimize the heuristics value
        if (playerLetter == Board.W){
            return max(new Board(board), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        //If the B plays then it wants to MINimize the heuristics value
        else{
            return min(new Board(board), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
	}
	
    // The max and min functions are called interchangingly, one after another until a max depth is reached
	public Move max(Board board, int depth, int alpha, int beta){
        Random r = new Random();
        /* If MAX is called on a state that is terminal or after a maximum depth is reached,
         * then a heuristic is calculated on the state and the move returned.
         */
		if((board.isTerminal()) || (depth == maxDepth)){
			Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
			return lastMove;
		}
        //The children-moves of the state are calculated
		ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.W));
		Move maxMove = new Move(Integer.MIN_VALUE);
		for (Board child : children){
            //And for each child min is called, on a lower depth
			Move move = min(child, depth + 1, alpha, beta);
			//The child-move with the greatest value is selected and returned by max
			if(move.getValue() >= maxMove.getValue()){
				alpha = Math.max(alpha, move.getValue());
                if ((move.getValue() == maxMove.getValue())){
                    //If the heuristic has the same value then we randomly choose one of the two moves
                    if (r.nextInt(2) == 0){
                        maxMove.setRow(child.getLastMove().getRow());
                        maxMove.setCol(child.getLastMove().getCol());
                        maxMove.setValue(move.getValue());
                    }
                }
                else{
                    maxMove.setRow(child.getLastMove().getRow());
                    maxMove.setCol(child.getLastMove().getCol());
                    maxMove.setValue(move.getValue());
                }
			}	
			//we should prune with alpha >= beta. The reason is that even though we can't do better, we might be able to do worse
			if(alpha >= beta){ 
				return maxMove;
			}
		}
		return maxMove;
	}

    //Min works similarly to max
	public Move min(Board board, int depth, int alpha, int beta){
        Random r = new Random();
		if((board.isTerminal()) || (depth == maxDepth)){
			Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
			return lastMove;
		}
		ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.B));
		Move minMove = new Move(Integer.MAX_VALUE);
		for (Board child : children){
			Move move = max(child, depth + 1, alpha, beta);
			if(move.getValue() <= minMove.getValue()){
				beta = Math.min(beta, move.getValue());
                if ((move.getValue() == minMove.getValue())){
                    if (r.nextInt(2) == 0){
                        minMove.setRow(child.getLastMove().getRow());
                        minMove.setCol(child.getLastMove().getCol());
                        minMove.setValue(move.getValue());
                    }
                }
                else{
                    minMove.setRow(child.getLastMove().getRow());
                    minMove.setCol(child.getLastMove().getCol());
                    minMove.setValue(move.getValue());
                }
            }
			if(beta <= alpha){ 
				return minMove;
			}
			
		}
		return minMove;
    }
}