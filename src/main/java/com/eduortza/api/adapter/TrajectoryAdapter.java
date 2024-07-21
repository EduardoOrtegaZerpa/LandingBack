package com.eduortza.api.adapter;

import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.out.persistence.entities.TrajectoryEntity;
import com.eduortza.api.adapter.out.persistence.mappers.TrajectoryMapper;
import com.eduortza.api.adapter.out.persistence.repository.SpringTrajectoryRepository;
import com.eduortza.api.application.port.out.Trajectory.GetTrajectoryPort;
import com.eduortza.api.application.port.out.Trajectory.UpdateTrajectoryPort;
import com.eduortza.api.domain.Trajectory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrajectoryAdapter implements GetTrajectoryPort, UpdateTrajectoryPort {

    private final SpringTrajectoryRepository springTrajectoryRepository;

    public TrajectoryAdapter(SpringTrajectoryRepository springTrajectoryRepository) {
        this.springTrajectoryRepository = springTrajectoryRepository;
    }

    @Override
    public Trajectory getTrajectory() {
        List<TrajectoryEntity> entities = springTrajectoryRepository.findAll();
        if (entities.isEmpty()) {
            throw new NonExistsException("No Trajectory entity found.");
        }
        return TrajectoryMapper.mapToDomain(entities.get(0));
    }

    @Override
    public void updateTrajectory(Trajectory trajectory) throws Exception {
        if (trajectory == null) {
            throw new NullPointerException("Trajectory is null");
        }

        if (!springTrajectoryRepository.existsById(trajectory.getId())) {
            throw new NonExistsException("Trajectory with id " + trajectory.getId() + " does not exist");
        }

        springTrajectoryRepository.save(TrajectoryMapper.mapToEntity(trajectory));
    }
}
