//package root.pieces;
//import root.*;

public class Knight implements Piece {

    PlayerType owner;
    PieceType type = PieceType.KNIGHT;

    public Knight(PlayerType o) {
        this.owner = o;
    }

    @Override
    public boolean isValidMove(Move move, Board board) {

        if(isLShapedMove(move)){
            int rowDest = move.getTo().getRow();
            int colDest = move.getTo().getColumn();

            if(Utils.isSpace(board.getBoardMatrix().get(rowDest).get(colDest)) || board.getBoardMatrix().get(rowDest).get(colDest).getOwner() != owner) {
                return true;
            } else return false;
        } else  return false;
    }

    @Override
    public PlayerType getOwner() {
        return this.owner;
    }

    @Override
    public PieceType getType() {
        return this.type;
    }

    /**
     * Calculate the step needed to navigate through matrix in a specific direction
     * @return 
     */
   

    public boolean isLShapedMove(Move move) {
        Integer rowStep = Math.abs(move.getTo().getRow() - move.getFrom().getRow());
        Integer colStep = Math.abs(move.getTo().getColumn() - move.getFrom().getColumn());

        if( (rowStep == 2 && colStep == 1 ) || (colStep == 2 && rowStep == 1)){
            return true;
        } else return false;
    	
    }
}
