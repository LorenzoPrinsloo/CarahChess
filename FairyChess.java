//package root;

//import root.pieces.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;


public class FairyChess {

    public static void main(String[] args) {

        try {

            Scanner boardScanner = new Scanner(new File(args[0]));
            Scanner moveScanner = new Scanner(new File(args[1]));

            Board board = ScannerInput.readBoard(boardScanner);
            List<Move> moves = ScannerInput.readMoves(moveScanner);
            
            
            int line = 0;
            for(Move move: moves){

                board.move(move, line);
                line++;
            }

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        
        
        Board.printBoard();
    }
}