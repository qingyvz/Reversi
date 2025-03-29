package Reversi.qingyv;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputUtils {

    private Scanner scanner = new Scanner(System.in);
    private List<ChessGame> chessGames;
    private int num = 0;

    // private boolean allowFunctionlities
    public InputUtils(List<ChessGame> chessGames) {
        this.chessGames = chessGames;
    }

    public String ScanCommand(String word) {

        // 处理用户输入
        lockFuntionlities();
        priInputFormat();
        // String word = scanner.nextLine().trim();
        // try {
        if (word.matches(generateRegex()))
            word = parseInput(word);
        // } catch (Exception e) {
        // throw new IllegalArgumentException("error input");
        // }
        else
            word = null;
        return word;
    }

    public String parseInput(String word) {
        if (word.matches("^([0-8])$")) {// Change
            int parseWord = Integer.parseInt(word);
            if (parseWord > chessGames.size() || parseWord <= 0) {
                throw new IndexOutOfBoundsException("No such game");
            }
            num = parseWord - 1;
            return "GAME" + String.valueOf(num);
        } else if (word.matches(("^([1-8])([a-hA-H])$"))) {// chess
            Chess(word);
            return "GAME" + String.valueOf(num);
        } else if (word.equals("peace")) {// add peace
            return "ADDPEACE" + String.valueOf(num);
        } else if (word.equals("reversi")) {// add reversi
            return "ADDREVERSI" + String.valueOf(num);
        } else if (word.equals("pass")) {// pass
            return "PASS" + String.valueOf(num);
        } else if (word.equals("quit")) {// quit
            return "QUIT" + String.valueOf(num);
        }
        return null;
    }

    public void Chess(String word) {
        int n0 = Integer.parseInt(String.valueOf(word.charAt(0)));
        int n1 = Character.toUpperCase(word.charAt(1)) - 'A' + 1;
        int inputNum = n0 <= chessGames.get(num).getField().getActuallyRoW() && n0 > 0 ? n0 : 0;
        int inputChar = n1 <= chessGames.get(num).getField().getActuallyCol() && n1 > 0 ? n1 : 0;
        chessGames.get(num).getField().chess(inputNum, inputChar, chessGames.get(num).whoChess());
        Utils.printMap(chessGames.get(num).getField().getfieldMap(), inputNum, inputChar);

    }

    public ChessGame getGame() {
        ChessGame i = null;
        while (i == null) {
            try {
                i = chessGames.get(num);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("try agagin");// if map is null

            }
        }
        return i;
    }

    public String parseOutput(String command, String code) {

        String parseWord = command.equals("number") ? parseNumber(code) : parseChar(code);

        return parseWord;
    }

    private String parseChar(String code) {
        char[] codes = code.toCharArray();
        StringBuilder parseChar = new StringBuilder(); // 使用 StringBuilder 来拼接字符串
        for (char i : codes) {
            if (Character.isLetter(i)) { // 检查是否为字母
                parseChar.append(i); // 如果是字母，拼接到结果中
            }
        }
        return parseChar.toString(); // 返回拼接后的字符串
    }

    private String parseNumber(String code) {
        return String.valueOf(code.charAt(code.length() - 1));
    }

    private String generateRegex() {
        StringBuilder regex = new StringBuilder("^(");
        for (String i : chessGames.get(num).getField().getCanUseFuntionlity().keySet()) {
            if (chessGames.get(num).getField().getCanUseFuntionlity().get(i)) {
                String[] parts = i.split("\\:");
                regex.append(parts[0] + "|");
            }
        }
        regex.append(")$");
        // System.out.println(regex.toString());
        return regex.toString();
    }

    public void priInputFormat() {
        for (String i : chessGames.get(num).getField().getCanUseFuntionlity().keySet()) {
            if (chessGames.get(num).getField().getCanUseFuntionlity().get(i))
                System.out.print(i);
        }
    }

    public void addGame() {
        chessGames.get(chessGames.size() - 1).initialize();
        // int colPlayer = 30;
        int len = chessGames.size();
        String type = chessGames.get(len - 1).getField().getType();
        for (int i = 1; i < len; i++) {
            chessGames.get(len - 1).getField().initializeAndAddGameList(chessGames.get(i).getField().getType(), i + 1);
        }

        for (ChessGame i : chessGames) {
            i.getField().initializeAndAddGameList(type, len);
        }
    }

    public void lockFuntionlities() {
        if (!chessGames.get(num).getField().getCanChess()) {
            chessGames.get(num).getField().lockFuntionlity(new String[] { "([1-8])([a-hA-H]): chess|" });
        }
        if (!chessGames.get(num).getNotEnd()) {
            chessGames.get(num).getField().lockFuntionlity(new String[] { "pass: pass|", "([1-8])([a-hA-H]): chess|" });
        }
        if (chessGames.size() > chessGames.get(0).getField().getRow()) {
            chessGames.get(num).getField()
                    .lockFuntionlity(new String[] { "peace: add peace|", "reversi: add reversi|" });
        }

    }

}
