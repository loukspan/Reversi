import GUI.ReversiBoard;
import java.util.Scanner;

public class Main { 
	
	public static void main(String[] args)
	{
		//We create the players and the board
        //MaxDepth for the MiniMax algorithm is set to 2; feel free to change the values
		
		//o max einai o pc o min einai oi kiniseis pou pisteuei to pc oti tha kanei o antipalos
		
		
		Scanner sc = new Scanner(System.in);
		//Ask maxDepth (default = 2)
		int maxDepth = 2;
		System.out.println("Enter MaxDepth for the MiniMax algorithm: ");
        maxDepth = sc.nextInt();        
        
		//X=white, O=black
		GamePlayer WPlayer = new GamePlayer(maxDepth, Board.W); //PC
		Board board = new Board();

		//Ask who will play first		
		System.out.println("Do you want to play first? ");
        String firstPlayer = sc.next();
        if(firstPlayer.equalsIgnoreCase("YES")){
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
					board.handleEntrance();
					break;
                //If B (Player) played last, then W (PC) plays now
				case Board.B:
                    System.out.println("W moves");
					Move WMove = WPlayer.MiniMax(board);
					if(board.noMoves(Board.W)){
						System.out.println("White has no moves");
						board.setLastLetterPlayed(Board.W);
					}
					else{
						board.makeMove(WMove.getRow(), WMove.getCol(), Board.W);
					}
					break;
				default:
					break;
			}
			board.print();
		}
		System.out.println(board.winner());
		sc.close();
	}
}
