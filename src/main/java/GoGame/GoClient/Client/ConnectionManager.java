package GoGame.GoClient.Client;

import java.io.*;
import java.net.Socket;

public class ConnectionManager implements IConnectionManager {

    private final Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    ConnectionManager (Socket socket) {

        this.socket = socket;
        try {
            bufferedReader = new BufferedReader( new InputStreamReader( socket.getInputStream()) );
            bufferedWriter = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream()) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Listener extends Thread {

        final IMessageReceiver receiver;

        private Listener(IMessageReceiver receiver) {

            this.receiver = receiver;
        }

        @Override
        public void run(){
            while(true){
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        receiver.receive(null);
                        return;
                    }

                    int separatorIndex = line.indexOf('\t');
                    Message message;
                    if (separatorIndex < 0 || separatorIndex >= line.length())
                        message = new Message(line, "");
                    else
                        message = new Message(line.substring(0, separatorIndex), line.substring(separatorIndex + 1));

                    receiver.receive(message);
                }
                catch (IOException e) {
                    System.out.println("Read error");
                    receiver.receive(new Message("showloginerror", ""));
                }
            }
        }
    }

    @Override
    public void disconnect() throws IOException {

        socket.close();
    }

    @Override
    public void sendMessage(Message message) {

        try {
            bufferedWriter.write(message.getHeader() + '\t' + message.getValue());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startListening(IMessageReceiver receiver) {

        Listener listener = new Listener(receiver);
        listener.start();
    }
}
