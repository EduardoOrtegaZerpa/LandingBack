package com.eduortza.api.application.service.Project;

import com.eduortza.api.application.port.in.Project.modify.ModifyProjectCommand;
import com.eduortza.api.application.port.in.Project.modify.ModifyProjectPort;
import com.eduortza.api.application.port.out.FilePort;
import com.eduortza.api.application.port.out.Project.GetProjectPort;
import com.eduortza.api.application.port.out.Project.UpdateProjectPort;
import com.eduortza.api.common.UseCase;
import com.eduortza.api.domain.Project;
import jakarta.transaction.Transactional;
import com.eduortza.api.application.exception.StoreException;

@UseCase
public class ModifyProjectService implements ModifyProjectPort {

    private final UpdateProjectPort updateProjectPort;
    private final GetProjectPort getProjectPort;
    private final FilePort filePort;

    public ModifyProjectService(UpdateProjectPort updateProjectPort, FilePort filePort, GetProjectPort getProjectPort) {
        this.updateProjectPort = updateProjectPort;
        this.filePort = filePort;
        this.getProjectPort = getProjectPort;
    }

    @Transactional
    @Override
    public void modifyProject(long id, ModifyProjectCommand modifyProjectCommand) {

            Project project = getProjectPort.get(id);

            if (project == null) {
                throw new NullPointerException("Project is null");
            }

            if (modifyProjectCommand.getTitle() != null) {
                project.setTitle(modifyProjectCommand.getTitle());
            }

            if (modifyProjectCommand.getContent() != null) {
                project.setContent(modifyProjectCommand.getContent());
            }

            if (modifyProjectCommand.getDescription() != null) {
                project.setDescription(modifyProjectCommand.getDescription());
            }

            if (modifyProjectCommand.getGithubUrl() != null) {
                project.setGithubUrl(modifyProjectCommand.getGithubUrl());
            }

            if (modifyProjectCommand.getImage() != null) {
                 try {
                     filePort.deleteFile("src/main/resources/static/" + project.getImageUrl());
                     String fileName = filePort.saveFile(modifyProjectCommand.getImage(), "src/main/resources/static/images");
                     project.setImageUrl("images/" + fileName);
                 } catch (Exception e) {
                     throw new StoreException("Error while trying to store image", e);
                 }
            }


            try {
                updateProjectPort.update(project);
            } catch (Exception e) {
                throw new StoreException("Error while trying to update in Database", e);
            }
    }
}
