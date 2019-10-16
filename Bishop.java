//package root.pieces;

//import root.*;

//import static Utils.isDiagonalMove;

public class Bishop extends Piece {


    public Bishop(PlayerType o) {
        this.owner = o;
        type = PieceType.BISHOP;
    }
     Utils ut = new Utils();
    @Override
    public boolean isValidMove(Move move, Board board) {
        if(Utils.isDiagonalMove(move)){
            return Utils.isValidDiagonalMove(move, board, owner);
        } else { return false;}
    }
}
