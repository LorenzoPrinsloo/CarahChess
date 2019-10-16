//package root.pieces;

//import root.*;

//import static Utils.isDiagonalMove;

public class Bishop implements Piece {

    PlayerType owner;
    PieceType type = PieceType.BISHOP;

    public Bishop(PlayerType o) {
        this.owner = o;
    }
     Utils ut = new Utils();
    @Override
    public boolean isValidMove(Move move, Board board) {
        if(Utils.isDiagonalMove(move)){
            return Utils.isValidDiagonalMove(move, board, owner);
        } else { return false;}
    }

    @Override
    public PlayerType getOwner() {
        return this.owner;
    }

    @Override
    public PieceType getType() {
        return this.type;
    }
}