package Reversi.qingyv;

public class Player {

    private String name;
    private String chessColor;

    public Player(String name, String chessColor) {
        this.name = name;
        this.chessColor = chessColor;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return chessColor;
    }
}
