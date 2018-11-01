import java.util.ArrayList;

public class Board
{
    //Variables for the Boards values
	//X=white, O=black
	public static final int W = 1;
	public static final int B = -1;
	public static final int EMPTY = 0;
    
    //Immediate move that lead to this board
    private Move lastMove;

    /* Variable containing who played last; whose turn resulted in this board
     * Even a new Board has lastLetterPlayed value; it denotes which player will play first
     */
	private int lastLetterPlayed;

	private int [][] gameBoard;
	
	public Board()
	{
		lastMove = new Move();
		lastLetterPlayed = B;
		gameBoard = new int[8][8];
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				gameBoard[i][j] = EMPTY;
			}
			gameBoard[3][3] = B;
			gameBoard[4][4] = B;
			gameBoard[4][3] = W;
			gameBoard[3][4] = W;
			
		}
	}
	
	public Board(Board board)
	{
		lastMove = board.lastMove;
		lastLetterPlayed = board.lastLetterPlayed;
		gameBoard = new int[8][8];
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				gameBoard[i][j] = board.gameBoard[i][j];
			}
		}
	}
		
	public Move getLastMove()
	{
		return lastMove;
	}
	
	public int getLastLetterPlayed()
	{
		return lastLetterPlayed;
	}
	
	public int[][] getGameBoard()
	{
		return gameBoard;
	}

	public void setLastMove(Move lastMove)
	{
		this.lastMove.setRow(lastMove.getRow());
		this.lastMove.setCol(lastMove.getCol());
		this.lastMove.setValue(lastMove.getValue());
	}
	
	public void setLastLetterPlayed(int lastLetterPlayed)
	{
		this.lastLetterPlayed = lastLetterPlayed;
	}
	
	public void setGameBoard(int[][] gameBoard)
	{
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				this.gameBoard[i][j] = gameBoard[i][j];
			}
		}
	}

    //Make a move; it places a letter in the board
	public void makeMove(int row, int col, int letter)
	{	
		gameBoard[row][col] = letter;
		lastMove = new Move(row, col);
		lastLetterPlayed = letter;
	}

    //Checks whether a move is valid; whether a square is empty
	public boolean isValidMove(int row, int col)
	{
		if ((row == -1) || (col == -1) || (row > 7) || (col > 7))
		{
			return false;
		}
		if(gameBoard[row][col] != EMPTY)
		{
			return false;
		}
		return true;
	}

    /* Generates the children of the state
     * Any square in the board that is empty results to a child
     */
	public ArrayList<Board> getChildren(int letter)
	{
		ArrayList<Board> children = new ArrayList<Board>();
		for(int row=0; row<8; row++)
		{
			for(int col=0; col<8; col++)
			{
				if(isValidMove(row, col))
				{
					Board child = new Board(this);
					child.makeMove(row, col, letter);
					children.add(child);
				}
			}
		}
		return children;
	}

	/*
     * The heuristic we use to evaluate is
     * the number our almost complete tic-tac-toes (having 2 letter in a row, column or diagonal)
     * minus the number of the opponent's almost complete tic-tac-toes
     * Special case: if a complete tic-tac-toe is present it counts as ten
     */
	public int evaluate()
	{
		int Xlines = 0;
		int Olines = 0;
        int sum;

        //Checking rows
		for(int row=0; row<8; row++)
		{
            sum = gameBoard[row][0] + gameBoard[row][1] + gameBoard[row][2] + gameBoard[row][3] + gameBoard[row][4] + gameBoard[row][5] + gameBoard[row][6] + gameBoard[row][7];
            if(sum == 8)
			{
                Xlines = Xlines + 10;
			}
            else if(sum == 7)
			{
                Xlines++;
			}
            else if(sum == -8)
			{
                Olines = Olines + 10;
			}
            else if(sum == -8)
			{
                Olines++;
			}
		}

        //Checking columns
		for(int col=0; col<8; col++)
		{
            sum = gameBoard[0][col] + gameBoard[1][col] + gameBoard[2][col] + gameBoard[col][3] + gameBoard[col][4] + gameBoard[col][5] + gameBoard[col][6] + gameBoard[col][7];
            if(sum == 8)
			{
                Xlines = Xlines + 10;
			}
            else if(sum == 7)
			{
                Xlines++;
			}
            else if(sum == -8)
			{
                Olines = Olines + 10;
			}
            else if(sum == -7)
			{
                Olines++;
			}
		}

        //Checking  diagonals
        sum = gameBoard[0][0] + gameBoard[1][1] + gameBoard[2][2] + gameBoard[3][3] + gameBoard[4][4] + gameBoard[5][5] + gameBoard[6][6] + gameBoard[7][7];
        if(sum == 8)
        {
            Xlines = Xlines + 10;
        }
        else if(sum == 7)
        {
            Xlines++;
        }
        else if(sum == -8)
        {
            Olines = Olines + 10;
        }
        else if(sum == -7)
        {
            Olines++;
        }
        sum = gameBoard[0][7] + gameBoard[1][6] + gameBoard[2][5] + gameBoard[3][4] + gameBoard[4][3] + gameBoard[5][2] + gameBoard[6][1] + gameBoard[7][0];
        if(sum == 8)
        {
            Xlines = Xlines + 10;
        }
        else if(sum == 7)
        {
            Xlines++;
        }
        else if(sum == -8)
        {
            Olines = Olines + 10;
        }
        else if(sum == -7)
        {
            Olines++;
        }

		return Xlines - Olines;
	}

    /*
     * A state is terminal if there is a tic-tac-toe
     * or no empty tiles are available
     */
    public boolean isTerminal()
    {
    	
        //Checking if there is a horizontal tic-tac-toe
		for(int row=0; row<3; row++)
		{
    		if((gameBoard[row][0] == gameBoard[row][1]) && (gameBoard[row][1] == gameBoard[row][2]) && (gameBoard[row][0] != EMPTY))
			{
                return true;
			}
		}

        //Checking if there is a vertical tic-tac-toe
		for(int col=0; col<3; col++)
		{
    		if((gameBoard[0][col] == gameBoard[1][col]) && (gameBoard[1][col] == gameBoard[2][col]) && (gameBoard[0][col] != EMPTY))
			{
                return true;
			}
		}

        //Checking if there is a diagonal tic-tac-toe
        if((gameBoard[0][0] == gameBoard[1][1]) && (gameBoard[1][1] == gameBoard[2][2]) && (gameBoard[1][1] != EMPTY))
		{
            return true;
        }
        if((gameBoard[0][2] == gameBoard[1][1]) && (gameBoard[1][1] == gameBoard[2][0]) && (gameBoard[1][1] != EMPTY))
		{
            return true;
        }

        //Checking if there is at least one empty tile
        for(int row=0; row<3; row++)
		{
			for(int col=0; col<3; col++)
			{
				if(gameBoard[row][col] == EMPTY)
				{
                    return false;
                }
            }
        }
        return true;
    }

    //Prints the board
	public void print()
	{
		System.out.println("* A B C D E F G H *");
		for(int row=0; row<8; row++)
		{
			int index = row+1;
			System.out.print("" + index + " ");
			for(int col=0; col<8; col++)
			{
				switch (gameBoard[row][col])
				{
					case W:
						System.out.print("W ");
						break;
					case B:
						System.out.print("B ");
						break;
					case EMPTY:
						System.out.print("- ");
						break;
					default:
						break;
				}
			}
			System.out.println("*");
		}
		System.out.println("*******************");
	}
}
