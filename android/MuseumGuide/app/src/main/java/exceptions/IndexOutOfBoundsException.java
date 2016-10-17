package exceptions;

public class IndexOutOfBoundsException extends Exception {
    public IndexOutOfBoundsException(){
        super("Index given is greater than the size!");
    }
}
