package com.eduortza.api.application.port.in.Trajectory.load;
import com.eduortza.api.domain.Trajectory;

public interface LoadTrajectoryPort {
    Trajectory loadTrajectory(long id);
}
