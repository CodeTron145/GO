package GoGame.GoClient.GUI;

import GoGame.GoClient.Client.Message;
import GoGame.GoClient.GuiManager;

import javax.swing.*;
import java.awt.*;

public class Lobby extends ReceiverFrame {

    public Lobby (GuiManager guiManager) {

        this.setSize(800,800);
        getContentPane().setBackground(Color.GREEN);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setTitle("Go Lobby");

        this.setLayout(new BorderLayout());
        JButton joinButton = new JButton("Join selected game");
        JButton refreshButton = new JButton("Refresh");
        JButton createNewGameButton = new JButton("Create new game");
        JButton createNewGameWithBotButton = new JButton("Create game with bot");

        joinButton.setFont(new Font("TimesRoman",Font.PLAIN,15));
        refreshButton.setFont(new Font("TimesRoman",Font.PLAIN,15));
        createNewGameButton.setFont(new Font("TimesRoman",Font.PLAIN,15));
        createNewGameWithBotButton.setFont(new Font("TimesRoman",Font.PLAIN,15));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GREEN);
        this.add(buttonPanel,BorderLayout.SOUTH);

        buttonPanel.add(joinButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(createNewGameButton);
        buttonPanel.add(createNewGameWithBotButton);

    }

    @Override
    public void receive(Message message) {

    }
}
