//package root.pieces;
//
//import root.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class King implements Piece {

    PlayerType owner;

    PieceType type = PieceType.KING;

    public King(PlayerType o) {
        this.owner = o;
    }

    @Override
    public boolean isValidMove(Move move, Board board) {

        // has only moved one space in any direction
    	
    	//CASTLINGMOVE
    	if(ScannerInput.isKingSideCastling || ScannerInput.isQueenSideCastling){

    		return true;
    		
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
            	

            	if(!isInCheck(move.getTo(), board)){

            		
                    return true;
                } else return false; // Is in Check by Opposition Piece when moving to the 'To' Position
                	
            } else return false; // Blocked by own Piece
           
            
        } else return false; // More then one space moved
        
    	}
     }




    @Override
    public PlayerType getOwner() {
        return this.owner;
    }

    @Override
    public PieceType getType() {
        return this.type;
    }

    public boolean isInCheck(final Position kingPosition, final Board board) {

        return board.getBoardMatrix().stream().flatMap(Collection::stream)
            .filter(piece -> { if(!Utils.isSpace(piece)) return piece.getOwner() != owner; else return false;})
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