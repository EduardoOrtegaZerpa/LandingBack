package com.eduortza.api.adapter.out.persistence.repository;

import com.eduortza.api.adapter.out.persistence.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringProjectRepository extends JpaRepository<ProjectEntity, Long> {
}
