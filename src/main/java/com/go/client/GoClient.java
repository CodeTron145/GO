package com.go.client;

import com.go.server.Game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class GoClient {

    public static void main(String[] args) throws Exception {

        try (
                Socket socket = new Socket("localhost", 1792);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                )
        {

            dos.writeUTF("1");
            dos.flush();
            Thread.sleep(1000);

            String in = dis.readUTF();

            if (in.equals("1")) {

               new Launcher();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
