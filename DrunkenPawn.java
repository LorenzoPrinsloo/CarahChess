//package root.pieces;
//
//import root.*;

public class DrunkenPawn extends Pawn {

    public DrunkenPawn(PlayerType o) {
        super(o);
        type = PieceType.DRUNKED_PAWN;
    }

    @Override
    public boolean isValidMove(Move move, Board board) {

        int rowDiff = move.getTo().getRow() - move.getFrom().getRow();
        int colDiff = move.getTo().getColumn() - move.getFrom().getColumn();

        if(rowDiff < 0 && owner == PlayerType.WHITE) {
            if(rowDiff == -1){ // move upwards
                Piece dest = board.getBoardMatrix().get(move.getTo().getRow()).get(move.getTo().getColumn());

                if(Math.abs(colDiff) == 1 && dest.getOwner() != owner){
                    return true;
                } else if(colDiff == 0 && Utils.isSpace(dest)) {
                    return true;
                } else return false;

            } else{

            	return false;}

        } else if(rowDiff > 0 && owner == PlayerType.BLACK) {
            if(rowDiff == 1){ // move downwards
                Piece dest = board.getBoardMatrix().get(move.getTo().getRow()).get(move.getTo().getColumn());


                if(Math.abs(colDiff) == 1 && dest.getOwner() != owner){
                    return true;
                } else if(colDiff == 0 && Utils.isSpace(dest)) {
                    return true;
                } else return false;

            } else return false;
        } else {
            Piece dest = board.getBoardMatrix().get(move.getTo().getRow()).get(move.getTo().getColumn());

            if(Utils.isSpace(dest)) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public Piece makeOfficer(PieceType requested, Board board) {
        return super.makeOfficer(requested, board);
    }
}
