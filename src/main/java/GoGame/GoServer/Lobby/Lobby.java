package GoGame.GoServer.Lobby;

import GoGame.GoServer.Game.Game;
import GoGame.GoServer.Player.Bot;
import GoGame.GoServer.Player.Player;
import GoGame.GoServer.Server.Message;
import GoGame.GoServer.Strategy.LobbyStrategy;
import GoGame.GoServer.Exceptions.GameNotExistingException;
import GoGame.GoServer.Exceptions.NoSlotsAvailableException;

import java.util.HashMap;
import java.util.UUID;

public class Lobby {

    private static Lobby instance = null;
    private Lobby(){}

    private final HashMap<UUID, Player> players = new HashMap<>();
    private final HashMap<UUID, Game> games = new HashMap<>();

    public synchronized void addPlayer(Player newPlayer)
    {
        players.put(newPlayer.getUuid(), newPlayer);
        newPlayer.setPlayerStrategy(new LobbyStrategy());
        newPlayer.sendMessage(new Message("showlobby", ""));

        Message playersMessage = getLobbyPlayersMessage();
        Message gamesMessage = getGamesMessage();
        players.forEach((uuid, player)-> {player.sendMessage(playersMessage);player.sendMessage(gamesMessage);});
    }

    public synchronized void createGame(Player player, int size){
        removePlayer(player);
        Game newGame = new Game(size, player);
        games.put(newGame.getUuid(), newGame);

        Message gamesMessage = getGamesMessage();
        players.forEach((uuid, game)-> game.sendMessage(gamesMessage));
    }

    public synchronized void createGameWithBot(Player player, int size){
        removePlayer(player);;
        Game newGame = new Game(size, player);
        try {
            System.out.println("Creating a new game with bot");
            newGame.addPlayer(new Bot(newGame, size), size);
        } catch (NoSlotsAvailableException e) {
            System.out.println("Fatal game error somehow");
        }
        games.put(newGame.getUuid(), newGame);

        Message gamesMessage = getGamesMessage();
        players.forEach((uuid, game)-> game.sendMessage(gamesMessage));
    }

    public synchronized void removePlayer(Player removedPlayer){
        players.remove(removedPlayer.getUuid());

        Message playersMessage = getLobbyPlayersMessage();
        Message gamesMessage = getGamesMessage();
        players.forEach((uuid, player)-> {player.sendMessage(playersMessage);player.sendMessage(gamesMessage);});
    }

    public synchronized void removeGame(Game removedGame){
        games.remove(removedGame.getUuid());

        Message playersMessage = getLobbyPlayersMessage();
        Message gamesMessage = getGamesMessage();
        players.forEach((uuid, player)-> {player.sendMessage(playersMessage);player.sendMessage(gamesMessage);});
    }

    public synchronized void addPlayerToGame(Player player, String gameUuid) throws GameNotExistingException, NoSlotsAvailableException {
        UUID uuid;
        try{
            uuid = UUID.fromString(gameUuid);
        }
        catch (Exception ex){
            throw new GameNotExistingException();
        }

        Game game = games.get(uuid);
        if(game == null){
            throw new GameNotExistingException();
        }
        game.addPlayer(player, game.getSize());
    }


    public HashMap<UUID, Player> getLobbyPlayers(){
        return players;
    }

    public HashMap<UUID, Game> getGames(){
        return games;
    }

    public synchronized Message getLobbyPlayersMessage() {
        if(players.size() == 0){
            return new Message("lobbyPlayers", "");
        }

        StringBuilder stringBuilder = new StringBuilder();
        players.forEach((uuid, player) -> {
            stringBuilder.append(",");
            stringBuilder.append(player.getNick());
        });
        return new Message("lobbyPlayers", stringBuilder.toString().substring(1));
    }

    public synchronized Message getGamesMessage() {
        if(games.size() == 0){
            return new Message("games", "");
        }

        StringBuilder stringBuilder = new StringBuilder();
        games.forEach((uuid, game) -> {
            stringBuilder.append(",");
            stringBuilder.append(game.getUuid().toString());
            stringBuilder.append(";");
            stringBuilder.append(game.getDescription());
        });
        return new Message("games", stringBuilder.toString().substring(1));
    }

    public static synchronized Lobby getInstance(){
        if(instance == null){
            instance = new Lobby();
        }
        return instance;
    }

    private void clean(){

        instance = null;

    }

}
