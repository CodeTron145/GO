package GoGame.GoClient.GUI;

import GoGame.GoClient.Client.Message;
import GoGame.GoClient.GuiManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Lobby extends ReceiverFrame {

    private final List players = new List();
    private final List games = new List();
    private final ArrayList<String> gamesUUID = new ArrayList();

    public Lobby (GuiManager guiManager) {

        int activeSize = 5;
        String[] size = {"5","9","13","19"};

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
        JComboBox sizeComboBox = new JComboBox(size);

        joinButton.setText("Join selected game");
        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("Join Game clicked...");
                if (games.getSelectedItem() != null)
                    guiManager.sendMessage(new Message("JoinGame", gamesUUID.get(games.getSelectedIndex())));
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("Refresh clicked...");
                guiManager.sendMessage(new Message("getLobbyPlayers", ""));
                guiManager.sendMessage(new Message("getGames", ""));
            }
        });

        createNewGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("CreateNewGame clicked...");
                System.out.println(guiManager);
                guiManager.sendMessage(new Message("createGame", ""));
            }
        });

        createNewGameWithBotButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("CreateNewGameWithBot clicked...");
                System.out.println(guiManager);
                guiManager.sendMessage(new Message("createGameWithBot", ""));
            }
        });

        sizeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

            }
        });

        joinButton.setFont(new Font("TimesRoman",Font.PLAIN,17));
        refreshButton.setFont(new Font("TimesRoman",Font.PLAIN,17));
        createNewGameButton.setFont(new Font("TimesRoman",Font.PLAIN,17));
        createNewGameWithBotButton.setFont(new Font("TimesRoman",Font.PLAIN,17));
        sizeComboBox.setFont(new Font("TimesRoman",Font.PLAIN,17));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GREEN);
        this.add(buttonPanel,BorderLayout.SOUTH);

        buttonPanel.add(joinButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(createNewGameButton);
        buttonPanel.add(createNewGameWithBotButton);
        buttonPanel.add(sizeComboBox);



    }

    @Override
    public void receive(Message message) {

    }
}
