package com.example.ateIt.exception;

public class NotExistDietException extends RuntimeException {
    public NotExistDietException() {
        super("주어진 id에 해당하는 Diet가 존재하지 않습니다.");
    }
}
