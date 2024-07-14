package com.eduortza.api.adapter.out.persistence.repository;

import com.eduortza.api.adapter.out.persistence.entities.TrajectoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringTrajectoryRepository extends JpaRepository<TrajectoryEntity, Long> {

}
