package com.soultech.ddd.microservice.adapter.map;

import com.soultech.ddd.microservice.adapter.jpa.entity.CourseEntity;
import com.soultech.ddd.microservice.adapter.jpa.entity.StudentEntity;
import com.soultech.ddd.microservice.dto.EnrollmentRow;
import com.soultech.ddd.microservice.model.Course;
import com.soultech.ddd.microservice.model.Enrollment;
import com.soultech.ddd.microservice.model.Student;

public interface ModelMapper {

    Course map(CourseEntity entity);

    CourseEntity map(Course model);

    Student map(StudentEntity entity);

    StudentEntity map(Student model);

    Enrollment map(EnrollmentRow row);
}
