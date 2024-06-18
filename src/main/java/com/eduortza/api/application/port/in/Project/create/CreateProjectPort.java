package com.eduortza.api.application.port.in.Project.create;

import com.eduortza.api.domain.Project;

public interface CreateProjectPort {
    Project createProject(CreateProjectCommand createProjectCommand);
}
