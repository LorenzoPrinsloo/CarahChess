//package root.pieces;
//
//import root.Board;
//import root.Move;
//import root.PieceType;
//import root.PlayerType;

public class Space extends Piece {


    public Space() {
        owner = PlayerType.NOT_SET;
        type = PieceType.SPACE;
    }

    @Override
    public boolean isValidMove(Move move, Board board) { // Moving a space is never valid
        return false;
    }
}
