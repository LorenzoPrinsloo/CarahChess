//package root;

//import root.errors.MoveValidationErrors;
//import root.pieces.*;

import java.util.*;
import java.util.stream.Collectors;

public class Board {

    static int badmove = 1;
    static PlayerType currentPlayer;
    static CostlingStatus blackStatus;
    static CostlingStatus whiteStatus;
    static List<List<Piece>> boardMatrix;
    static int halfMoveClock; // Every time other than pawn or drunken pawn moves
    static int moveCounter; // Every time Black moves this increments
    static Map<PieceType, Integer> pieceCount;

    // valid status variables
    private static boolean isValidCapture = true; // add new ones to resetStatusVaraibles
    private static boolean isValidMove = true;
    private static boolean isValidPromotion = true;
    private static boolean isValidCheck = true;
    private static boolean isValidCheckMate = true;
    private static boolean isValidCastling = true;

    public Board(Board cp) {
        this.currentPlayer = cp.getCurrentPlayer();
        this.blackStatus = new CostlingStatus(cp.getBlackStatus().hasKingCostling, cp.getBlackStatus().hasQueenCostling);
        this.whiteStatus = new CostlingStatus(cp.getWhiteStatus().hasKingCostling, cp.getWhiteStatus().hasQueenCostling);
        this.boardMatrix = cp.getBoardMatrix().stream().map(ArrayList::new).collect(Collectors.toList());
        this.halfMoveClock = cp.getHalfMoveClock();
        this.moveCounter = cp.getMoveCounter();
        this.pieceCount = new HashMap<>(cp.getPieceCount());
    }

    public Board(PlayerType player, List<List<Piece>> board, Map<PieceType, Integer> pc, CostlingStatus blackStatus, CostlingStatus whiteStatus, int halfMoveClock, int moveCounter) {
        currentPlayer = player;
        this.blackStatus = blackStatus;
        this.whiteStatus = whiteStatus;
        boardMatrix = board;
        this.halfMoveClock = halfMoveClock;
        this.moveCounter = moveCounter;
        pieceCount = pc;
    }


    public void move(Move move, int line) throws Exception {

    Piece piece = null;


    if(move.from != null && move.to != null){

       piece = Utils.getPiece(move.from.getRow(),move.from.getColumn(), boardMatrix);

    }

//if(normal == true) {
    if( (piece.isValidMove(move, this)) ) { //if valid move apply move and switch player

            if(move.isCapture){
                isAllocatedCapture(move, line, piece);
                captureRuleSet(move, line, piece);

            } else if(move.isNormal) {
//                isAllocatedMove(move, line, piece);
                moveRuleSet(move, line, piece);

            } else if(move.isPromotion) {
            	badmove--;
                isAllocatedPromotion(move, line, piece);
                promotionRuleSet(move, line, piece);

                if(isValidPromotion) {
                    Pawn pawn = (Pawn) piece;

                    pawn.makeOfficer(move.promotionPiece, this);
                }

            } else if(move.isCheck) {
                King oponentKing = getKingFor(Utils.nextPlayer(currentPlayer));
                Position kingPos = Utils.findPositionOnBoard(oponentKing, boardMatrix);

                if(!Utils.isSamePosition(kingPos, move.to)){
                    isValidCheck = false;
//                 System.out.println("Valid Check a");
                    MoveValidationErrors.illegalCheck(line+badmove);

                }
            } else if(move.isCheckMate){
                King oponentKing = getKingFor(Utils.nextPlayer(currentPlayer));
                Position kingPos = Utils.findPositionOnBoard(oponentKing, boardMatrix);

                if(!oponentKing.isInCheckMate(kingPos, this)){
                    isValidCheckMate = false;
//                    System.out.println("Valid Check b");
                    MoveValidationErrors.illegalCheckmate(line+badmove);

                }
            } else if(move.isCastling) {
                if(move.isQueenSide) {
                    if(currentPlayer == PlayerType.WHITE){
                    	//switch
                        if(!blackStatus.hasQueenCostling){
                            isValidCastling = false;
//                            System.out.println("Valid Cast a");
                            MoveValidationErrors.illegalCastlingMove(line+badmove);

                        }
                    } else { // White
                        if(!whiteStatus.hasQueenCostling){
                            isValidCastling = false;
//                            System.out.println("Valid Cast b");
                            MoveValidationErrors.illegalCastlingMove(line+badmove);

                        }
                    }
                } else { // King Side
                    if(currentPlayer == PlayerType.WHITE){
                    	//switch
                        if(!blackStatus.hasKingCostling){
                            isValidCastling = false;
//                            System.out.println("Valid Cast c");
                            MoveValidationErrors.illegalCastlingMove(line+badmove);

                        }
                    } else { // White
                        if(!whiteStatus.hasKingCostling){
                            isValidCastling = false;
//                            System.out.println("Valid Cast d");
                            MoveValidationErrors.illegalCastlingMove(line+badmove);

                        }
                    }
                }

                boolean valid = castlingRuleSet(move , line , piece);

                if(!valid){
                	isValidCastling = false;
//                	System.out.println("Castling h");
                	MoveValidationErrors.illegalCastlingMove(line+badmove);
                }



            }


            if(isValidPromotion && isValidCheckMate && isValidCheck && isValidMove && isValidCapture && isValidCastling) {

            	PieceType ToPiece = Utils.getPiece(move.to.getRow(), move.to.getColumn(), boardMatrix).getType();
            	PieceType FromPiece = Utils.getPiece(move.from.getRow(), move.from.getColumn(), boardMatrix).getType();

                if(move.isCastling){ // CASTLING
                    moveCastling(move);
                } else {// DEFAULT

                    movePiece(move, piece); // Move/Capture
                }

                if( (ToPiece != PieceType.PAWN) || (ToPiece != PieceType.DRUNKED_PAWN) ){
                    this.halfMoveClock++;
                }
                if( (ToPiece == PieceType.PAWN) || (ToPiece == PieceType.DRUNKED_PAWN) || (move.isCapture) ){
                	this.halfMoveClock = 0;
                }

                if(currentPlayer.equals(PlayerType.BLACK)){ // Valid Black move then increment counter
                    moveCounter++;
                }



                this.currentPlayer = Utils.nextPlayer(currentPlayer);
            }


        }
//}

//        else if( (KingPiece.isValidMove(move, this)) && (RookPiece.isValidMove(move, this))){ //is Castling move
//
//         if(move.isCastling) {
//
//
//                if(move.isQueenSide) {
//                    if(currentPlayer == PlayerType.WHITE){
//                    	//switch
//                        if(!whiteStatus.hasQueenCostling){
//                            isValidCastling = false;
////                            System.out.println("Valid Cast a");
//                            MoveValidationErrors.illegalCastlingMove(badmove);
//
//                        }
//                    } else { // Black
//                        if(!blackStatus.hasQueenCostling){
//                            isValidCastling = false;
////                            System.out.println("Valid Cast b");
//                            MoveValidationErrors.illegalCastlingMove(badmove);
//
//                        }
//                    }
//                } else { // King Side
//                    if(currentPlayer == PlayerType.WHITE){
//                    	//switch
//                        if(!whiteStatus.hasKingCostling){
//                            isValidCastling = false;
////                            System.out.println("Valid Cast c");
//                            MoveValidationErrors.illegalCastlingMove(badmove);
//
//                        }
//                    } else { // Black
//                        if(!blackStatus.hasKingCostling){
//                            isValidCastling = false;
////                            System.out.println("Valid Cast d");
//                            MoveValidationErrors.illegalCastlingMove(badmove);
//
//                        }
//                    }
//                }
//
//                boolean valid = castlingRuleSet(move , line , piece);
//
//                if(valid == false){
//                	isValidCastling = false;
////                	System.out.println("Castling x");
//                	MoveValidationErrors.illegalCastlingMove(badmove);
//                }
//
//
//
//            }
//
//
//            if(isValidPromotion && isValidCheckMate && isValidCheck && isValidMove && isValidCapture && isValidCastling) {
//
//            	PieceType KingToPiece = Utils.getPiece(move.Kingto.getRow(), move.Kingto.getColumn(), boardMatrix).getType();
//            	PieceType KingFromPiece = Utils.getPiece(move.Kingfrom.getRow(), move.Kingfrom.getColumn(), boardMatrix).getType();
//            	PieceType RookToPiece = Utils.getPiece(move.Rookto.getRow(), move.Rookto.getColumn(), boardMatrix).getType();
//                PieceType RookfromPiece = Utils.getPiece(move.Rookfrom.getRow(), move.Rookfrom.getColumn(), boardMatrix).getType();
//
//                if(move.isCastling){ // CASTLING
//                    moveCastling(move);
//                }
//
//                    this.halfMoveClock++;
//
//                if( (move.isCapture) ){
//                	this.halfMoveClock = 0;
//                }
//
//                if(currentPlayer.equals(PlayerType.BLACK)){ // Valid Black move then increment counter
//                    moveCounter++;
//                }
//
//                this.currentPlayer = Utils.nextPlayer(currentPlayer);
//
//            }
//        }

        // else stay on same player and throw exception
        else {
        	if(move.isCapture){
        		MoveValidationErrors.illegalCapture(line+badmove);
        	}
            MoveValidationErrors.illegalMove(line+badmove);

        }

        resetStatusVaraibles();
       }


public boolean castlingRuleSet(Move move, int line, Piece piece){
    	boolean valid = true;

    	//halfmoveClock
        if(halfMoveClock >=50){
//        	System.out.println("Castling l");
        	MoveValidationErrors.illegalCastlingMove(line+badmove);
        	valid = false;
        }

        //pieces in way
        if( (currentPlayer.equals(PlayerType.WHITE)) ){

        if(ScannerInput.isQueenSideCastling){


        //LEFTSIDE QUEEN; WHITE
        for(int i = 5; i > 1; i--){
        	if( (Utils.getPiece(9, i, boardMatrix).owner != PlayerType.NOT_SET) ){
        		valid = false;
//        		System.out.println("Castling m");
        		MoveValidationErrors.illegalCastlingMove(line+badmove);
        	}
          }
        }
        else if(ScannerInput.isKingSideCastling){

        //RIGHTSIDE KING; WHITE
        for(int i = 6; i < 9; i++){
        	if( (Utils.getPiece(9, i, boardMatrix).owner != PlayerType.NOT_SET) ){
        		valid = false;
//        		System.out.println("Castling p");
        		MoveValidationErrors.illegalCastlingMove(line+badmove);
        	}
          }
        }

      }

        else{// player: black

        	if(ScannerInput.isQueenSideCastling){
        	//LEFTSIDE QUEEN; BLACK
        	for(int i = 5; i > 1; i--){
        		if( (Utils.getPiece(0, i, boardMatrix).owner != PlayerType.NOT_SET) ){
//                System.out.println("Castling r ");
            	valid = false;
        		MoveValidationErrors.illegalCastlingMove(line+badmove);
        		}
        	}
        	}
        	else if(ScannerInput.isKingSideCastling){
        	//RIGHTSIDE KING; BLACK
        	for(int i = 6; i < 9; i++){
        		if( (Utils.getPiece(0, i, boardMatrix).owner != PlayerType.NOT_SET) ){
//        		System.out.println("Castling b ");
        		valid = false;
        		MoveValidationErrors.illegalCastlingMove(line+badmove);
        		}
        	}
        }
    }

        return valid;
}

    public void promotionRuleSet(Move move, int line, Piece piece) throws Exception {


        // Validate piece is a Drunken Pawn or Pawn

        //1.
    	Piece pieceQ =  Utils.getPiece(move.to.getRow(),move.to.getColumn(), boardMatrix);
//    	System.out.print("piece is "+pieceQ.getType());

        if( (!(pieceQ.getType().equals(PieceType.PAWN))) && (!(pieceQ.getType().equals(PieceType.DRUNKED_PAWN))) ){
        	//if not pawn piece
            isValidPromotion = false;
//            System.out.println("Valid Promotion a");
            MoveValidationErrors.illegalPromotion(line+badmove);


        } else {
            if(currentPlayer == PlayerType.WHITE){
            	//switch
                if(move.to.getRow() == 0) {
                	//board section allowed promotion
//                	System.out.println("Valid Promotion b");
                    isValidPromotion = true;

                } else {
                	//not reached section
                    isValidPromotion = false;
//                    System.out.println("Valid Promotion c");
                    MoveValidationErrors.illegalPromotion(line+badmove);
                }
            } else { // Is BLACK
                if(move.to.getRow() == 9) {
                	//board in section allowed promotion
                    isValidPromotion = true;

                }
                else {
                	//not reached section
                    isValidPromotion = false;
//                    System.out.println("Valid Promotion z");
                    MoveValidationErrors.illegalPromotion(line+badmove);
                }
            }
         //CHANGED : LOOK
            // If 0 then no piece found with that Type in map

            if(ScannerInput.promotionP == PieceType.SPACE){
            	isValidPromotion = true;
            }

            int available = pieceCount.getOrDefault(move.promotionPiece ,0);

            //2.
            if(available > 0){
                isValidPromotion = true;

            }

            else {
                isValidPromotion = false;
//                System.out.println("Valid Promotion d");
                MoveValidationErrors.illegalPromotion(line+badmove);
            }

            //3.
            if(ScannerInput.promotionP.equals(PieceType.ELEPHANT)) {
                isValidPromotion = false;
//                System.out.println("Valid Promotion e");
                MoveValidationErrors.illegalPromotion(line+badmove);
            }

            King k = getCurrentKing();
            Position kingPos = Utils.findPositionOnBoard(k, boardMatrix);

            //4.
            if(k.isInCheck(kingPos, this)){ //Current king is in check and move doesnt remove him from check
                Board simBoard = new Board(this);

                simBoard.setEntryInBoardMatrix(move.from.column,move.from.row, new Space());
                simBoard.setEntryInBoardMatrix(move.to.column, move.to.row, piece);

                if(k.isInCheck(Utils.findPositionOnBoard(k, simBoard.getBoardMatrix()), simBoard)) {
                    isValidPromotion = false;
//                    System.out.println("Valid Promotion f");
                    MoveValidationErrors.illegalPromotion(line+badmove);
                }
            } else if(k.isInCheckMate(kingPos, this)){
                isValidPromotion = false;
//                System.out.println("Valid Promotion g");
                MoveValidationErrors.illegalPromotion(line+badmove);
            }
        }
    }

    public void moveRuleSet(Move move, int line, Piece piece) throws Exception {


        // Invalid if move piece doesn't exist in position specified
    	Position piecePos = move.from;


        if(pieceExistsAt(piece, piecePos)){
        	//piece at start
            isValidMove = true;
        } else {
            isValidMove = false;
//            System.out.println("Move a");
            MoveValidationErrors.illegalMove(line+badmove);
        }




        King k = getCurrentKing();
        Position kingPos = Utils.findPositionOnBoard(k, boardMatrix);

        if(k.isInCheck(kingPos, this)){ //Current king is in check and move doesn't remove him from check
            Board simBoard = new Board(this);

            simBoard.setEntryInBoardMatrix(move.from.column, move.from.row, new Space());
            simBoard.setEntryInBoardMatrix(move.to.column, move.to.row, piece);

            if(k.isInCheck(Utils.findPositionOnBoard(k, simBoard.getBoardMatrix()), simBoard)) {
                isValidMove = false;
                MoveValidationErrors.illegalMove(line+badmove);

            }
        }

        if(halfMoveClock == 50 && !(piece instanceof Pawn)) {
            isValidMove = false;
//            System.out.println("Move d ");
            MoveValidationErrors.illegalMove(line+badmove);

        }
    }

    public void captureRuleSet(Move move, int line, Piece piece) throws Exception {


        Piece capturedPiece = Utils.getPiece(move.to.getRow(), move.to.getColumn(), boardMatrix);
        Piece actionPiece = Utils.getPiece(move.from.getRow(), move.from.getColumn(), boardMatrix); // piece performing the capture action

        if(capturedPiece.getOwner() != actionPiece.getOwner()){
        	//capture opponent's pieces only
            isValidCapture = true;
        } else {

            isValidCapture = false;
//            System.out.println("Capture W");
            MoveValidationErrors.illegalCapture(line+badmove);
        }

        King k = getCurrentKing();
        King oponentKing = getKingFor(Utils.nextPlayer(currentPlayer));

        Position kingPos = Utils.findPositionOnBoard(k, boardMatrix);

        if(k.isInCheck(kingPos, this)){ //Current king is in check and capture doesnt remove him from check
            Board simBoard = new Board(this);

            simBoard.setEntryInBoardMatrix(move.from.column, move.from.row, new Space());
            simBoard.setEntryInBoardMatrix(move.to.column, move.to.row, piece);

            if(k.isInCheck(Utils.findPositionOnBoard(k, simBoard.getBoardMatrix()), simBoard)) {
                isValidCapture = false;

//                System.out.println("Capture L");
                MoveValidationErrors.illegalCapture(line+badmove);
            }
        } else if(k.isInCheckMate(kingPos, this)){
            isValidCapture = false;
//            System.out.println("Capture J");
            MoveValidationErrors.illegalCapture(line+badmove);
        }
    }

    public boolean pieceExistsAt(Piece piece, Position position) {
        Piece foundPiece = Utils.getPiece(position.row, position.column, boardMatrix);

        if(piece.getClass().equals(foundPiece.getClass()) && piece.getOwner().equals(foundPiece.getOwner())) {
            return true;
        } else return false;
    }




    /**
     * Checks if Capture Boolean is Set on Move when move puts opposition King in Capture
     */
    public void isAllocatedCapture(Move move, int line, Piece movedPiece) throws Exception {

        King oponentKing = getKingFor(Utils.nextPlayer(currentPlayer));
        King myKing = getKingFor(movedPiece.getOwner());
        Board simBoard = new Board(this);
        Position opkingPos = Utils.findPositionOnBoard(oponentKing, simBoard.getBoardMatrix());
        Position myKingPos = Utils.findPositionOnBoard(myKing, simBoard.getBoardMatrix());


        simBoard.setEntryInBoardMatrix(move.from.column, move.from.row, new Space());
        simBoard.setEntryInBoardMatrix(move.to.column, move.to.row, movedPiece);

        //change: add checkmate when relevant > (oponentKing.isInCheckMate(opkingPos, simBoard)  && !move.isCheckMate)

        if((oponentKing.isInCheck(opkingPos, simBoard) && !move.isCheck)) {
            isValidCapture = false;

//            System.out.println("Capture M");
            MoveValidationErrors.illegalCapture(line+badmove);
        }
    }

    /**
     * Checks if Move boolean is set on Move when
     */
    public void isAllocatedMove(Move move, int line, Piece piece) throws Exception {


        Piece pieceAtDestination = Utils.getPiece(move.to.row, move.to.column, boardMatrix);

        if( (pieceAtDestination.getOwner()) == (Utils.nextPlayer(currentPlayer)) ){
        	isValidMove = false; //should have been marked capture
        	MoveValidationErrors.illegalMove(line+badmove);
        }
        else{
        	isValidMove = true;
        }

        King oponentKing = getKingFor(Utils.nextPlayer(currentPlayer));
        Board simBoard = new Board(this);
        Position kingPos = Utils.findPositionOnBoard(oponentKing, simBoard.getBoardMatrix());

        simBoard.setEntryInBoardMatrix(move.from.column, move.from.row, new Space() );
        simBoard.setEntryInBoardMatrix(move.to.column, move.to.row, piece);

        if(oponentKing.isInCheck(kingPos,simBoard) && !move.isCheck){

        	isValidMove = false;
        	MoveValidationErrors.illegalMove(line+badmove);
        }

        }

    public void isAllocatedPromotion(Move move, int line, Piece piece) throws Exception {


        King oponentKing = getKingFor(Utils.nextPlayer(currentPlayer));
        Board simBoard = new Board(this);
        Position kingPos = Utils.findPositionOnBoard(oponentKing, simBoard.getBoardMatrix());


        simBoard.setEntryInBoardMatrix(move.from.column, move.from.row, new Space());
        simBoard.setEntryInBoardMatrix(move.to.column, move.to.row, piece);

        if((oponentKing.isInCheck(kingPos, simBoard) && !move.isCheck) || (oponentKing.isInCheckMate(kingPos, simBoard)  && !move.isCheckMate)) {
            isValidMove = false;
            MoveValidationErrors.illegalPromotion(line+badmove);
        }
    }

    public void moveCastling(Move move) throws Exception {

        King king  =  getCurrentKing();
        Rook rookToMove = new Rook(currentPlayer);
        Position kingFrom = Utils.findPositionOnBoard(king, boardMatrix);

        if(currentPlayer == PlayerType.WHITE){
            move.setFrom(kingFrom);

            if(move.isQueenSide){
                whiteStatus.hasQueenCostling = false;
                whiteStatus.hasKingCostling = false;
                move.setTo(new Position(2, 9));
            } else { // King side
                whiteStatus.hasKingCostling = false;
                whiteStatus.hasQueenCostling = false;
                move.setTo(new Position(8, 9));
            }

        } else { // BLACK
            move.setFrom(kingFrom);

            if(move.isQueenSide){
                blackStatus.hasQueenCostling = false;
                blackStatus.hasKingCostling = false;
                move.setTo(new Position(2, 0));
            } else { // King Side
                blackStatus.hasKingCostling = false;
                blackStatus.hasQueenCostling = false;
                move.setTo(new Position(8, 0));
            }
        }

        movePiece(move, king);// First we move the king

        if(currentPlayer == PlayerType.WHITE){

            if(move.isQueenSide){
                Position p = new Position(0, 9);

                move.setFrom(p);
                move.setTo(new Position(3, 9));
                rookToMove = (Rook) Utils.getPiece(p.row, p.column, boardMatrix);

            } else { // King side
                Position p = new Position(9, 9);

                move.setFrom(p);
                move.setTo(new Position(7, 9));
                rookToMove = (Rook) Utils.getPiece(p.row, p.column, boardMatrix);

            }
        } else { // BLACK

            if(move.isQueenSide){
                Position p = new Position(0, 0);

                move.setFrom(p);
                move.setTo(new Position(3, 0));
                rookToMove = (Rook) Utils.getPiece(p.row, p.column, boardMatrix);

            } else { // King side
                Position p = new Position(9, 0);

                move.setFrom(p);
                move.setTo(new Position(7, 0));
                rookToMove = (Rook) Utils.getPiece(p.row, p.column, boardMatrix);

            }
        }

        movePiece(move, rookToMove);
    }

    public void movePiece(Move move, Piece piece) {

    	setEntryInBoardMatrix(move.from.getColumn(),move.from.getRow(),new Space());

    	setEntryInBoardMatrix(move.to.getColumn(),move.to.getRow(),piece);


    }

    private King getKingFor(PlayerType playerType){
        return (King) boardMatrix.stream().flatMap(Collection::stream)
                .filter(p -> p.getOwner() == playerType && Utils.isKing(p)).findFirst().get();
    }

    public King getCurrentKing(){
        return getKingFor(currentPlayer);
    }


    public PlayerType getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerType currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public CostlingStatus getBlackStatus() {
        return blackStatus;
    }

    public void setBlackStatus(CostlingStatus blackStatus) {
        this.blackStatus = blackStatus;
    }

    public CostlingStatus getWhiteStatus() {
        return whiteStatus;
    }

    public void setWhiteStatus(CostlingStatus whiteStatus) {
        this.whiteStatus = whiteStatus;
    }

    public List<List<Piece>> getBoardMatrix() {
        return boardMatrix;
    }

    public void setBoardMatrix(List<List<Piece>> boardMatrix) {
        this.boardMatrix = boardMatrix;
    }

    public void setEntryInBoardMatrix(int column, int row, Piece piece) {


        int rowCount = 0;
        for(List<Piece> pieces:  this.getBoardMatrix()){
            if(rowCount == row) {
                pieces.set(column, piece);
                this.boardMatrix.set(row, pieces);

            }
            rowCount++;
        }
    }

    public void setEntryInPieceCount(PieceType type, Integer i) {
        this.pieceCount.replace(type, i);
    }

    public int getHalfMoveClock() {
        return halfMoveClock;
    }

    public void setHalfMoveClock(int halfMoveClock) {

        this.halfMoveClock = halfMoveClock;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    public Map<PieceType, Integer> getPieceCount() {
        return pieceCount;
    }

    public void setPieceCount(Map<PieceType, Integer> pieceCount) {
        this.pieceCount = pieceCount;
    }

    public void resetStatusVaraibles(){
        this.isValidCapture = true;
        this.isValidMove = true;
        this.isValidPromotion = true;
        this.isValidCheck = true;
        this.isValidCheckMate = true;
        this.isValidCastling = true;
    }

    public static void printBoard(){

    	//print piece allocation
    	 int counting = 0;

         for(int indexE = 0; indexE < ScannerInput.numbersPieces.length; indexE++){
         	try{
         		int value = Integer.parseInt(String.valueOf(ScannerInput.numbersPieces[indexE]));
         		counting++;
         	} catch (Exception e){
         		break;
         	}
         }

        for(int indexB = 0; indexB < counting; indexB++){
         	System.out.println(ScannerInput.charsUsed[indexB] + ":" + ScannerInput.numbersPieces[indexB]);

         }

        //divider
        System.out.println("-----");

        //board

        for(List<Piece> row: boardMatrix){
        	int i = 0;
            for(Piece piece: row){
            	i++;
                char dp;
                if(piece.getOwner() == PlayerType.WHITE){
                	//switch print
                    String c = getPieceAllocationChar(piece.getType()) + "";
                    c.trim();
                    dp = c.toUpperCase().charAt(0);

                } else {
                    dp = getPieceAllocationChar(piece.getType());
                }

                if (i==10) {
                	System.out.print(dp);
                	// System.out.print(" ");
                }else{
                	System.out.print(dp);
                	System.out.print(" ");
                }
            }

            System.out.println();
        }

        //divider
        System.out.println("-----");

        //status line
        if(currentPlayer == PlayerType.BLACK) {
            System.out.print("b");
        } else {
            System.out.print("w");
        }
        System.out.print(":");

        if(blackStatus.hasQueenCostling){
            System.out.print("+");
        } else {
            System.out.print("-");
        }

        if(blackStatus.hasKingCostling){
            System.out.print("+");
        } else {
            System.out.print("-");
        }

        if(whiteStatus.hasQueenCostling){
            System.out.print("+");
        } else {
            System.out.print("-");
        }

        if(whiteStatus.hasKingCostling){
            System.out.print("+");
        } else {
            System.out.print("-");
        }

        System.out.print(":" + halfMoveClock);
        System.out.print(":" + moveCounter);


    }


    public static char getPieceAllocationChar(PieceType type){
        char allocationChar = ' ';
        switch (type) {
            case KING: allocationChar = 'k'; break;
            case QUEEN: allocationChar = 'q';break;
            case AMAZON: allocationChar = 'a';break;
            case PAWN: allocationChar = 'p';break;
            case BISHOP: allocationChar = 'b';break;
            case DRAGON: allocationChar = 'f';break;
            case ROOK: allocationChar = 'r';break;
            case KNIGHT: allocationChar = 'n';break;
            case ELEPHANT: allocationChar = 'e';break;
            case PRINCESS: allocationChar = 'w';break;
            case DRUNKED_PAWN: allocationChar = 'd';break;
            case SPACE: allocationChar = '.';break;
        }

        return allocationChar;
    }
}
