package com.go.server;

import com.go.client.Launcher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;

public class GoServer {

    public static void main(String[] args) throws Exception {

        while (true) {

            try (ServerSocket server = new ServerSocket(1792)) {

                Socket client = server.accept();
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                DataInputStream in = new DataInputStream(client.getInputStream());

                String entry = in.readUTF();

                out.writeUTF("1");
                out.flush();

                if (entry.equals("1")) {

                }

                while (!client.isClosed()) {

                    System.out.println("aaaa");
                    entry = in.readUTF();
                    Thread.sleep(100);
                }

                in.close();
                out.close();
                client.close();

            }
            catch (IOException e) {
                //e.printStackTrace();
            }
        }

    }
}
