package com.epam.geometry.exception;

public class FileAccessException extends Exception {
    private Throwable cause;
    private String message;
   public FileAccessException() {
        super();
    }

    public FileAccessException(String message) {
        super(message);
        this.message=message;

    }

    public FileAccessException(String message, Throwable cause) {
        super(message, cause);
        this.message=message;
        this.cause = cause;

    }

    public FileAccessException(Throwable cause) {
        super(cause);
        this.cause=cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
