package com.soultech.ddd.microservice.port;

public interface RestPresenterOutputPort {

    // copiado desde https://github.com/SoulMan87/ddd-clean-rest

    <T> void presentOk(T content);

    void presentError(Throwable t);
}
