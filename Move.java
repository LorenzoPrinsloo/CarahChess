//package root;

public class Move {

    Position from;
    Position to;
    Position Kingfrom;
    Position Kingto;
    Position Rookfrom;
    Position Rookto;
    PieceType promotionPiece;
    boolean isPromotion;
    boolean isCapture;
    boolean isNormal;
    boolean isCheckMate;
    boolean isCheck;
    boolean isCastling;
    boolean isQueenSide;
    boolean isKingSide;

    public Move(Position from, Position to) { // Default Move
        this.from = from;
        this.to = to;
        this.isCapture = false;
        this.isPromotion = false;
        this.isNormal = true;
        this.isCheck = false;
        this.isCheckMate = false;
        this.isCastling = false;
    }

    public Move(Position from, Position to, boolean isCheck, boolean isCheckMate) { // Default Move
        this.from = from;
        this.to = to;
        this.isCapture = false;
        this.isPromotion = false;
        this.isNormal = true;
        this.isCheck = isCheck;
        this.isCheckMate = isCheckMate;
        this.isCastling = false;
    }

    public Move(Position from, Position to, PieceType promotionPiece, boolean isPromotion) { // Promotion Move
        this.from = from;
        this.to = to;
        this.promotionPiece = promotionPiece;
        this.isPromotion = isPromotion;
        this.isCapture = false;
        this.isNormal = false;
        this.isCastling = false;
    }

    public Move(Position from, Position to, boolean isCapture, boolean isCheck, boolean isCheckMate) { // Capture Move
        this.from = from;
        this.to = to;
        this.isCapture = isCapture;
        this.isPromotion = false;
        this.isNormal = false;
        this.isCheck = isCheck;
        this.isCheckMate = isCheckMate;
        this.isCastling = false;
    }

    public Move(Position Kingfrom, Position Kingto, Position Rookfrom, Position Rookto, boolean isCastling, boolean isQueenSide, boolean isKingSide){ // Castling Move
    	this.isCastling = isCastling;
        this.isCapture = false;
        this.isPromotion = false;
        this.isNormal = false;
        this.isCheck = false;
        this.isCheckMate = false;
        this.isQueenSide = isQueenSide;
        this.isKingSide = isKingSide;
        
        this.Kingfrom = Kingfrom;
        this.Kingto = Kingto; 
        this.Rookfrom = Rookfrom; 
        this.Rookto = Rookto;
    }

    public Position getFrom() {
        return this.from;
    }
    
    public Position KinggetFrom(){
    	return this.Kingfrom;
    }

    public void setFrom(Position from) {
        this.from = from;
    }

    public Position getTo() {
        return this.to;
    }
    
    public Position KinggetTo(){
    	return this.Kingto;
    }

    public void setTo(Position to) {
        this.to = to;
    }

    public boolean isCapture() {
        return isCapture;
    }

    public void setCapture(boolean capture) {
        isCapture = capture;
    }
}
