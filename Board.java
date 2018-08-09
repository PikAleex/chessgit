package A2c;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * This class builds the tiles of a board and sets the layout.
 * The eventhandler are also present in this class for mouse clicks
 * @author Alexander Sui
 * @version 1.0
 */
public class Board {
    
    /** Rectangle for the shape of the board tiles */
    private transient Rectangle boardTile;
    /** Tile object on the board tile*/
    private Tile tile;
    /** Pane containing everything */
    private GridPane pane;
    /** The tile object being moved */
    private Tile selected;
    /** The Tile clicked */
    private Tile temp;
    /** Boolean indicating if something is clicked or not */
    private boolean clicked;
    /** Array containing the two active players */
    private Player[] players;
    /** A player in the game */
    private Player player;
    /** Int indicating whose turn */
    private int turn;
    /** Boolean indicating if a move is allowed or not */
    private boolean allowed;
    /** Boolean indicating if a straight path is clear or not */
    private boolean clear;
    /** List of all the nodes on the board */
    private ObservableList<Node> onBoard;
    /** 2D array to save the positions of all the pieces */
    private Piece[][] positions;
    /** What level a board is */
    private int level;
    /** Reference to another board 1 */
    private Board other1;
    /** Reference to another board 2 */
    private Board other2;
    /** The gui of the game */
    private GUI gui;
    
    /**
     * Constructs an object of type Board.
     * The board is instantiated with grey and white tiles laid out.
     * Then setPieces is called to set up the starting positions of the pieces.
     * @param players Player[] - list of active players
     * @param gui GUI - the gui being used
     * @param newGame boolean - the new game indicator
     * @param positions Piece[][] - array of piece positions
     * @param turn int - turn indicator
     * @param level int - level of the board
     */
    public Board(Player[] players, GUI gui, boolean newGame, Piece[][] positions, int turn, int level) {
        this.gui = gui;
        this.players = players;
        player = null;
        this.level = level;
        pane = new GridPane();
        this.turn = turn;
        selected = null;
        clicked = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardTile = gui.setBoardTile(i, j);
                pane.add(boardTile, i, j);
                if (newGame) {
                    setPieces(i, j);
                    this.positions = new Piece[8][8];
                } else {
                    this.positions = positions;
                    reloadPieces(i, j);
                }
            }
        }
    }
    
    /**
     * Sets the other 2 boards to respective global variables
     * @param other1 Board - one of the other boards
     * @param other2 Board - one of the other boards
     */
    public void setOtherBoards(Board other1, Board other2) {
        this.other1 = other1;
        this.other2 = other2;
    }
    
    /**
     * Sets pieces to their starting positions
     * @param i int - horizontal position
     * @param j int - vertical position
     */
    public void setPieces(int i, int j) {
        if (j == 0 || j == 1)
            player = players[0];
        else
            player = players[1];
        
        if (j == 0 && level == 0 || j == 1 && level == 0 || j == 6 && level == 0 || j == 7 && level == 0) {
            tile = new Tile(player, i, j, level);
        } else {
            tile = new Tile(i, j, null, level);
        }
        tile.setOnMousePressed(this::processClick);
        pane.add(tile, i, j);
    }
    
    /**
     * Returns the pane with the entire board and its pieces
     * @return pane GridPane - the layout of the board
     */
    public GridPane getPane() {
        return pane;
    }
    
    /**
     * Sets click boolean to true on initial click,
     * and sets selected to the clicked tile
     * @param temp Tile - the clicked tile
     */
    public void setSelecting(Tile temp) {
        clicked = true;
        selected = temp;      
    }
    
    /**
     * Deselects a selected tile and resets clicked boolean and temp Tile
     */
    public void deselect() {
        clicked = false;
        temp = null;
        selected = null;
    }
    
    /**
     * Checks if two tiles are on the same board level
     * @return true if the tiles are on the same board level
     */
    public boolean checkBoard() {
        if (temp.getLevel() == selected.getLevel())
            return true;
        return false;
    }
    
    /**
     * Switches control of the pieces (changing player turns)
     */
    public void changeTurns() {
        turn = (turn == 0) ? 1 : 0;
    }
    
    /**
     * This method is called when a tile is clicked.
     * This method will also handle where a Piece goes when moving
     * @param click MouseEvent - the click of a tile
     */
    public void processClick(MouseEvent click) {
        temp = (Tile) click.getSource();
        clear = false;
        
        if (selected == null && temp.getPiece() != null && temp.checkColour() == turn) { // Initial click of a piece
            System.out.println("Selected " + temp.getPiece().getID());
            setSelecting(temp);
            if (other1 != null && other2 != null) {
                other1.setSelecting(temp);
                other2.setSelecting(temp);
            }
            selected.lightUp(clicked);       
        } else {
            if (temp.getPiece() == null && !clicked || temp.checkColour() != turn && !clicked) {
                gui.notYourTurn((turn == 0) ? "White player" : "Black player"); 
            } else {
                if (clicked && selected.checkColour() == temp.checkColour()) { // Deselect previous piece, select new valid piece
                    System.out.println("Deselected " + selected.getPiece().getID());
                    System.out.println("Selected " + temp.getPiece().getID());
                    selected.lightUp(false);
                    setSelecting(temp);
                    if (other1 != null && other2 != null) {
                        other1.setSelecting(temp);
                        other2.setSelecting(temp);
                    }
                    selected.lightUp(clicked);
                } else { // Moving to another spot
                    allowed = selected.getPiece().move(selected, temp);

                    if (!checkBoard()) // Check whether on the same board or migration
                        if (Math.abs(selected.getX() - temp.getX()) > Math.abs(selected.getLevel() - temp.getLevel())
                                && !(selected.getPiece() instanceof Knight)
                                || Math.abs(selected.getY() - temp.getY()) > Math.abs(selected.getLevel() - temp.getLevel())
                                && !(selected.getPiece() instanceof Knight)
                                || temp.getX() == selected.getX() && temp.getY() == selected.getY() 
                                && !(selected.getPiece() instanceof Knight)) {
                            allowed = false;
                        }
                    
                    if (selected != null && temp != null && allowed)
                        clear = checkClear(selected, temp);
                    
                    if (clear) {
                        if (temp.getPiece() != null && temp.getPiece() instanceof King) { // Seeing if a player won
                            gui.endGame(selected.getPiece().checkColour() == 0 ? "White" : "Black");
                        }
                        temp.setPiece(selected.getPiece());
                        temp.setSymbol(selected.getPiece());
                        temp.setPlayer(selected.getPlayer());
                        temp.setColour(selected.getPlayer().checkColour());
                        selected.setPiece(null);
                        selected.setSymbol(null);
                        selected.setColour(-1);
                        selected.lightUp(false);
                        temp.lightUp(false);
                        deselect();
                        if (other1 != null && other2 != null) {
                            other1.deselect();
                            other2.deselect();
                        }
                        System.out.println("Moved");
                        changeTurns();
                        if (other1 != null && other2 != null) {
                            other1.changeTurns();
                            other2.changeTurns();
                        }
                    } else {
                        gui.invalidMove();
                    }
                }
            }
        }
    }
    
    /**
     * Iterate through the whole board checking the objects in each tile
     * @param x int - x coordinate of a Tile
     * @param y int - y coordinate of a Tile
     * @return the Tile at the passed coordinates
     */
    public Tile getTile(int x, int y) {
        onBoard = pane.getChildren();
        for (Node node : onBoard) {
            if (GridPane.getRowIndex(node) == y && GridPane.getColumnIndex(node) == x && (node instanceof Tile)) {
                return (Tile) node;
            }
        }
        return null;
    }
    
    /**
     * Check whether a straight path is clear or not
     * @param src Tile - Piece/Tile being moved
     * @param dest Tile - destination tile
     * @return true if the straight path is clear of pieces
     */
    public boolean checkClear(Tile src, Tile dest) {
        boolean clear = true;
        int srcX = src.getX();
        int srcY = src.getY();
        int destX = dest.getX();
        int destY = dest.getY();
        int x = srcX;
        int y = srcY;
        
        if (src.getLevel() != dest.getLevel())
            clear = checkFloor(src, dest);
        else if (srcX - destX == 0 || srcY - destY == 0 || Math.abs(srcX - destX) == Math.abs(srcY - destY)) {
            x -= (srcX - destX != 0) ? (srcX - destX) / Math.abs(srcX - destX) : 0;
            y -= (srcY - destY != 0) ? (srcY - destY) / Math.abs(srcY - destY) : 0;

            while (x != destX || y != destY) {
                if (getTile(x, y).getPiece() != null)
                    clear = false;
                x -= (srcX - destX != 0) ? (srcX - destX) / Math.abs(srcX - destX) : 0;
                y -= (srcY - destY != 0) ? (srcY - destY) / Math.abs(srcY - destY) : 0;
            }
        }
        return clear;
    }
    
    /**
     * Checks if the board migration is blocked. There is only 1 if statement
     * for 2 floors because moving 1 floor doesn't require additional checks
     * @param src Tile - Tile of piece being moved
     * @param dest Tile - Tile piece to be moved to
     * @return true if the move is possible.
     */
    public boolean checkFloor(Tile src, Tile dest) {
        boolean clear = true;
        int srcX = src.getX();
        int srcY = src.getY();
        int destX = dest.getX();
        int destY = dest.getY();
        int x = srcX;
        int y = srcY;
        int diff = src.getLevel() - dest.getLevel();
        
        if (Math.abs(diff) == 2 && Math.abs(srcX - destX) == 2 || Math.abs(diff) == 2 && Math.abs(srcY - destY) == 2
                || Math.abs(diff) == 2 && Math.abs(srcX - destX) == Math.abs(srcY - destY) 
                && Math.abs(srcY - destY) == 2) { // Moving 2 boards
            if (src.getLevel() > dest.getLevel()) { // moving down 2 boards
                if (srcX - destX == 0) { // Moving vertically
                    if (other1.getTile(x, y - (srcY - destY) / Math.abs(srcY - destY)).getPiece() != null)
                        clear = false;
                } else if (srcY - destY == 0) { // Moving horizontally
                    if (other1.getTile(x - (srcX - destX) / Math.abs(srcX - destX), y).getPiece() != null)
                        clear = false;
                } else if (Math.abs(srcX - destX) == Math.abs(srcY - destY)) {
                    if (other1.getTile(x - (srcX - destX) / Math.abs(srcX - destX), y - (srcY - destY) / Math.abs(srcY - destY)).getPiece() != null)
                        clear = false;
                }
            } else if (src.getLevel() < dest.getLevel()) { // moving up 2 boards
                if (srcX - destX == 0) { // Moving vertically 
                    if (other2.getTile(x, y - (srcY - destY) / Math.abs(srcY - destY)).getPiece() != null)
                        clear = false;
                } else if (srcY - destY == 0) { // Moving horizontally
                    if (other2.getTile(x - (srcX - destX) / Math.abs(srcX - destX), y).getPiece() != null)
                        clear = false;
                } else if (Math.abs(srcX - destX) == Math.abs(srcY - destY)) {
                    if (other2.getTile(x - (srcX - destX) / Math.abs(srcX - destX), y - (srcY - destY) / Math.abs(srcY - destY)).getPiece() != null)
                        clear = false;
                }
            }
        } else {
            if (Math.abs(diff) == 2 && Math.abs(srcX - destX) == 1  || Math.abs(diff) == 2 && Math.abs(srcY - destY) == 1
                || Math.abs(diff) == 2 && Math.abs(srcX - destX) == Math.abs(srcY - destY) 
                && Math.abs(srcY - destY) == 1) {
                clear = false;
            }
        }
        
        return clear;
    }
    
    /**
     * Loading the positions array with pieces then passing back to the game
     * @return a filled positions array to the game
     */
    public Piece[][] savingPositions() {
        onBoard = pane.getChildren();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                for (Node node : onBoard) {
                    if (GridPane.getRowIndex(node) == j && GridPane.getColumnIndex(node) == i && (node instanceof Tile)) {
                        Piece piece = ((Tile)node).getPiece();
                        positions[i][j] = piece;
                    }
                }
            }
        }
        return positions;
    }
    
    /**
     * Recreates all the pieces from the saved file
     * @param i int - x coordinate
     * @param j int - y coordinate
     */
    public void reloadPieces(int i, int j) {
        tile = new Tile(i, j, positions[i][j], level);
        tile.setOnMousePressed(this::processClick);
        pane.add(tile, i, j);
    }
    
    /**
     * Returns whose turn it is
     * @return turn int - 0 if White player, 1 if Black player
     */
    public int getTurn() {
        return turn;
    }
    
}
