//package root.pieces;
//
//import root.Board;
//import root.Move;
//import root.PieceType;
//import root.PlayerType;

public interface Piece {

    public PlayerType owner = PlayerType.NOT_SET;

    public PieceType type = PieceType.NOT_SET;

    public boolean isValidMove(Move move, Board board);

    public PlayerType getOwner();

    public PieceType getType();
}