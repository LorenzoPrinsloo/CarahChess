//package root;

//import com.sun.tools.javac.util.Pair;
//import root.pieces.King;
//import root.pieces.Piece;
//import root.pieces.Space;
import java.util.List;

public class Utils {


    public static int columnPosition(char inputChar) {
    	//!!!! REMEMBER TO ADD 1
    	String alphabet = "abcdefghij";
    	int numberVal = 0;
    	
    	if(inputChar == alphabet.charAt(0)){
    		return numberVal;
    	}
    	else if(inputChar == alphabet.charAt(1)){
    		numberVal = 1;
    	}
    	else if(inputChar == alphabet.charAt(2)){
    		numberVal = 2;
    	}
    	else if(inputChar == alphabet.charAt(3)){
    		numberVal = 3;
    	}
    	else if(inputChar == alphabet.charAt(4)){
    		numberVal = 4;
    	}
    	else if(inputChar == alphabet.charAt(5)){
    		numberVal = 5;
    	}
    	else if(inputChar == alphabet.charAt(6)){
    		numberVal = 6;
    	}
    	else if(inputChar == alphabet.charAt(7)){
    		numberVal = 7;
    	}
    	else if(inputChar == alphabet.charAt(8)){
    		numberVal = 8;
    	}
    	else if(inputChar == alphabet.charAt(9)){
    		numberVal = 9; 
    	}
        return numberVal;
    }

    public static char[] toArray(List<Character> chars) {
        char[] charArray = new char[chars.size()];

        int i = 0;
        for(char c: chars) {
            charArray[i] = c;
            i++;
        }

        return charArray;
    }

    public static boolean isBetween(int start, int finish, int value) {
        if (start <= value && value <= finish) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calculate the step needed to navigate through matrix in a specific direction
     */
    
    
    public static Integer fstCalc(Move move) {
        Integer fst_row = toStep(move.getTo().getRow() - move.getFrom().getRow());

        return fst_row;
    }
    
    public static Integer sndCalc(Move move){
    	Integer snd_col = toStep(move.getTo().getColumn() - move.getFrom().getColumn());
    	return snd_col;
    }

    /**
     * Convert difference in number to a step for a array/iterator
     */
    private static Integer toStep(Integer i){
        if(i > 0){
            return 1;
        } else if(i < 0){
            return -1;
        } else {
            return 0;
        }
    }

    public static boolean isDiagonalMove(Move move) {
        Integer left = Math.abs(move.getFrom().getRow() - move.getTo().getRow());
        Integer right = Math.abs(move.getFrom().getColumn() - move.getTo().getColumn());

        return left == right;
    }

    public static boolean isLongitudinalMove(Move move){
        
    	Integer rowDif = move.getFrom().getRow() - move.getTo().getRow();
        Integer colDif = move.getFrom().getColumn() - move.getTo().getColumn();

        if(rowDif == 0 || colDif == 0){
            return true;
        } else return false;
    }
    

    public static boolean isValidLongitudinalMove(Move move, Board board, PlayerType owner) {
        // fst is rowStep and snd is colStep
        List<List<Piece>> boardMatrix = board.getBoardMatrix();

        int rowCurrent = move.getFrom().getRow() + fstCalc(move); // this is done so first block checked isn't the piece itself
        int colCurrent = move.getFrom().getColumn() + sndCalc(move);
        
        int rowDest = move.getTo().getRow();
        int colDest = move.getTo().getColumn();
        
        while((rowCurrent <= 9) && (colCurrent <=9) && (rowCurrent >=0) && (colCurrent>=0)){
        	
            if((isSpace(boardMatrix.get(rowCurrent).get(colCurrent)) || boardMatrix.get(rowCurrent).get(colCurrent).getOwner() != owner) && rowCurrent == rowDest && colCurrent == colDest) {
                return true;
            } else if(!isSpace(boardMatrix.get(rowCurrent).get(colCurrent))) {
                return false;
            }

            rowCurrent = rowCurrent + fstCalc(move);
            colCurrent = colCurrent + sndCalc(move);
        }
           
        return true;
    }

    public static boolean isValidDiagonalMove(Move move, Board board, PlayerType owner) {
        // fst is rowStep and snd is colStep
        List<List<Piece>> boardMatrix = board.getBoardMatrix();

        int rowCurrent = move.getFrom().getRow() + fstCalc(move); // this is done so first block checked isn't the piece itself
        int colCurrent = move.getFrom().getColumn() + sndCalc(move);

        int rowDest = move.getTo().getRow();
        int colDest = move.getTo().getColumn();

        while(rowCurrent <= 9 && colCurrent <= 9 && (rowCurrent >=0) && (colCurrent>=0)){
            if((isSpace(boardMatrix.get(rowCurrent).get(colCurrent)) || boardMatrix.get(rowCurrent).get(colCurrent).getOwner() != owner) && rowCurrent == rowDest && colCurrent == colDest) {
                return true;
            } else if(!isSpace(boardMatrix.get(rowCurrent).get(colCurrent))) {

                return false;
            }

            rowCurrent = rowCurrent + fstCalc(move);
            colCurrent = colCurrent + sndCalc(move);
        }

        return true;
    }

    public static Position findPositionOnBoard(final Piece piece, List<List<Piece>> matrix) throws Exception {

        Position pos = new Position(0,0);

        for(List<Piece> row: matrix){

            pos.setColumn(0);

            for(Piece p: row){
                if(p.hashCode() == piece.hashCode()){
                    return pos;
                }
                pos.incrementColumn();
            }

            pos.incrementRow();
        }

        // Hasnt found the piece on the board which shouldn't happen so we throw a exception
        throw new Exception("No Piece found on Board for "+ piece);
    }

    public static boolean isSpace(Piece p) {
        return p.getClass() == Space.class;
    }

    public static boolean isKing(Piece p) {
        return p.getClass() == King.class;
    }

    public static Piece getPiece(int row, int column, List<List<Piece>> matrix) {
        return matrix.get(row).get(column);
    }

    public static PlayerType nextPlayer(PlayerType currentPlayer) {
        if(currentPlayer == PlayerType.BLACK){
            return PlayerType.WHITE;
        } else return PlayerType.BLACK;
    }

    public static boolean isSamePosition(Position p1, Position p2){
        if(p1.getRow() == p2.getRow() && p1.getColumn() == p2.getColumn()){
            return true;
        } else {
            return false;
        }
    }
    
    public static int reverseConV(int line){
    //from theirs to mine	
    
    	if(line == 10){
          	line = 0;
          }
          else if(line == 9){
          	line = 1;
          }
          else if(line == 8){
          	line = 2;
          }
          else if(line == 7){
          	line = 3;
          }
          else if(line == 6){
          	line = 4;
          }
          else if(line == 5){
          	line = 5;
          }
          else if(line == 4){
          	line = 6;
          }
          else if(line == 3){
          	line = 7;
          }
          else if(line == 2){
          	line = 8;
          }
          else if(line == 1){
          	line = 9;
    }
    	return line;
    }

    public static int conV(int line){
    //from mine to theirs
    	
      if(line == 0){
      	line = 10;
      }
      else if(line == 1){
      	line = 9;
      }
      else if(line == 2){
      	line = 8;
      }
      else if(line == 3){
      	line = 7;
      }
      else if(line == 4){
      	line = 6;
      }
      else if(line == 5){
      	line = 5;
      }
      else if(line == 6){
      	line = 4;
      }
      else if(line == 7){
      	line = 3;
      }
      else if(line == 8){
      	line = 2;
      }
      else if(line == 9){
      	line = 1;
    }
      return line;
}
}
