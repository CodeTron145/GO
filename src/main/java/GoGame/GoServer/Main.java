package GoGame.GoServer;

import GoGame.GoServer.Server.Connection;
import GoGame.GoServer.Server.Greeter;

import java.io.IOException;
import java.util.Scanner;

class Main {
    public static void main(String[] args){

        System.out.println("Start of server!");
        Greeter connectionGreeter = null;
        System.out.println("Connection with greeter...");
        try {
            connectionGreeter = new Greeter(new Connection(6666));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Gotcha!");
        System.out.println("Starting server");
        connectionGreeter.start();

        System.out.println("To quit press 'Enter'");
        Scanner s = new Scanner(System.in);
        s.nextLine();
        System.out.println("Ending");

        connectionGreeter.close();
        System.exit(0);
    }
}
