package Reversi.qingyv;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private GameLauncher game = new GameLauncher(); // 使用你的游戏类

    // @GetMapping("/board")
    // public ResponseEntity<Cell[][]> getBoard(@RequestParam int gameId) {
    // return
    // ResponseEntity.ok(game.getChessGame().get(gameId).getField().getfieldMap());
    // }

    // 处理移动请求
    @PostMapping("/command")
    public ResponseEntity<?> inputCommand(@RequestBody Request request) {
        game.run(request.getCommand());
        int number = game.getparseInt1();
        return ResponseEntity.ok(game.getChessGame().get(number).getField().getfieldMap());
    }

    // 请求参数 DTO
    @Data // Lombok 注解，自动生成 getter 和 setter
    static class Request {
        private String command;
    }
}