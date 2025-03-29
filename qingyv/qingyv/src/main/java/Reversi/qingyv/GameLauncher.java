package Reversi.qingyv;

import java.util.ArrayList;
import java.util.List;

public class GameLauncher {
    private static Peace peaceLauncher = new Peace("Peace", 1);
    private static Reversi reversiLauncher = new Reversi("Reversi", 2);
    private static List<ChessGame> chessGames = new ArrayList<>();
    private static InputUtils input = new InputUtils(chessGames);
    private static int parseInt1 = 0;

    public enum gameFunctionalities {
        GAME, ADDPEACE, ADDREVERSI, QUIT, PASS
    }

    public enum chessColor {
        BLACK, WHITE
    }

    public void run(String word) {
        String code = input.ScanCommand(word);
        Utils.clear();
        if (code == null) {
            chessGames.get(parseInt1).launcher();
            throw new NullPointerException("Error input. Please try again.");
        }
        parseInt1 = Integer.parseInt(input.parseOutput("number", code));
        gameFunctionalities functionality = gameFunctionalities.valueOf(input.parseOutput("char", code));
        switch (functionality) {
            case GAME: {
                try {
                    chessGames.get(parseInt1).launcher();
                } catch (IndexOutOfBoundsException e) {
                    throw new IllegalArgumentException("Index out of bounds. Please try again.", e);
                }

                break;
            }
            case ADDPEACE: {
                chessGames.add(new Peace("Peace", chessGames.size() + 1));
                input.addGame();
                chessGames.get(parseInt1).launcher();
                break;
            }
            case ADDREVERSI: {
                chessGames.add(new Reversi("Reversi", chessGames.size() + 1));
                input.addGame();
                chessGames.get(parseInt1).launcher();
                break;
            }
            case QUIT: {
                System.exit(0);
                break;
            }
            case PASS: {
                chessGames.get(parseInt1).getField().pass(1);
                chessGames.get(parseInt1).launcher();

                break;
            }
            default:
                throw new AssertionError("\"Unexpected value");
        }
    }

    public List<ChessGame> getChessGame() {
        return chessGames;
    }

    public int getparseInt1() {
        return parseInt1;
    }

    public void gameInitialize() {
        peaceLauncher.initialize();
        reversiLauncher.initialize();
        chessGames.add(peaceLauncher);
        chessGames.add(reversiLauncher);
    }
}