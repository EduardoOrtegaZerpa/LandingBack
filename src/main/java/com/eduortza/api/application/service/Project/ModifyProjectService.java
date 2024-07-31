package com.eduortza.api.application.service.Project;

import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.application.exception.FileManagerException;
import com.eduortza.api.application.exception.LoadingException;
import com.eduortza.api.application.port.in.Project.modify.ModifyProjectCommand;
import com.eduortza.api.application.port.in.Project.modify.ModifyProjectPort;
import com.eduortza.api.application.port.out.FilePort;
import com.eduortza.api.application.port.out.Project.GetProjectPort;
import com.eduortza.api.application.port.out.Project.UpdateProjectPort;
import com.eduortza.api.common.UseCase;
import com.eduortza.api.domain.Project;
import jakarta.transaction.Transactional;
import com.eduortza.api.application.exception.StoreException;
import org.springframework.beans.factory.annotation.Value;

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

    @Value("${app.image.base.url}")
    private String imageBaseUrl;

    @Transactional
    @Override
    public void modifyProject(long id, ModifyProjectCommand modifyProjectCommand) {

            Project project;

            try {
                project = getProjectPort.get(id);
            } catch (Exception e) {
                throw new LoadingException("Error while trying to get from Database", e);
            }

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
                     String fileName = project.getImageUrl().substring(project.getImageUrl().lastIndexOf("/") + 1);
                     filePort.deleteFile(fileName);
                     String fileNameCommand = filePort.saveFile(modifyProjectCommand.getImage());
                     String imageUrl = imageBaseUrl + fileNameCommand;
                     project.setImageUrl(imageUrl);
                 } catch (Exception e) {
                     throw new FileManagerException("Error while trying to store image", e);
                 }
            }


            try {
                updateProjectPort.update(project);
            } catch (NonExistsException e) {
                throw new StoreException("Project not found", e);
            }
            catch (Exception e) {
                throw new StoreException("Error while trying to update in Database", e);
            }
    }
}
