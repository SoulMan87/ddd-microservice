package com.soultech.ddd.microservice.exception;

public class GenericEnrollmentError extends RuntimeException {

    public GenericEnrollmentError (String message){
        super(message);
    }
}
