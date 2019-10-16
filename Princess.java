//package root.pieces;
//
//import root.*;

public class Princess extends Knight {

    PlayerType owner;
    PieceType type = PieceType.PRINCESS;

    public Princess(PlayerType o) {
        super(o);
        this.owner = o;
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
        if(super.isLShapedMove(move)) {
            return super.isValidMove(move, board);
        } else if(Utils.isDiagonalMove(move)){
            return Utils.isValidDiagonalMove(move, board, owner);
        } else return false;
    }

    @Override
    public PlayerType getOwner() {
        return this.owner;
    }
}