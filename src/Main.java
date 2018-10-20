import GUI.OthelloBoard;

public class Main {
	

	public static void main(String[] args) {
		OthelloBoard othelloBoard = new OthelloBoard();
		char[][] myBoard = new char[8][8];
		othelloBoard.InitialState(myBoard );
		othelloBoard.PrintB(myBoard);

	}

}
