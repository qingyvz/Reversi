package Reversi.qingyv;

public class NumberAndLetter implements Cell {

    @Override
    public Cell change(char ch) {
        return new NumberAndLetter(ch);
    }

    private char ch;
    private boolean isChar;
    private int i;
    private boolean spareOrOccupied = true;

    public NumberAndLetter(char ch) {
        this.ch = ch;
        isChar = true;
    }

    public NumberAndLetter(int i) {
        this.i = i;
        isChar = false;
    }

    public void change(int num) {
        this.i = num;
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
        if (!isChar) {
            System.out.printf("%2d", i);
        } else {
            System.out.printf("%2c", ch);
        }
    }

    @Override
    public String type() {
        return "NumberAndLetter" + Character.toString(ch);
    }
}
