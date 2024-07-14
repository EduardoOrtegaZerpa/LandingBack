package com.eduortza.api.application.port.out.Trajectory;

import com.eduortza.api.domain.Trajectory;

public interface UpdateTrajectoryPort {
    void updateTrajectory(Trajectory trajectory) throws Exception;
}
