public class Player {
	private int gridsOwned;
	private char color;
	private boolean checkIfLost;


	public Player(char color){
		this.color = color;
		this.gridsOwned = 0;
		this.checkIfLost = false;
	}

	public char getColor(){
		return color;
	}

	public void setLost(){
		this.checkIfLost = true;
	}

	public boolean getCheckIfLost(){
		return checkIfLost;
	}

	public void setColor(char c){
		this.color = c;
	}

	public int getGridsOwned(){
		return gridsOwned;
	}

	public void setGridsOwned(int gridsOwned){
		this.gridsOwned = gridsOwned;
	}

	public String toString(){
		if(color == 'R'){
			return "Red";
		}else if(color == 'G'){
			return "Green";
		}else if(color == 'B'){
			return "Blue";
		}else{
			return "Purple";
		}
	}
}
