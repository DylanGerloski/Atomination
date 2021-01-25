import java.util.ArrayList;
import java.io.IOException;

public class Game{

  private Board board;
  private ArrayList<Player> players;
  private ArrayList<Player> originalPlayers;
  private int numberOfPlayers;
  private int turnNumber;

  public Game(Board board, int numberOfPlayers){
    this.board = board;
    this.players = new ArrayList<>();
    this.originalPlayers = new ArrayList<>();
    this.turnNumber = 0;
    this.numberOfPlayers = numberOfPlayers;
  }

  public void setNumberOfPlayers(int n){
    this.numberOfPlayers = n;
  }

  public int getNumberOfPlayers(){
    return numberOfPlayers;
  }

  public int getTurnNumber(){
    return turnNumber;
  }

  public void increaseTurnNumber(){
    turnNumber += 1;
  }
  public Board getBoard(){
    return this.board;
  }

  public void removePlayer(Player p){
    players.remove(p);
  }

  public ArrayList<Player> getPlayers(){
    return players;
  }

  public void addPlayers(ArrayList<Player> p){
    for(Player P : p){
      players.add(P);
    }
  }

  public void addOriginalPlayers(ArrayList<Player> p){
    for(Player P : p){
      originalPlayers.add(P);
    }
  }

  public void getPlayerStats(){
    for(Player player : players){
      this.board.updateGridOwned(player);
    }
    for(Player p : originalPlayers){
      if(!p.getCheckIfLost()){
        System.out.println("Player " + p.toString() + ":\nGrid Count: " + p.getGridsOwned() + "\n");
      }
      else{
        System.out.println("Player " + p.toString() + ":\nLost\n");
      }
    }
  }

  public void undo(){
    turnNumber -= 1;
    board.undoMove(turnNumber);
    int idx = turnNumber % numberOfPlayers;
    System.out.println(players.get(idx).toString() + "'s Turn\n");

  }

  public void saveGame(){

  }

  public Game loadGame(ArrayList<Integer> xCords, ArrayList<Integer> yCords){
    char[] playerColors = {'R', 'G', 'P', 'B' };
    for (int i = 0; i < numberOfPlayers; i++){
      players.add(new Player(playerColors[i]));
      originalPlayers.add(new Player(playerColors[i]));
    }
    for(int i = 0; i < xCords.size(); i ++){
      int idx = this.turnNumber % this.numberOfPlayers;
      if(board.placeGrid(players.get(idx), xCords.get(i), yCords.get(i))){}
        this.turnNumber += 1;
    }
    return this;
  }




  public boolean place(Player p, int i, int j){
    return board.placeGrid(p, i, j);
  }
}
