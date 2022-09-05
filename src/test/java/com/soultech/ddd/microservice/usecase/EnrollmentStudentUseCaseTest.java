package com.soultech.ddd.microservice.usecase;

import com.soultech.ddd.microservice.model.Course;
import com.soultech.ddd.microservice.model.EnrollResult;
import com.soultech.ddd.microservice.model.Student;
import com.soultech.ddd.microservice.port.PersistenceOperationsOutputPort;
import com.soultech.ddd.microservice.port.RestPresenterOutputPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EnrollmentStudentUseCaseTest {

    @Mock
    private RestPresenterOutputPort presenter;

    @Mock
    private PersistenceOperationsOutputPort persistenceOps;

    @BeforeEach
    void setUp() {

        //Se retorna una simulación de curso cuando se necesita obtener uno
        lenient().when(persistenceOps.obtainCourseById(anyInt()))
                .thenAnswer(invocation -> Course.builder()
                        .id(invocation.getArgument(0))
                        .title("Hexagonal Architecture 101")
                        .build());

        //Se retorna una simulación de estudiante en algunos cursos cuando se necesita uno
        lenient().when(persistenceOps.obtainStudentById(anyInt()))
                .thenAnswer(invocation -> Student.builder()
                        .id(invocation.getArgument(0))
                        .fullName("Tintan")
                        .coursesIds(Set.of(1, 2, 3))
                        .build());

        //Retorna un id del curso o del estudiante cuando se desea persistir
        lenient().when(persistenceOps.persist(any(Course.class)))
                .thenAnswer(invocation -> ((Course) invocation.getArgument(0)).getId());

        lenient().when(persistenceOps.persist(any(Student.class)))
                .thenAnswer(invocation -> ((Student) invocation.getArgument(0)).getId());
    }

    @Test
    void testEnroll_StudentCanEnrollCourseSheIsNotYetEnrolledIn() {

        final EnrollStudentUseCase useCase = new EnrollStudentUseCase(presenter, persistenceOps);

        //matricula un estudiante en un nuevo curso
        useCase.enroll(4, 1);
        assertNoError();

        //verifica el resultado de la matricula
        final ArgumentCaptor<EnrollResult> resultArg = ArgumentCaptor.forClass(EnrollResult.class);
        verify(presenter, times(1))
                .presentOk(resultArg.capture());

        final EnrollResult enrollResult = resultArg.getValue();
        Assertions.assertThat(enrollResult.isCourseAdded()).isTrue();
        Assertions.assertThat(enrollResult.getStudent().getCoursesIds())
                .containsOnly(1, 2, 3, 4);
    }

    private void assertNoError() {
        verify(presenter, times(0)).presentError(any(Throwable.class));
    }
}
