package com.eduortza.api.application.service.Project;

import com.eduortza.api.application.exception.DeleteException;
import com.eduortza.api.application.port.in.Project.remove.RemoveProjectCommand;
import com.eduortza.api.application.port.in.Project.remove.RemoveProjectPort;
import com.eduortza.api.application.port.out.FilePort;
import com.eduortza.api.application.port.out.Project.DeleteProjectPort;
import com.eduortza.api.application.port.out.Project.GetProjectPort;
import jakarta.transaction.Transactional;

public class RemoveProjectService implements RemoveProjectPort {

    private final DeleteProjectPort deleteProjectPort;
    private final FilePort filePort;
    private final GetProjectPort getProjectPort;

    public RemoveProjectService(DeleteProjectPort deleteProjectPort, FilePort filePort, GetProjectPort getProjectPort) {
        this.deleteProjectPort = deleteProjectPort;
        this.filePort = filePort;
        this.getProjectPort = getProjectPort;
    }

    @Transactional
    @Override
    public void removeProject(RemoveProjectCommand removeProjectCommand) {
        long id = removeProjectCommand.getId();
        try {
            String imageUrl = getProjectPort.get(id).getImageUrl();
            filePort.deleteFile("src/main/resources/static/" + imageUrl);
            deleteProjectPort.delete(id);
        } catch (Exception e) {
            throw new DeleteException("Error while trying to delete from Database", e);
        }
    }

    @Transactional
    @Override
    public void removeAllProjects() {
        try {
            getProjectPort.getAll().forEach(project -> {
                filePort.deleteFile("src/main/resources/static/" + project.getImageUrl());
            });
            deleteProjectPort.deleteAll();
        } catch (Exception e) {
            throw new DeleteException("Error while trying to delete from Database", e);
        }
    }
}
