package Reversi.qingyv;

public interface ChessGame {

    public void initialize();

    public Field getField();

    public void launcher();

    public void endOrNot();

    public String whoChess();

    public boolean getNotEnd();

    // public boolean[] getCanUseFuntionlity();
}
