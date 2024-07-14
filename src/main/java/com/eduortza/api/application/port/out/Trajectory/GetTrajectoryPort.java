package com.eduortza.api.application.port.out.Trajectory;

import com.eduortza.api.domain.Trajectory;

public interface GetTrajectoryPort {
    Trajectory getTrajectoryById(long id);
}
