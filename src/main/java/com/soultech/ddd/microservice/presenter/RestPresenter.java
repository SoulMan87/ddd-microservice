package com.soultech.ddd.microservice.presenter;

import com.soultech.ddd.microservice.exception.EntityDoesExistError;
import com.soultech.ddd.microservice.port.RestPresenterOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RestPresenter implements RestPresenterOutputPort {

    private final HttpServletResponse httpServletResponse;

    private final MappingJackson2HttpMessageConverter jacksonConverter;

    @Override
    public <T> void presentOk(T content) {

        final DelegatingServerHttpResponse httOutputMessage =
                new DelegatingServerHttpResponse(new ServletServerHttpResponse(httpServletResponse));

        httOutputMessage.setStatusCode(HttpStatus.OK);

        try {
            jacksonConverter.write(content, MediaType.APPLICATION_JSON, httOutputMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void presenterError(Throwable t) {

        try {
            TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
        } catch (NoTransactionException e) {

        }

        final DelegatingServerHttpResponse httpOutputMessage =
                new DelegatingServerHttpResponse(new ServletServerHttpResponse(httpServletResponse));

        if (t instanceof EntityDoesExistError) {
            httpOutputMessage.setStatusCode(HttpStatus.BAD_REQUEST);
        } else {
            httpOutputMessage.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            jacksonConverter.write(Map.of("Error", Optional.ofNullable(t.getMessage()).orElse("null")),
                    MediaType.APPLICATION_JSON, httpOutputMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
