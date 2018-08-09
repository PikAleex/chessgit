package A2c;
/**
 * Rook piece.
 * @author Alexander Sui
 * @version 1.0
 */
public class Rook extends Piece {

    /** The text for the piece label */
    private String pic;
    /** Int to check player(colour) */
    private int player;
    /** Boolean to check for first move */
    private boolean firstMove;
    /** "Name" of the piece */
    private String id;
    
    /**
     * Constructs an object of type Rook.
     * @param isWhite int - this is to indicate the colour of the piece
     */
    public Rook(int isWhite) {
        pic = ((isWhite == 0) ? "♖" : "♜");
        player = isWhite;
        id = "rook";
    }

    /**
     * Returns the label (string) of the Piece
     * @return pic String - the pieces identification
     */
    public String getSymbol() {
        return pic;
    }
    
    /**
     * Checks the colour of a the Piece
     * @return 0 if player is White, 1 if Black, -1 if empty
     */
    @Override
    public int checkColour() {
        return player;
    }

    /**
     * Returns the "name" of the piece
     * @see A2a.Piece#getID()
     * @return id is the name of the piece
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * Returns boolean of if a move is valid or not
     * @see A2a.Piece#move(A2a.Tile, A2a.Tile)
     * @param src Tile - piece being moved
     * @param dest Tile - destination tile
     * @return true if the move is valid
     */
    @Override
    public boolean move(Tile src, Tile dest) {
        int srcX = src.getX();
        int srcY = src.getY();
        int destX = dest.getX();
        int destY = dest.getY();
        
        if ((srcX == destX && srcY != destY) 
                || (srcX != destX && srcY == destY))
            return true;
        return false;
    }
    

}