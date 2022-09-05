package com.soultech.ddd.microservice.adapter.map;


import com.soultech.ddd.microservice.adapter.jpa.entity.CourseEntity;
import com.soultech.ddd.microservice.adapter.jpa.entity.StudentEntity;
import com.soultech.ddd.microservice.model.Course;
import com.soultech.ddd.microservice.model.Student;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelMapperTest {

    @Test
    void testMapCourseToCourseEntity() {

        final ModelMapper mapper = new DefaultModelMapper();

        final Course course = Course.builder()
                .id(1)
                .title("English 101")
                .build();

        assertThat(course.getNumberOfStudents())
                .isEqualTo(0);

        final CourseEntity entity = mapper.map(course);

        assertThat(entity)
                .extracting(CourseEntity::getId, CourseEntity::getTitle, CourseEntity::getNumberOfStudents)
                .containsOnly(1, "English 101", 0);
    }

    @Test
    void testMapCourseEntityToModel() {

        final ModelMapper mapper = new DefaultModelMapper();

        final CourseEntity entity = new CourseEntity();

        entity.setId(1);
        entity.setTitle("English 101");

        final Course course = mapper.map(entity);

        assertThat(course)
                .extracting(Course::getId, Course::getTitle)
                .containsOnly(1, "English 101");
    }

    @Test
    void testMapStudentEntityToModel_NullCoursesIdSetMapsToEmptySet() {

        final ModelMapper mapper = new DefaultModelMapper();

        final StudentEntity entity = new StudentEntity();
        entity.setId(1);
        entity.setFullName("Denzel Washington");
        entity.setCoursesIds(null);

        final Student student = mapper.map(entity);

        assertThat(student.getCoursesIds())
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    void testMapStudentEntityToModel_SameCoursesIdsSet() {

        final ModelMapper mapper = new DefaultModelMapper();

        final StudentEntity entity = new StudentEntity();
        entity.setId(1);
        entity.setFullName("Denzel Washington");
        entity.setCoursesIds(Set.of(1, 2, 3));

        final Student student = mapper.map(entity);

        assertThat(student.getCoursesIds())
                .containsOnly(1, 2, 3);
    }
}
