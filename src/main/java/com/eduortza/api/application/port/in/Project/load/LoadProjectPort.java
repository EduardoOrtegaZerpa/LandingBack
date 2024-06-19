package com.eduortza.api.application.port.in.Project.load;

import com.eduortza.api.domain.Project;

public interface LoadProjectPort {
    Project loadProject(long id);
    Iterable<Project> loadAllProjects();
}
