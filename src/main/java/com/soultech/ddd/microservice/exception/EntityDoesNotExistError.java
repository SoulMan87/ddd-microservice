package com.soultech.ddd.microservice.exception;

public class EntityDoesNotExistError extends GenericEnrollmentError{
    public EntityDoesNotExistError(String message) {
        super(message);
    }
}
