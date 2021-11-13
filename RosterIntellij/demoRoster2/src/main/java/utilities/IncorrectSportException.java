package utilities;

public class IncorrectSportException extends java.lang.Exception {

    /**
     * Creates a new instance of <code>IncorrectSportException</code> without detail message.
     */
    public IncorrectSportException() {
    }


    /**
     * Constructs an instance of <code>IncorrectSportException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public IncorrectSportException(String msg) {
        super(msg);
    }
}
