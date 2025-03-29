package Reversi.qingyv;

import java.io.IOException;
import java.util.List;

public class Utils {

    public static void clear() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void printMap(Cell[][] prMap, int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int n = 0; n < col; n++) {
                prMap[i][n].draw();
            }
            System.out.println();
        }
    }

    public static boolean isAllFull(List<ChessGame> chessGames) {
        boolean isAllFull = true;
        for (ChessGame i : chessGames) {
            if (i != null) {
                isAllFull = !i.getNotEnd();
            }
            if (!isAllFull) {
                return isAllFull;
            }
        }
        return isAllFull;
    }

    public static int[] getScoreAndFullMap(int actuallyRow, int actuallyCol, Field fieldMap) {
        int[] scoreAndFullMap = { 0, 0, 0 };
        for (int i = 0; i <= actuallyCol; i++) {
            for (int n = 0; n <= actuallyRow; n++) {
                if (fieldMap.getfieldMap()[i][n].type().equals("BLACK")) {
                    scoreAndFullMap[0] += 1;
                    scoreAndFullMap[2] += 1;
                } else if (fieldMap.getfieldMap()[i][n].type().equals("WHITE")) {
                    scoreAndFullMap[1] += 1;
                    scoreAndFullMap[2] += 1;
                }
            }
        }
        return scoreAndFullMap;
    }
}
