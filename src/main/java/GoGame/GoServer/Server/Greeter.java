package GoGame.GoServer.Server;

import GoGame.GoServer.Lobby.Lobby;
import GoGame.GoServer.Players.Player;

import java.io.IOException;

public class Greeter extends Thread {

    private Connection connectionManager;
    private boolean exitFlag = false;

    public Greeter(Connection connectionManager){
        this.connectionManager= connectionManager;
    }

    public void close(){
        exitFlag = true;
        connectionManager.closeConnections();
    }

    @Override
    public void run(){

        while(!exitFlag){
            try {
                System.out.println("Waiting for players...");
                connectionManager.getConnection();
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
