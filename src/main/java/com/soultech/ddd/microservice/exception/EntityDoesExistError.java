package com.soultech.ddd.microservice.exception;

public class EntityDoesExistError extends GenericEnrollmentError {
    public EntityDoesExistError(String message) {
        super(message);
    }
}
