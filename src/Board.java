import java.util.ArrayList;
import java.util.Scanner;

public class Board{	
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

	//board array
	private int [][] gameBoard;
	
	//grade board array
	private int[][] gradeBoard = new int[][]{
			{ 10000, -2000, 1000,  500,  500, 1000, -2000, 10000 },
			{ -2000, -5000, -250, -200, -200, -250, -5000, -2000 },
			{ 1000,   -250,  -50, -100, -100,  -50,  -250,  1000 },
			{ 500,    -200, -100,    0,    0, -100,  -200,   500 },
			{ 500,    -200, -100,    0,    0, -100,  -200,   500 },
			{ 1000,   -250,  -50, -100, -100,  -50,  -250,  1000 },
			{ -2000, -5000, -250, -200, -200, -250, -5000, -2000 },
			{ 10000, -2000, 1000,  500,  500, 1000, -2000, 10000 },
		};
	
	public Board(){
		lastMove = new Move();
		lastLetterPlayed = B;
		gameBoard = new int[8][8];
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				gameBoard[i][j] = EMPTY;
			}
			gameBoard[3][3] = W;
			gameBoard[4][4] = W;
			gameBoard[4][3] = B;
			gameBoard[3][4] = B;
		}
	}
	
	public Board(Board board){
		lastMove = board.lastMove;
		lastLetterPlayed = board.lastLetterPlayed;
		gameBoard = new int[8][8];
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				gameBoard[i][j] = board.gameBoard[i][j];
			}
		}
	}
		
	public Move getLastMove(){
		return lastMove;
	}
	
	public int getLastLetterPlayed(){
		return lastLetterPlayed;
	}
	
	public int[][] getGameBoard(){
		return gameBoard;
	}

	public void setLastMove(Move lastMove){
		this.lastMove.setRow(lastMove.getRow());
		this.lastMove.setCol(lastMove.getCol());
		this.lastMove.setValue(lastMove.getValue());
	}
	
	public void setLastLetterPlayed(int lastLetterPlayed){
		this.lastLetterPlayed = lastLetterPlayed;
	}
	
	public void setGameBoard(int[][] gameBoard){
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
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
	
	//Handle the user's entrance
	public void handleEntrance(){
		try {	      		
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
			else{
				System.out.println("Invalid Entrance, please try again");
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid Entrance, please try again");
		} catch (NumberFormatException ex) {
			System.out.println("Invalid Entrance, please try again");
		}
	}
	
	//These functions check if a move is valid and in which directions
	public boolean isValidUp(int row, int col, int letter){
		if(row > 1){																//if row <= 1 - sure not valid
			if(gameBoard[row-1][col] != letter && gameBoard[row-1][col] != EMPTY){	//if up there is an opponent's pawn
				for(int i=row-1; i>0; i--){ 										//then search from that pawn to edge
					if (gameBoard[i-1][col] == EMPTY || gameBoard[row-1][col] == available){	//if you find EMPTY - not valid
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
					if (gameBoard[row-1][col+1] == EMPTY || gameBoard[row-1][col+1] == available){	//if you find EMPTY - not valid
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
					if (gameBoard[row][i+1] == EMPTY || gameBoard[row][col+1] == available){	//if you find EMPTY - not valid
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
					if (gameBoard[row+1][col+1] == EMPTY || gameBoard[row+1][col+1] == available){	//if you find EMPTY - not valid
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
					if (gameBoard[i+1][col] == EMPTY || gameBoard[row+1][col] == available){	//if you find EMPTY - not valid
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
					if (gameBoard[row+1][col-1] == EMPTY || gameBoard[row+1][col-1] == available){	//if you find EMPTY - not valid
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
					if (gameBoard[row][i-1] == EMPTY || gameBoard[row][col-1] == available){	//if you find EMPTY - not valid
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
					if (gameBoard[row-1][col-1] == EMPTY || gameBoard[row-1][col-1] == available){	//if you find EMPTY - not valid
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
	
    //Checks whether a move is valid
	public boolean isValidMove(int row, int col, int letter){
		//if out of bounds
		if ((row == -1) || (col == -1) || (row > 7) || (col > 7)){
			return false;
		}
		//if there is already a pawn
		if (gameBoard[row][col] != EMPTY && gameBoard[row][col] != available){
			return false;
		}
		//if there is at least one of opponent's pawns to flip
		if (!isValidUp(row, col, letter) && !isValidUpRight(row, col, letter) && !isValidRight(row, col, letter) && !isValidRightDown(row, col, letter) && !isValidDown(row, col, letter) && !isValidDownLeft(row, col, letter) && !isValidLeft(row, col, letter) && !isValidLeftUp(row, col, letter)){
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
	
	//Make a move, it places a letter in the board
	public void makeMove(int row, int col, int letter){			
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
	public ArrayList<Board> getChildren(int letter){
		ArrayList<Board> children = new ArrayList<Board>();
		for(int row=0; row<8; row++){
			for(int col=0; col<8; col++){
				if(isValidMove(row, col, letter)){
					Board child = new Board(this);
					child.makeMove(row, col, letter);
					children.add(child);
				}
			}
		}
		return children;
	}

    //The heuristic we use to evaluate every state
	public int evaluate() {
		
		int value = 0;
		
		//POSITIONAL		
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				value += gameBoard[i][j] * gradeBoard[i][j];
			}
		}
		
		//MOBILITY
		ArrayList<Board> WMobility = new ArrayList<Board>(getChildren(W));	//available W moves
		ArrayList<Board> WCornerMobility = new ArrayList<Board>();			//available W corner moves
		for (Board child : WMobility){
			int row = child.lastMove.getRow();
			int col = child.lastMove.getCol();
			if (row < 2 && col < 2 || row < 2 && col > 6 || row > 6 && col < 2 || row > 6 && col > 6){
				WCornerMobility.add(child);
			}
		}
		ArrayList<Board> BMobility = new ArrayList<Board>(getChildren(B));	//available B moves
		ArrayList<Board> BCornerMobility = new ArrayList<Board>();			//available B corner moves
		for (Board child : BMobility){
			int row = child.lastMove.getRow();
			int col = child.lastMove.getCol();
			if (row < 2 && col < 2 || row < 2 && col > 6 || row > 6 && col < 2 || row > 6 && col > 6){
				BCornerMobility.add(child);
			}
		}
		
		if(WMobility.size() + BMobility.size()!=0){
			value += 10*(WCornerMobility.size() - BCornerMobility.size()) + ((WMobility.size() - BMobility.size())/(WMobility.size() + BMobility.size())) ;
		}
		
		//GREED
		//Having more pieces than opponent is values, especially late game
		if(Pawns(W) + Pawns(B) > 55 && getWinner() == W) {
			value += 1000;
		}
		
		//STABILITY
		//Try to have one piece in each col/row midgame
		if (Pawns(W) + Pawns(B) > 20 && Pawns(W) + Pawns(B) < 50) {
			for(int row = 0; row < 8; row++) {
				int sum = 0;
				for(int col = 0; col < 8; col++) {
					if (gameBoard[row][col] == W) {
						sum++;
					}
				}
				if (sum > 0) {
					value += 5;
				}
			}
			for(int col = 0; col < 8; col++) {
				int sum = 0;
				for(int row = 0; row < 8; row++) {
					if (gameBoard[row][col] == W) {
						sum++;
					}
				}
				if (sum > 0) {
					value += 5;
				}
			}			
		}
		
		//Secured rows are valuable
		for(int col = 0; col < 8; col+=7) {
			for(int row = 0; row < 8; row++) {
				if (gameBoard[0][col] == W) {
					if(gameBoard[row][col] != W) {
						break;
					}
					value += 5;
				}
			}
			for(int row = 8 - 1; row >= 0; row--) {
				if (gameBoard[7][col] == W) {
					if(gameBoard[row][col] != W) {
						break;
					}
					value += 5;
				}
			}
		}
				
		//Secured cols are valuable
		for(int row = 0; row < 8; row+=7) {
			for(int col = 0; col < 8; col++) {
				if (gameBoard[row][0] == W) {
					if(gameBoard[row][col] != W) {
						break;
					}
					value += 5;
				}
			}
			for(int col = 8 - 1; col >= 0; col--) {
				if (gameBoard[7][col] == W) {
					if(gameBoard[row][col] != W) {
						break;
					}
					value += 5;
				}
			}
		}
			
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
    public boolean isTerminal(){
        //Check if there is no available moves
        if(noMoves(Board.W) && noMoves(Board.B)){
        	return true;
        }
        //Check if there is at least one empty tile
        for(int row=0; row<8; row++){
			for(int col=0; col<8; col++){
				if(gameBoard[row][col] == EMPTY || gameBoard[row][col] == available){
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
	public void print(){
		System.out.println("  A B C D E F G H  ");
		for(int row=0; row<8; row++){
			int index = row+1;
			System.out.print("" + index + " ");
			for(int col=0; col<8; col++){
				switch (gameBoard[row][col]){
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
			System.out.print("" + index + " ");
			System.out.println("");
		}
		System.out.println("  A B C D E F G H  ");
		System.out.println("");
	}
	
}
