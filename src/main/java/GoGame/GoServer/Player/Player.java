package GoGame.GoServer.Player;

import GoGame.GoServer.MessageReceiver;
import GoGame.GoServer.Server.Connection;
import GoGame.GoServer.Server.Message;
import GoGame.GoServer.Strategy.Strategy;

import java.util.ArrayList;
import java.util.UUID;

public class Player implements MessageReceiver {

    Connection connectionManager;
    private Strategy playerStrategy;
    private String nick;
    private UUID uuid;
    private static final ArrayList<String> usedNicks;
    static{
        usedNicks = new ArrayList<>();
    }

    public Player(String nick, Connection connectionManager){
        this.connectionManager = connectionManager;
        this.uuid = UUID.randomUUID();
        setNick(nick);
    }

    public UUID getUuid(){
        return uuid;
    }
    public String getNick(){
        return nick;
    }
    private void setNick(String nick) {
        usedNicks.remove(this.nick);
        if(!usedNicks.contains(nick)){
            this.nick = nick;
        }
        else{
            int i = 0;
            while(usedNicks.contains(nick +i)){
                i++;
            }
            this.nick = nick+i;
        }
        usedNicks.add(this.nick);
        System.out.println("Your nick has been set to " + "NextNick");
    }

    public void startReceiveMessages(){
        this.connectionManager.startListening(this);
    }
    public void sendMessage(Message message){
        connectionManager.sendMessage(message);
    }

    public void setPlayerStrategy(Strategy playerStrategy){
        this.playerStrategy = playerStrategy;
    }

    @Override
    public void receive(Message message) {
        if(playerStrategy == null){
            System.out.println("Strategy is not found");
            return;
        }
        if(message == null){
            playerStrategy.forceQuit(this);
            try{
                connectionManager.disconnect();
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        }
        else{
            playerStrategy.handleMessage(message, this);
        }
    }
}
