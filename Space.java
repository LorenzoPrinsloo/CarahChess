//package root.pieces;
//
//import root.Board;
//import root.Move;
//import root.PieceType;
//import root.PlayerType;

public class Space implements Piece {

    PieceType type = PieceType.SPACE;

    public Space() {
    }

    @Override
    public boolean isValidMove(Move move, Board board) { // Moving a space is never valid
        return false;
    }

    @Override
    public PlayerType getOwner() {
        return PlayerType.NOT_SET;
    }

    @Override
    public PieceType getType() {
        return this.type;
    }
}