import java.util.ArrayList;
import java.awt.Point;

public class Board{

  private int width;
  private int height;
  private Grid[][] boardCordinates;
  private ArrayList<Grid[][]> boardStates;
  private ArrayList<Integer> xCords;
  private ArrayList<Integer> yCords;


  public Board(int width, int height){
    this.height = height;
    this.width = width;
    this.boardStates = new ArrayList<>();
    this.boardCordinates = new Grid[this.width][this.height];
    for(int w = 0; w < width; w ++){
      for(int h = 0; h < height; h ++){
        boardCordinates[w][h] = new Grid();
      }
    }
    updateGameState();
    this.xCords = new ArrayList<>();
    this.yCords = new ArrayList<>();
  }

  public int getWidth(){return this.width;}
  public int getHeight(){return this.height;}
  public ArrayList<Integer> getYCords(){return this.yCords;}
  public ArrayList<Integer> getXCords(){return this.xCords;}
  public Grid[][] getBoardCords(){return this.boardCordinates;}


  public void displayBoard(){

    System.out.print("\n+");
    for(int w = 0; w < ((width * 3) - 1 ); w ++){
      System.out.print("-");
    }
    System.out.print("+\n");
    for(int h = 0; h < height; h ++){
      System.out.print("|");
      for(int w = 0; w < width ; w++){
        System.out.print(boardCordinates[w][h].toString() + "|");
      }
      System.out.println("");
    }
    System.out.print("+");
    for(int w = 0; w < ((width * 3) - 1 ); w ++){
      System.out.print("-");
    }
    System.out.print("+\n\n");
  }

  public boolean placeGrid(Player p, int w, int h){
    if(h >= 0 && h <height && w >= 0 && w < width){
      xCords.add(w);
      yCords.add(h);
      if(boardCordinates[w][h].getOwner() == null ){
        boardCordinates[w][h].setOwner(p);
        boardCordinates[w][h].setAtomCount(boardCordinates[w][h].getAtomCount() + 1);
        if(checkAtomCount(w,h)){
          ArrayList<Point> adj = getAdjacent(w, h);
          updateAdjacent(adj,p,w,h);
        }
        updateGameState();
        return true;
      }else if(boardCordinates[w][h].getOwner().getColor() == p.getColor()){
        boardCordinates[w][h].setOwner(p);
        boardCordinates[w][h].setAtomCount(boardCordinates[w][h].getAtomCount() + 1);
        if(checkAtomCount(w,h)){
          ArrayList<Point> adj = getAdjacent(w, h);
          updateAdjacent(adj,p,w,h);
        }
        updateGameState();
        return true;
      }else{
        return false;
      }
    }else{
      return false;
    }
  }

  public boolean isEdge(int w, int h){
    if(w - 1 < 0 || w + 1 >= width || h - 1 < 0 || h + 1 >= height){
      return true;
    }else{return false;}
  }


  public boolean isCorner(int w, int h){
    if((w == 0 && h == 0) || (w == 0 && h == height - 1) || (w == width -1 && h == 0) || (w == width - 1 && h == height - 1)){
      return true;
    }else{
      return false;
    }
  }

  public boolean checkAtomCount(int w, int h){
    if(isEdge(w,h)){
      if(isCorner(w,h)){
        if(boardCordinates[w][h].getAtomCount() >= 2){
          return true;
        }else{
          return false;
        }
      }else{
        if(boardCordinates[w][h].getAtomCount() >= 3){
          return true;
        }else{
          return false;
        }
      }
    }else{
      if(boardCordinates[w][h].getAtomCount() >= 4){
        return true;
      }else{
        return false;
      }
    }
  }

  private ArrayList<Point> getAdjacent(int w, int h){
    ArrayList<Point> points = new ArrayList<>();
    if(h > 0 ){
      int temph = h - 1;
      points.add(new Point(w,temph));
    } if(w > 0){
      int tempw = w - 1;
      points.add(new Point(tempw, h));
    }if(h < height - 1){
      int tempH = h + 1;
      points.add(new Point(w, tempH));
    } if(w < width - 1){
      int tempW = w + 1;
      points.add(new Point(tempW, h));
    }

    return points;
  }


  public void updateAdjacent(ArrayList<Point> AdjacentCordinates, Player player, int originalI, int originalJ){
    boardCordinates[originalI][originalJ].setOwner(null); //removes the atoms and owner from orignal grid to be expanded
    boardCordinates[originalI][originalJ].setAtomCount(0);
    for(Point p : AdjacentCordinates){
      if(boardCordinates[(int)p.getX()][(int)p.getY()].getOwner() == null){  //if adjacent square has no owner, set square to current player, atom count + 1
        boardCordinates[(int)p.getX()][(int)p.getY()].setOwner(player);
        boardCordinates[(int)p.getX()][(int)p.getY()].setAtomCount(1);
      }else if(boardCordinates[(int)p.getX()][(int)p.getY()].getOwner().getColor() != player.getColor()){ //if adjacent square is owned by different player
        boardCordinates[(int)p.getX()][(int)p.getY()].setOwner(player);
        boardCordinates[(int)p.getX()][(int)p.getY()].setAtomCount(boardCordinates[(int)p.getX()][(int)p.getY()].getAtomCount() + 1);
      }else {
        boardCordinates[(int)p.getX()][(int)p.getY()].setAtomCount(boardCordinates[(int)p.getX()][(int)p.getY()].getAtomCount() + 1); //if adjacent square is owned by same player
      }
      //removes players who do not own any more grid spaces
      ArrayList<Player> losers = checkIfLost(Atomination.currentGame.getPlayers());
      if(losers != null){
        for(Player lostPlayer : losers){
          Atomination.currentGame.removePlayer(lostPlayer);
          lostPlayer.setLost();
        }
      }
      /*checks if only onw player is remaining
        if true, prints that name out and exits that gmae
      */
      if(Atomination.currentGame.getPlayers().size() == 1 && Atomination.currentGame.getTurnNumber() >= Atomination.currentGame.getNumberOfPlayers()){
        System.out.println(Atomination.currentGame.getPlayers().get(0).toString() + " Wins!");
        System.exit(0);
      }
      // checks the expanded grid space to see if it also need to be exapnded
      if(checkAtomCount((int)p.getX(), (int)p.getY())){ //checks the new grid space to see if it should also be expanded, if true expands that grid
        ArrayList<Point> newAdj = getAdjacent((int)p.getX(), (int)p.getY());
        updateAdjacent(newAdj, player,(int)p.getX(), (int)p.getY());
      }
    }
  }

  /*
   *
   takes the players that are current in the game and returns a list of players who do not own any grid spaces
   return a null list if no one was eliminated during that turn
  */
  private ArrayList<Player> checkIfLost(ArrayList<Player> players){
    ArrayList<Player> losers = new ArrayList<>();
    for(Player p : players){
      boolean hasAtomsLeft = false;
      for(int w = 0; w < width; w ++){
        for(int h = 0; h < height; h ++){
          if(boardCordinates[w][h].getOwner() != null){
            if(boardCordinates[w][h].getOwner().getColor() == p.getColor()){
                hasAtomsLeft = true;
            }
          }
        }
      }
      if(!hasAtomsLeft){
        losers.add(p);
      }
    }
    return losers;
  }
  //function used to store the current bored state each turn, used for undo
  private void updateGameState(){
    Grid[][] newBoardCordinates = new Grid[width][height];
    for(int W = 0; W < width; W ++){
      for(int H = 0; H < height; H ++){
        newBoardCordinates[W][H] = new Grid();
        newBoardCordinates[W][H].setOwner(boardCordinates[W][H].getOwner());
        newBoardCordinates[W][H].setAtomCount(boardCordinates[W][H].getAtomCount());
      }
    }
    boardStates.add(newBoardCordinates);
  }

  //loops through each player and the board and updates their grid counts, used for player stats
  public void updateGridOwned(Player p){
    int count = 0;
    for(int w = 0; w < width; w ++){
      for(int h = 0; h < height; h ++){
        if(boardCordinates[w][h].getOwner() != null){
          if(p.getColor() == boardCordinates[w][h].getOwner().getColor()){
          count ++;
          }
        }
      }
    }
    p.setGridsOwned(count);
  }

  public void undoMove(int turnNumber){
    //  System.out.println("number of board states: " + boardStates.size());
      Grid[][] newBoardCordinates = boardStates.get(turnNumber);
  //    System.out.println(turnNumber);
      for(int w = 0; w < width; w ++){
        for(int h = 0; h < height; h ++){
        //  System.out.println(newBoardCordinates[w][h].toString());
        boardCordinates[w][h].setOwner(newBoardCordinates[w][h].getOwner());
        boardCordinates[w][h].setAtomCount(newBoardCordinates[w][h].getAtomCount());
        }
      }
      boardStates.remove(turnNumber);
  }

}
