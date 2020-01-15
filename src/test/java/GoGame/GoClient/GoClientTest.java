package GoGame.GoClient;

import GoGame.GoClient.Client.Message;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GoClientTest {


    @Test
    public void testConnection() throws IOException {

        Message testMessage = new Message("showlobby","");
        Message testMessage2 = new Message("showlogin","");
        Message testMessage3 = new Message("showboard","19");
        Message testMessage4 = new Message("setwhitepawn","1,1");
        Message testMessage5 = new Message("setblackpawn","1,2");
        Message testMessage6 = new Message("deletepawn","1,1");
        Message testMessage7 = new Message("deletepawn","1,2");
        Message testMessage8 = new Message("yourscore","50");
        Message testMessage9 = new Message("opponentsscore","50");
        Message testMessage10 = new Message("pass","");
        Message testMessage11 = new Message("abortgame","");

        ServerSocket testServerSocket = new ServerSocket(2020);

        GuiManager testGuiManager = new GuiManager();

        testGuiManager.connect("127.0.0.1",2020);
        Socket testSocket = testServerSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(testSocket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(testSocket.getOutputStream()));

        testGuiManager.sendMessage(testMessage);
        testGuiManager.receive(testMessage);
        testGuiManager.receive(testMessage2);
        testGuiManager.receive(testMessage3);
        testGuiManager.receive(testMessage4);
        testGuiManager.receive(testMessage5);
        testGuiManager.receive(testMessage6);
        testGuiManager.receive(testMessage7);
        testGuiManager.receive(testMessage8);
        testGuiManager.receive(testMessage9);
        testGuiManager.receive(testMessage10);
        testGuiManager.receive(testMessage11);
        testServerSocket.close();
    }
}