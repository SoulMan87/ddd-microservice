package com.soultech.ddd.microservice.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ModelTest {

    @Test
    void testStudentBuilder_ErrorBuildWithEmptyFullName() {
        assertThrows(IllegalArgumentException.class, () -> Student.builder().build());
        assertThrows(IllegalArgumentException.class,
                () -> Student.builder()
                        .fullName(null)
                        .build());
        assertThrows(IllegalArgumentException.class,
                () -> Student.builder()
                        .fullName("")
                        .build());
        assertThrows(IllegalArgumentException.class,
                () -> Student.builder()
                        .fullName(" ")
                        .build());
    }

    @Test
    void testStudentIsImmutable() {
        final Student studentBefore = Student.builder()
                .id(1)
                .fullName("Mario Moreno Cantiflas")
                .build();

        final EnrollResult enrollResult = studentBefore.enrollInCourse(1);
        final Student studentAfter = enrollResult.getStudent();

        assertThat(studentBefore.getCoursesIds())
                .isEmpty();
        assertThat(studentAfter.getCoursesIds())
                .containsOnly(1);
    }
}
