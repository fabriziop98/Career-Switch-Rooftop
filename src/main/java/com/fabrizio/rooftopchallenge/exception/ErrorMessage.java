/**
 * @author fpratici
 */
package com.fabrizio.rooftopchallenge.exception;

public class ErrorMessage {

    private final String error;
    private final String message;
    private final String path;
    private final String fullMessage;


    public ErrorMessage(Exception exception, String path) {
        this.error = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.path = path;
        this.fullMessage =  exception.getCause().getCause().getMessage();
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public String getFullMessage() {
        return fullMessage;
    }

    @Override
    public String toString() {
        return "ErrorMessage [error=" + error + ", message=" + message + ", path=" + path +", fullMessage =" + fullMessage +  "]";
    }

}
