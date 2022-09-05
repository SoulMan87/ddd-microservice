package com.soultech.ddd.microservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) //omite campos nulos https://www.baeldung.com/jackson-ignore-null-fields
public class CreateCourseResponse {

    Boolean existsAlready;
    Integer courseId;
}
