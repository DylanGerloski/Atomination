# Atomination
Java desktop game similar to minesweeper (kind of)


### Rules and Features ####


##Rules##
Atomination is a game that can be played with 2-4 players. When you start the game, you will see a grid containing squares.
Each player takes a turn to place an atom (circle) on a specific square.
Each individual square has a limit of atoms that a player can place.
Corner squares have a limit of 2 atoms, edge squares have a limit of 3 and any other square has a limit of 4.
A player may only place atoms on squares that do not already contain atoms, or a square that already has your own atom(s) on it.
Once a sqaure contains its limit of atoms, it will spread its atoms to any adjacent sqaures, and the original square will be empty.
If an adjacent square is controlled by another player, the spread will transfer controll over to you and you will also controll any atoms that were previously there.
After the spread, if the adjacent square also has now reached its atom limit, it too will spread. This can happen as many times as there are squares the have reach its atom limit.
A player wins the game once the grid contains only their colored atoms and the opposing players have no control on any other squares.


### Feautures and how to compile ##
Upon downloading, you can either play a terminal version of the game or a GUI version.
The terminal version allows you to choose between 2-4 players, the size of the grid you play on, save and load previous games, stats, and an undo feature.
To play the termianl version, simply compile the java files like you would any other java file.
To see the feautures and specefic commands you can use, type "help"

The GUI version uses a fixed board size and is always between 2 players. 
To compile the GUI version, check the file build.sh. Depending on what system you use, it contains the command line command to compile and run the GUI. 
