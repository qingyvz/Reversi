package Reversi.qingyv;

public interface Cell {

    // Removed duplicate method declaration

    void setSpare(); // {spareOroccupied = true;}

    void setOccupied(); // {spareOroccupied = false;}

    boolean isSpare();// {return spareOroccupied;}

    String draw();

    Cell change(char ch);

    String type();

}
