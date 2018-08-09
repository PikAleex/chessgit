package A2c;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Player object containing player's colour 
 * @author Alexander Sui
 * @version 1.0
 */
public class Player implements Serializable {

    /** List of pieces available for a player */
    private ArrayList<Piece> pieceList = new ArrayList<Piece>();
    /** Colour of the player */
    private int isWhite;
    
    /**
     * Constructs an object of type Player.
     * @param isWhite int - colour of player
     */
    public Player(int isWhite) {
        pieceList = new ArrayList<Piece>();
        this.isWhite = isWhite;
    }
    
    /**
     * Add Pieces to the piece list
     * @param piece Piece - a chess piece
     */
    public void addPiece(Piece piece) {
        pieceList.add(piece);
    }
    
    /**
     * Returns colour of player
     * @return 0 if player is White, 1 if player is Black, -1 if empty
     */
    public int checkColour() {
        return isWhite;
    }
}
