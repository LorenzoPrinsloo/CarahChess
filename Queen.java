//package root.pieces;
//
//import root.*;


public class Queen extends Piece {

    public Queen(PlayerType o) {
        this.owner = o;
        type = PieceType.QUEEN;
    }

    @Override
    public boolean isValidMove(Move move, Board board) {

        if(Utils.isDiagonalMove(move)){
            return Utils.isValidDiagonalMove(move, board, owner);
        } else { // We know this will be a vertical or horizontal
           return Utils.isValidLongitudinalMove(move, board, owner);
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
}
