package com.eduortza.api.adapter;

import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.out.persistence.mappers.TrajectoryMapper;
import com.eduortza.api.adapter.out.persistence.repository.SpringTrajectoryRepository;
import com.eduortza.api.application.port.out.Trajectory.GetTrajectoryPort;
import com.eduortza.api.application.port.out.Trajectory.UpdateTrajectoryPort;
import com.eduortza.api.domain.Trajectory;
import org.springframework.stereotype.Repository;

@Repository
public class TrajectoryAdapter implements GetTrajectoryPort, UpdateTrajectoryPort {

    private final SpringTrajectoryRepository springTrajectoryRepository;

    public TrajectoryAdapter(SpringTrajectoryRepository springTrajectoryRepository) {
        this.springTrajectoryRepository = springTrajectoryRepository;
    }

    @Override
    public Trajectory getTrajectoryById(long id) {
        return springTrajectoryRepository.findById(id)
                .map(TrajectoryMapper::mapToDomain)
                .orElseThrow(() -> new NonExistsException("Trajectory with id " + id + " does not exist"));
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
