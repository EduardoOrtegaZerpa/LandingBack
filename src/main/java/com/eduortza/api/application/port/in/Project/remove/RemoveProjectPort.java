package com.eduortza.api.application.port.in.Project.remove;

public interface RemoveProjectPort {
    void removeProject(RemoveProjectCommand command);
    void removeAllProjects();
}
