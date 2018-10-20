package GUI;

public class OthelloBoard {

	public OthelloBoard () {
		
	}
	public char[][] InitialState(char[][] Board) {
		for(int i = 0; i < 8; i++){
			for (int j = 0 ; j < 8; j++){
				Board[i][j] = '-';
				if((i == 3 && j == 3) || (i == 4 && j == 4)){
					Board[3][3] = 'X';
					Board[4][4] = 'X';
				}
				if((i == 3 && j == 4) || (i == 4 && j == 3)){
					Board[3][4] = 'O';
					Board[4][3] = 'O';
				}
			}
		}
		return Board;
		
	}
	
	public void PrintB(char[][] Board) {
		for(int i = 0; i < 8; i++){
			for (int j = 0 ; j < 8; j++){
				System.out.print(Board[i][j]);
			}
			System.out.println(" ");
		}
	}
	
}


