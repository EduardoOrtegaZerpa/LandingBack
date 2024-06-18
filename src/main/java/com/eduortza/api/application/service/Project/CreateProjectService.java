package com.eduortza.api.application.service.Project;

import com.eduortza.api.application.exception.FileManagerException;
import com.eduortza.api.application.port.in.Project.create.CreateProjectCommand;
import com.eduortza.api.application.port.in.Project.create.CreateProjectPort;
import com.eduortza.api.application.port.out.FilePort;
import com.eduortza.api.application.port.out.Project.StoreProjectPort;
import com.eduortza.api.common.UseCase;
import com.eduortza.api.domain.Project;
import jakarta.transaction.Transactional;
import com.eduortza.api.application.exception.StoreException;

import java.util.Date;

@UseCase
public class CreateProjectService implements CreateProjectPort {

    private final StoreProjectPort storeProjectPort;
    private final FilePort filePort;

    public CreateProjectService(StoreProjectPort storeProjectPort, FilePort filePort) {
        this.storeProjectPort = storeProjectPort;
        this.filePort = filePort;
    }

    @Transactional
    @Override
    public Project createProject(CreateProjectCommand createProjectCommand) {

            Project project = new Project();

            project.setTitle(createProjectCommand.getTitle());
            project.setContent(createProjectCommand.getContent());
            project.setDescription(createProjectCommand.getDescription());
            project.setGithubUrl(createProjectCommand.getGithubUrl());
            project.setCreated(new Date());


            try {
                String fileName = filePort.saveFile(createProjectCommand.getImage(), "src/main/resources/static/images");
                project.setImageUrl("images/" + fileName);
            } catch (Exception e) {
                throw new FileManagerException("Error while trying to store image", e);
            }

            try {
                return storeProjectPort.store(project);
            } catch (Exception e) {
                throw new StoreException("Error while trying to store in Database", e);
            }
    }

}
