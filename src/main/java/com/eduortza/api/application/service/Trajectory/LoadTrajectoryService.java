package com.eduortza.api.application.service.Trajectory;

import com.eduortza.api.application.exception.LoadingException;
import com.eduortza.api.application.port.in.Trajectory.load.LoadTrajectoryPort;
import com.eduortza.api.application.port.out.Trajectory.GetTrajectoryPort;
import com.eduortza.api.common.UseCase;
import com.eduortza.api.domain.Trajectory;
import jakarta.transaction.Transactional;

@UseCase
public class LoadTrajectoryService implements LoadTrajectoryPort {

    private final GetTrajectoryPort getTrajectoryPort;

    public LoadTrajectoryService(GetTrajectoryPort getTrajectoryPort) {
        this.getTrajectoryPort = getTrajectoryPort;
    }

    @Transactional
    @Override
    public Trajectory loadTrajectory(long id) {
        try {
            return getTrajectoryPort.getTrajectoryById(id);
        } catch (Exception e) {
            throw new LoadingException("Error while trying to load from Database", e);
        }
    }
}
