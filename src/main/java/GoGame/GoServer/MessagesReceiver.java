package GoGame.GoServer;

import GoGame.GoServer.Server.Message;

interface MessageReceiver {
    void receive(Message message);
}


