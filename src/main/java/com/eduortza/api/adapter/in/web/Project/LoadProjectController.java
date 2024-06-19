package com.eduortza.api.adapter.in.web.Project;

import com.eduortza.api.adapter.exception.ProjectException;
import com.eduortza.api.application.port.in.Project.load.LoadProjectPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoadProjectController {

    private final LoadProjectPort loadProjectPort;

    public LoadProjectController(LoadProjectPort loadProjectPort) {
        this.loadProjectPort = loadProjectPort;
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<Object> loadProject(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(loadProjectPort.loadProject(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProjectException(e.getMessage()));
        }
    }

    @GetMapping("/project")
    public ResponseEntity<Object> loadAllProjects() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(loadProjectPort.loadAllProjects());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProjectException(e.getMessage()));
        }
    }
}
