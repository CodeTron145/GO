package GoGame.GoServer.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ConnectionFactory {

    private ServerSocket serverSocket;

    public ConnectionFactory(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public Connection getConnectionManager() throws IOException, SocketException {
        Socket socket = null;
        try{
            socket = serverSocket.accept();
            return new Connection(socket);
        }
        catch (Exception ex) {
            return null;
        }
    }

    public void closeConnections() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Server socket already closed");
        }
    }
}
