package GoGame.GoClient;

import GoGame.GoClient.Client.Client;
import GoGame.GoClient.Client.IMessageReceiver;
import GoGame.GoClient.Client.Message;
import GoGame.GoClient.GUI.Board;
import GoGame.GoClient.GUI.Lobby;
import GoGame.GoClient.GUI.LoginWindow;
import GoGame.GoClient.GUI.ReceiverFrame;

import javax.swing.*;

public class GuiManager implements IMessageReceiver {

    private final Client client;
    private ReceiverFrame viewFrameReceiver = null;

    public GuiManager () {

        client = new Client ();
        showLoginWindow();
    }

    private void showBoard(int sizeFromMessage) {

        if(viewFrameReceiver != null){
            viewFrameReceiver.dispose();
        }
        viewFrameReceiver = new Board(this, sizeFromMessage);
    }

    private void showLoginWindow() {

        if(viewFrameReceiver != null){
            viewFrameReceiver.dispose();
        }
        viewFrameReceiver = new LoginWindow(this);
    }

    private void showLoginWindowError() {

        client.disconnect();
        if(viewFrameReceiver != null){
            viewFrameReceiver.dispose();
        }
        viewFrameReceiver = new LoginWindow(this);
        JOptionPane.showMessageDialog(viewFrameReceiver, "Connection to the server lost!");
    }

    private void showLobby(){

        if(viewFrameReceiver != null){
            viewFrameReceiver.dispose();
        }
        viewFrameReceiver = new Lobby(this);
    }

    @Override
    public void receive(Message message) {

        switch (message.getHeader()) {

            case "showlobby" :
                showLobby();
                break;
            case "showloginerror" :
                showLoginWindowError();
                break;
            case "showboard" :
                showBoard(Integer.parseInt(message.getValue()));
                break;
            case "showlogin" :
                showLoginWindow();
                break;
            case "info" :
                JOptionPane.showMessageDialog(viewFrameReceiver, message.getValue());
                break;
            case "invalidmoveinfo" :
                JOptionPane.showMessageDialog(viewFrameReceiver, message.getValue());
                break;
            default:
                viewFrameReceiver.receive(message);
                break;
        }
    }

    public void sendMessage(Message message){
        if(client != null) {
            client.sendMessage(message);
            System.out.println("Sending Message " + message.toString());
        }
    }

    public void connect(String ip, int port){
        client.setMessageReceiver (this);
        client.connect(ip, port);
    }
}
