import GUI.ReversiBoard;

public class Main {
	

	public static void main(String[] args) {
		ReversiBoard ReversiBoard = new ReversiBoard();
		char[][] myBoard = new char[9][9];
		ReversiBoard.InitialState(myBoard);
		ReversiBoard.PrintB(myBoard);

	}

}
