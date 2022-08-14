package com.soultech.ddd.microservice.adapter.jpa;

import com.soultech.ddd.microservice.adapter.jpa.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentEntityRepository extends JpaRepository<StudentEntity, Integer> {

    boolean existsByFullNameLike(String fullName);
}
