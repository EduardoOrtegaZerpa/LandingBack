package com.eduortza.api.application.port.in.Trajectory.modify;

import com.eduortza.api.domain.Trajectory;

public interface ModifyTrajectoryPort {
    Trajectory modifyTrajectory(ModifyTrajectoryCommand command);
}
