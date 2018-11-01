import GUI.ReversiBoard;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) 
	{
		/*
		ReversiBoard ReversiBoard = new ReversiBoard();
		char[][] myBoard = new char[9][9];
		ReversiBoard.InitialState(myBoard);
		ReversiBoard.PrintB(myBoard);
		*/

		//We create the players and the board
        //MaxDepth for the MiniMax algorithm is set to 2; feel free to change the values
		//maxDepth poses epomenes kiniseis koitaei to pc
		
		//o max einai o pc o min einai oi kiniseis pou pisteuei to pc oti tha kanei o antipalos
		
		
		Scanner sc = new Scanner(System.in);
		//Ask maxDepth (default = 2)
		int maxDepth = 2;
		System.out.println("Enter MaxDepth for the MiniMax algorithm: ");
        maxDepth = sc.nextInt();        
        
		//X=white, O=black
		GamePlayer WPlayer = new GamePlayer(maxDepth, Board.W); //PC
		GamePlayer BPlayer = new GamePlayer(maxDepth, Board.B); //Player
		Board board = new Board();

		//Ask who will play first
		
		System.out.println("Do you want to play first? ");
        String firstPlayer = sc.next();
        if(firstPlayer.equalsIgnoreCase("YES")){
        	//Put this for the B (Player) to play first
        	board.setLastLetterPlayed(Board.W);
        }
        
		board.print();
				
        //While the game has not finished
		while(!board.isTerminal())
		{
			System.out.println();
			switch (board.getLastLetterPlayed())
			{
                //If W (PC) played last, then B (Player) plays now
				case Board.W:
                    System.out.println("B moves");
                    System.out.println("Enter number of row");
                    int row = sc.nextInt();
                    System.out.println("Enter number of column");
                    int col = sc.nextInt();				
					board.makeMove(row, col, Board.B);
					break;
                //If B (Player) played last, then W (PC) plays now
				case Board.B:
                    System.out.println("W moves");
					Move WMove = WPlayer.MiniMax(board);
					board.makeMove(WMove.getRow(), WMove.getCol(), Board.W);
					break;
				default:
					break;
			}
			board.print();
		}

		sc.close();
		
	}
}
