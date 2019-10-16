//package root.pieces;
//
//import root.*;

public class Princess extends Knight {

    public Princess(PlayerType o) {
        super(o);
        this.owner = o;
        type = PieceType.PRINCESS;
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
        if(super.isLShapedMove(move)) {
            return super.isValidMove(move, board);
        } else if(Utils.isDiagonalMove(move)){
            return Utils.isValidDiagonalMove(move, board, owner);
        } else return false;
    }
}
