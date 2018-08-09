package A2c;
/**
 * Pawn piece.
 * @author Alexander Sui
 * @version 1.0
 */
public class Pawn extends Piece {

    /** The text for the piece label */
    private String pic;
    /** Int to check player(colour) */
    private int player;
    /** Boolean to check for first move */
    private boolean firstMove;
    /** "Name" of the piece */
    private String id;
    
    /**
     * Constructs an object of type Pawn.
     * @param isWhite int - this is to indicate the colour of the piece
     */
    public Pawn(int isWhite) {
        pic = ((isWhite == 0) ? "♙" : "♟");
        player = isWhite;
        firstMove = true;
        id = "pawn";
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
     * @param src Tile - piece being moved
     * @param dest Tile - destination tile
     * @return true if the move is valid
     */
    @Override
    public boolean move(Tile src, Tile dest) {
        boolean legal = false;
        int srcX = src.getX();
        int srcY = src.getY();
        int destX = dest.getX();
        int destY = dest.getY();
        int player = src.getPiece().checkColour();
        
        if (firstMove) {
            if (player == 0) {
                if ((srcX == destX && destY - srcY == 1 && Math.abs(src.getLevel() - dest.getLevel()) == 1)
                        || (srcX == destX && destY - srcY == 2 && Math.abs(src.getLevel() - dest.getLevel()) == 2)
                        || (srcX == destX && destY - srcY == 1 && src.getLevel() == dest.getLevel())
                        || (srcX == destX && destY - srcY == 2 && src.getLevel() == dest.getLevel())) {
                    firstMove = false;
                    legal = true;
                }
            } else if (player == 1) {
                if ((srcX == destX && srcY - destY == 1 && Math.abs(src.getLevel() - dest.getLevel()) == 1) 
                        || (srcX == destX && srcY - destY == 2 && Math.abs(src.getLevel() - dest.getLevel()) == 2)
                        || (srcX == destX && srcY - destY == 1 && src.getLevel() == dest.getLevel())
                        || (srcX == destX && srcY - destY == 2 && src.getLevel() == dest.getLevel())) {
                    firstMove = false;
                    legal = true;
                }
            }
        } else if (src.getLevel() == dest.getLevel()) {
                if (player == 0) {
                    if (srcX == destX && destY - srcY == 1 && dest.getPiece() == null) {
                        legal = true;
                    } else if ((destX == srcX - 1 && destY - srcY == 1 && dest.getPiece() == null) 
                            || (destX == srcX + 1 && destY - srcY == 1 && dest.getPiece() == null)) {
                        legal = false;
                    } else if ((destX == srcX - 1 && destY - srcY == 1 && dest.getPiece().checkColour() == 1) 
                            || (destX == srcX + 1 && destY - srcY == 1 && dest.getPiece().checkColour() == 1)) {
                        legal = true;
                    }
                } else if (player == 1) {
                    if (srcX == destX && destY - srcY == -1 && dest.getPiece() == null) {
                        legal = true;
                    } else if ((destX == srcX - 1 && destY - srcY == -1 && dest.getPiece() == null) 
                            || (destX == srcX + 1 && destY - srcY == -1 && dest.getPiece() == null)) {
                        legal = false;
                    } else if ((destX == srcX - 1 && destY - srcY == -1 && dest.getPiece().checkColour() == 0) 
                            || (destX == srcX + 1 && destY - srcY == -1 && dest.getPiece().checkColour() == 0)) {
                        legal = true;
                    } 
                }
        } else {
            legal = moveBoards(src, dest);
        }
        return legal;
    }
    
    /**
     * Checks if the board migration move is legal
     * @param src Tile - piece being moved
     * @param dest Tile - Tile piece is moving to
     * @return true if the move is legal
     */
    public boolean moveBoards(Tile src, Tile dest) {
        boolean legal = false;
        int srcX = src.getX();
        int srcY = src.getY();
        int destX = dest.getX();
        int destY = dest.getY();
        int player = src.getPiece().checkColour();
     
        if (player == 0) {
            if (srcX == destX && destY - srcY == 1 && dest.getPiece() == null) {
                legal = true;
            } else if ((destX == srcX - 1 && destY - srcY == 1 && dest.getPiece() == null) 
                    || (destX == srcX + 1 && destY - srcY == 1 && dest.getPiece() == null)) {
                legal = false;
            } else if ((destX == srcX - 1 && destY - srcY == 1 && dest.getPiece().checkColour() == 1) 
                    || (destX == srcX + 1 && destY - srcY == 1 && dest.getPiece().checkColour() == 1)) {
                legal = true;
            }
        } else if (player == 1) {
            if (srcX == destX && destY - srcY == -1 && dest.getPiece() == null) {
                legal = true;
            } else if ((destX == srcX - 1 && destY - srcY == -1 && dest.getPiece() == null) 
                    || (destX == srcX + 1 && destY - srcY == -1 && dest.getPiece() == null)) {
                legal = false;
            } else if ((destX == srcX - 1 && destY - srcY == -1 && dest.getPiece().checkColour() == 0) 
                    || (destX == srcX + 1 && destY - srcY == -1 && dest.getPiece().checkColour() == 0)) {
                legal = true;
            } 
        }  
            
        return legal;
    }
    

}
