package com.soultech.ddd.microservice.adapter;

import com.soultech.ddd.microservice.adapter.jpa.CourseEntityRepository;
import com.soultech.ddd.microservice.adapter.jpa.StudentEntityRepository;
import com.soultech.ddd.microservice.adapter.map.ModelMapper;
import com.soultech.ddd.microservice.dto.EnrollmentRow;
import com.soultech.ddd.microservice.exception.EntityDoesNotExistError;
import com.soultech.ddd.microservice.model.Course;
import com.soultech.ddd.microservice.model.Enrollment;
import com.soultech.ddd.microservice.model.Student;
import com.soultech.ddd.microservice.port.PersistenceOperationsOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersistenceGateway implements PersistenceOperationsOutputPort {

    final CourseEntityRepository courseRepo;
    final StudentEntityRepository studentRepo;
    final NamedParameterJdbcOperations jdbcOps;
    final ModelMapper mapper;

    @Override
    public Integer persist(Course course) {
        return courseRepo.save(mapper.map(course)).getId();
    }

    @Override
    public Course obtainCourseById(Integer courseId) throws EntityDoesNotExistError {
        try {
            return mapper.map(courseRepo.getById(courseId));
        } catch (EntityNotFoundException e) {
            throw new EntityDoesNotExistError("No se pudo encontrar el curso con Id: %d".formatted(courseId));
        }
    }

    @Override
    public boolean courseExistsWithTitle(String title) {
        return courseRepo.existsCourseEntityByTitleLike(title);
    }

    @Override
    public Integer persist(Student student) {
        return studentRepo.save(mapper.map(student)).getId();
    }

    @Override
    public Student obtainStudentById(Integer studentId) throws EntityDoesNotExistError {
        try {
            return mapper.map(studentRepo.getById(studentId));
        } catch (EntityNotFoundException e) {
            throw new EntityDoesNotExistError("No se pudo encontrar el estudiante con el Id: %d".formatted(studentId));
        }
    }

    @Override
    public boolean studentExistsWithFullName(String fullName) {
        return studentRepo.existsByFullNameLike(fullName);
    }

    @Override
    public Set<Enrollment> findEnrollments(Integer studentId) {

        return jdbcOps.queryForStream(EnrollmentRow.SQL,
                        Map.of("studentId", studentId),
                        new BeanPropertyRowMapper<>(EnrollmentRow.class))
                .map(mapper::map)
                .collect(Collectors.toSet());
    }
}
