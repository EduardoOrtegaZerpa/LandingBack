package com.eduortza.api.adapter;

import com.eduortza.api.adapter.exception.AlreadyExistsException;
import com.eduortza.api.adapter.exception.NonExistsException;
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
        if (!springProjectRepository.existsById(id)) {
            throw new NonExistsException("Project with id " + id + " does not exist");
        }

        springProjectRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        springProjectRepository.deleteAll();
    }

    @Override
    public Project get(long id) {
        return springProjectRepository.findById(id)
                .map(ProjectMapper::mapToDomain)
                .orElseThrow(() -> new NonExistsException("Project with id " + id + " does not exist"));
    }

    @Override
    public List<Project> getAll() {
        return springProjectRepository.findAll().stream()
                .map(ProjectMapper::mapToDomain)
                .toList();
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
        if (project == null) {
            throw new RuntimeException("Project is null");
        }

        if (!springProjectRepository.existsById(project.getId())) {
            throw new NonExistsException("Project with id " + project.getId() + " does not exist");
        }

        springProjectRepository.save(ProjectMapper.mapToEntity(project));
    }
}
