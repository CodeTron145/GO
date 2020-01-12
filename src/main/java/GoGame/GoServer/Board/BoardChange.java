package GoGame.GoServer.Board;

public class BoardChange {

    private final ChangeType changeType;
    private final Stone pawnType;
    private final int x;
    private final int y;

    public Stone getPawnType() {
        return pawnType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ChangeType getChangeType(){
        return changeType;
    }

    public static BoardChange delete(int x, int y){
        System.out.println("Deleting change " + x + " " + y);
        return new BoardChange(x, y);
    }

    public static BoardChange add(int x, int y, Stone pawnType){
        return new BoardChange(x, y, pawnType);
    }

    private BoardChange(int x, int y){
        this.x = x;
        this.y = y;
        this.changeType = ChangeType.Delete;
        this.pawnType = null;
    }

    private BoardChange(int x, int y, Stone pawnType){
        this.x = x;
        this.y = y;
        this.changeType = ChangeType.Add;
        this.pawnType = pawnType;
    }

    public enum ChangeType{
        Add,
        Delete
    }
}
