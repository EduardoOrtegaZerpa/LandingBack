package com.eduortza.api.adapter;

import com.eduortza.api.adapter.exception.AlreadyExistsException;
import com.eduortza.api.adapter.out.persistence.entities.ProjectEntity;
import com.eduortza.api.adapter.out.persistence.mappers.ProjectMapper;
import com.eduortza.api.adapter.out.persistence.repository.SpringProjectRepository;
import com.eduortza.api.application.port.out.Project.StoreProjectPort;
import com.eduortza.api.application.port.out.Project.UpdateProjectPort;
import com.eduortza.api.application.port.out.Project.DeleteProjectPort;
import com.eduortza.api.application.port.out.Project.GetProjectPort;
import com.eduortza.api.domain.Project;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectAdapter implements StoreProjectPort, UpdateProjectPort, DeleteProjectPort, GetProjectPort {

    private final SpringProjectRepository springProjectRepository;

    public ProjectAdapter(SpringProjectRepository springProjectRepository) {
        this.springProjectRepository = springProjectRepository;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Project get(long id) {
        return null;
    }

    @Override
    public List<Project> getAll() {
        return List.of();
    }

    @Override
    public Project store(Project project) {

        if (project == null) {
            throw new RuntimeException("Project is null");
        }

        if (springProjectRepository.existsById(project.getId())) {
            throw new AlreadyExistsException("Project with id " + project.getId() + " already exists");
        }

        ProjectEntity projectEntity = springProjectRepository.save(ProjectMapper.mapToEntity(project));
        return ProjectMapper.mapToDomain(projectEntity);
    }

    @Override
    public void update(Project project) {

    }
}
