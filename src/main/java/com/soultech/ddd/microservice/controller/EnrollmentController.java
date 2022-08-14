package com.soultech.ddd.microservice.controller;

import com.soultech.ddd.microservice.dto.CreateCourseRequest;
import com.soultech.ddd.microservice.dto.CreateStudentRequest;
import com.soultech.ddd.microservice.dto.EnrollRequest;
import com.soultech.ddd.microservice.dto.EnrollmentsQuery;
import com.soultech.ddd.microservice.port.EnrollStudentInputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnrollmentController {

    private final WebApplicationContext appContext;

    @PostMapping("/create-course")
    public void createCourse(@RequestBody CreateCourseRequest createCourseRequest) {
        final EnrollStudentInputPort enrollmentStudentUseCase = getUseCase();
        enrollmentStudentUseCase.createCourse(createCourseRequest.getTitle());
    }

    @PostMapping("/create-student")
    public void createStudent(@RequestBody CreateStudentRequest createStudentRequest) {
        final EnrollStudentInputPort enrollmentStudentUseCase = getUseCase();
        enrollmentStudentUseCase.createStudent(createStudentRequest.getFullName());
    }

    @PostMapping("/enroll")
    public void enroll(@RequestBody EnrollRequest enrollRequest) {
        final EnrollStudentInputPort enrollmentStudentUseCase = getUseCase();
        enrollmentStudentUseCase.enroll(enrollRequest.getStudentId(),
                enrollRequest.getStudentId());
    }

    @PostMapping("/enrollments")
    public void enrollments(@RequestBody EnrollmentsQuery enrollmentsQuery) {
        final EnrollStudentInputPort enrollmentStudentUseCase = getUseCase();
        enrollmentStudentUseCase.findEnrollmentsForStudent(enrollmentsQuery.getStudentId());
    }

    private EnrollStudentInputPort getUseCase() {
        return appContext.getBean(EnrollStudentInputPort.class);
    }
}
