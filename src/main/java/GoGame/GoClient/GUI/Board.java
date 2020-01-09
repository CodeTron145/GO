package GoGame.GoClient.GUI;

import GoGame.GoClient.Client.Message;
import GoGame.GoClient.GuiManager;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class Board extends ReceiverFrame{

    private final BoardPanel boardPanel = new BoardPanel(this);
    private final GuiManager guiManager;

    public Board (GuiManager guiManager) {

        this.guiManager = guiManager;

        setSize(800,900);
        setLayout(new BorderLayout());
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Go Board");

        JPanel scorePanel = new JPanel();

        JLabel playerScore = new JLabel("Your Score: 0",SwingConstants.CENTER);
        JLabel opponentScore = new JLabel("Opponent's Score: 0",SwingConstants.CENTER);
        playerScore.setFont(new Font("TimesRoman",Font.PLAIN,16));
        opponentScore.setFont(new Font("TimesRoman",Font.PLAIN,16));

        scorePanel.setLayout(new GridLayout(1,2));
        scorePanel.add(playerScore);
        scorePanel.add(opponentScore);

        JPanel buttonPanel = new JPanel();

        add(scorePanel,BorderLayout.NORTH);
        add(boardPanel,BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.SOUTH);
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

    @Override
    public void receive(Message message) {

    }
}
