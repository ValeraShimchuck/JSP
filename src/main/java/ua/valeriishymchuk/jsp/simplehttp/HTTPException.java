package ua.valeriishymchuk.jsp.simplehttp;

public class HTTPException extends Exception{

    public HTTPException(String message) {
        super(message);
    }

    public HTTPException(String message, Throwable cause) {
        super(message, cause);
    }

    public HTTPException(Throwable cause) {
        super(cause);
    }

}
