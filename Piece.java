package A2c;
import java.io.Serializable;

/**
 * This abstract class contains all the similar methods used by a Piece
 * @author Alexander Sui
 * @version 1.0
 */
public abstract class Piece implements Serializable {
    
    /**
     * Returns the label (string) of the Piece
     * @return pic String - the pieces identification
     */
    public abstract String getSymbol();
    
    /**
     * Checks the colour of a the Piece
     * @return true if the piece is "White", false if the piece is "Black"
     */
    public abstract int checkColour();
    
    /**
     * Returns the "name" of the piece
     * @return id is the name of the piece
     */
    public abstract String getID();
    
    /**
     * Returns boolean of if a move is valid or not
     * @param src Tile - piece being moved
     * @param dest Tile - destination tile
     * @return true if the move is valid
     */
    public abstract boolean move(Tile src, Tile dest);
        
}
