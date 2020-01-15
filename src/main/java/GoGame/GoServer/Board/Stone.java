package GoGame.GoServer.Board;

public enum Stone {
    White,
    Black,
    Empty;

    public static Stone other(Stone type){
        if(type == White){
            return Black;
        }
        if(type == Black){
            return White;
        }
        return Empty;
    }
}
