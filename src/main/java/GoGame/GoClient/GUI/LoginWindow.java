package GoGame.GoClient.GUI;

import GoGame.GoClient.Client.Message;
import GoGame.GoClient.GuiManager;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends ReceiverFrame {

    private final TextField hostNameTextField;
    private final TextField portTextField;

    public LoginWindow (GuiManager guiManager) {

        setSize(300,300);
        getContentPane().setBackground(Color.GREEN);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Go Login");

        setLayout(new FlowLayout());
        setLayout(null);

        Label title = new Label("Welcome Player", Label.CENTER);
        Label hostName = new Label("Host Name", Label.CENTER);
        Label port = new Label("Port", Label.CENTER);

        title.setFont(new Font("TimesRoman",Font.PLAIN,25));
        hostName.setFont(new Font("TimesRoman",Font.PLAIN,20));
        port.setFont(new Font("TimesRoman",Font.PLAIN,20));

        hostNameTextField = new TextField(30);
        hostNameTextField.setText("localhost");
        portTextField = new TextField(20);
        portTextField.setText("2020");

        hostNameTextField.setFont(new Font("TimesRoman",Font.PLAIN,15));
        portTextField.setFont(new Font("TimesRoman",Font.PLAIN,15));

        Button confirm = new Button("Log In");
        confirm.setFont(new Font("TimesRoman",Font.PLAIN,20));
        confirm.addActionListener(e -> {
            try{
                guiManager.connect(hostNameTextField.getText(), Integer.parseInt(portTextField.getText()));
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Incorrect port!");
            }
        });

        add(title);
        add(hostName);
        add(hostNameTextField);
        add(port);
        add(portTextField);
        add(confirm);

        title.setBounds(0,10,300,70);
        hostName.setBounds(0, 90, 150, 60);
        port.setBounds(0, 140, 150, 60);
        hostNameTextField.setBounds(150, 100, 130, 40);
        portTextField.setBounds(150, 150, 130, 40);
        confirm.setBounds(100, 210, 100, 50);
    }

    @Override
    public void receive(Message message) {
        //empty message
    }
}
