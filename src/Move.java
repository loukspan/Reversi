

public class Move {
	
	int x,y;
	
	public Move(int x, int y) {
		this.x = x;
		this.y = y;
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean IsLegal(int x, int y) {
		if(x<0 || y<0){
			throw new IllegalArgumentException("Positions must be non-negative!");
		}
		return false;
		
	}
	
}
