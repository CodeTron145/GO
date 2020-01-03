package com.go.client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gui extends JFrame {

    public Gui (int size) {

        super("Go");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeGlob = size * 100;
        int locationX = (screenSize.width - sizeGlob) / 2;
        int locationY = (screenSize.height - sizeGlob) / 2;
        this.setBounds(locationX, locationY, sizeGlob, sizeGlob);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


    }
}
