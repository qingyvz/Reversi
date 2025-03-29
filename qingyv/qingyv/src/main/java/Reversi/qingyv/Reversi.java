package Reversi.qingyv;

import java.util.HashMap;

public class Reversi implements ChessGame {

    private int row = 9;
    private int col = 50;
    private int actuallyRow = 4;
    private int actuallyCol = 4;
    private String type = null;
    private int number = 0;
    private Field fieldMap2;
    private boolean notEnd = true;
    // private boolean[] canUseFuntionlity = { true, true, true, true, true, true };
    private boolean on = true;

    public Reversi(String type, int number) {
        this.type = type;
        this.number = number;
        this.fieldMap2 = new Field(row, col, actuallyRow, actuallyCol, number, type);
        this.notEnd = fieldMap2.getFullMap() < (actuallyCol * actuallyRow);
    }

    @Override
    public void initialize() {
        fieldMap2.initialize();
    }

    @Override
    public void launcher() {
        if (!notEnd) {
            System.out.println("this field game over,choose other fields");

        }
        fieldMap2.calculatChessLocation(whoChess());
        fieldMap2.setScore(Utils.getScoreAndFullMap(actuallyRow, actuallyCol, fieldMap2));
        Utils.printMap(fieldMap2.getfieldMap(), row, col);
        endOrNot();
    }

    @Override
    public Field getField() {
        return fieldMap2;
    }

    @Override
    public void endOrNot() {
        int[] scoreAndFullMap = Utils.getScoreAndFullMap(actuallyRow, actuallyCol, fieldMap2);
        notEnd = scoreAndFullMap[2] < (actuallyCol * actuallyRow) && fieldMap2.getBothCanChess();
        if (!notEnd) {
            System.out.println("WHITE:" + scoreAndFullMap[1] + " BLACK:" + scoreAndFullMap[0]);
            if (scoreAndFullMap[1] > scoreAndFullMap[0]) {
                System.out.println("WHITE WIN");
            } else if (scoreAndFullMap[1] < scoreAndFullMap[0]) {
                System.out.println("BLACK WIN");
            } else {
                System.out.println("DRAW");
            }
        } else if (!fieldMap2.getCanChess()) {
            System.out.println("No chess can be placed, please enter pass");
        }

    }

    @Override
    public String whoChess() {
        String who = (fieldMap2.getChessTimes() % 2 == 0) ? "WHITE" : "BLACK";
        return who;
    }

    public boolean getNotEnd() {
        return notEnd;
    }

}
