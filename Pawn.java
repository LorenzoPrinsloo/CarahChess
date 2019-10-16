//package root.pieces;
//
//import root.*;

import java.util.List;

public class Pawn extends Piece {

    boolean isFirstMove;

    public Pawn(PlayerType o) {
        this.owner = o;
        this.isFirstMove = true;
        this.type = PieceType.PAWN;
    }

    public Pawn(PlayerType o, boolean isFirstMove) {
        this.owner = o;
        this.isFirstMove = isFirstMove;
        this.type = PieceType.PAWN;
    }

    @Override
    public boolean isValidMove(Move move, Board board) {

        int rowDiff = move.getTo().getRow() - move.getFrom().getRow();
        int colDiff = move.getTo().getColumn() - move.getFrom().getColumn();

        if( (rowDiff < 0) && (owner == PlayerType.WHITE) ) {
            if(rowDiff == -1){ // move upwards
                Piece dest = board.getBoardMatrix().get(move.getTo().getRow()).get(move.getTo().getColumn());

                if(Math.abs(colDiff) == 1 && dest.getOwner() != owner){
                    return true;
                } else if(colDiff == 0 && Utils.isSpace(dest)) {
                    return true;
                } else return false;

            } else if(rowDiff == -2 && isFirstMove) {

                Piece oneAhead = board.getBoardMatrix().get(move.getTo().getRow()-1).get(move.getTo().getColumn());
                Piece twoAhead = board.getBoardMatrix().get(move.getTo().getRow()).get(move.getTo().getColumn());

                if(Utils.isSpace(oneAhead) && Utils.isSpace(twoAhead)){
                    return true;
                } else return false;
            } else return false;
        } else if(rowDiff > 0 && owner == PlayerType.BLACK) {


            if(rowDiff == 1){
                Piece dest = board.getBoardMatrix().get(move.getTo().getRow()).get(move.getTo().getColumn());

                if(Math.abs(colDiff) == 1 && dest.getOwner() != owner){
                    return true;
                } else if(colDiff == 0 && Utils.isSpace(dest)) {
                    return true;
                } else return false;

            } else if(rowDiff == 2 && isFirstMove) {

                Piece oneAhead = board.getBoardMatrix().get(move.getTo().getRow()-1).get(move.getTo().getColumn());
                Piece twoAhead = board.getBoardMatrix().get(move.getTo().getRow()).get(move.getTo().getColumn());

                if(Utils.isSpace(oneAhead) && Utils.isSpace(twoAhead)){
                    return true;
                } else return false;
            } else return false;
        } else return false;
    }

    public Piece makeOfficer(PieceType requested, Board board) {

        Piece piece = null;

        switch(requested) {
            case KING: piece = new King(owner); break;
            case QUEEN: piece = new Queen(owner); break;
            case AMAZON: piece = new Amazon(owner); break;
            case DRAGON: piece = new Dragon(owner); break;
            case DRUNKED_PAWN : piece = new DrunkenPawn(owner); break;
            case BISHOP: piece = new Bishop(owner); break;
            case KNIGHT: piece = new Knight(owner); break;
            case PAWN: piece = new Pawn(owner); break;
            case PRINCESS: piece = new Princess(owner); break;
            case ROOK : piece = new Rook(owner); break;
        }

//        board.setEntryInPieceCount(PieceType.PAWN, board.getPieceCount().get(PieceType.PAWN) - 1);
//        board.setEntryInPieceCount(requested, board.getPieceCount().get(requested) + 1);

        int rowCount = 0;
        int columnCount = 0;
        for(List<Piece> row: board.getBoardMatrix()) {

            columnCount = 0;
            for(Piece p: row){
                if(p.hashCode() == this.hashCode()) {
                    board.setEntryInBoardMatrix(columnCount, rowCount, p);
                }
                columnCount++;
            }

            rowCount++;
        }

        return piece;
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
