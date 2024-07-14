package com.eduortza.api.adapter.out.persistence.mappers;

import com.eduortza.api.adapter.out.persistence.entities.TrajectoryEntity;
import com.eduortza.api.domain.Trajectory;

public class TrajectoryMapper {

    public static TrajectoryEntity mapToEntity(Trajectory trajectory) {
        return new TrajectoryEntity(
            trajectory.getId(),
            trajectory.getContent()
        );
    }

    public static Trajectory mapToDomain(TrajectoryEntity trajectoryEntity) {
        return new Trajectory(
            trajectoryEntity.getId(),
            trajectoryEntity.getContent()
        );
    }
}
