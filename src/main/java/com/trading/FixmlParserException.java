package com.trading;

class FixmlParserException extends RuntimeException {

    FixmlParserException(String message) {
        super(message);
    }

    FixmlParserException(Throwable cause) {
        super(cause);
    }
}
