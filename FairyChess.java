//package root;

//import root.pieces.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;


public class FairyChess {

    public static void main(String[] args) {

        try {

            Scanner boardScanner = new Scanner(new File("/Users/cmlprinsloogmail.com/Documents/Java/21807744-project-master/resource/king_valid_2.board"));
            Scanner moveScanner = new Scanner(new File("/Users/cmlprinsloogmail.com/Documents/Java/21807744-project-master/resource/king_valid_2.moves"));

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
