package com.go.client;

import java.util.Scanner;

public class Launcher {

    public Launcher () {

        int size = 5;
        int mode = 0;
        Scanner scan = new Scanner(System.in);
        boolean activ = true;
        int inputInt;

        while (activ) {

            System.out.print("\nGO" +
                    "\n---------------------" +
                    "\nBoard size: " + size + "x" + size +
                    "\nMode: Player vs " + (mode == 0 ? "Player" : "Bot") +
                    "\n1. Change size" +
                    "\n2. Change mod" +
                    "\n3. Start game" +
                    "\n4. Load game" +
                    "\n0. Exit" +
                    "\nChoose: ");

            inputInt = scan.nextInt();

            switch (inputInt) {
                case 1 :
                    size = -1;
                    while (size == -1) {
                        System.out.print("\n\nSize" +
                                "\n---------------------" +
                                "\n1. 5x5" +
                                "\n2. 9x9" +
                                "\n3. 13x13" +
                                "\n4. 19x19" +
                                "\nChoose: ");
                        inputInt = scan.nextInt();
                        size = inputInt == 1 ? 5 : (inputInt == 2 ? 9 :
                                (inputInt == 3 ? 13 : (inputInt == 4 ? 19 : -1)));
                    }
                    break;
                case 2:
                    mode = -1;
                    while (mode == -1) {
                        System.out.print("\n\nMode" +
                                "\n---------------------" +
                                "\n1. Player vs Player" +
                                "\n2. Player vs Bot" +
                                "\nChoose: ");
                        inputInt = scan.nextInt();
                        mode = inputInt == 1 ? 0 : (inputInt == 2 ? 1 : -1);
                    }
                    break;
                case 3:
                    Gui gui = new Gui();
                    gui.setVisible(true);
                    break;
                case 4:

                    break;
                case 0:
                    activ = false;
                    break;
            }
        }
    }
}