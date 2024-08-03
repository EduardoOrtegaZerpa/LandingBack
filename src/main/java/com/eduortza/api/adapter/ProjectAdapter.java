package com.eduortza.api.adapter;

import com.eduortza.api.adapter.exception.AlreadyExistsException;
import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.out.persistence.entities.ProjectEntity;
import com.eduortza.api.adapter.out.persistence.mappers.ProjectMapper;
import com.eduortza.api.adapter.out.persistence.repository.SpringProjectRepository;
import com.eduortza.api.application.exception.DeleteException;
import com.eduortza.api.application.exception.StoreException;
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
    public void delete(long id) throws NonExistsException, DeleteException {
        if (!springProjectRepository.existsById(id)) {
            throw new NonExistsException("Project with id " + id + " does not exist");
        }

        try {
            springProjectRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeleteException("Error deleting Project entity with id " + id);
        }
    }

    @Override
    public void deleteAll() throws DeleteException {
        try {
            springProjectRepository.deleteAll();
        } catch (Exception e) {
            throw new DeleteException("Error deleting all Project entities.");
        }
    }

    @Override
    public Project get(long id) throws NonExistsException {
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
    public Project store(Project project) throws AlreadyExistsException, StoreException {

        if (project == null) {
            throw new NullPointerException("Project is null");
        }

        if (springProjectRepository.existsById(project.getId())) {
            throw new AlreadyExistsException("Project with id " + project.getId() + " already exists");
        }

        ProjectEntity projectEntity;
        try {
            projectEntity = springProjectRepository.save(ProjectMapper.mapToEntity(project));
        } catch (Exception e) {
            throw new StoreException("Error storing Project entity.");
        }
        return ProjectMapper.mapToDomain(projectEntity);
    }

    @Override
    public void update(Project project) throws NonExistsException, StoreException {
        if (project == null) {
            throw new NullPointerException("Project is null");
        }

        if (!springProjectRepository.existsById(project.getId())) {
            throw new NonExistsException("Project with id " + project.getId() + " does not exist");
        }

        try {
            springProjectRepository.save(ProjectMapper.mapToEntity(project));
        } catch (Exception e) {
            throw new StoreException("Error updating Project entity.");
        }
    }
}
