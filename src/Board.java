import java.util.ArrayList;
import java.util.Scanner;

public class Board
{
	
	Scanner sc = new Scanner(System.in);
	
    //Variables for the Boards values
	//W=white, B=black, available = available moves as "."
	public static final int W = 1;
	public static final int B = -1;
	public static final int EMPTY = 0;
	public static final int available = 2;
    
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
			gameBoard[3][3] = W;
			gameBoard[4][4] = W;
			gameBoard[4][3] = B;
			gameBoard[3][4] = B;
			
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
	
	public boolean noMoves(int letter){
		//The children-moves of the state are calculated
		ArrayList<Board> children = new ArrayList<Board>(getChildren(letter));
		if(children.isEmpty()){
			//Thelei tsekarisma
			return true;
		}
		return false;
	}
	
	public void handleEntrance(){
		if(noMoves(Board.B)){
			System.out.println("Black has no moves");
			lastLetterPlayed = Board.B;
		}
		else{
			while(true){
	        	System.out.println("B moves");
	        	System.out.println("Enter position (e.g. A-1)");
	        	String position = sc.next();
	        	String[] parts = position.split("-");
	        	int row = Integer.parseInt(parts[1])-1;	
	        	int col = -1;
	        	if(parts[0].equals("A")) {col = 0;}
	        	else if(parts[0].equals("B")) {col = 1;}
	        	else if(parts[0].equals("C")) {col = 2;}
	        	else if(parts[0].equals("D")) {col = 3;}
	        	else if(parts[0].equals("E")) {col = 4;}
	        	else if(parts[0].equals("F")) {col = 5;}
	        	else if(parts[0].equals("G")) {col = 6;}
	        	else if(parts[0].equals("H")) {col = 7;}
	        	if(isValidMove(row, col, Board.B)) {        		
	            	makeMove(row, col, Board.B);
	            	break;
	            }
	        	else{
	        		System.out.println("Invalid Position, please try again");	
	        	}
	        }
		}
	}

	public boolean isValidUp(int row, int col, int letter){
		if(row > 1){																//if row <= 1 - sure not valid
			if(gameBoard[row-1][col] != letter && gameBoard[row-1][col] != EMPTY && gameBoard[row-1][col] != available){	//if up there is an opponent's pawn
				for(int i=row-1; i>0; i--){ 										//then search from that pawn to edge
					if (gameBoard[i-1][col] == EMPTY || gameBoard[row-1][col] == available){								//if you find EMPTY - not valid
						return false;
					}
					if (gameBoard[i-1][col] == letter){ 							//if there is a player's pawn
						return true;												//valid
					}	
				}
			}
		}
		return false;
	}
	
	public boolean isValidUpRight(int row, int col, int letter){
		if(row > 1 && col < 6){															//check if there is room up and right
			if(gameBoard[row-1][col+1] != letter && gameBoard[row-1][col+1] != EMPTY && gameBoard[row-1][col+1] != available){	//if up there is an opponent's pawn
				row--;
				col++;
				while(row>0 && col<7){
					if (gameBoard[row-1][col+1] == EMPTY || gameBoard[row-1][col+1] == available){								//if you find EMPTY - not valid
						return false;
					}
					if (gameBoard[row-1][col+1] == letter){ 							//if there is a player's pawn
						return true;													//valid
					}
				row--;
				col++;
				}
			}
		}
		return false;
	}
	
	public boolean isValidRight(int row, int col, int letter){
		if(col < 6){																//if col < 6 - sure not valid
			if(gameBoard[row][col+1] != letter && gameBoard[row][col+1] != EMPTY && gameBoard[row][col+1] != available){	//if right there is an opponent's pawn
				for(int i=col+1; i<7; i++){ 										//then search from that pawn to edge
					if (gameBoard[row][i+1] == EMPTY || gameBoard[row][col+1] == available){								//if you find EMPTY - not valid
						return false;
					}
					if (gameBoard[row][i+1] == letter){ 							//if there is a player's pawn
						return true;												//valid
					}	
					
				}
			}
		}
		return false;
	}
	
	public boolean isValidRightDown(int row, int col, int letter){
		if(row < 6 && col < 6){															//check if there is room right and down
			if(gameBoard[row+1][col+1] != letter && gameBoard[row+1][col+1] != EMPTY && gameBoard[row+1][col+1] != available){	//if up there is an opponent's pawn
				row++;
				col++;
				while(row<7 && col<7){
					if (gameBoard[row+1][col+1] == EMPTY || gameBoard[row+1][col+1] == available){								//if you find EMPTY - not valid
						return false;
					}
					if (gameBoard[row+1][col+1] == letter){ 							//if there is a player's pawn
						return true;													//valid
					}
				row++;
				col++;
				}
			}
		}
		return false;
	}
	
	public boolean isValidDown(int row, int col, int letter){
		if(row < 6){																//if row < 6 - sure not valid
			if(gameBoard[row+1][col] != letter && gameBoard[row+1][col] != EMPTY && gameBoard[row+1][col] != available){	//if down there is an opponent's pawn
				for(int i=row+1; i<7; i++){ 										//then search from that pawn to edge
					if (gameBoard[i+1][col] == EMPTY || gameBoard[row+1][col] == available){							//if you find EMPTY - not valid
						return false;
					}
					if (gameBoard[i+1][col] == letter){ 							//if there is a player's pawn
						return true;												//valid
					}
				}
			}
		}
		return false;
	}
	
	public boolean isValidDownLeft(int row, int col, int letter){
		if(row < 6 && col > 1){															//check if there is room down and left
			if(gameBoard[row+1][col-1] != letter && gameBoard[row+1][col-1] != EMPTY && gameBoard[row+1][col-1] != available){	//if up there is an opponent's pawn
				row++;
				col--;
				while(row<7 && col>0){
					if (gameBoard[row+1][col-1] == EMPTY || gameBoard[row+1][col-1] == available){								//if you find EMPTY - not valid
						return false;
					}
					if (gameBoard[row+1][col-1] == letter){ 							//if there is a player's pawn
						return true;													//valid
					}
				row++;
				col--;
				}
			}
		}
		return false;
	}
	
	public boolean isValidLeft(int row, int col, int letter){
		if(col > 1){																//if col > 1 - sure not valid
			if(gameBoard[row][col-1] != letter && gameBoard[row][col-1] != EMPTY && gameBoard[row][col-1] != available){	//if left there is an opponent's pawn
				for(int i=col-1; i>0; i--){ 										//then search from that pawn to edge
					if (gameBoard[row][i-1] == EMPTY || gameBoard[row][col-1] == available){							//if you find EMPTY - not valid
						return false;
					}
					if (gameBoard[row][i-1] == letter){ 							//if there is a player's pawn
						return true;												//valid
					}	
					
				}
			}
		}
		return false;
	}
	
	public boolean isValidLeftUp(int row, int col, int letter){
		if(row > 1 && col > 1){															//check if there is room left and up
			if(gameBoard[row-1][col-1] != letter && gameBoard[row-1][col-1] != EMPTY && gameBoard[row-1][col-1] != available){	//if up there is an opponent's pawn
				row--;
				col--;
				while(row>0 && col>0){
					if (gameBoard[row-1][col-1] == EMPTY || gameBoard[row-1][col-1] == available){								//if you find EMPTY - not valid
						return false;
					}
					if (gameBoard[row-1][col-1] == letter){ 							//if there is a player's pawn
						return true;													//valid
					}
				row--;
				col--;
				}
			}
		}
		return false;
	}


	public boolean isValid(int row, int col, int letter)
	{
		if (isValidUp(row, col, letter) || isValidUpRight(row, col, letter) || isValidRight(row, col, letter) || isValidRightDown(row, col, letter) || isValidDown(row, col, letter) || isValidDownLeft(row, col, letter) || isValidLeft(row, col, letter) || isValidLeftUp(row, col, letter))
		{
			return true;
		}
		
		return false;
	}

	
    //Checks whether a move is valid; whether a square is empty
	public boolean isValidMove(int row, int col, int letter)
	{
		if ((row == -1) || (col == -1) || (row > 7) || (col > 7))
		{
			return false;
		}
		if (gameBoard[row][col] != EMPTY && gameBoard[row][col] != available)
		{
			return false;
		}
		if (!isValidUp(row, col, letter) && !isValidUpRight(row, col, letter) && !isValidRight(row, col, letter) && !isValidRightDown(row, col, letter) && !isValidDown(row, col, letter) && !isValidDownLeft(row, col, letter) && !isValidLeft(row, col, letter) && !isValidLeftUp(row, col, letter))
		{
			return false;
		}
		return true;
	}

	//Make a move; it places a letter in the board
		public void makeMove(int row, int col, int letter)
		{			
			gameBoard[row][col] = letter;
			lastMove = new Move(row, col);
			lastLetterPlayed = letter;
			
			if (isValidUp(row, col, letter)){
				int i = row-1;
				while(gameBoard[i][col] != letter) //while up there is an opponent's pawn
				{
					gameBoard[i][col] = letter; //Change opponent's pawn in player's
					i--;
				}			
			}
			
			if (isValidUpRight(row, col, letter)){
				int i = row-1;
				int j = col+1;
				while(gameBoard[i][j] != letter) //while up there is an opponent's pawn
				{
					gameBoard[i][j] = letter; //Change opponent's pawn in player's
					i--;
					j++;
				}	
			}
			
			if (isValidRight(row, col, letter)){
				int i = col+1;
				while(gameBoard[row][i] != letter) //while up there is an opponent's pawn
				{
					gameBoard[row][i] = letter; //Change opponent's pawn in player's
					i++;
				}	
			}

			if (isValidRightDown(row, col, letter)){
				int i = row+1;
				int j = col+1;
				while(gameBoard[i][j] != letter) //while up there is an opponent's pawn
				{
					gameBoard[i][j] = letter; //Change opponent's pawn in player's
					i++;
					j++;
				}
			}

			if (isValidDown(row, col, letter)){
				int i = row+1;
				while(gameBoard[i][col] != letter) //while up there is an opponent's pawn
				{
					gameBoard[i][col] = letter; //Change opponent's pawn in player's
					i++;
				}	
			}

			if (isValidDownLeft(row, col, letter)){
				int i = row+1;
				int j = col-1;
				while(gameBoard[i][j] != letter) //while up there is an opponent's pawn
				{
					gameBoard[i][j] = letter; //Change opponent's pawn in player's
					i++;
					j--;
				}
			}

			if (isValidLeft(row, col, letter)){
				int i = col-1;
				while(gameBoard[row][i] != letter) //while up there is an opponent's pawn
				{
					gameBoard[row][i] = letter; //Change opponent's pawn in player's
					i--;
				}	
			}

			if (isValidLeftUp(row, col, letter)){
				int i = row-1;
				int j = col-1;
				while(gameBoard[i][j] != letter) //while up there is an opponent's pawn
				{
					gameBoard[i][j] = letter; //Change opponent's pawn in player's
					i--;
					j--;
				}
			}
			
			
		}
	
		
	public void	placeAvailablePositions(int letter){
		//The children-moves of the state are calculated
		ArrayList<Board> children = new ArrayList<Board>(getChildren(letter));
		for (Board child : children){
			gameBoard[child.getLastMove().getRow()][child.getLastMove().getCol()] = available;
		}
	}
	
	public void removeAvailablePositions(int letter){
		//The children-moves of the state are calculated
		ArrayList<Board> children = new ArrayList<Board>(getChildren(letter));
		for (Board child : children){
			gameBoard[child.getLastMove().getRow()][child.getLastMove().getCol()] = EMPTY;
		}
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
				if(isValidMove(row, col, letter))
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
		int value=0;
		return value;
	}

    /*
     * A state is terminal if there is a tic-tac-toe
     * or no empty tiles are available
     */
    public boolean isTerminal()
    {
        //Checking if there is at least one empty tile
        for(int row=0; row<8; row++)
		{
			for(int col=0; col<8; col++)
			{
				if(gameBoard[row][col] == EMPTY || gameBoard[row][col] == available)
				{
                    return false;
                }
            }
		}        
        //thelei tsekarisma
        //Check if there is no available moves
        if(!noMoves(Board.W) || !noMoves(Board.B)){
        	return false;
        }
        
        return true;
    }

    //Finds the winner
    public String winner(){
    	int sumW = 0;
        int sumB = 0;
        String winner = "White";
        for(int row=0; row<8; row++){
        	for(int col=0; col<8; col++){
        		if(gameBoard[row][col] == W){
                    sumW++;
                }
        		if(gameBoard[row][col] == B){
                    sumB++;
        		}
 			}
        }
        if(sumB>sumW){
        	winner = "Black";
        }
        return "Total white = "+sumW+", total black = "+sumB+", the winner is "+winner;
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
					case available:
						System.out.print(". ");
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
