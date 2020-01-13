package GoGame.GoServer.Game;

import GoGame.GoServer.Board.BoardChange;
import GoGame.GoServer.Board.Stone;
import GoGame.GoServer.Board.Tile;
import GoGame.GoServer.Exceptions.InvalidMoveException;
import GoGame.GoServer.Exceptions.TrashDataException;

import java.util.ArrayList;
import java.util.List;

class Logic {

    private final int size;
    private Stone makingMove;
    private final Tile[][] tiles;
    private int lastWhiteX = -1;
    private int lastWhiteY = -1;
    private int lastBlackX = -1;
    private int lastBlackY = -1;
    private int whitePoints = 0;
    private int blackPoints = 0;
    private boolean lastMovePassed = false;
    private final Game endGameHandler;

    Logic(Game endGameHandler, int size){
        this.endGameHandler = endGameHandler;
        this.size = size;
        this.tiles = new Tile[size][size];
        for(int x = 0; x < size; x++){
            for(int y= 0; y < size; y++){
                tiles[x][y] = new Tile();
            }
        }
        this.makingMove = Stone.White;
    }

    public int getWhitePoints() {
        return whitePoints;
    }

    public int getBlackPoints() {
        return blackPoints;
    }

    private int getBreaths(int x, int y, Stone type){
        return getBreaths(x, y, type, new ArrayList<>());
    }
    private int getBreaths(int x, int y, Stone chainType, List<Tile> visited){
        if(x < 0 || x >= size || y < 0 || y >= size)
            return 0;
        if(visited.contains(tiles[x][y]))
            return 0;
        visited.add(tiles[x][y]);

        Stone type = tiles[x][y].type;
        if(type == Stone.Empty){
            return 1;
        }
        else if(type == chainType){
            return getBreaths(x+1, y, chainType, visited) +
                    getBreaths(x-1, y, chainType, visited) +
                    getBreaths(x, y + 1, chainType, visited) +
                    getBreaths(x, y -1, chainType, visited);
        }
        else{
            return 0;
        }
    }

    synchronized ArrayList<BoardChange> setPawn(int x, int y, Stone type) throws InvalidMoveException, TrashDataException {
        ArrayList<BoardChange> changes = new ArrayList<>();

        if(type == Stone.Empty){
            throw new TrashDataException("Type must be either Black or White");
        }
        if(type != makingMove){
            throw new InvalidMoveException("It's not your turn!");
        }
        if(x < 0 || x >= size || y < 0 || y >= size){
            throw new InvalidMoveException("Invalid coordinates");
        }
        if(tiles[x][y].type != Stone.Empty){
            throw new InvalidMoveException("Can't place pawn on a non-empty tile");
        }

        tiles[x][y].type = type;
        int breaths = getBreaths(x, y, type);
        boolean killsOpponent = false;
        for(int xIndex = 0; xIndex < size; xIndex++)
            for(int yIndex = 0; yIndex < size; yIndex++){
                if(tiles[xIndex][yIndex].type == Stone.other(type) && getBreaths(xIndex, yIndex, tiles[xIndex][yIndex].type) <= 0){
                    killsOpponent = true;
                    break;
                }
            }
        tiles[x][y].type = Stone.Empty;
        if(breaths <= 0 && !killsOpponent) {
            throw new InvalidMoveException("Can't place pawn on a tile with no breaths");
        }
        if(type == Stone.White && x == lastWhiteX && y == lastWhiteY ||
                type == Stone.Black && x == lastBlackX && y == lastBlackY){
            throw new InvalidMoveException("Can't place pawn here because of ko rule");
        }

        tiles[x][y].type = type;
        if(type == Stone.White){
            lastWhiteX = x;
            lastWhiteY = y;
        }
        else{
            lastBlackX = x;
            lastBlackY = y;
        }

        changes.add(BoardChange.add(x, y, type));
        makingMove = Stone.other(makingMove);
        lastMovePassed = false;

        for(int xIndex = 0; xIndex < size; xIndex++)
            for(int yIndex = 0; yIndex < size; yIndex++){
                if(tiles[xIndex][yIndex].type == Stone.other(type) && getBreaths(xIndex, yIndex, tiles[xIndex][yIndex].type) <= 0){
                    tiles[xIndex][yIndex].isAlive = false;
                    if(type == Stone.White) whitePoints++;
                    else blackPoints++;
                    changes.add(BoardChange.delete(xIndex, yIndex));
                }
            }

        for(int xIndex = 0; xIndex < size; xIndex++)
            for(int yIndex = 0; yIndex < size; yIndex++){
                if(!tiles[xIndex][yIndex].isAlive){
                    tiles[xIndex][yIndex].type = Stone.Empty;
                    tiles[xIndex][yIndex].isAlive = true;
                }
            }
        return changes;
    }

    public boolean pass(Stone type) throws InvalidMoveException{
        if(type == makingMove){
            if(lastMovePassed){
                endGame();
                return false;
            }
            else{
                makingMove = Stone.other(makingMove);
                lastMovePassed = true;
                return true;
            }
        }
        else{
            throw new InvalidMoveException("It's not your turn!");
        }
    }

    protected boolean checkFinalTileType(int x, int y, ArrayList<Tile> visited, Stone type){
        if(x < 0 || x >= size || y < 0 || y >= size)
            return true;

        if(visited.contains(tiles[x][y])){
            return true;
        }
        visited.add(tiles[x][y]);
        if(tiles[x][y].type == Stone.other(type)){
            return false;
        }
        else if(tiles[x][y].type == type){
            return true;
        }
        else{
            return checkFinalTileType(x+1, y, visited, type) &&
                    checkFinalTileType(x-1, y, visited, type) &&
                    checkFinalTileType(x, y+1, visited, type) &&
                    checkFinalTileType(x, y-1, visited, type);
        }
    }

    protected Stone assignFinalTileType(int x, int y){
        if(tiles[x][y].type != Stone.Empty){
            return Stone.Empty;
        }
        if(checkFinalTileType(x, y, new ArrayList<>(), Stone.White)){
            return Stone.White;
        }
        if(checkFinalTileType(x, y, new ArrayList<>(), Stone.Black)){
            return Stone.Black;
        }
        return Stone.Empty;
    }

    private void endGame(){
        System.out.println("Game over!");

        ArrayList<BoardChange> boardChanges = new ArrayList<>();
        for(int x = 0; x < size; x++)
            for(int y = 0; y < size; y++) {
                if(tiles[x][y].type != Stone.Empty && getBreaths(x, y, Stone.White) < 2 && getBreaths(x, y, Stone.Black) < 2){
                    boardChanges.add(BoardChange.delete(x, y));
                    tiles[x][y].isAlive = false;
                }
            }

        for(int x = 0; x < size; x++)
            for(int y = 0; y < size; y++) {
                if (!tiles[x][y].isAlive) {
                    tiles[x][y].type = Stone.Empty;
                }
            }

        for(int x = 0; x < size; x++)
            for(int y = 0; y < size; y++){

                Stone finalType = assignFinalTileType(x, y);
                if(finalType == Stone.White){
                    whitePoints++;
                }
                else if(finalType == Stone.Black){
                    blackPoints++;
                }
            }

        endGameHandler.handleEndGame(boardChanges);
    }
}