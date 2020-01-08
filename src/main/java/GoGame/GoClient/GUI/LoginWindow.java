package GoGame.GoClient.GUI;

import GoGame.GoClient.Client.Message;
import GoGame.GoClient.GuiManager;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends ReceiverFrame {

    private final TextField hostNameTextField;
    private final TextField portTextField;

    public LoginWindow (GuiManager guiManager) {

        setSize(400,400);
        setBackground(Color.ORANGE);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Go Login");

        setLayout(new FlowLayout());
        setLayout(null);
    }

    @Override
    public void receive(Message message) {

    }
}
