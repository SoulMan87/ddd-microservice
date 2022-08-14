package com.soultech.ddd.microservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateResponseResponse {

    Boolean existsAlready;

    Integer courseId;
}
