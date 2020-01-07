package GoGame.GoClient.Client;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements IMessageReceiver {

    private IConnectionManager connectionManager = null;
    private IMessageReceiver messageReceiver = null;

    public void connect (String host, int port) {

        try {
            Socket clientSocket = new Socket(host, port);
            connectionManager = new ConnectionManager(clientSocket);
            connectionManager.startListening(this);
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to a server! Unknown host!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Can't connect to a server!");
        }
    }

    public void disconnect() {

        connectionManager = null;
    }

    @Override
    public void receive(Message message) {

    }
}
