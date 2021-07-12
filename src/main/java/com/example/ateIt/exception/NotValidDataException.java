package com.example.ateIt.exception;

public class NotValidDataException extends RuntimeException {
    public NotValidDataException() {
        super("적합하지 않은 데이터입니다.");
    }
}
