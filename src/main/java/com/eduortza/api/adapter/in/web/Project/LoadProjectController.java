package com.eduortza.api.adapter.in.web.Project;

import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.exception.ProjectException;
import com.eduortza.api.application.port.in.Project.load.LoadProjectPort;
import com.eduortza.api.domain.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
public class LoadProjectController {

    private final LoadProjectPort loadProjectPort;

    public LoadProjectController(LoadProjectPort loadProjectPort) {
        this.loadProjectPort = loadProjectPort;
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<Object> loadProject(@PathVariable Long id) {
        try {
            Project project = loadProjectPort.loadProject(id);
            return ResponseEntity.status(HttpStatus.OK).body(project);
        } catch (NonExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProjectException(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProjectException(e.getMessage()));
        }
    }

    @GetMapping("/project")
    public ResponseEntity<Object> loadAllProjects() {
        try {
            Iterable<Project> projectIterator = loadProjectPort.loadAllProjects();
            List<Project> projectList = StreamSupport.stream(projectIterator.spliterator(), false).toList();

            return ResponseEntity.status(HttpStatus.OK).body(projectList);
        } catch (NonExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProjectException(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProjectException(e.getMessage()));
        }
    }
}
