package GoGame.GoClient.GUI;

import GoGame.GoClient.Client.IMessageReceiver;
import GoGame.GoClient.Client.Message;

import javax.swing.*;

public abstract class ReceiverFrame extends JFrame implements IMessageReceiver {
    @Override
    public abstract void receive (Message message);
}
