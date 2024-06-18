package com.eduortza.api.application.port.in.Project.load;

import com.eduortza.api.domain.Project;

public interface LoadProjectPort {
    Project loadProject(LoadProjectCommand command);
    Iterable<Project> loadAllProjects();
}
