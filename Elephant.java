//package root.pieces;
//
//import root.Board;
//import root.Move;
//import root.PieceType;
//import root.PlayerType;

public class Elephant extends Rook {

    PlayerType owner;

    PieceType type = PieceType.ELEPHANT;

    public Elephant(PlayerType o) {
        super(o);
        this.owner = o;
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
        if(owner == PlayerType.BLACK) {
            if(move.getTo().getRow() < 5){
                return false;
            } else {
                return super.isValidMove(move, board);
            }
        } else { // PLayerType White
            if(move.getTo().getRow() > 5){
                return false;
            } else {
                return super.isValidMove(move, board);
            }
        }
    }

    @Override
    public PlayerType getOwner() {
        return this.owner;
    }
}