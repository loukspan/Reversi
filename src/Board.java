import java.util.ArrayList;
import java.util.Scanner;

public class Board
{	
	Scanner sc = new Scanner(System.in);
	
    //Variables for the Boards values
	//W=white, B=black, available = available moves
	public static final int W = 1;
	public static final int B = -1;
	public static final int EMPTY = 0;
	public static final int available = 2;
    
    //Immediate move that lead to this board
    private Move lastMove;

    //Who played last
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
	
	//Checks if the player has available moves
	public boolean noMoves(int letter){
		ArrayList<Board> children = new ArrayList<Board>(getChildren(letter));
		if(children.isEmpty()){
			return true;
		}
		return false;
	}
	
	//Handle the user's turn
	public void handleEntrance(){
		placeAvailablePositions(Board.B);
		print();
		removeAvailablePositions(Board.B);
		
		//If there are no moves user can't play
		if(noMoves(Board.B)){
			System.out.println("Black has no moves");
			lastLetterPlayed = Board.B;
		}
		else{
	      	try {
	      		System.out.println("It's Black's move");
				System.out.println("Enter your move (e.g. A1)");
				String position = sc.next();
				String[] parts = position.split("");
				int row = Integer.parseInt(parts[1])-1;	
				int col = -1;
				if(parts[0].equalsIgnoreCase("A")) {col = 0;}
				else if(parts[0].equalsIgnoreCase("B")) {col = 1;}
				else if(parts[0].equalsIgnoreCase("C")) {col = 2;}
				else if(parts[0].equalsIgnoreCase("D")) {col = 3;}
				else if(parts[0].equalsIgnoreCase("E")) {col = 4;}
				else if(parts[0].equalsIgnoreCase("F")) {col = 5;}
				else if(parts[0].equalsIgnoreCase("G")) {col = 6;}
				else if(parts[0].equalsIgnoreCase("H")) {col = 7;}
				if(isValidMove(row, col, Board.B)) {        		
					makeMove(row, col, Board.B);
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Invalid Entrance, please try again");
			} catch (NumberFormatException ex) {
				System.out.println("Invalid Entrance, please try again");
			}
		}
	}
	
	//These functions check if a move is valid and in which directions
	public boolean isValidUp(int row, int col, int letter){
		if(row > 1){																//if row <= 1 - sure not valid
			if(gameBoard[row-1][col] != letter && gameBoard[row-1][col] != EMPTY){	//if up there is an opponent's pawn
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
			if(gameBoard[row-1][col+1] != letter && gameBoard[row-1][col+1] != EMPTY){	//if up there is an opponent's pawn
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
			if(gameBoard[row][col+1] != letter && gameBoard[row][col+1] != EMPTY){	//if right there is an opponent's pawn
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
			if(gameBoard[row+1][col+1] != letter && gameBoard[row+1][col+1] != EMPTY){	//if up there is an opponent's pawn
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
			if(gameBoard[row+1][col] != letter && gameBoard[row+1][col] != EMPTY){	//if down there is an opponent's pawn
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
			if(gameBoard[row+1][col-1] != letter && gameBoard[row+1][col-1] != EMPTY){	//if up there is an opponent's pawn
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
			if(gameBoard[row][col-1] != letter && gameBoard[row][col-1] != EMPTY){	//if left there is an opponent's pawn
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
			if(gameBoard[row-1][col-1] != letter && gameBoard[row-1][col-1] != EMPTY){	//if up there is an opponent's pawn
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


	//Check if a move is valid in at least one direction
	public boolean isValid(int row, int col, int letter)
	{
		if (isValidUp(row, col, letter) || isValidUpRight(row, col, letter) || isValidRight(row, col, letter) || isValidRightDown(row, col, letter) || isValidDown(row, col, letter) || isValidDownLeft(row, col, letter) || isValidLeft(row, col, letter) || isValidLeftUp(row, col, letter))
		{
			return true;
		}
		
		return false;
	}

	
    //Checks whether a move is valid
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

	//Flip the pawns 
	public void flip(int row, int col, int letter){
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
	
	//Make a move; it places a letter in the board
		public void makeMove(int row, int col, int letter)
		{			
			gameBoard[row][col] = letter;
			lastMove = new Move(row, col);
			lastLetterPlayed = letter;
			flip(row, col, letter);
		}
	
	//Place the available moves in the board so the user can see
	public void	placeAvailablePositions(int letter){
		//The children-moves of the state are calculated
		ArrayList<Board> children = new ArrayList<Board>(getChildren(letter));
		for (Board child : children){
			gameBoard[child.getLastMove().getRow()][child.getLastMove().getCol()] = available;
		}
	}
	
	//Remove the available moves from the board to continue with the next move
	public void removeAvailablePositions(int letter){
		//The children-moves of the state are calculated
		ArrayList<Board> children = new ArrayList<Board>(getChildren(letter));
		for (Board child : children){
			gameBoard[child.getLastMove().getRow()][child.getLastMove().getCol()] = EMPTY;
		}
	}
		
    //Generates the children of the state
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

    //The heuristic we use to evaluate every state
	public int evaluate(int letter) {
		int opp = B;
		if (letter == B) {
			opp = W;
		}
		return evaluateState(letter, opp) - evaluateState(opp, letter);
	}
	
	public int evaluateState(int letter, int opp)
	{
		int value = 0;
		
		//STABILITY
		int[][] gradeBoard = new int[][]{
			  { 400, -20, 10, 5, 5, 10, -20, 400 },
			  { -20, -50, -2, -2, -2, -2, -50, -20 },
			  { 10, -2, -1, -1, -1, -1, -2, 10 },
			  { 5, -2, -1, -1, -1, -1, -2, 5 },
			  { 5, -2, -1, -1, -1, -1, -2, 5 },
			  { 10, -2, -1, -1, -1, -1, -2, 10 },
			  { -20, -50, -2, -2, -2, -2, -50, -20 },
			  { 400, -20, 10, 5, 5, 10, -20, 400 },
			};
		
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				if(gameBoard[i][j] == letter)
			value += gradeBoard[i][j];
			}
		}
		/*
		//MOBILITY
		//Minimize opponent's moves mid to late game
		if (Pawns(W) + Pawns(B) > 30) {
			ArrayList<Board> children = new ArrayList<Board>(getChildren(opp));
			if(children.size() == 0) {
				value += 5;
			} else if (children.size() < 3) {
				value += 2;
			} else {
				value -= 2;//children.size();
			}
		}
		/*
		if (getWinner() == letter && Pawns(W) + Pawns(B) > 55) {
			return 10000;
		}
		
		//CORNER STABILITY
		if (gameBoard[0][0] == letter || (lastLetterPlayed != letter && isValid(0,0,letter))) {
			value += 400;
		}
		if (gameBoard[7][7] == letter || (lastLetterPlayed != letter && isValid(7,7,letter))) {
			value += 400;
		}
		if (gameBoard[0][7] == letter || (lastLetterPlayed != letter && isValid(0,7,letter))) {
			value += 400;
		}
		if (gameBoard[7][0] == letter || (lastLetterPlayed != letter && isValid(7,0,letter))) {
			value += 400;
		}
				
		//Letting corners for the opponent is bad
		if (gameBoard[0][1] == letter && isValid(0, 0, opp)) {
			value += 200;
		}
		if (gameBoard[1][0] == letter && isValid(0, 0, opp)) {
			value += 200;
		}
		if (gameBoard[1][1] == letter && gameBoard[0][0] == EMPTY) {
			value -= 200;
		}
		if (gameBoard[6][7] == letter && isValid(7, 7, opp)) {
			value += 200;
		}
		if (gameBoard[7][6] == letter && isValid(7, 7, opp)) {
			value += 200;
		}
		if (gameBoard[6][6] == letter && gameBoard[7][7] == EMPTY) {
			value -= 200;
		}
		if (gameBoard[1][7] == letter && isValid(0, 7, opp)) {
			value += 200;
		}
		if (gameBoard[0][6] == letter && isValid(0, 7, opp)) {
			value += 200;
		}
		if (gameBoard[1][6] == letter && gameBoard[0][7] == EMPTY) {
			value -= 200;
		}
		if (gameBoard[7][1] == letter && isValid(7, 0, opp)) {
			value += 200;
		}
		if (gameBoard[6][0] == letter && isValid(7, 0, opp)) {
			value += 200;
		}
		if (gameBoard[6][1] == letter && gameBoard[7][0] == EMPTY) {
			value -= 200;
		}
		
		//EDGE STABILITY
		for(int row = 0; row < 8; row++) {
			if (gameBoard[row][0] == letter) {
				value += 20;
			}
			if (gameBoard[row][7] == letter) {
				value += 20;
			}
		}
		for(int col = 0; col < 8; col++) {
			if (gameBoard[0][col] == letter) {
				value += 20;
			}
			if (gameBoard[7][col] == letter) {
				value += 20;
			}
		}
		
		//MOBILITY
		
		//Having more pieces than opponent is values, especially late game
		if(Pawns(W) + Pawns(B) > 50) {
			value += 20*(Pawns(letter) - Pawns(opp));
		}
				
		
	
		//Try to have one piece in each col/row midgame
		if (Pawns(W) + Pawns(B) > 20 && Pawns(W) + Pawns(B) < 50) {
			for(int row = 0; row < 8; row++) {
				int sum = 0;
				for(int col = 0; col < 8; col++) {
					if (gameBoard[row][col] == letter) {
						sum++;
					}
				}
				if (sum > 0) {
					value += 10;
				}
			}
			for(int col = 0; col < 8; col++) {
				int sum = 0;
				for(int row = 0; row < 8; row++) {
					if (gameBoard[row][col] == letter) {
						sum++;
					}
				}
				if (sum > 0) {
					value += 10;
				}
			}			
		}
		
		//Secured rows are valuable
		for(int col = 0; col < 8; col+=7) {
			for(int row = 0; row < 8; row++) {
				if (gameBoard[0][col] == letter) {
					if(gameBoard[row][col] != letter) {
						break;
					}
					value += 20;
				}
			}
			for(int row = 8 - 1; row >= 0; row--) {
				if (gameBoard[7][col] == letter) {
					if(gameBoard[row][col] != letter) {
						break;
					}
					value += 20;
				}
			}
		}
				
		//Secured cols are valuable
		for(int row = 0; row < 8; row+=7) {
			for(int col = 0; col < 8; col++) {
				if (gameBoard[row][0] == letter) {
					if(gameBoard[row][col] != letter) {
						break;
					}
					value += 20;
				}
			}
			for(int col = 8 - 1; col >= 0; col--) {
				if (gameBoard[7][col] == letter) {
					if(gameBoard[row][col] != letter) {
						break;
					}
					value += 20;
				}
			}
		}
		*/
		return value;
	}

	 //Count player's pawns
    public int Pawns(int letter){
    	int sum = 0; 
    	for(int row=0; row<8; row++){
         	for(int col=0; col<8; col++){
         		if(gameBoard[row][col] == letter){
                     sum++;
                 }
  			}
        }
    	return sum;
    }
	
    //Checks if a state is is terminal
    public boolean isTerminal()
    {
    	// needs tseking
        //Check if there is no available moves
        if(noMoves(Board.W) && noMoves(Board.B)){
        	return true;
        }
        //Check if there is at least one empty tile
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
        
        return true;
    }

    //Finds the winner
    public int getWinner(){
        if(Pawns(B)>Pawns(W)){
        	return B;
        }
        //Return 0 if we have no winner
        if(Pawns(B)==Pawns(W)){
        	return 0;
        }
        return W;
    }
    
    //Print outcome
    public String toStringWinner(){
    	if(Pawns(B)==Pawns(W)){
    		return "It's a tie with "+Pawns(W)+" pawns each";
    	}
    	if(getWinner()==B){
    		return "White has "+Pawns(W)+" pawns, Black has "+Pawns(B)+" pawns, so the winner is Black";
    	}
    	return "White has "+Pawns(W)+" pawns, Black has "+Pawns(B)+" pawns, so the winner is White";
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
