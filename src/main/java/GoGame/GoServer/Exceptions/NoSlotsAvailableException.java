package GoGame.GoServer.Exceptions;

public class NoSlotsAvailableException extends Exception {
    public NoSlotsAvailableException(){
        super("No Slots Are available!");
    }
}
