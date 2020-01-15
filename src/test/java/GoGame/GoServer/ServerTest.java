package GoGame.GoServer;

import GoGame.GoServer.Exceptions.GameNotExistingException;
import GoGame.GoServer.Exceptions.NoSlotsAvailableException;
import GoGame.GoServer.Game.Game;
import GoGame.GoServer.Lobby.Lobby;
import GoGame.GoServer.Player.Player;
import GoGame.GoServer.Server.ConnectionFactory;
import GoGame.GoServer.Server.Greeter;
import GoGame.GoServer.Server.Message;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServerTest {

    @Test
    public void testServerResistance() throws IOException {


        Random randomByte = new Random(100);
        byte[] randomBytes = new byte[500];
        randomByte.nextBytes(randomBytes);
        randomBytes[499]='\n';
        String s = new String(randomBytes);

        System.out.println("Creating connection greeter...");
        Greeter connectionGreeter = null;
        connectionGreeter = new Greeter(new ConnectionFactory(2020));
        System.out.println("Done");

        connectionGreeter.start();

        Socket testSocket = new Socket("localhost",2020);

        System.out.println("Creting in and out readers");
        BufferedReader in = new BufferedReader( new InputStreamReader( testSocket.getInputStream()) );
        DataOutputStream out = new DataOutputStream(testSocket.getOutputStream());

        in.readLine();
        in.readLine();

        out.write(randomBytes);

        in.close();
        out.close();
        testSocket.close();
        connectionGreeter.close();
    }


    @Test
    public void testUnexpectedDisconnect() throws IOException {

        Greeter connectionGreeter = null;
        connectionGreeter = new Greeter(new ConnectionFactory(2020));
        connectionGreeter.start();

        Socket socket1 = new Socket("127.0.0.1",2020);

        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( socket1.getInputStream()) );
        BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter( socket1.getOutputStream()) );

        bufferedReader.readLine();
        bufferedReader.readLine();

        assertFalse(Lobby.getInstance().getLobbyPlayers().isEmpty());

        bufferedWriter.write("creategame"+'\t'+""+'\n');
        bufferedWriter.newLine();
        bufferedWriter.flush();

        bufferedReader.readLine();

        bufferedReader.close();
        bufferedWriter.close();
        socket1.close();
        connectionGreeter.close();
    }

    private class PlayerMock extends Player {
        public PlayerMock() {
            super("Test", null);
        }
        @Override
        public void startReceiveMessages(){}
        public void sendMessage(Message message){}
    }

    @org.junit.Test
    public void testAddPlayer() throws NoSlotsAvailableException, GameNotExistingException {
        Player testPlayer1 = new ServerTest.PlayerMock();
        Player testPlayer2 = new ServerTest.PlayerMock();

        //adding a player to lobby
        assertFalse(Lobby.getInstance().getLobbyPlayers().containsValue(testPlayer1));
        Lobby.getInstance().addPlayer(testPlayer1);
        assertTrue(Lobby.getInstance().getLobbyPlayers().containsValue(testPlayer1));

        //adding a player to game
        Game testGame = new Game(19,testPlayer1);
        String uuid = testGame.getUuid().toString();
        Lobby.getInstance().getGames().put(testGame.getUuid(),testGame);
        Lobby.getInstance().addPlayerToGame(testPlayer2,uuid );


    }

    @org.junit.Test
    public void testCreateGame() {
        Player testPlayer = new ServerTest.PlayerMock();
        int gamesCount = Lobby.getInstance().getGames().size();
        Lobby.getInstance().createGame(testPlayer, 19);
        assertTrue(Lobby.getInstance().getGames().size() > gamesCount);
        assertFalse(Lobby.getInstance().getLobbyPlayers().containsValue(testPlayer));
    }

    @Test
    public void testRemoveGame(){

        Player testPlayer = new ServerTest.PlayerMock();
        Lobby.getInstance().removeGame(new Game(19,testPlayer));

    }

    @org.junit.Test
    public void testRemovePlayer() {
        Player testPlayer = new ServerTest.PlayerMock();
        assertFalse(Lobby.getInstance().getLobbyPlayers().containsValue(testPlayer));
        Lobby.getInstance().addPlayer(testPlayer);
        assertTrue(Lobby.getInstance().getLobbyPlayers().containsValue(testPlayer));
        Lobby.getInstance().removePlayer(testPlayer);
        assertFalse(Lobby.getInstance().getLobbyPlayers().containsValue(testPlayer));
    }

    @Test
    public void testGameWithBot(){

        Player testPlayer = new ServerTest.PlayerMock();
        Lobby.getInstance().createGameWithBot(testPlayer, 19);

    }
}