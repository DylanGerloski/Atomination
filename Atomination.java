import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

public class Atomination{

  public static Game currentGame;
  public static boolean loaded = false;
  private static final String HELP =
          "HELP\tdisplays this help message\n" +
          "QUIT\tquits the current game\n" +
          "\n" +
          "DISPLAY\tdraws the game board in terminal\n" +
          "START\t<number of players> <width> <height> starts the game\n" +
          "PLACE\t<x> <y> places an atom in a grid space\n" +
          "UNDO\tundoes the last move made\n" +
          "STAT\tdisplays game statistics\n" +
          "SAVE\t<filename> saves the state of the game\n" +
          "LOAD\t<filename> loads a save file\n";

  public static void main(String[] args){
      Scanner scanner = new Scanner(System.in);
      while(scanner.hasNext()){
        String string = scanner.nextLine();
        String[] command = string.split(" ");
        command[0] = command[0].toUpperCase();
        switch (command[0]){
          case "HELP" : System.out.println(HELP);
          break;

          case "QUIT" : quit();

          case "DISPLAY" : currentGame.getBoard().displayBoard();
          break;

          case "START" :
          if(command.length != 4){
            System.out.println("Invalid amount of arguments\n");
            break;
          }else{
            try{
              int width= Integer.parseInt(command[2]);
              int height = Integer.parseInt(command[3]);
              int numberOfPlayers = Integer.parseInt(command[1]);
              startGame(numberOfPlayers,width, height);
              break;
            }catch(NumberFormatException ex){
              System.out.println("Arguments must be integers\n");
              break;
            }
          }

          case "PLACE" :
          if(command.length != 3){
            System.out.println("Invalid amount of arguments\n");
            break;
          }else{
            try{
              if(currentGame.getNumberOfPlayers() != 0){
                int idx = currentGame.getTurnNumber() % currentGame.getNumberOfPlayers();
                Player p = currentGame.getPlayers().get(idx);
                int i = Integer.parseInt(command[1]);
                int j = Integer.parseInt(command[2]);
                if(currentGame.place(p,i,j)){
                  currentGame.increaseTurnNumber();
                  idx = currentGame.getTurnNumber() % currentGame.getNumberOfPlayers();
                  System.out.println(currentGame.getPlayers().get(idx).toString() + "'s Turn\n");
                }else{
                  System.out.println("Invalid coordinates");
                }
              }
            }catch(NumberFormatException ex){
              System.out.println("Arguments must be an integer\n");
            }
        }

          break;

          case "UNDO" : currentGame.undo();
          break;

          case "STAT" : currentGame.getPlayerStats();
          break;

          case "SAVE" : saveGame(command[1]);
          break;

          case "LOAD" : if(loaded == false && currentGame == null){
                          load(command[1]);
                          System.out.println("Game Loaded\n");
                        }else{
                          System.out.println("Restart Application to Load Save");
                        }
          break;


          default : System.out.println("Not a valid command\n");
        }
      }

  }

  public static void startGame(int amountOfPlayers, int width, int height ){
    if(currentGame == null){
      if((height >= 2 && height <= 255) && (width >= 2 && width <=255) && (amountOfPlayers >= 2 && amountOfPlayers <=4)){
        Board board = new Board(width, height);
        currentGame = new Game(board, amountOfPlayers);
        char[] playerColors = {'R', 'G', 'P', 'B' };
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Player> orignalPlayers = new ArrayList<>();
        for (int i = 0; i < amountOfPlayers; i++){
          players.add(new Player(playerColors[i]));
        }
        currentGame.addPlayers(players);
        currentGame.addOriginalPlayers(players);
        System.out.println("Game Ready");
        System.out.println("Red's Turn\n");
      }else{
        System.out.println("Invalid Command line arguements");
      }
    }
    else{ System.out.println("Invalid Command");

    }
  }

  public static void load(String filename){
    loaded = true;
    File file = new File(filename);
    try(FileInputStream fip = new FileInputStream(file)){
      int singleCharInt;
      int count = 0;
      int width = 0, height = 0, numberOfPlayers = 0;
      ArrayList<Integer> coordinates = new ArrayList<>();
      while((singleCharInt = fip.read()) != -1){
        singleCharInt = (int) singleCharInt;
        if(count == 0){
          width = singleCharInt;
        }else if (count == 1){
          height = singleCharInt;
        }else if(count == 2){
          numberOfPlayers = singleCharInt;
        }else{
          coordinates.add(singleCharInt);
        }
        count ++;
      }
   loadGame(width, height, numberOfPlayers, coordinates);
    }catch(FileNotFoundException e){
      System.out.println("Cannot Load Save");
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  public static void loadGame(int width, int height, int numOfPlayers, ArrayList<Integer> cords){
    ArrayList<Integer> xCords = new ArrayList<>();
    ArrayList<Integer> yCords = new ArrayList<>();

    for(int xIndex = 0; xIndex < cords.size(); xIndex += 4){
      xCords.add(cords.get(xIndex));
    }
    for(int yIndex = 1; yIndex < cords.size(); yIndex += 4){
      yCords.add(cords.get(yIndex));
    }
    Board board = new Board(width, height);
    Game game = new Game(board, numOfPlayers);
    currentGame = game.loadGame(xCords, yCords);
    //System.out.println("xCords: " + xCords.toString() + " Ycords: "+ yCords.toString());
  }

  public static void saveGame(String fileName){
    File file = new File(fileName);
    if(file.exists()){
      System.out.println("File Already Exists\n");
      return;
    }else{
      Byte width = (byte)currentGame.getBoard().getWidth();
      Byte height = (byte)currentGame.getBoard().getHeight();
      Byte numOfplayers = (byte)currentGame.getNumberOfPlayers();
      Byte zero = (byte)0;
      try(FileOutputStream fop = new FileOutputStream(file)){
        fop.write(width);
        fop.write(height);
        fop.write(numOfplayers);
        for(int i = 0; i < currentGame.getBoard().getYCords().size(); i++){
          int x = currentGame.getBoard().getXCords().get(i);
          int y = currentGame.getBoard().getYCords().get(i);
          Byte X = (byte) x, Y = (byte) y;
          fop.write(X);
          fop.write(Y);
          fop.write(zero);
          fop.write(zero);
        }
        System.out.println("Game Saved\n");
      }catch(FileNotFoundException e){
        System.out.println("Cannot Load Save");
      }catch(IOException e){
        e.printStackTrace();
      }
    }
  }

  public static void quit(){
    System.out.println("Bye!");
    System.exit(0);
  }
}
