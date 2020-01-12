package GoGame.GoClient.GUI;

import GoGame.GoClient.Client.Message;
import GoGame.GoClient.GuiManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Lobby extends ReceiverFrame {

    private final List players = new List();
    private final List games = new List();
    private final ArrayList<String> gamesUUID = new ArrayList<>();

    public Lobby (GuiManager guiManager) {

        String[] size = {"5","9","13","19"};

        setSize(800,800);
        getContentPane().setBackground(Color.GREEN);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Go Lobby");

        setLayout(new BorderLayout());
        JButton joinButton = new JButton("Join selected game");
        JButton createNewGameButton = new JButton("Create new game");
        JButton createNewGameWithBotButton = new JButton("Create game with bot");
        JComboBox<String> sizeComboBox = new JComboBox<>(size);

        joinButton.addActionListener(actionEvent -> {
                System.out.println("Join Game clicked...");
                if (games.getSelectedItem() != null)
                    guiManager.sendMessage(new Message("JoinGame", gamesUUID.get(games.getSelectedIndex())));
        });

        createNewGameButton.addActionListener(actionEvent -> {
                System.out.println("CreateNewGame clicked...");
                System.out.println(guiManager);
                guiManager.sendMessage(new Message("createGame", (size[sizeComboBox.getSelectedIndex()])));
        });

        createNewGameWithBotButton.addActionListener(actionEvent ->{
                System.out.println("CreateNewGameWithBot clicked...");
                System.out.println(guiManager);
                guiManager.sendMessage(new Message("createGameWithBot", (size[sizeComboBox.getSelectedIndex()])));
        });

        joinButton.setFont(new Font("TimesRoman",Font.PLAIN,20));
        createNewGameButton.setFont(new Font("TimesRoman",Font.PLAIN,20));
        createNewGameWithBotButton.setFont(new Font("TimesRoman",Font.PLAIN,20));
        sizeComboBox.setFont(new Font("TimesRoman",Font.PLAIN,20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GREEN);
        add(buttonPanel,BorderLayout.SOUTH);

        buttonPanel.add(joinButton);
        buttonPanel.add(createNewGameButton);
        buttonPanel.add(createNewGameWithBotButton);
        buttonPanel.add(sizeComboBox);

        JPanel nameListPanel = new JPanel();
        add(nameListPanel,BorderLayout.NORTH);
        nameListPanel.setBackground(Color.GREEN);
        nameListPanel.setLayout(new GridLayout(1,2));

        JLabel playersListName = new JLabel("Players in lobby", SwingConstants.CENTER);
        JLabel gamesListName = new JLabel("List of games", SwingConstants.CENTER);
        playersListName.setFont(new Font("TimesRoman",Font.PLAIN,30));
        gamesListName.setFont(new Font("TimesRoman",Font.PLAIN,30));
        nameListPanel.add(playersListName);
        nameListPanel.add(gamesListName);

        JPanel listPanel = new JPanel();
        add(listPanel,BorderLayout.CENTER);
        listPanel.setBackground(Color.GREEN);
        listPanel.setLayout(new GridLayout(1,2));

        players.setFont(new Font("TimesRoman",Font.PLAIN,20));
        games.setFont(new Font("TimesRoman",Font.PLAIN,20));

        listPanel.add(players);
        listPanel.add(games);
    }

    @Override
    public void receive(Message message) {
        if(message.getHeader().equals("lobbyplayers")){
            players.removeAll();

            for(String player : message.getValue().split(","))
                players.add(player);
        } else if(message.getHeader().equals("games")){
            games.removeAll();
            gamesUUID.clear();

            for(String game : message.getValue().split(",")){
                String[] gameInfo = game.split(";");
                if(gameInfo.length < 2)
                   continue;
                gamesUUID.add(gameInfo[0]);
                games.add(gameInfo[1]);
            }
        }
        System.out.println("Lobby received message " + message.toString());
    }
}
