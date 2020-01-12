package GoGame.GoServer;

import GoGame.GoServer.Server.Message;

public interface MessagesReceiver {
    void receive(Message message);
}
