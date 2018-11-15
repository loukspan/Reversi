import java.util.Scanner;

public class Main { 
	
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		//Default MaxDepth for the MiniMax algorithm is set to 2;
		int maxDepth = 2;
		System.out.println("Enter MaxDepth for the MiniMax algorithm: ");
        maxDepth = sc.nextInt();        
        //We create the player PC and the board
		GamePlayer WPlayer = new GamePlayer(maxDepth, Board.W); //PC
		Board board = new Board(); //Board

		board.print();
		
		//Ask who will play first		
		System.out.println("Do you want to play first? ");
        String firstPlayer = sc.next();
        if(firstPlayer.equalsIgnoreCase("YES")){
        	board.setLastLetterPlayed(Board.W);
        }
        				
        //While the game has not finished
		while(!board.isTerminal()){
			System.out.println();
			switch (board.getLastLetterPlayed()){
                //If W (PC) played last, then B (Player) plays now
				case Board.W:
					System.out.println("It's Black's move");
					System.out.println("Dots ('.') are your available moves!");
					board.placeAvailablePositions(Board.B);
					board.print();
					board.removeAvailablePositions(Board.B);
					
					//If there are no moves user can't play
					if(board.noMoves(Board.B)){
						System.out.println("Black has no moves");
						board.setLastLetterPlayed(Board.B);
					}
					else{
						board.handleEntrance();
						board.print();
					}
					
					break;
                //If B (Player) played last, then W (PC) plays now
				case Board.B:
					System.out.println("It's White's move");
                   	if(board.noMoves(Board.W)){
						System.out.println("White has no moves");
						board.setLastLetterPlayed(Board.W);
					}
					else{
						Move WMove = WPlayer.MiniMax(board);
						if(board.isValidMove(WMove.getRow(), WMove.getCol(), Board.W)) {        		
							board.makeMove(WMove.getRow(), WMove.getCol(), Board.W);
							board.print();
						}
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
