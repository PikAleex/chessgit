package A2c;
import javafx.scene.layout.GridPane;
import java.io.*;

/**
 * Game creates all the objects
 * @author Alexander Sui
 * @version 1.0
 */
public class Game implements Serializable {
    
    /** List of active players */
    private Player[] players;
    /** White player */
    private Player wPlayer;
    /** Black player */
    private Player bPlayer;
    /** The GUI for the game */
    private transient GUI gui = new GUI();
    /** Check whether new game or not */
    private boolean newGame;
    /** 2D array to store piece positions (bottom board) */
    private Piece[][] posB;
    /** 2D array to store piece positions (middle board) */
    private Piece[][] posM;
    /** 2D array to store piece positions (top board) */
    private Piece[][] posT;
    /** 2D array to store piece positions */
    private Piece[][] pos;
    /** Player turn */
    private int turn;
    /** Array of the 3 boards */
    private transient Board[] boards;
    
    /**
     * Constructs an object of type Game.
     * @param args String[] - arguments from main of driver class
     * @param newGame boolean - new game indicator
     */
    public Game(String[] args, boolean newGame) {
        wPlayer = new Player(0);
        bPlayer = new Player(1);
        this.newGame = newGame;
        players = new Player[]{wPlayer, bPlayer};
        boards = new Board[3];
        for (int i = 0; i <= 2; i++) {
            if (i == 0)
                pos = posB;
            else if (i == 1)
                pos = posM;
            else if (i == 2)
                pos = posT;
            boards[i] = new Board(players, gui, newGame, pos, turn, i);
        }
        boards[0].setOtherBoards(boards[1], boards[2]);
        boards[1].setOtherBoards(boards[0], boards[2]);
        boards[2].setOtherBoards(boards[0], boards[1]);
    }
    
    /**
     * Save whose turn it is.
     */
    public void saveTurn() {
        turn = boards[0].getTurn();
    }
    
    /**
     * Returns the 2D array with the saved positions
     * @return the saved positions of each piece (bottom board)
     */
    public Piece[][] getPosB() {
        return posB;
    }
    
    /**
     * Returns the 2D array with the saved positions
     * @return the saved positions of each piece (middle board)
     */
    public Piece[][] getPosM() {
        return posM;
    }
    
    /**
     * Returns the 2D array with the saved positions
     * @return the saved positions of each piece (top board)
     */
    public Piece[][] getPosT() {
        return posT;
    }
    
    /**
     * Set new game indicator to false.
     */
    public void setNotNewGame() {
        newGame = false;
    }
    
    /**
     * Check if the current game is new or not
     * @return true if it is a new game, false if it is a loaded game
     */
    public boolean checkGame() {
        return newGame;
    }
    
    /**
     * Saves the array of piece positions
     */
    public void savePositions() {
        posB = boards[0].savingPositions();
        posM = boards[1].savingPositions();
        posT = boards[2].savingPositions();
    }
    
    /** 
     * Display the game.
     * @param args String[] - arguments from main of driver class
     */
    public void showGame(String[] args) {
        gui.setUp(this, boards);
        gui.display(args);
    }
    
    /**
     * Create a new gui for a loaded game
     */
    public void createGUI() {
        gui = new GUI();
    }
    
    /**
     * Create a new board for a loaded game
     */
    public void createBoards() {
        boards = new Board[3];
        boards[0] = new Board(players, gui, false, posB, turn, 0);
        boards[1] = new Board(players, gui, false, posM, turn, 1);
        boards[2] = new Board(players, gui, false, posT, turn, 2);
        boards[0].setOtherBoards(boards[1], boards[2]);
        boards[1].setOtherBoards(boards[0], boards[2]);
        boards[2].setOtherBoards(boards[0], boards[1]);
    }
    
    /**
     * Returns the pane for the bottom board
     * @return the bottom Board
     */
    public GridPane getBotPane() {
        return boards[0].getPane();
    }
    
    /**
     * Returns the pane for the middle board
     * @return the middle Board
     */
    public GridPane getMidPane() {
        return boards[1].getPane();
    }
    
    /**
     * Returns the pane for the top board
     * @return the top Board
     */
    public GridPane getTopPane() {
        return boards[2].getPane();
    }
    
}
