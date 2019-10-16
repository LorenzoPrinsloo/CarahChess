//package root.pieces;
//
//import root.*;

// AKA Castle
public class Rook extends Piece {

    public Rook(PlayerType o) {
        this.owner = o;
        type = PieceType.ROOK;
    }

    @Override
    public boolean isValidMove(Move move, Board board) {

    	if(move.isCastling){
    		return true;
    	}

        if(Utils.isLongitudinalMove(move)) {
            return Utils.isValidLongitudinalMove(move, board, owner);
        } else return false;
    }
}
