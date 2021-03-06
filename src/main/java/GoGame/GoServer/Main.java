package GoGame.GoServer;

import GoGame.GoServer.Server.ConnectionFactory;
import GoGame.GoServer.Server.Greeter;

import java.io.IOException;
import java.util.Scanner;

class Main {
    public static void main(String[] args){
        System.out.println("Starting server...\n");

        Greeter connectionGreeter = null;
        try {
            System.out.println("Creating connection greeter");

            connectionGreeter = new Greeter(new ConnectionFactory(2020));

            System.out.println("Done!");
        } catch (IOException e) {
            System.out.println("Error creating connection manager :" + e.getMessage());
        }
        System.out.println("Starting server...");
        connectionGreeter.start();

        System.out.println("Press enter to quit!");
        Scanner s = new Scanner(System.in);
        s.nextLine();
        System.out.println("Ending!");

        connectionGreeter.close();
        System.exit(0);
    }
}
