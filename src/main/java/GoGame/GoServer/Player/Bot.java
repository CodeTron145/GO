package GoGame.GoServer.Player;

import GoGame.GoServer.Board.Stone;
import GoGame.GoServer.Game.Game;
import GoGame.GoServer.Server.Message;

import java.util.Random;

public class Bot extends Player {

    private final Stone[][] tiles;
    private final Game game;
    private final int size;
//    private IMessageReceiver messageReceiver;

    public Bot(Game game, int size) {
        super("Bot", null);
        this.game = game;
        this.size = size;
        System.out.println("Creating bot");

        tiles = new Stone[size][size];

        System.out.println("Bot created");

        for(int x = 0; x < size; x++)
            for(int y = 0; y < size; y++){
                tiles[x][y] = Stone.Empty;
            }
    }

    private final Random generator = new Random();

    private void makeMove(int i){
        System.out.println("Bot moves now");
        int x, y;
        if(i < 2){
            x = generator.nextInt(size);
            y = generator.nextInt(size);
        }
        else{
            game.pass(this);
            return;
        }
        game.setPawn(x, y, this);
    }

    private int i = 0;

    @Override
    public void sendMessage(Message message) {
        System.out.println("Bot received message " + message.toString());

        switch (message.getHeader()) {
            case "setwhitepawn": {
                int x = Integer.parseInt(message.getValue().split(",")[0]);
                int y = Integer.parseInt(message.getValue().split(",")[1]);
                tiles[x][y] = Stone.White;
                break;
            }
            case "setblackpawn": {
                int x = Integer.parseInt(message.getValue().split(",")[0]);
                int y = Integer.parseInt(message.getValue().split(",")[1]);
                tiles[x][y] = Stone.Black;
                break;
            }
            case "deletepawn": {
                int x = Integer.parseInt(message.getValue().split(",")[0]);
                int y = Integer.parseInt(message.getValue().split(",")[1]);
                tiles[x][y] = Stone.Empty;
                break;
            }
            case "yourturn":
                System.out.println("its bots turn");
                i = 0;
                makeMove(i);
                break;
            case "invalidmoveinfo":
                i++;
                makeMove(i);
                break;
        }
    }
}
