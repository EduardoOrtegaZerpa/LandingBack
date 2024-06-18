package com.eduortza.api.application.service.Project;

import com.eduortza.api.application.exception.DeleteException;
import com.eduortza.api.application.port.in.Project.remove.RemoveProjectCommand;
import com.eduortza.api.application.port.in.Project.remove.RemoveProjectPort;
import com.eduortza.api.application.port.out.Project.DeleteProjectPort;
import jakarta.transaction.Transactional;

public class RemoveProjectService implements RemoveProjectPort {

    private final DeleteProjectPort deleteProjectPort;

    public RemoveProjectService(DeleteProjectPort deleteProjectPort) {
        this.deleteProjectPort = deleteProjectPort;
    }

    @Transactional
    @Override
    public void removeProject(RemoveProjectCommand removeProjectCommand) {
        long id = removeProjectCommand.getId();
        try {
            deleteProjectPort.delete(id);
        } catch (Exception e) {
            throw new DeleteException("Error while trying to delete from Database", e);
        }
    }

    @Transactional
    @Override
    public void removeAllProjects() {
        try {
            deleteProjectPort.deleteAll();
        } catch (Exception e) {
            throw new DeleteException("Error while trying to delete from Database", e);
        }
    }
}
