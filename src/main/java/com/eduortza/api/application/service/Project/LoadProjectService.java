package com.eduortza.api.application.service.Project;

import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.application.exception.LoadingException;
import com.eduortza.api.application.port.in.Project.load.LoadProjectPort;
import com.eduortza.api.application.port.out.Project.GetProjectPort;
import com.eduortza.api.common.UseCase;
import com.eduortza.api.domain.Project;
import jakarta.transaction.Transactional;

@UseCase
public class LoadProjectService implements LoadProjectPort {

    private final GetProjectPort getProjectPort;

    public LoadProjectService(GetProjectPort getProjectPort) {
        this.getProjectPort = getProjectPort;
    }

    @Transactional
    @Override
    public Project loadProject(long id) {
        try {
            return getProjectPort.get(id);
        } catch (NonExistsException e) {
            throw new LoadingException("Project not found", e);
        }
        catch (Exception e) {
            throw new LoadingException("Error while trying to load from Database", e);
        }
    }

    @Transactional
    @Override
    public Iterable<Project> loadAllProjects() {
        try {
            return getProjectPort.getAll();
        } catch (Exception e) {
            throw new LoadingException("Error while trying to load from Database", e);
        }
    }

}
