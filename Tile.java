package A2c;
import java.io.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

/**
 * This class instantiates all the pieces required to play.
 * @author Alexander Sui
 * @version 1.0
 */
public class Tile extends StackPane implements Serializable {
    
    /** The shape that are the pieces */
    private transient Rectangle tile;
    /** The "pic"ture or name of the piece */
    private transient Text pic;
    /** A Piece object */
    private Piece piece;
    /** Horizontal counter (by tile) */
    private int width;
    /** Vertical counter (by tile) */
    private int height;
    /** Boolean to indicate colour of piece */
    private int isWhite;
    /** Player object to know which player's turn/piece something is */
    private Player player;
    /** Level of the board */
    private int level;
    
    /**
     * Constructs an object of type Tile. This is used when creating a new game
     * @param player Player - to indicate what colour a piece is going to be
     * @param width int - horizontal position
     * @param height int - vertical position
     * @param level int - board level
     */
    public Tile(Player player, int width, int height, int level) {
        tile = new Rectangle(50, 50);
        tile.setFill(Color.TRANSPARENT);
        piece = null;
        this.player = player;
        pic = new Text("");
        this.width = width;
        this.height = height;
        this.level = level;
        if (player == null)
            isWhite = -1;
        else
            isWhite = player.checkColour();
        setInitialPieces();
        getChildren().addAll(tile, pic);
    }
    
    /**
     * Constructs an object of type Tile. This is used when loading a game
     * @param width int - horizontal position
     * @param height int - vertical position
     * @param piece Piece - chess piece
     * @param level int - board level
     */
    public Tile(int width, int height, Piece piece, int level) {
        tile = new Rectangle(50, 50);
        tile.setFill(Color.TRANSPARENT);
        this.piece = piece;
        this.level = level;
        if (piece == null)
            player = null;
        else
            player = new Player(piece.checkColour());
        pic = new Text("");
        this.width = width;
        this.height = height;
        if (player == null)
            isWhite = -1;
        else
            isWhite = player.checkColour();
        getChildren().addAll(tile, pic);
    }
    
    /**
     * Initializes the player pieces to their appropriate starting positions
     * using height and width as coordinates on the board.
     * Then setSymbol is called to label the pieces.
     */
    public void setInitialPieces() {
        if (height == 1 || height == 6) {
            piece = new Pawn(isWhite);
        } else if (width == 0 && height == 0 || width == 7 && height == 0 || width == 0 && height == 7 || width == 7 && height == 7) {
            piece = new Rook(isWhite);
        } else if (width == 1 && height == 0 || width == 6 && height == 0 || width == 1 && height == 7 || width == 6 && height == 7) {
            piece = new Knight(isWhite);
        } else if (width == 2 && height == 0 || width == 5 && height == 0 || width == 2 && height == 7 || width == 5 && height == 7) {
            piece = new Bishop(isWhite);
        } else if (width == 3 && height == 0 || width == 3 && height == 7) {
            piece = new Queen(isWhite);
        } else if (width == 4 && height == 0 || width == 4 && height == 7) {
            piece = new King(isWhite);
        }
        
        if (piece != null)
            player.addPiece(piece);
    }
    
    /**
     * Sets the label for the pieces for identification.
     * If null is accepted as a parameter, that means the tile is empty.
     * @param piece Piece - the piece to be labeled
     */
    public void setSymbol(Piece piece) {
        pic.setFont(new Font(30));
        if (piece != null)
            pic.setText(piece.getSymbol());
        else 
            pic.setText("");
    }
    
    /**
     * Check the colour of a piece.
     * @return true if the piece belongs to the "White" player,
     * false if the "Black" player
     */
    public int checkColour() {
        return isWhite;
    }
    
    /**
     * Sets a Piece to the piece variable.
     * @param piece Piece - either a chess Piece or null
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    
    /**
     * Sets the player for the Tile
     * @param player Player - either White player or Black player
     */
    public void setPlayer(Player player) {
        this.player = player;
        if (getPiece() != null)
            isWhite = checkColour();
    }
    
    /**
     * Sets the colour of the Tile
     * @param isWhite int - 0 for White, 1 for Black, -1 for empty
     */
    public void setColour(int isWhite) {
        this.isWhite = isWhite;
    }
    
    /**
     * Returns the Player of the Tile
     * @return player Player - either White or Black player
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * Returns the X coordinate of Tile on the Board
     * @return width int - X coordinate
     */
    public int getX() {
        return width;
    }
    
    /**
     * Returns the Y coordinate of Tile on the Board
     * @return height int - Y coordinate
     */
    public int getY() {
        return height;
    }
    
    /**
     * Returns the Piece of this Tile
     * @return piece Piece - piece of this tile
     */
    public Piece getPiece() {
        return piece;
    }
    
    /**
     * Returns the board level of the Tile
     * @return
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * Sets the Tile's board level
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
    }
    
    /**
     * Highlights the actively selected piece
     * @param clicked boolean - true if a piece is selected
     */
    public void lightUp(boolean clicked) {
        if (clicked)
            tile.setFill(Color.ROYALBLUE);
        else
            tile.setFill(Color.TRANSPARENT);
    }
    
}
