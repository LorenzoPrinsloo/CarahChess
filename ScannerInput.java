//package root;
//
//import root.pieces.*;

import java.util.*;

//import static root.Utils.columnPosition;


public class ScannerInput {

	public static boolean isKingSideCastling = false;
    public static boolean isQueenSideCastling = false;
	public static List<Move> moves = new ArrayList<>();
	public static boolean hasSymbol = false;
	public static String PromoPiece;
	public static boolean moveCast = false;
	public static String[] CastlingType = new String[2];
	public static char[] charsUsed = new char[12];
	public static char[] numbersPieces = new char[12];
	public static int Errorlinecounter; 
	public static PieceType promotionP;
	
	
	public static List<Move> readMoves(Scanner scanner) {

        while (scanner.hasNext()) {

            String line = scanner.nextLine();
            if (line.startsWith("%")) {
            	Board.badmove++;
                continue;
            }

         
         if( (line.lastIndexOf('0')==2) && (line.substring(0, 3).equals("0-0")) ){
        	 isKingSideCastling = true;
         }
         else if( (line.lastIndexOf('0')==4) && (line.substring(0, 5).equals("0-0-0")) ){
        	 isQueenSideCastling = true;
         }

            String delimiter;
            if (line.lastIndexOf('x') > 0) {
                delimiter = "x";
            } else {
                delimiter = "-";
            }

            //String [] of positions (start) then (destination)
            String[] positions;


            int fromRow;
            int toRow;
            int fromColumn;
            int toColumn;
            char fromAlphaCharacter;
            char toAlphaCharacter;
            int fromRowRook = 10; // no row 10; if not pick-up is Queen/King Castling
            int toRowRook = 10; // no row 10; if not pick-up is Queen/King Castling
            int fromColRook = 11; // no column 11; if not pick-up is Queen/King Castling
            int toColRook = 11; // no column 11; if not pick-up is Queen/King Castling

            //String [] of positions (start) then (destination)
            positions = line.split(delimiter);
                     
            
            if( (isKingSideCastling == true) && (Board.currentPlayer == PlayerType.BLACK)){
            	fromRow = 0;
                toRow = 0;
                
                fromRowRook = 0;
                toRowRook = 0; 
                
                fromColRook = Utils.columnPosition('j');
                toColRook = Utils.columnPosition('h');
                
                fromAlphaCharacter = 'f';
                toAlphaCharacter = 'i';
                
                fromColumn = Utils.columnPosition(fromAlphaCharacter);
                toColumn = Utils.columnPosition(toAlphaCharacter);
                
            } else if((isKingSideCastling == true) && (Board.currentPlayer == PlayerType.WHITE)){
            	fromRow = 9;
                toRow = 9;
                
                fromRowRook = 9;
                toRowRook = 9; 
                
                fromColRook = Utils.columnPosition('j');
                toColRook = Utils.columnPosition('h');
                        
                fromAlphaCharacter = 'f';
                toAlphaCharacter = 'i';
                        
                fromColumn = Utils.columnPosition(fromAlphaCharacter);
                toColumn = Utils.columnPosition(toAlphaCharacter);
                
            } else if( ((isQueenSideCastling == true) && (Board.currentPlayer == PlayerType.BLACK)) ){
            	fromRow = 0;
                toRow = 0;
                
                fromRowRook = 0;
                toRowRook = 0; 
                
                fromColRook = Utils.columnPosition('a');
                toColRook = Utils.columnPosition('d');
                                
                fromAlphaCharacter = 'f';
                toAlphaCharacter = 'c';
                                
                fromColumn = Utils.columnPosition(fromAlphaCharacter);
                toColumn = Utils.columnPosition(toAlphaCharacter);
            	
            } else if(((isQueenSideCastling == true) && (Board.currentPlayer == PlayerType.WHITE)) ){
            	
            	fromRow = 9;
                toRow = 9;
                
                fromRowRook = 9;
                toRowRook = 9; 
                
                fromColRook = Utils.columnPosition('a');
                toColRook = Utils.columnPosition('d');
                                
                fromAlphaCharacter = 'f';
                toAlphaCharacter = 'c';
                                
                fromColumn = Utils.columnPosition(fromAlphaCharacter);
                toColumn = Utils.columnPosition(toAlphaCharacter);
            	
            } else{ // normal move
            	
            	 fromRow = Utils.reverseConV(Integer.parseInt(String.valueOf( (positions[0].charAt(1) ) ) ) );
                 toRow = Utils.reverseConV(Integer.parseInt(String.valueOf((positions[1].charAt(1) ) ) ) );
                 

                 //first move's start column (alphabet)
                 fromAlphaCharacter = positions[0].charAt(0);
                 //first move's destination column (alphabet)
                 toAlphaCharacter = positions[1].charAt(0);


                 //convert to integer
                 fromColumn = Utils.columnPosition(fromAlphaCharacter);
                 toColumn = Utils.columnPosition(toAlphaCharacter);
            }


            Move m;

            // IF Move is a Promotion/Capture/Normal Move
            if(line.lastIndexOf('=') > 0) {
            

                PieceType pt = null;
                switch (line.split("=")[1].toUpperCase()) {
                    case "K": pt = PieceType.KING;
                    case "Q": pt = PieceType.QUEEN;
                    case "A": pt = PieceType.AMAZON;
                    case "P": pt = PieceType.PAWN;
                    case "B": pt = PieceType.BISHOP;
                    case "F": pt = PieceType.DRAGON;
                    case "R": pt = PieceType.ROOK;
                    case "N": pt = PieceType.KNIGHT;
                    case "E": pt = PieceType.ELEPHANT;
                    case "W": pt = PieceType.PRINCESS;
                    case "D": pt = PieceType.DRUNKED_PAWN;
                    case ".": pt = PieceType.SPACE;
                    
                }
                m = new Move(new Position(fromColumn, fromRow), new Position(toColumn, toRow), pt, true);
            } else if (line.lastIndexOf('x') > 0) {
//            	System.out.print("Reach E");
            	
                int checkIndex = line.lastIndexOf("+");
                
                boolean isCheck = false;
                boolean isCheckMate = false;

                if (checkIndex == 5) {
                	
                    isCheck = true;
                } else if (checkIndex == 6) {
               
                    isCheck = true;
                    isCheckMate = true;
                }
                m = new Move(new Position(fromColumn, fromRow), new Position(toColumn, toRow), true, isCheck, isCheckMate);
            } else if(isKingSideCastling || isQueenSideCastling) {
            	
                    if(isQueenSideCastling){ // IS Queen side
                        m = new Move(new Position(fromColumn, fromRow), new Position(toColumn, toRow), new Position(fromColRook, fromRowRook), new Position(toColRook, toRowRook), true, true, false);
                    } else { // IS King side
                        m = new Move(new Position(fromColumn, fromRow), new Position(toColumn, toRow), new Position(fromColRook, fromRowRook), new Position(toColRook, toRowRook), true, false, true);
                    }
                } else {
                        int checkIndex = line.lastIndexOf("+");
                        boolean isCheck = false;
                        boolean isCheckMate = false;

                        if (checkIndex == 5) {
//                        	System.out.print(" Reach A ");
                            isCheck = true;
                        } else if (checkIndex == 6) {
//                         	System.out.print(" Reach B ");
                            isCheck = true;
                            isCheckMate = true;
                        }

                        m = new Move(new Position(fromColumn, fromRow), new Position(toColumn, toRow), isCheck, isCheckMate);
                    }

                    moves.add(m);
                }

                return moves;
            }

    public static Board readBoard(Scanner scanner) {

        scanner.useDelimiter("-----");

        String pieceCount = scanner.next();
        String board = scanner.next();
        String status = scanner.next();

        Map<PieceType, Integer> pc = new HashMap<>();
        
        int indexD = 0;
        int indexA = 0;
        for(String row: pieceCount.trim().split("\n")){
        	
        	char w = row.split(":")[1].charAt(0);
            char p = row.split(":")[0].charAt(0);
            
            //char val then number allocated for end board
            charsUsed[indexA]= p;
            
            //num values
            numbersPieces[indexD] = w;
            
            Integer n = Integer.parseInt(row.split(":")[1]);
        	
            switch (p) {
                case 'k': pc.put(PieceType.KING, n);
                case 'q': pc.put(PieceType.QUEEN, n);
                case 'a': pc.put(PieceType.AMAZON, n);
                case 'p': pc.put(PieceType.PAWN, n);
                case 'b': pc.put(PieceType.BISHOP, n);
                case 'f': pc.put(PieceType.DRAGON, n);
                case 'r': pc.put(PieceType.ROOK, n);
                case 'n': pc.put(PieceType.KNIGHT, n);
                case 'e': pc.put(PieceType.ELEPHANT, n);
                case 'w': pc.put(PieceType.PRINCESS, n);
                case 'd': pc.put(PieceType.DRUNKED_PAWN, n);
            }
            indexA++;
            indexD++;
        }
        

        List<List<Piece>> boardAsPieces = new ArrayList<>();

        for(String piece : board.trim().split("\n")){

            List<Piece> row = new ArrayList<>();

            String[] chars = piece.split(" ");

            for(String c: chars) {

                switch(c.charAt(0)) {
                    case 'K' : row.add(new King(PlayerType.WHITE)); break;
                    case 'k' : row.add(new King(PlayerType.BLACK)); break;
                    case 'Q' : row.add(new Queen(PlayerType.WHITE)); break;
                    case 'q' : row.add(new Queen(PlayerType.BLACK)); break;
                    case 'A' : row.add(new Amazon(PlayerType.WHITE)); break;
                    case 'a' : row.add(new Amazon(PlayerType.BLACK)); break;
                    case 'F' : row.add(new Dragon(PlayerType.WHITE)); break;
                    case 'f' : row.add(new Dragon(PlayerType.BLACK)); break;
                    case 'D' : row.add(new DrunkenPawn(PlayerType.WHITE)); break;
                    case 'd' : row.add(new DrunkenPawn(PlayerType.BLACK)); break;
                    case 'B' : row.add(new Bishop(PlayerType.WHITE)); break;
                    case 'b' : row.add(new Bishop(PlayerType.BLACK)); break;
                    case 'E' : row.add(new Elephant(PlayerType.WHITE)); break;
                    case 'e' : row.add(new Elephant(PlayerType.BLACK)); break;
                    case 'N' : row.add(new Knight(PlayerType.WHITE)); break;
                    case 'n' : row.add(new Knight(PlayerType.BLACK)); break;
                    case 'P' : row.add(new Pawn(PlayerType.WHITE)); break;
                    case 'p' : row.add(new Pawn(PlayerType.BLACK)); break;
                    case 'W' : row.add(new Princess(PlayerType.WHITE)); break;
                    case 'w' : row.add(new Princess(PlayerType.BLACK)); break;
                    case 'R' : row.add(new Rook(PlayerType.WHITE)); break;
                    case 'r' : row.add(new Rook(PlayerType.BLACK)); break;
                    default : row.add(new Space()); break;
                }
            }

            boardAsPieces.add(row);
        }

        String[] statusItems = status.trim().split(":");

        PlayerType pt;
        if(statusItems[0].equals("w")) {
           pt = PlayerType.WHITE;
        } else {
            pt = PlayerType.BLACK;
        }

        String costling = statusItems[1];
        CostlingStatus blackStatus = new CostlingStatus();

        if(costling.charAt(0) == '+') {
            blackStatus.setHasQueenCostling(true);
        } else {
            blackStatus.setHasQueenCostling(false);
        }

        if(costling.charAt(1) == '+') {
            blackStatus.setHasKingCostling(true);
        } else {
            blackStatus.setHasKingCostling(false);
        }

        CostlingStatus whiteStatus = new CostlingStatus();

        if(costling.charAt(2) == '+') {
            whiteStatus.setHasQueenCostling(true);
        } else {
            whiteStatus.setHasQueenCostling(false);
        }

        if(costling.charAt(3) == '+') {
            whiteStatus.setHasKingCostling(true);
        } else {
            whiteStatus.setHasKingCostling(false);
        }

        return new Board(pt, boardAsPieces, pc, blackStatus, whiteStatus, Integer.parseInt(statusItems[2]), Integer.parseInt(statusItems[3]));

    }


}