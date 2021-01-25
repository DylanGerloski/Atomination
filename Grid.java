public class Grid {
	private Player owner;
	private int atomCount;
	private int xMaxBound;
	private int yMaxBound;
	private int xMinBound;
	private int yMinBound;

	public Grid(int xMaxBound, int yMaxBound, int xMinBound, int yMinBound){
		this.xMaxBound = xMaxBound;
		this.yMaxBound = yMaxBound;
		this.xMinBound = xMinBound;
		this.yMinBound = yMinBound;
	}
	public Grid(){

	}

	public int getXMaxBound(){
		return this.xMaxBound;
	}
	public int getYMaxBound(){
		return this.yMaxBound;
	}
	public int getXMinBound(){
		return this.xMinBound;
	}
	public int getYMinBound(){
		return this.yMinBound;
	}


  public String toString(){
    if(owner == null){
      return "  ";
    }else {
      return Character.toString(owner.getColor()) + atomCount;
    }
  }

  public void setOwner(Player p){
    this.owner = p;
  }

	public Player getOwner(){
		return this.owner;
	}
  public void setAtomCount(int i){
    this.atomCount = i;
  }

	public int getAtomCount(){
		return atomCount;
	}


}
