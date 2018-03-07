package com.botscrew.nlpclient.provider.dialogflow.exception;

public class DialogFlowException extends RuntimeException {

    public DialogFlowException() {
        super();
    }

    public DialogFlowException(String s) {
        super(s);
    }

    public DialogFlowException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
