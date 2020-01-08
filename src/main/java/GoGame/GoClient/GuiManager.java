package GoGame.GoClient;

import GoGame.GoClient.Client.Client;
import GoGame.GoClient.Client.IMessageReceiver;
import GoGame.GoClient.Client.Message;
import GoGame.GoClient.GUI.ReceiverFrame;

import javax.swing.*;

public class GuiManager implements IMessageReceiver {

    private final Client client;
    private ReceiverFrame viewFrameReceiver = null;

    public GuiManager () {

        client = new Client ();
        showLoginWindow();
    }

    private void showBoard() {}

    private void showLoginWindow() {}

    private void showLobby(){}

    @Override
    public void receive(Message message) {
        if(message == null){
            client.disconnect();
            showLoginWindow();
            JOptionPane.showMessageDialog(viewFrameReceiver, "Connection to the server lost!");
            return;
        }

        if(message.getHeader().equals("showlobby")){
            showLobby();
        }
        else if(message.getHeader().equals("showboard")){
            showBoard();
        }
        else if(message.getHeader().equals("showlogin")){
            showLoginWindow();
        }
        else if(message.getHeader().toLowerCase().equals("info") ||
                message.getHeader().toLowerCase().equals("invalidmoveinfo") ){
            JOptionPane.showMessageDialog(viewFrameReceiver, message.getValue());
        }
        else{
            viewFrameReceiver.receive(message);
        }
    }

    public void sendMessage(Message message){
        if(client != null) {
            client.sendMessage(message);
            System.out.println("Sending Message " + message.toString());
        }
    }

    public void connect(String ip, int port){
        client.setMessageReceiver(this);
        client.connect(ip, port);
    }
}
