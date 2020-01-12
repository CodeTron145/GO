package GoGame.GoServer.Server;

import GoGame.GoServer.Lobby.Lobby;
import GoGame.GoServer.Player.Player;

import java.io.IOException;

public class Greeter extends Thread {

    private final ConnectionFactory connectionManagerFactory;
    private boolean exitFlag = false;

    public Greeter(ConnectionFactory connectionManagerFactory){
        this.connectionManagerFactory = connectionManagerFactory;
    }

    public void close(){
        exitFlag = true;
        connectionManagerFactory.closeConnections();
    }

    @Override
    public void run(){

        while(!exitFlag){
            try {
                System.out.println("Waiting for players...");
                Connection connectionManager = connectionManagerFactory.getConnectionManager();
                if(connectionManager == null)
                    return;
                Player newPlayer = new Player("Player", connectionManager);
                Lobby.getInstance().addPlayer(newPlayer);
                newPlayer.startReceiveMessages();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
