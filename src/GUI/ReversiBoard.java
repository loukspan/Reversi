package GUI;

public class ReversiBoard {

	public ReversiBoard () {
		
	}
	public char[][] InitialState(char[][] Board) {
		for(int i = 0; i < 9; i++){
			for (int j = 0 ; j < 9; j++){
				if(j != 0)Board[i][j] = '-';
				
				if((i == 4 && j == 4) || (i == 5 && j == 5)){
					Board[4][4] = 'X';
					Board[5][5] = 'X';
				}
				if((i == 4 && j == 5) || (i == 5 && j == 4)){
					Board[4][5] = 'O';
					Board[5][4] = 'O';
				}
				//if(i==0 && j==1) Board[0][1] = 'A';
				//if(i==0 && j==2) Board[0][2] = 'B';
				char x = 'A';
				Board[0][1] = x;
				if(j<7) Board[0][j+2] = (char) (x+j+1);
				
				char y = '1';
				Board[1][0] = y;
				if(i<7) Board[i+2][0] = (char) (y+i+1);
			}
		}
		return Board;
		
	}
	
	public void PrintB(char[][] Board) {
		for(int i = 0; i < 9; i++){
			for (int j = 0 ; j < 9; j++){
				System.out.print(Board[i][j]);
			}
			System.out.println(" ");
		}
	}
	
}


