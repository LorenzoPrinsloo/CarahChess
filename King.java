//package root.pieces;
//
//import root.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class King extends Piece {

    public King(PlayerType o) {
        this.owner = o;
        type = PieceType.KING;
    }



    @Override
    public boolean isValidMove(Move move, Board board) {
        Piece p = Utils.getPiece(move.from.row, move.from.column, board.getBoardMatrix());
        // has only moved one space in any direction

    	//CASTLINGMOVE
    	if(move.isCastling){

    	    if(move.isQueenSide){
    	        if(getOwner() == PlayerType.BLACK) {
    	            if(board.getBlackStatus().hasQueenCostling) {
                        for(int i = 5; i > 1; i--){
                            if( (Utils.getPiece(0, i, board.getBoardMatrix()).owner != PlayerType.NOT_SET) ){
                                return false;
                            }
                        }
                        return true;
                    } else return false;
                    } else { // WHITE
                    if(board.getWhiteStatus().hasQueenCostling) {
                        for(int i = 5; i > 1; i--){
                            if( (Utils.getPiece(9, i, board.getBoardMatrix()).owner != PlayerType.NOT_SET) ){
                                return false;
                            }
                        }
                        return true;
                    } else return false;
                    }
                } else { // Kingside
    	            if(getOwner() == PlayerType.BLACK) {
    	                if(board.getBlackStatus().hasKingCostling) {
                            for(int i = 6; i < 9; i++){
                                if( (Utils.getPiece(0, i, board.getBoardMatrix()).owner != PlayerType.NOT_SET) ){
                                    return false;
                                }
                            }
                            return true;
                        } else return false;
                    } else { // WHITE
                        if(board.getWhiteStatus().hasKingCostling) {
                            for(int i = 6; i < 9; i++){
                                if( (Utils.getPiece(9, i, board.getBoardMatrix()).owner != PlayerType.NOT_SET) ){
                                    return false;
                                }
                            }
                            return true;
                        } else return false;
                    }
                }
            }

    	else{
    	//NOT CASTLING MOVE

    	//1
        int rowDiff = Math.abs(move.getTo().getRow() - move.getFrom().getRow());
        //1
        int colDiff = Math.abs(move.getTo().getColumn() - move.getFrom().getColumn());
        //7
        int To_row = move.getTo().getRow();
        //3
		int To_col = move.getTo().getColumn();
		Piece PieceUse = board.getBoardMatrix().get(To_row).get(To_col);



        if(Utils.isBetween(0,1, rowDiff) && Utils.isBetween(0,1, colDiff)) {


            if( (!Utils.isSpace(PieceUse)) || (PieceUse.getOwner() != owner) ){


                Board simBoard = new Board(board);

                simBoard.setEntryInBoardMatrix(move.from.column,move.from.row, new Space());
                simBoard.setEntryInBoardMatrix(move.to.column, move.to.row, p);

            	if(!isInCheck(move.getTo(), simBoard)){

                    return true;
                } else return false; // Is in Check by Opposition Piece when moving to the 'To' Position

            } else return false; // Blocked by own Piece


        } else return false; // More then one space moved

    	}
     }

    public boolean isInCheck(final Position kingPosition, final Board board) {

        return board.getBoardMatrix().stream().flatMap(Collection::stream)
            .filter(piece -> { if(!Utils.isSpace(piece)) return piece.getOwner() != owner; else return false;}) // filters out all the spaces and our own pieces
            .anyMatch(opositionPiece -> {
                Position piecePos = null;
                try {
                    piecePos = Utils.findPositionOnBoard(opositionPiece, board.getBoardMatrix());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Move checkMove = new Move(piecePos, kingPosition);
                return opositionPiece.isValidMove(checkMove, board);
            });
    }

    public boolean isInCheckMate(final Position currentKingPosition, final Board board) {
        List<Move> posibleMoves = getPosibleMoves(currentKingPosition, board);

        return posibleMoves.stream().allMatch(move -> isInCheck(move.getTo(), board));
    }

    private List<Move> getPosibleMoves(final Position currentKingPosition, final Board board){

        return board.getBoardMatrix().stream().flatMap(Collection::stream)
        .map(p -> {
            Position toPos = null;
            try {
                toPos = Utils.findPositionOnBoard(p, board.getBoardMatrix());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new Move(currentKingPosition, toPos);
        })
        .filter(move -> this.isValidMove(move, board))
        .collect(Collectors.toList());
    }
}
