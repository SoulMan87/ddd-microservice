package com.soultech.ddd.microservice.port;

public interface RestPresenterOutputPort {

    <T> void presentOk(T content);

    void presenterError(Throwable t);
}
