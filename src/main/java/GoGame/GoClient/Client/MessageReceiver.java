package GoGame.GoClient.Client;

import java.io.IOException;

public interface MessageReceiver {

    void disconnect() throws IOException;
    void sendMessage(Message message);
    void startListening(MessageReceiver receiver);
}
