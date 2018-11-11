import java.util.Scanner;

public class Main { 
	
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		//Default MaxDepth for the MiniMax algorithm is set to 2;
		int maxDepth = 2;
		System.out.println("Enter MaxDepth for the MiniMax algorithm: ");
        maxDepth = sc.nextInt();        
        //We create the players and the board
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
					board.print();
					break;
                //If B (Player) played last, then W (PC) plays now
				case Board.B:
					System.out.println("It's White's move");
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
		}
		
		//Print the winner
		System.out.println(board.toStringWinner());
		sc.close();
	}
}
