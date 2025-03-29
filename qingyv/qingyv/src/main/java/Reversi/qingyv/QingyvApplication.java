package Reversi.qingyv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class QingyvApplication {
	private static GameLauncher gameLauncher = new GameLauncher();

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(QingyvApplication.class, args);
		GameLauncher gameLauncher = context.getBean(GameLauncher.class);
		gameLauncher.gameInitialize();
		gameLauncher.getChessGame().get(0).launcher();
		while (!Utils.isAllFull(gameLauncher.getChessGame())) {
			try {
				gameLauncher.run();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
