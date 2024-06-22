package com.eduortza.api.adapter.in.web.Project;

import com.eduortza.api.adapter.exception.JwtAuthorizationException;
import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.exception.ProjectException;
import com.eduortza.api.adapter.out.persistence.services.JwtService;
import com.eduortza.api.application.port.in.Project.remove.RemoveProjectPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
public class RemoveProjectController {

    private final RemoveProjectPort removeProjectPort;
    private final JwtService jwtService;

    public RemoveProjectController(RemoveProjectPort removeProjectPort) {
        this.removeProjectPort = removeProjectPort;
        this.jwtService = new JwtService();
    }

    @DeleteMapping("/project/{id}")
    public ResponseEntity<Object> removeProject(@PathVariable Long id) {
        try {
            jwtService.authorizeAdminAccess();
            removeProjectPort.removeProject(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (NonExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (JwtAuthorizationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (ProjectException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/project")
    public ResponseEntity<Object> removeAllProjects() {
        try {
            jwtService.authorizeAdminAccess();
            removeProjectPort.removeAllProjects();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (NonExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (JwtAuthorizationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (ProjectException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
