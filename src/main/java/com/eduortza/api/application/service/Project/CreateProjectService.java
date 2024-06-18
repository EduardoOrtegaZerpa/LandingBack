package com.eduortza.api.application.service.Project;

import com.eduortza.api.application.port.in.Project.create.CreateProjectCommand;
import com.eduortza.api.application.port.in.Project.create.CreateProjectPort;
import com.eduortza.api.application.port.out.Project.StoreProjectPort;
import com.eduortza.api.common.UseCase;
import com.eduortza.api.domain.Project;
import jakarta.transaction.Transactional;
import com.eduortza.api.application.exception.StoreException;

import java.util.Date;

@UseCase
public class CreateProjectService implements CreateProjectPort {

    private final StoreProjectPort storeProjectPort;

    public CreateProjectService(StoreProjectPort storeProjectPort) {
        this.storeProjectPort = storeProjectPort;
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

            //TO DO: subir la imagen a un servidor y guardar la url

            try {
                return storeProjectPort.store(project);
            } catch (Exception e) {
                throw new StoreException("Error while trying to store in Database", e);
            }
    }

}
