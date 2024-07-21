package com.eduortza.api.application.service.Trajectory;

import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.application.exception.LoadingException;
import com.eduortza.api.application.exception.StoreException;
import com.eduortza.api.application.port.in.Trajectory.modify.ModifyTrajectoryCommand;
import com.eduortza.api.application.port.in.Trajectory.modify.ModifyTrajectoryPort;
import com.eduortza.api.application.port.out.Trajectory.GetTrajectoryPort;
import com.eduortza.api.application.port.out.Trajectory.UpdateTrajectoryPort;
import com.eduortza.api.common.UseCase;
import com.eduortza.api.domain.Trajectory;
import jakarta.transaction.Transactional;

@UseCase
public class ModifyTrajectoryService implements ModifyTrajectoryPort {

    private final UpdateTrajectoryPort updateTrajectoryPort;
    private final GetTrajectoryPort getTrajectoryPort;

    public ModifyTrajectoryService(UpdateTrajectoryPort updateTrajectoryPort, GetTrajectoryPort getTrajectoryPort) {
        this.updateTrajectoryPort = updateTrajectoryPort;
        this.getTrajectoryPort = getTrajectoryPort;
    }

    @Transactional
    @Override
    public Trajectory modifyTrajectory(ModifyTrajectoryCommand command) {

        Trajectory trajectory;


        try {
            trajectory = getTrajectoryPort.getTrajectory();
        } catch (Exception e) {
            throw new LoadingException("Error while trying to get from Database", e);
        }

        if (trajectory == null) {
            throw new NullPointerException("Trajectory is null");
        }

        if (command.getContent() != null) {
            trajectory.setContent(command.getContent());
        }

        try {
            updateTrajectoryPort.updateTrajectory(trajectory);
            return trajectory;
        } catch (NonExistsException e) {
            throw new StoreException("Trajectory with id " + trajectory.getId() + " does not exist", e);
        }
        catch (Exception e) {
            throw new StoreException("Error while trying to store in Database", e);
        }
    }

}
