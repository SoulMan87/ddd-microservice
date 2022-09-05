package com.soultech.ddd.microservice.presenter;

import com.soultech.ddd.microservice.exception.EntityDoesNotExistError;
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

    //REST presenter de clean architecture
    //copiado desde https://github.com/SoulMan87/ddd-clean-rest
    @Override
    public <T> void presentOk(T content) {

        final DelegatingServerHttpResponse httpOutputMessage =
                new DelegatingServerHttpResponse(new ServletServerHttpResponse(httpServletResponse));

        httpOutputMessage.setStatusCode(HttpStatus.OK);

        try {
            jacksonConverter.write(content, MediaType.APPLICATION_JSON, httpOutputMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void presentError(Throwable t) {

        //metodo que hace un rollback si es necesario
        try {
            TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
        } catch (NoTransactionException e) {
            //no hace nada si no se ejecuta en ninguna transacion
        }

        final DelegatingServerHttpResponse httpOutputMessage =
                new DelegatingServerHttpResponse(new ServletServerHttpResponse(httpServletResponse));

        if (t instanceof EntityDoesNotExistError) {
            httpOutputMessage.setStatusCode(HttpStatus.BAD_REQUEST);
        } else {
            httpOutputMessage.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            jacksonConverter.write(Map.of("error", Optional.ofNullable(t.getMessage()).orElse("null")),
                    MediaType.APPLICATION_JSON, httpOutputMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
