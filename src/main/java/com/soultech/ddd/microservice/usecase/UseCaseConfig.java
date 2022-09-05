package com.soultech.ddd.microservice.usecase;

import com.soultech.ddd.microservice.port.EnrollStudentInputPort;
import com.soultech.ddd.microservice.port.PersistenceOperationsOutputPort;
import com.soultech.ddd.microservice.presenter.RestPresenter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class UseCaseConfig {

    @Bean
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public EnrollStudentInputPort enrollStudentUseCase(PersistenceOperationsOutputPort persistenceOps,
                                                       HttpServletResponse httpServletResponse,
                                                       MappingJackson2HttpMessageConverter httpMessageConverter) {
        return new EnrollStudentUseCase(new RestPresenter(httpServletResponse, httpMessageConverter), persistenceOps);
    }
}
