package com.soultech.ddd.microservice.dto;

import lombok.Data;

@Data
public class EnrollRequest {

    Integer courseId;
    Integer studentId;
}
