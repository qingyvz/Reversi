package Reversi.qingyv;

public class WhiteChess extends Chess implements Cell {

    private boolean spareOrOccupied = true;

    public WhiteChess() {
        chessColor = "WHITE";
    }

    @Override
    public BlackChess change(char ch) {
        return new BlackChess();
    }

    @Override
    public void setSpare() {
        spareOrOccupied = true;
    }

    @Override
    public void setOccupied() {
        spareOrOccupied = false;
    }

    @Override
    public boolean isSpare() {
        return spareOrOccupied;
    }

    @Override
    public void draw() {
        if (!isSpare()) {
            System.out.printf("%2c", '●');
        }

        // 画棋子
    }

    @Override
    public String type() {
        return chessColor;
    }

}
