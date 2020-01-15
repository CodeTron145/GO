package GoGame.GoServer.Strategy;

import GoGame.GoServer.Lobby.Lobby;
import GoGame.GoServer.Player.Player;
import GoGame.GoServer.Server.Message;
import GoGame.GoServer.Exceptions.GameNotExistingException;
import GoGame.GoServer.Exceptions.NoSlotsAvailableException;

public class LobbyStrategy implements Strategy {

    @Override
    public void handleMessage(Message message, Player sender) {
        switch (message.getHeader()) {
            case "getlobbyplayers":
                sender.sendMessage(Lobby.getInstance().getLobbyPlayersMessage());
                break;
            case "getgames":
                sender.sendMessage(Lobby.getInstance().getGamesMessage());
                break;
            case "creategame":
                Lobby.getInstance().createGame(sender, Integer.parseInt(message.getValue()));
                break;
            case "creategamewithbot":
                Lobby.getInstance().createGameWithBot(sender, Integer.parseInt(message.getValue()));
                break;
            case "joingame":
                try {
                    Lobby.getInstance().addPlayerToGame(sender, message.getValue());
                } catch (GameNotExistingException e) {
                    sender.sendMessage(new Message("Info", "Game" + message.getValue() + "does not exist"));
                } catch (NoSlotsAvailableException e) {
                    sender.sendMessage(new Message("Info", "There are no empty slots available in that game"));
                }
                break;
            default:
                System.out.println("Nieznany header " + message.getHeader());
                break;
        }
    }

    @Override
    public void forceQuit(Player sender) {
        Lobby.getInstance().removePlayer(sender);
    }
}
