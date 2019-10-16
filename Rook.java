//package root.pieces;
//
//import root.*;

// AKA Castle
public class Rook implements Piece {

    PlayerType owner;

    PieceType type = PieceType.ROOK;

    public Rook(PlayerType o) {
        this.owner = o;
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
    	
    	if(ScannerInput.isKingSideCastling || ScannerInput.isQueenSideCastling){
    		return true;
    	}

        if(Utils.isLongitudinalMove(move)) {
            return Utils.isValidLongitudinalMove(move, board, owner);
        } else return false;
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