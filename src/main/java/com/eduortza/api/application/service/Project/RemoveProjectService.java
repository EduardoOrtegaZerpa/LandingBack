package com.eduortza.api.application.service.Project;

import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.application.exception.DeleteException;
import com.eduortza.api.application.port.in.Project.remove.RemoveProjectPort;
import com.eduortza.api.application.port.out.FilePort;
import com.eduortza.api.application.port.out.Project.DeleteProjectPort;
import com.eduortza.api.application.port.out.Project.GetProjectPort;
import com.eduortza.api.common.UseCase;
import jakarta.transaction.Transactional;

@UseCase
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
    public void removeProject(long id) {
        try {
            String imageUrl = getProjectPort.get(id).getImageUrl();
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            filePort.deleteFile(fileName);
            deleteProjectPort.delete(id);
        } catch (NonExistsException e) {
            throw new DeleteException("Project not found", e);
        }
        catch (Exception e) {
            throw new DeleteException("Error while trying to delete from Database", e);
        }
    }

    @Transactional
    @Override
    public void removeAllProjects() {
        try {
            getProjectPort.getAll().forEach(project -> {
                try {
                    String imageUrl = project.getImageUrl();
                    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                    filePort.deleteFile(fileName);
                } catch (Exception e) {
                    throw new DeleteException("Error while trying to delete from Database", e);
                }
            });
            deleteProjectPort.deleteAll();
        } catch (Exception e) {
            throw new DeleteException("Error while trying to delete from Database", e);
        }
    }
}
