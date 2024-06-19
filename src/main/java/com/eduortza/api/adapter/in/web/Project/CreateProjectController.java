package com.eduortza.api.adapter.in.web.Project;

import com.eduortza.api.adapter.exception.AlreadyExistsException;
import com.eduortza.api.adapter.exception.ProjectException;
import com.eduortza.api.application.port.in.Project.create.CreateProjectCommand;
import com.eduortza.api.application.port.in.Project.create.CreateProjectPort;
import com.eduortza.api.domain.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateProjectController {

    private final CreateProjectPort createProjectPort;

    public CreateProjectController(CreateProjectPort createProjectPort) {
        this.createProjectPort = createProjectPort;
    }

    @PostMapping("/project")
    public ResponseEntity<Object> createProject(@RequestBody CreateProjectCommand createProjectCommand) {
        try {
            Project project = createProjectPort.createProject(createProjectCommand);
            return ResponseEntity.status(HttpStatus.CREATED).body(project);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ProjectException(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProjectException(e.getMessage()));
        }
    }
}
