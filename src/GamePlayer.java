import java.util.ArrayList;
import java.util.Random;

public class GamePlayer
{
    //Variable that holds the maximum depth the MiniMax algorithm will reach for this player
	private int maxDepth;
    //Variable that holds which letter this player controls
	private int playerLetter;
	
	public GamePlayer()
	{
		maxDepth = 2;
		playerLetter = Board.W;
	}
	
	public GamePlayer(int maxDepth, int playerLetter)
	{
		this.maxDepth = maxDepth;
		this.playerLetter = playerLetter;
	}
	
    //Initiates the MiniMax algorithm
	public Move MiniMax(Board board)
	{
        //If the W plays then it wants to MAXimize the heuristics value
        if (playerLetter == Board.W)
        {
            return max(new Board(board), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        //If the B plays then it wants to MINimize the heuristics value
        else
        {
            return min(new Board(board), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
	}
	
    // The max and min functions are called interchangingly, one after another until a max depth is reached
	public Move max(Board board, int depth, int alpha, int beta)
	{
		System.out.println("Max ");
        Random r = new Random();
        /* If MAX is called on a state that is terminal or after a maximum depth is reached,
         * then a heuristic is calculated on the state and the move returned.
         */
		if((board.isTerminal()) || (depth == maxDepth))
		{
			System.out.println("maxDepth MAX");
			
			Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate(board.getLastMove().getRow(), board.getLastMove().getCol(), Board.W));
			System.out.println("Return last max move with row "+lastMove.getRow()+" col "+lastMove.getCol()+" value "+lastMove.getValue());
			return lastMove;
		}
        //The children-moves of the state are calculated
		ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.W));
		Move maxMove = new Move(Integer.MIN_VALUE);
		int p = 0;
		for (Board child : children)
		{
			p++;
			System.out.println("Child "+p);
            //And for each child min is called, on a lower depth
			Move move = min(child, depth + 1, alpha, beta);
			System.out.println("Create Min move row "+move.getRow()+" col "+move.getCol()+" value "+move.getValue());
            
			//The child-move with the greatest value is selected and returned by max
			if(move.getValue() >= maxMove.getValue())
			{
				alpha = Math.max(alpha, move.getValue());
				System.out.println("ALPHA "+alpha);
				System.out.println("move.getValue >= maxMove");
                if ((move.getValue() == maxMove.getValue()))
                {
                	System.out.println("move.getValue == maxMove");
                    //If the heuristic has the save value then we randomly choose one of the two moves
                    if (r.nextInt(2) == 0)
                    {
                        maxMove.setRow(child.getLastMove().getRow());
                        maxMove.setCol(child.getLastMove().getCol());
                        maxMove.setValue(move.getValue());
                    }
                }
                else
                {
                	System.out.println("move.getValue > maxMove");
                    maxMove.setRow(child.getLastMove().getRow());
                    maxMove.setCol(child.getLastMove().getCol());
                    maxMove.setValue(move.getValue());
                }
			}	
			System.out.println("CHECK BETA TO PRUNE "+beta);
			if(alpha >= beta){ //we should prune with alpha = beta. The reason is that even though we can't do better, we might be able to do worse
				return maxMove;
			}
		}
		System.out.println("Return max move with row "+maxMove.getRow()+" col "+maxMove.getCol()+" value "+maxMove.getValue());
		return maxMove;
	}

    //Min works similarly to max
	public Move min(Board board, int depth, int alpha, int beta)
	{
		System.out.println("Min ");
        Random r = new Random();
		if((board.isTerminal()) || (depth == maxDepth))
		{
			System.out.println("maxdepth MIN");
			
			Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate(board.getLastMove().getRow(), board.getLastMove().getCol(), Board.B));
			System.out.println("Return last min move with row "+lastMove.getRow()+" col "+lastMove.getCol()+" value "+lastMove.getValue());
			return lastMove;
		}
		ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.B));
		Move minMove = new Move(Integer.MAX_VALUE);
		int p = 0;
		for (Board child : children)
		{
			p++;
			System.out.println("Child "+p);
			Move move = max(child, depth + 1, alpha, beta);
			System.out.println("Create Max move row "+move.getRow()+" col "+move.getCol()+" value "+move.getValue());
			
			if(move.getValue() <= minMove.getValue())
			{
				System.out.println("move.getValue <= minMove");
				beta = Math.min(beta, move.getValue());
				System.out.println("BETA "+beta);
                if ((move.getValue() == minMove.getValue()))
                {
                	System.out.println("move.getValue == minMove");
                    if (r.nextInt(2) == 0)
                    {
                        minMove.setRow(child.getLastMove().getRow());
                        minMove.setCol(child.getLastMove().getCol());
                        minMove.setValue(move.getValue());
                    }
                }
                else
                {
                	System.out.println("move.getValue < minMove");
                    minMove.setRow(child.getLastMove().getRow());
                    minMove.setCol(child.getLastMove().getCol());
                    minMove.setValue(move.getValue());
                }
            }
			System.out.println("CHECK ALPHA TO PRUNE "+alpha);
			if(beta <= alpha){ 
				return minMove;
			}
			
		}
		
		System.out.println("Return min move with row "+minMove.getRow()+" col "+minMove.getCol()+" value "+minMove.getValue());
        return minMove;
    }
}
