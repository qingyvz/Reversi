package Reversi.qingyv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.List;
import java.util.Iterator;

public class Field {

    private int row;
    private int col;
    private int actuallyRow;
    private int actuallyCol;
    private Cell[][] fieldMap;
    private Player[] players;
    private int fullMap = 0;
    private int chessTimes = 0;
    private boolean notEnd = fullMap < (actuallyCol * actuallyRow);
    private int numberfield;
    private String type = null;
    private HashMap<int[], List<int[]>> needReversiChesses = new HashMap<>();
    private boolean canChess = true;
    private boolean[] bothCanChess = { true, true };
    private int nameCol;
    private HashMap<String, Boolean> canUseFuntionlity = new HashMap<String, Boolean>();
    private final int colPlayer = 13;

    public Field(int row, int col, int actuallyRow, int actuallyCol, int numberfield, String type) {
        this.col = col;
        this.row = row;
        this.actuallyRow = actuallyRow;
        this.actuallyCol = actuallyCol;
        this.numberfield = numberfield;
        this.type = type;
        fieldMap = new Cell[row][col];
    }

    public void chess(int number, int letter, String whiteOrBlack) {
        if (fieldMap[number][letter].isSpare()) {
            // chess whiteOrBlack
            fieldMap[number][letter] = (whiteOrBlack.equals("WHITE")) ? new WhiteChess() : new BlackChess();
            fieldMap[number][letter].setOccupied();
            fieldMap[5][nameCol - 1] = (whiteOrBlack.equals("WHITE") ? new NumberAndLetter('.')
                    : new NumberAndLetter('●'));// bad
            fieldMap[4][nameCol - 1] = (whiteOrBlack.equals("BLACK") ? new NumberAndLetter('.')
                    : new NumberAndLetter('○'));
            chessTimes++;
        } else {
            throw new IllegalArgumentException("This place has been occupied, please enter again");
        }
        if (type.equals("Reversi"))
            reversiChesses(number, letter);
    }

    public Cell[][] getfieldMap() {
        return fieldMap;
    }

    private void createFieldPlayer() {
        // 可以改成自主注册
        Player Tom = new Player("Tom", "BLACK");
        Player Jerry = new Player("Jerry", "WHITE");
        players = new Player[2];
        players[0] = Tom;
        players[1] = Jerry;

    }

    public void initialize() {
        // initilize fieldMap
        for (int i = 0; i < row; i++) {
            if (i > 0 && i <= actuallyRow) {
                fieldMap[i][0] = new NumberAndLetter(i);// initilize row
                fieldMap[i][0].setOccupied();
            }
            for (int n = 0; n < col; n++) {
                boolean beyondMap = i > actuallyRow || n > actuallyCol || (i == 0 && n == 0);
                boolean aside = i == 0 && n <= actuallyCol;
                if (beyondMap) {
                    fieldMap[i][n] = new NumberAndLetter(' ');
                    fieldMap[i][n].setOccupied();
                    continue;
                }
                if (n != 0) {
                    fieldMap[i][n] = aside ? new NumberAndLetter((char) ('A' + n - 1)) : new NumberAndLetter('.');// initilize
                    // fieldMap[i][n] = aside ? new NumberAndLetter((char) ('A' + n - 1)) : new
                    // BlackChess(); // for test
                }
                if (aside || type.equals("Reversi")) {
                    fieldMap[i][n].setOccupied();
                }

            }
        }
        initializePlayer();
        initializeNumberField();
        initializeAndAddGameList(null, 0);
        initializeFuntionlity();
        // initilize chess

        // Bad
        int core = actuallyRow / 2;
        fieldMap[core][core].setSpare();
        fieldMap[core][core + 1].setSpare();
        fieldMap[core + 1][core].setSpare();
        fieldMap[core + 1][core + 1].setSpare();
        chess(core, core, "WHITE");
        chess(core + 1, core + 1, "WHITE");
        chess(core, core + 1, "BLACK");
        chess(core + 1, core, "BLACK");

        // int core = actuallyRow / 2;//for test
        // fieldMap[core][core].setSpare();
        // fieldMap[core + 1][core + 1].setSpare();
        // fieldMap[core][core + 1] = new NumberAndLetter('.');
        // chess(core, core, "WHITE");
        // chess(core + 1, core + 1, "WHITE");

    }

    public void initializeNumberField() {
        insertWord(3, colPlayer, "Game" + numberfield);
    }

    public void initializePlayer() {
        createFieldPlayer();
        int i = 0;
        for (Player someBody : players) {
            int rowPlayer = i + 4;
            int colPlayer1 = colPlayer;
            String name1 = "Player" + (i + 1);

            colPlayer1 = insertWord(rowPlayer, colPlayer1, name1) + 1;
            colPlayer1 = insertWord(rowPlayer, colPlayer1, "[" + someBody.getName() + "]");
            if (someBody.getColor().equals("WHITE")) {
                fieldMap[rowPlayer][colPlayer1++] = new NumberAndLetter('●');
            }
            nameCol = colPlayer1;
            i++;
        }
    }

    public void initializeAndAddGameList(String addGameType, int addGameNumber) {
        int colPlayer2 = colPlayer + 23;
        int row = 3;
        int i = 1;
        if (addGameType == null) {
            insertWord(row++, colPlayer2, " GameList");
            insertWord(row++, colPlayer2, i++ + "." + "Peace");
            insertWord(row++, colPlayer2, i++ + "." + "Reversi");
        } else {
            insertWord(row + addGameNumber, colPlayer2, addGameNumber + "." + addGameType);
        }
    }

    public void initializeFuntionlity() {
        canUseFuntionlity.put("([1-8])([a-hA-H]): chess|", true);
        canUseFuntionlity.put("([0-8]): change fieldMap|", true);
        canUseFuntionlity.put("peace: add peace|", true);
        canUseFuntionlity.put("reversi: add reversi|", true);
        canUseFuntionlity.put("pass: pass|", true);
        canUseFuntionlity.put("quit: quit|", true);
    }

    public int insertWord(int row, int col, String word) {
        char[] words = word.toCharArray();
        for (char i : words) {
            fieldMap[row][col++] = new NumberAndLetter(i);
        }
        return col;
    }

    public int getFullMap() {
        return fullMap;
    }

    public int getChessTimes() {
        return chessTimes;
    }

    public void pass(int i) {
        unlockFuntionlity(new String[] { "([1-8])([a-hA-H]): chess|" });
        chessTimes += i;
    }

    public int getActuallyRoW() {
        return actuallyRow;
    }

    public int getActuallyCol() {
        return actuallyCol;
    }

    public String getType() {
        return type;
    }

    public boolean calculatChessLocation(String whiteOrBlack) {
        canChess = false;
        boolean oneCanChess = true;
        for (int i = 0; i <= actuallyRow; i++) {
            for (int n = 0; n <= actuallyCol; n++) {
                if (!fieldMap[i][n].type().equals(whiteOrBlack)) {
                    continue;
                }
                oneCanChess = checkArround(i, n, getOppositeColor(whiteOrBlack), whiteOrBlack);
            }
        }
        if (whiteOrBlack.equals("WHITE")) {
            bothCanChess[0] = oneCanChess;
        } else {
            bothCanChess[1] = oneCanChess;
        }
        return oneCanChess;
    }

    // too long
    public String getOppositeColor(String whiteOrBlack) {
        return whiteOrBlack.equals("WHITE") ? "BLACK" : "WHITE";
    }

    public boolean checkArround(int coreRow, int coreCol, String opWhiteOrBlack, String whiteOrBlack) {
        List<int[]> needReversiChess = new ArrayList<>();
        int[][] checkArray = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 },
                { 1, 1 } };
        for (int i = 0; i < 8; i++) {
            int deep = 1;
            int rowDeep = coreRow + deep * checkArray[i][0];
            int colDeep = coreCol + deep * checkArray[i][1];
            if (isOutOfRange(rowDeep, colDeep)) {
                continue;
            }
            if (!meetWhiteOrBlack(rowDeep, colDeep, opWhiteOrBlack)) {
                continue;
            }
            needReversiChess.add(new int[] { rowDeep, colDeep });
            deep++;
            rowDeep = coreRow + deep * checkArray[i][0];
            colDeep = coreCol + deep * checkArray[i][1];
            while (!isOutOfRange(rowDeep, colDeep)) {
                if (meetWhiteOrBlack(rowDeep, colDeep, whiteOrBlack)) {
                    break;
                } else if (meetBlank(rowDeep, colDeep) && fieldMap[rowDeep][colDeep] instanceof NumberAndLetter) {
                    fieldMap[rowDeep][colDeep] = fieldMap[rowDeep][colDeep].change('+');
                    fieldMap[rowDeep][colDeep].setSpare();
                    collectNeedReversiChesses(new ArrayList<int[]>(needReversiChess), rowDeep, colDeep);
                    canChess = true;
                    break;
                } // update
                needReversiChess.add(new int[] { rowDeep, colDeep });
                deep++;
                rowDeep = coreRow + deep * checkArray[i][0];
                colDeep = coreCol + deep * checkArray[i][1];

            }
            needReversiChess.clear();

        }
        boolean oneCanChess = canChess;
        return oneCanChess;

    }

    public boolean isOutOfRange(int row, int col) {
        return row > actuallyRow || col > actuallyCol || row <= 0 || col <= 0;
    }

    public boolean meetBlank(int row, int col) {
        boolean isBlank = fieldMap[row][col].type().equals("NumberAndLetter.");
        boolean isBlankPlus = fieldMap[row][col].type().equals("NumberAndLetter+");
        return isBlank || isBlankPlus;
    }

    public boolean meetWhiteOrBlack(int row, int col, String whiteOrBlack) {
        return fieldMap[row][col].type().equals(whiteOrBlack);
    }

    public void collectNeedReversiChesses(List<int[]> needReversiChess, int rowDeep, int colDeep) {
        needReversiChesses.put(new int[] { rowDeep, colDeep }, needReversiChess);
    }

    public void reversiChesses(int number, int letter) {
        Iterator<HashMap.Entry<int[], List<int[]>>> iterator = needReversiChesses.entrySet().iterator();
        while (iterator.hasNext()) {
            HashMap.Entry<int[], List<int[]>> entry = iterator.next();
            int[] position = entry.getKey();
            List<int[]> needReversiChess = entry.getValue();
            boolean shouldReversi = position[0] == number && position[1] == letter;
            if (shouldReversi) {
                for (int[] i : needReversiChess) {
                    fieldMap[i[0]][i[1]] = fieldMap[i[0]][i[1]].change('.');
                    fieldMap[i[0]][i[1]].setOccupied();
                }
                iterator.remove(); // Safely remove the entry using the iterator
            }
        }

        for (HashMap.Entry<int[], List<int[]>> entry : needReversiChesses.entrySet())
            lockChess(entry.getKey());
        needReversiChesses.clear();
    }

    public void lockChess(int[] position) {
        if (fieldMap[position[0]][position[1]] instanceof NumberAndLetter) {
            fieldMap[position[0]][position[1]] = ((NumberAndLetter) fieldMap[position[0]][position[1]]).change('.');
            fieldMap[position[0]][position[1]].setOccupied();
        }
    }

    public boolean getCanChess() {
        return canChess;
    }

    public boolean getBothCanChess() {
        return bothCanChess[0] || bothCanChess[1];
    }

    public int getRow() {
        return row;
    }

    public void setScore(int[] score) {
        Integer scoreCol = nameCol;
        Integer score0 = score[0];
        Integer score1 = score[1];
        int i = 0;
        do {
            fieldMap[4][scoreCol] = fieldMap[4][scoreCol].change(score0.toString().charAt(i++));
            scoreCol++;
            fieldMap[5][scoreCol] = fieldMap[5][scoreCol].change(' ');
        } while ((score[0] /= 10) > 0);
        scoreCol = nameCol;
        i = 0;
        do {
            fieldMap[5][scoreCol] = fieldMap[5][scoreCol].change(score1.toString().charAt(i++));
            scoreCol++;
            fieldMap[5][scoreCol] = fieldMap[5][scoreCol].change(' ');
        } while ((score[1] /= 10) > 0);

        // fieldMap[4][scoreCol] =
        // fieldMap[4][scoreCol].change(Character.forDigit(score[1], 10));
        // fieldMap[5][scoreCol] =
        // fieldMap[5][scoreCol++].change(Character.forDigit(score[0], 10));
        // fieldMap[4][nameCol] =
        // fieldMap[4][nameCol].change(Character.forDigit(score[0], 10));
        // fieldMap[5][nameCol] =
        // fieldMap[5][nameCol].change(Character.forDigit(score[1], 10));
    }

    public void lockFuntionlity(String[] functionality) {
        for (String i : functionality) {
            canUseFuntionlity.put(i, false);
        }
    }

    public void unlockFuntionlity(String[] functionality) {
        for (String i : functionality) {
            canUseFuntionlity.put(i, true);
        }
    }

    public HashMap<String, Boolean> getCanUseFuntionlity() {
        return canUseFuntionlity;
    }
}
