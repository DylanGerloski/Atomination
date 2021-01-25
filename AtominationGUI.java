import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;
import java.util.ArrayList;

public class AtominationGUI extends PApplet {

    private Game game;
    private Board board;
    private Grid[][] GUIGrids = new Grid[10][6];
    private PImage background;
    private PImage oneRedCricle;
    private PImage oneGreenCircle;
    private PImage twoRedCircle;
    private PImage twoGreenCircle;
    private PImage threeRedCircle;
    private PImage threeGreenCircle;


    public AtominationGUI() {
    }


    public void mouseClicked(MouseEvent event) {
      convertClickToCords(mouseX, mouseY);
      game.increaseTurnNumber();
    }

    public void setup() {
      this.board = new Board(10,6);
      this.game = new Game(board, 2);
      Atomination.currentGame = this.game;
      ArrayList<Player> p = new ArrayList<>();
      p.add(new Player('R'));
      p.add(new Player('G'));
      game.addPlayers(p);
      int x = 0;
      for (int w=0; w < 640; w+=64) {
          int y = 0;
          for (int h = 0; h < 384; h+= 64) {
              int yMinBound = h;
              int yMaxBound = h + 64;
              int xMaxBound = w + 64;
              int xMinBound = w;
              GUIGrids[x][y] = new Grid(xMaxBound, yMaxBound, xMinBound, yMinBound);
              y++;
          }
          x++;

      }
        frameRate(60);
        size(640, 384);
        background = loadImage("assets/tile.png");
        oneRedCricle = loadImage("assets/red1.png");
        oneGreenCircle = loadImage("assets/green1.png");
        twoRedCircle = loadImage("assets/red2.png");
        twoGreenCircle = loadImage("assets/green2.png");
        threeRedCircle = loadImage("assets/red3.png");
        threeGreenCircle = loadImage("assets/green3.png");


    }

    public void settings() {
        /// DO NOT MODIFY SETTINGS
        size(640, 384);

    }

    public void draw() {
      /*I am maintaining two boards at once. An internal board that takes care of the logic of the game,
        As well as the GUI board that is being shown. I am constantly checking if the inernal board has
        Atoms in grid spaces. If true, then I load the correct circle in the corresspoding GUI grid space.
        X and Y are used for indeces of the internal board. W and H correspond to the corridinates of the
        GUI board.
      */
        int x = 0;
        for (int w=0; w < 640; w+=64) {
            int y = 0;
            for (int h = 0; h < 384; h+= 64) {
              image(background,w,h, 64, 64);
              Grid[][] grid = board.getBoardCords();
              if(grid[x][y].getAtomCount() == 1){
                if(grid[x][y].getOwner().getColor() == 'R'){
                  image(oneRedCricle, w, h, 64, 64);
                }else{
                  image(oneGreenCircle, w, h, 64 ,64);
                }
              }else if(grid[x][y].getAtomCount() == 2){
                if(grid[x][y].getOwner().getColor() == 'R'){
                  image(twoRedCircle, w, h, 64, 64);
                }else{
                  image(twoGreenCircle, w, h, 64 ,64);
                }
              }else if(grid[x][y].getAtomCount() == 3){
                if(grid[x][y].getOwner().getColor() == 'R'){
                  image(threeRedCircle, w, h, 64, 64);
                }else{
                  image(threeGreenCircle, w, h, 64 ,64);
                }
              }
                y++;

            }
            x++;

        }
    }

    public static void go() {
        AtominationGUI.main("AtominationGUI");
    }

    private void convertClickToCords(int xCord, int yCord){
      Integer[] cordinates = new Integer[2];
      for(int w = 0; w < 10; w ++){
        for(int h = 0; h < 6; h ++){
          if(xCord <= GUIGrids[w][h].getXMaxBound() && xCord >= GUIGrids[w][h].getXMinBound() && yCord >= GUIGrids[w][h].getYMinBound() && yCord <= GUIGrids[w][h].getYMaxBound()){
            cordinates[0] = GUIGrids[w][h].getXMinBound();
            cordinates[1] = GUIGrids[w][h].getYMinBound();
            int idx = game.getTurnNumber() % 2;
            Player p = game.getPlayers().get(idx);
            board.placeGrid(p, w, h);
          //  board.displayBoard();

          }
        }
      }
    }

    public static void main(String[] passedArgs) {
        String[] appletArgs = new String[] { "AtominationGUI" };
        PApplet.main(appletArgs);
    }



}
