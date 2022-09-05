package com.soultech.ddd.microservice.usecase;

import com.soultech.ddd.microservice.dto.CreateCourseResponse;
import com.soultech.ddd.microservice.dto.CreateStudentResponse;
import com.soultech.ddd.microservice.model.Course;
import com.soultech.ddd.microservice.model.EnrollResult;
import com.soultech.ddd.microservice.model.Student;
import com.soultech.ddd.microservice.port.EnrollStudentInputPort;
import com.soultech.ddd.microservice.port.PersistenceOperationsOutputPort;
import com.soultech.ddd.microservice.port.RestPresenterOutputPort;

import javax.transaction.Transactional;

public class EnrollStudentUseCase implements EnrollStudentInputPort {

    private final RestPresenterOutputPort restPresenter;

    private final PersistenceOperationsOutputPort persistenceOps;

    public EnrollStudentUseCase(RestPresenterOutputPort restPresenter, PersistenceOperationsOutputPort persistenceOps) {
        this.restPresenter = restPresenter;
        this.persistenceOps = persistenceOps;
    }

    @Override
    @Transactional
    public void createCourse(String title) {

        if (persistenceOps.courseExistsWithTitle(title)) {
            restPresenter.presentOk(CreateCourseResponse.builder().existsAlready(true));
        } else {
            final Integer courseId = persistenceOps.persist(Course.builder()
                    .title(title)
                    .build());

            restPresenter.presentOk(CreateCourseResponse.builder()
                    .existsAlready(false)
                    .courseId(courseId)
                    .build());
        }
    }


    @Override
    @Transactional
    public void createStudent(String fullName) {

        if (persistenceOps.studentExistsWithFullName(fullName)) {
            restPresenter.presentOk(CreateStudentResponse.builder().existsAlready(true).build());
        } else {
            final Integer studentId = persistenceOps.persist(Student.builder()
                    .fullName(fullName)
                    .build());

            restPresenter.presentOk(CreateStudentResponse.builder().existsAlready(false)
                    .studentId(studentId)
                    .build());
        }
    }

    @Transactional
    @Override
    public void enroll(Integer courseId, Integer studentId) {
        try {

            //intenta matricular al estudiante en el curso
            final Student student = persistenceOps.obtainStudentById(studentId);
            final EnrollResult enrollResult = student.enrollInCourse(courseId);

            //continua solo si la matricula ha resultado en un nuevo
            //curso sumado a los cursos del estudiante
            if (enrollResult.isCourseAdded()) {

                persistenceOps.persist(enrollResult.getStudent());

                final Course course = persistenceOps.obtainCourseById(courseId);
                final Course updatedCourse = course.enrollStudent();
                persistenceOps.persist(updatedCourse);
            }

            //afianza el resultado de la matricula
            restPresenter.presentOk(enrollResult);
        } catch (Exception e) {
            restPresenter.presentError(e);
        }

    }

    @Override
    public void findEnrollmentsForStudent(Integer studentId) {
        restPresenter.presentOk(persistenceOps.findEnrollments(studentId));
    }
}
