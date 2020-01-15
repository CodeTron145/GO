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
        Message testMessage3 = new Message("showboard","");
        Message testMessage4 = new Message("showloginerror","");
        ServerSocket testServerSocket = new ServerSocket(6666);

        GuiManager testGuiManager = new GuiManager();

        testGuiManager.connect("127.0.0.1",6666);
        Socket testSocket = testServerSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(testSocket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(testSocket.getOutputStream()));

        testGuiManager.sendMessage(testMessage);
        testGuiManager.receive(testMessage);
        testGuiManager.receive(testMessage2);
        testGuiManager.receive(testMessage3);
        testGuiManager.receive(testMessage4);
        testServerSocket.close();
    }
}