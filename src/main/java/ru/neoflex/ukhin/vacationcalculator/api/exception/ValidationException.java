package ru.neoflex.ukhin.vacationcalculator.api.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
