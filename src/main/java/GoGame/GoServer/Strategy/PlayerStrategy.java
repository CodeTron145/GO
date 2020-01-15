package GoGame.GoServer.Strategy;

import GoGame.GoServer.Game.Game;
import GoGame.GoServer.Player.Player;
import GoGame.GoServer.Server.Message;

public class PlayerStrategy implements Strategy {

    private final Game game;

    public PlayerStrategy(Game game){
        this.game = game;
    }

    @Override
    public void handleMessage(Message message, Player sender) {
        switch (message.getHeader()) {
            case "abortgame":
                game.endSession(true);
                break;
            case "pass":
                game.pass(sender);
                break;
            case "tileselected":
                int x = Integer.parseInt(message.getValue().split(",")[0]);
                int y = Integer.parseInt(message.getValue().split(",")[1]);
                game.setPawn(x, y, sender);
                break;
            default:
                System.out.println("Header " + message.getHeader() + "is not recognized");
                break;
        }
    }

    @Override
    public void forceQuit(Player sender) {
        game.kickPlayer(sender);
    }
}
