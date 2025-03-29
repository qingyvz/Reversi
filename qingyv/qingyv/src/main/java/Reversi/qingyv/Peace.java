package Reversi.qingyv;

public class Peace implements ChessGame {

    private int row = 9;
    private int col = 50;
    private int actuallyRow = 8;
    private int actuallyCol = 8;
    private String type = null;
    private int number = 0;
    private Field fieldMap1;
    private boolean notEnd;

    public Peace(String type, int number) {
        this.type = type;
        this.number = number;
        this.fieldMap1 = new Field(row, col, actuallyRow, actuallyCol, number, type);
        this.notEnd = fieldMap1.getFullMap() < (actuallyCol * actuallyRow);
    }
    // launcher

    @Override
    public void launcher() {
        endOrNot();
        if (!notEnd) {
            System.out.println("this field game over,choose other fields");
        }
        Utils.printMap(fieldMap1.getfieldMap(), row, col);
    }

    @Override
    public void initialize() {
        fieldMap1.initialize();
    }

    @Override
    public Field getField() {
        return fieldMap1;
    }

    @Override
    public void endOrNot() {
        notEnd = Utils.getScoreAndFullMap(actuallyRow, actuallyCol, fieldMap1)[2] < (actuallyCol * actuallyRow);
    }

    public boolean getNotEnd() {
        return notEnd;
    }

    @Override
    public String whoChess() {
        String who = (fieldMap1.getChessTimes() % 2 == 0) ? "WHITE" : "BLACK";
        return who;
    }

}