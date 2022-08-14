package com.soultech.ddd.microservice.adapter.map;

import com.soultech.ddd.microservice.adapter.jpa.entity.CourseEntity;
import com.soultech.ddd.microservice.adapter.jpa.entity.StudentEntity;
import com.soultech.ddd.microservice.dto.EnrollmentRow;
import com.soultech.ddd.microservice.model.Course;
import com.soultech.ddd.microservice.model.Enrollment;
import com.soultech.ddd.microservice.model.Student;
import org.springframework.stereotype.Service;

@Service
public class DefaultModelMapper implements ModelMapper {


    @Override
    public Course map(CourseEntity entity) {
        return Course.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .numberOfStudents(entity.getNumberOfStudents())
                .build();
    }

    @Override
    public CourseEntity map(Course model) {
        return CourseEntity.builder()
                .id(model.getId())
                .title(model.getTitle())
                .numberOfStudents(model.getNumberOfStudents())
                .build();
    }

    @Override
    public Student map(StudentEntity entity) {
        return Student.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .coursesIds(entity.getCoursesIds())
                .build();
    }

    @Override
    public StudentEntity map(Student model) {
        return StudentEntity.builder()
                .id(model.getId())
                .fullName(model.getFullName())
                .coursesIds(model.getCoursesIds())
                .build();
    }

    @Override
    public Enrollment map(EnrollmentRow row) {
        return Enrollment.builder()
                .courseId(row.getCourseId())
                .courseTitle(row.getCourseTitle())
                .build();
    }
}
