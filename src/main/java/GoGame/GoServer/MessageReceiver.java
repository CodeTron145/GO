package GoGame.GoServer;

import GoGame.GoServer.Server.Message;

public interface MessageReceiver {
    void receive(Message message);
}
