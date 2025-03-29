package Reversi.qingyv;

public class Main {
    private static GameLauncher gameLauncher = new GameLauncher();

    public static void main(String[] args) {
        gameLauncher.gameInitialize();
        gameLauncher.getChessGame().get(0).launcher();
        while (!Utils.isAllFull(gameLauncher.getChessGame())) {
            try {
                gameLauncher.run();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                // e.printStackTrace();

            }
        }
    }
}
