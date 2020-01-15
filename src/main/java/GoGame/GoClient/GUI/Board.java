package GoGame.GoClient.GUI;

import GoGame.GoClient.Client.Message;
import GoGame.GoClient.GuiManager;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class Board extends ReceiverFrame{

    private final BoardPanel boardPanel;
    private final GuiManager guiManager;
    private JLabel playerScore;
    private JLabel opponentScore;

    public Board (GuiManager guiManager, int sizeFromMessage) {

        this.guiManager = guiManager;
        boardPanel = new BoardPanel(this, sizeFromMessage);

        setSize(800,900);
        setLayout(new BorderLayout());
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Go Board");

        JPanel scorePanel = new JPanel();

        playerScore = new JLabel("Your Score: 0",SwingConstants.CENTER);
        opponentScore = new JLabel("Opponent's Score: 0",SwingConstants.CENTER);
        playerScore.setFont(new Font("TimesRoman",Font.PLAIN,16));
        opponentScore.setFont(new Font("TimesRoman",Font.PLAIN,16));

        scorePanel.setLayout(new GridLayout(1,2));
        scorePanel.setBackground(Color.GREEN);
        scorePanel.add(playerScore);
        scorePanel.add(opponentScore);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setSize(800,50);
        buttonPanel.setBackground(Color.GREEN);

        JButton passButton = new JButton("Pass");
        JButton resignButton = new JButton("Resign");
        passButton.setFont(new Font("TimesRoman",Font.PLAIN,25));
        resignButton.setFont(new Font("TimesRoman",Font.PLAIN,25));

        buttonPanel.add(passButton);
        buttonPanel.add(resignButton);

        add(scorePanel,BorderLayout.NORTH);
        add(boardPanel,BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.SOUTH);

        passButton.addActionListener(actionEvent -> {
                System.out.println("Pass clicked...");
                System.out.println(guiManager);
                guiManager.sendMessage(new Message("pass", ""));
        });

        resignButton.addActionListener(actionEvent -> {
                System.out.println("Resign clicked...");
                System.out.println(guiManager);
                guiManager.sendMessage(new Message("abortgame", ""));
        });
    }

    private synchronized void setBlackPawn(int x, int y){

        Pawn newBlackPawn = new Pawn(x,y,Color.BLACK);
        boardPanel.getPawns().add(newBlackPawn);
        repaint();
    }

    private synchronized void setWhitePawn(int x, int y){

        Pawn newWhitePawn = new Pawn(x,y,Color.WHITE);
        boardPanel.getPawns().add(newWhitePawn);
        repaint();
    }

    private synchronized void clearPawn(int x, int y){

        for (Iterator<Pawn> iterator = boardPanel.getPawns().iterator(); iterator.hasNext();) {
            Pawn center = iterator.next();
            if (center.getX()==x && center.getY()==y) {
                iterator.remove();
                break;
            }
        }
        repaint();
    }

    public void onTileSelected(int x, int y){

        guiManager.sendMessage(new Message("tileselected", x+","+y));
    }

    @Override
    public void receive(Message message) {

        switch (message.getHeader()) {
            case "setwhitepawn": {
                int x = Integer.parseInt(message.getValue().split(",")[0]);
                int y = Integer.parseInt(message.getValue().split(",")[1]);
                setWhitePawn(x, y);
                break;
            }
            case "setblackpawn": {
                int x = Integer.parseInt(message.getValue().split(",")[0]);
                int y = Integer.parseInt(message.getValue().split(",")[1]);
                setBlackPawn(x, y);
                break;
            }
            case "deletepawn": {
                int x = Integer.parseInt(message.getValue().split(",")[0]);
                int y = Integer.parseInt(message.getValue().split(",")[1]);
                clearPawn(x, y);
                break;
            }
            case "yourscore":
                playerScore.setText("Your score: " + message.getValue());
                break;
            case "opponentsscore":
                opponentScore.setText("Opponent's score: " + message.getValue());
                break;
        }
    }
}
