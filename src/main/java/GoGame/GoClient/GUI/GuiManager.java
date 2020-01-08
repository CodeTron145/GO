package GoGame.GoClient.GUI;

import GoGame.GoClient.Client.Client;
import GoGame.GoClient.Client.IMessageReceiver;
import GoGame.GoClient.Client.Message;

public class GuiManager implements IMessageReceiver {

    private final Client client;

    public GuiManager () {

        client = new Client ();

    }

    @Override
    public void receive(Message message) {

    }

    public void connect(String ip, int port){
        client.setMessageReceiver(this);
        client.connect(ip, port);
    }
}
