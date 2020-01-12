package GoGame.GoServer.Strategy;

import GoGame.GoServer.Player.Player;
import GoGame.GoServer.Server.Message;

public interface Strategy {
    void handleMessage(Message message, Player sender);
    void forceQuit(Player sender);
}
