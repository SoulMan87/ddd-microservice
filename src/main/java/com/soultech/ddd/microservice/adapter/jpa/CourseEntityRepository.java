package com.soultech.ddd.microservice.adapter.jpa;

import com.soultech.ddd.microservice.adapter.jpa.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseEntityRepository extends JpaRepository<CourseEntity, Integer> {

    boolean existsCourseEntityByTitleLike(String title);
}
