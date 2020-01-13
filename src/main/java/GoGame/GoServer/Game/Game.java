package GoGame.GoServer.Game;

import GoGame.GoServer.Board.BoardChange;
import GoGame.GoServer.Board.Stone;
import GoGame.GoServer.Lobby.Lobby;
import GoGame.GoServer.Player.Bot;
import GoGame.GoServer.Player.Player;
import GoGame.GoServer.Server.Message;
import GoGame.GoServer.Strategy.PlayerStrategy;
import GoGame.GoServer.Exceptions.InvalidMoveException;
import GoGame.GoServer.Exceptions.NoSlotsAvailableException;
import GoGame.GoServer.Exceptions.TrashDataException;


import java.util.List;
import java.util.UUID;

public class Game {

    private Player playerWhite = null;
    private Player playerBlack = null;
    private final Logic gameLogic;
    private final UUID uuid;
    private int size;

    public Game(int size, Player player){
        this.size = size;
        this.uuid = UUID.randomUUID();
        try {
            addPlayer(player, size);
        } catch (NoSlotsAvailableException e) {
            System.out.println("There are no empty slots!");
        }
        this.gameLogic = new Logic(this, size);
    }

    public void setPawn(int x, int y, Player player){
        Stone playerType = null;
        if(player == playerWhite){
            playerType = Stone.White;
            if(playerBlack == null){
                player.sendMessage(new Message("info", "Wait for your opponent!"));
                return;
            }
        }
        else if(player == playerBlack){
            playerType = Stone.Black;
        }
        else{
            System.out.println("Invalid action. Terminating server!");
        }
        try {
            List<BoardChange> boardChanges = gameLogic.setPawn(x, y, playerType);
            for(BoardChange boardChange : boardChanges){
                if(boardChange.getChangeType() == BoardChange.ChangeType.Add){

                    Message message = new Message(boardChange.getPawnType() == Stone.White ? "setWhitePawn" : "setBlackPawn", boardChange.getX()+","+boardChange.getY());

                    if(playerWhite != null)
                        playerWhite.sendMessage(message);

                    if(playerBlack != null)
                        playerBlack.sendMessage(message);
                }
                else if(boardChange.getChangeType() == BoardChange.ChangeType.Delete){

                    Message message = new Message("deletePawn", boardChange.getX() + "," + boardChange.getY());

                    System.out.println("Sending message after delete" + message);

                    if(playerWhite != null)
                        playerWhite.sendMessage(message);

                    if(playerBlack != null)
                        playerBlack.sendMessage(message);
                }
            }
            int whitePoints = gameLogic.getWhitePoints();
            int blackPoints = gameLogic.getBlackPoints();
            playerWhite.sendMessage(new Message("yourScore", whitePoints + ""));
            playerWhite.sendMessage(new Message("opponentsScore", blackPoints + ""));
            playerBlack.sendMessage(new Message("yourScore", blackPoints + ""));
            playerBlack.sendMessage(new Message("opponentsScore", whitePoints + ""));

            if(player == playerWhite) playerBlack.sendMessage(new Message("yourturn", ""));
            else playerWhite.sendMessage(new Message("yourturn", ""));

        } catch (InvalidMoveException e) {
            player.sendMessage(new Message("InvalidMoveInfo", e.getMessage()));
        } catch (TrashDataException e) {
            System.out.println("Trash data received");
        }
    }

    public void pass(Player player){
        try{
            if(player == playerWhite){
                if(gameLogic.pass(Stone.White)){
                    playerBlack.sendMessage(new Message("info", "Your opponent passed"));
                    playerBlack.sendMessage(new Message("yourturn", ""));
                }
            }
            else{
                if (gameLogic.pass((Stone.Black))){
                    playerWhite.sendMessage(new Message("info", "Your opponent passed"));
                    playerWhite.sendMessage(new Message("yourturn", ""));
                }
            }
        }
        catch(InvalidMoveException ex){
            player.sendMessage(new Message("info", ex.getMessage()));
        }
    }

    public String getDescription(){
        if(playerBlack == null){
            return playerWhite.getNick() + " waiting for an opponent...";
        }
        return playerWhite.getNick() + " vs " + playerBlack.getNick() + " " + gameLogic.getWhitePoints() + ":" + gameLogic.getBlackPoints();
    }

    public int getSize() {
        return size;
    }

    public void addPlayer(Player player, int size) throws NoSlotsAvailableException{
        System.out.println("Player " + player.getNick() + " tries to join the game");

        if(playerWhite != null && playerBlack != null)
            throw new NoSlotsAvailableException();


        Lobby.getInstance().removePlayer(player);
        player.setPlayerStrategy(new PlayerStrategy(this));
        player.sendMessage(new Message("showboard", Integer.toString(size)));

        if(playerWhite == null){
            playerWhite = player;
            playerWhite.sendMessage(new Message("colorinfo", "white"));
        }
        else if(playerBlack == null){
            playerBlack = player;
            playerWhite.sendMessage(new Message("OpponentInfo", playerBlack.getNick()));
            playerBlack.sendMessage(new Message("OpponentInfo", playerWhite.getNick()));
            playerBlack.sendMessage(new Message("colorinfo", "black"));
        }
    }

    public void endSession(boolean notifyPlayers){
        if(playerWhite != null){
            if(notifyPlayers)
                playerWhite.sendMessage(new Message("Info", "Game session has been closed"));
            if(!(playerWhite instanceof Bot))
                Lobby.getInstance().addPlayer(playerWhite);
        }
        if(playerBlack != null){
            if(notifyPlayers)
                playerBlack.sendMessage(new Message("Info", "Game session has been closed"));
            if(!(playerBlack instanceof Bot))
                Lobby.getInstance().addPlayer(playerBlack);
        }
        Lobby.getInstance().removeGame(this);
    }

    public void kickPlayer(Player player){
        if(playerWhite.getUuid() == player.getUuid()){
            playerWhite = null;
        }
        else if(playerBlack.getUuid() == player.getUuid()){
            playerBlack = null;
        }
        endSession(true);
    }

    public UUID getUuid(){
        return uuid;
    }

    public void handleEndGame(List<BoardChange> boardChanges) {

        for(BoardChange boardChange : boardChanges) {
            if (boardChange.getChangeType() == BoardChange.ChangeType.Delete) {
                Message message = new Message("deletePawn", boardChange.getX() + "," + boardChange.getY());
                System.out.println("Sending message after delete" + message);
                if (playerWhite != null) playerWhite.sendMessage(message);
                if (playerBlack != null) playerBlack.sendMessage(message);
            }
}

        int whitePoints = gameLogic.getWhitePoints();
        int blackPoints = gameLogic.getBlackPoints();

        playerWhite.sendMessage(new Message("yourScore", whitePoints + ""));
        playerWhite.sendMessage(new Message("opponentsScore", blackPoints + ""));
        playerBlack.sendMessage(new Message("yourScore", blackPoints + ""));
        playerBlack.sendMessage(new Message("opponentsScore", whitePoints + ""));

        if(whitePoints > blackPoints){
            playerWhite.sendMessage(new Message("info", "Game over! You winner!"));
            playerBlack.sendMessage(new Message("info", "Game over! You loser!"));
        }
        else if(whitePoints < blackPoints){
            playerBlack.sendMessage(new Message("info", "Game over! You winner!"));
            playerWhite.sendMessage(new Message("info", "Game over! You loser!"));
        }
        else{
            playerBlack.sendMessage(new Message("info", "Game over! Bye!"));
            playerWhite.sendMessage(new Message("info", "Game over! Bye!"));
        }

        endSession(false);
    }
}
