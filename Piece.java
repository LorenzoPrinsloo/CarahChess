//package root.pieces;
//
//import root.Board;
//import root.Move;
//import root.PieceType;
//import root.PlayerType;

abstract class Piece {

    public PlayerType owner = PlayerType.NOT_SET;

    public PieceType type = PieceType.NOT_SET;

    abstract public boolean isValidMove(Move move, Board board);

    public PlayerType getOwner() {
        return owner;
    }

    public PieceType getType() {
        return type;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append(type+"(");
        sb.append("owner = "+owner+"");
        sb.append(")");

        return sb.toString();
    }
}
