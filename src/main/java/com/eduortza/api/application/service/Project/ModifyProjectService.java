package com.eduortza.api.application.service.Project;

import com.eduortza.api.application.port.in.Project.modify.ModifyProjectCommand;
import com.eduortza.api.application.port.in.Project.modify.ModifyProjectPort;
import com.eduortza.api.application.port.out.Project.UpdateProjectPort;
import com.eduortza.api.common.UseCase;
import com.eduortza.api.domain.Project;
import jakarta.transaction.Transactional;
import com.eduortza.api.application.exception.StoreException;

@UseCase
public class ModifyProjectService implements ModifyProjectPort {

    private final UpdateProjectPort updateProjectPort;

    public ModifyProjectService(UpdateProjectPort updateProjectPort) {
        this.updateProjectPort = updateProjectPort;
    }

    @Transactional
    @Override
    public void modifyProject(ModifyProjectCommand modifyProjectCommand) {

            Project project = modifyProjectCommand.getOriginalProject();

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

            // TO DO: subir la imagen a un servidor y guardar la url


            try {
                updateProjectPort.update(project);
            } catch (Exception e) {
                throw new StoreException("Error while trying to update in Database", e);
            }
    }
}
