package GoGame.GoClient.Client;

public class Message {

    private final String header;
    private final String value;

    public Message (String header, String value) {
        this.header = header.toLowerCase();
        this.value = value;
    }
}
