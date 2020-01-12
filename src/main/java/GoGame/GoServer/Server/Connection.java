package GoGame.GoServer.Server;

import GoGame.GoServer.MessageReceiver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection {

    private Socket socket;
    private ServerSocket serverSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Connection(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void getConnection() throws IOException {
        this.socket = serverSocket.accept();
        bufferedReader = new BufferedReader( new InputStreamReader( socket.getInputStream()) );
        bufferedWriter = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream()) );
    }

    private class TcpListener extends Thread{

        final boolean exitFlag = false;
        final MessageReceiver receiver;

        TcpListener(MessageReceiver receiver){
            this.receiver = receiver;
        }

        @Override
        public void run(){
            try {
                while(!exitFlag){

                    String line = bufferedReader.readLine();
                    if(line == null){
                        receiver.receive(null);
                        return;
                    }

                    int separatorIndex = line.indexOf('\t');
                    Message message;
                    if(separatorIndex < 0 || separatorIndex >= line.length())
                        message = new Message(line, "");
                    else
                        message = new Message(line.substring(0, separatorIndex), line.substring(separatorIndex + 1));

                    receiver.receive(message);
                }
            }
            catch (IOException e) {
                e.getStackTrace();
            }
        }
    }

    public void closeConnections() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Server socket already closed");
        }
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public void sendMessage(Message message) {
        String value = "test";
        try {
            bufferedWriter.write(message.getHeader() + '\t' + message.getValue());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startListening(MessageReceiver receiver) {
        TcpListener tcpListener = new TcpListener(receiver);
        tcpListener.start();
    }
}
