package com.soultech.ddd.microservice.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Student {

    @Getter
    @EqualsAndHashCode.Include
    private final Integer id;

    @Getter
    private final String fullName;

    @Getter
    private final Set<Integer> coursesIds;

    @Builder
    public Student(Integer id, String fullName, Set<Integer> coursesIds) {

        this.id = id;
        this.fullName = Optional.ofNullable(fullName)
                .filter(f -> !f.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("El nombre no puede ser nulo o vacido"));
        this.coursesIds = Optional.ofNullable(coursesIds)
                .map(Collections::unmodifiableSet).orElse(Set.of());
    }

    public EnrollResult enrollInCourse(Integer courseId) {
        final Set<Integer> ids = new HashSet<>(coursesIds);
        final boolean added = ids.add(courseId);
        return EnrollResult.builder()
                .student(newStudent().coursesIds(ids).build())
                .courseAdded(added)
                .build();
    }

    private StudentBuilder newStudent() {
        return Student.builder()
                .id(id)
                .fullName(fullName)
                .coursesIds(coursesIds);
    }
}
