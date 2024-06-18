package com.eduortza.api.application.service.Project;

import com.eduortza.api.application.exception.LoadingException;
import com.eduortza.api.application.port.in.Project.load.LoadProjectCommand;
import com.eduortza.api.application.port.in.Project.load.LoadProjectPort;
import com.eduortza.api.application.port.out.Project.GetProjectPort;
import com.eduortza.api.domain.Project;
import jakarta.transaction.Transactional;

public class LoadProjectService implements LoadProjectPort {

    private final GetProjectPort getProjectPort;

    public LoadProjectService(GetProjectPort getProjectPort) {
        this.getProjectPort = getProjectPort;
    }

    @Transactional
    @Override
    public Project loadProject(LoadProjectCommand command) {
        try {
            return getProjectPort.get(command.getId());
        } catch (Exception e) {
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
