package com.botscrew.nlpclient.exception;

public class NlpClientException extends RuntimeException {

    public NlpClientException() {
        super();
    }

    public NlpClientException(String message) {
        super(message);
    }

    public NlpClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
