package com.eduortza.api.adapter.in.web.Project;

import com.eduortza.api.adapter.FyleSystemAdapter;
import com.eduortza.api.adapter.exception.AlreadyExistsException;
import com.eduortza.api.adapter.exception.JwtAuthorizationException;
import com.eduortza.api.adapter.exception.ProjectException;
import com.eduortza.api.adapter.out.persistence.services.JwtService;
import com.eduortza.api.application.port.in.Project.create.CreateProjectCommand;
import com.eduortza.api.application.port.in.Project.create.CreateProjectPort;
import com.eduortza.api.domain.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.logging.Logger;

@RestController
public class CreateProjectController {

    private final CreateProjectPort createProjectPort;
    private final JwtService jwtService;
    private final FyleSystemAdapter fyleSystemAdapter;

    public CreateProjectController(CreateProjectPort createProjectPort, JwtService jwtService, FyleSystemAdapter fyleSystemAdapter) {
        this.createProjectPort = createProjectPort;
        this.jwtService = jwtService;
        this.fyleSystemAdapter = fyleSystemAdapter;
    }

    @PostMapping("/project")
    public ResponseEntity<Object> createProject(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("content") String content,
            @RequestParam("githubUrl") String githubUrl,
            @RequestParam("image") MultipartFile image
    ) {
        File imageFile;
        try {
            imageFile = fyleSystemAdapter.createTempFile(image);
            CreateProjectCommand createProjectCommand = new CreateProjectCommand(title, content, description, imageFile, githubUrl);
            jwtService.authorizeAdminAccess();
            Project project = createProjectPort.createProject(createProjectCommand);
            return ResponseEntity.status(HttpStatus.CREATED).body(project);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ProjectException(e.getMessage()));
        } catch (JwtAuthorizationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ProjectException(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProjectException(e.getMessage()));
        }
    }
}
