package com.eduortza.api.adapter.in.web.Project;

import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.exception.ProjectException;
import com.eduortza.api.adapter.out.persistence.services.JwtService;
import com.eduortza.api.application.port.in.Project.modify.ModifyProjectCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.eduortza.api.application.port.in.Project.modify.ModifyProjectPort;
import com.eduortza.api.adapter.exception.JwtAuthorizationException;

@RestController
public class ModifyProjectController {

    private final ModifyProjectPort modifyProjectPort;
    private final JwtService jwtService;

    public ModifyProjectController(ModifyProjectPort modifyProjectPort, JwtService jwtService) {
        this.modifyProjectPort = modifyProjectPort;
        this.jwtService = jwtService;
    }

    @PutMapping("/project/{id}")
    public ResponseEntity<Object> modifyProject(@PathVariable Long id, @RequestBody ModifyProjectCommand command) {
        try {
            jwtService.authorizeAdminAccess();
            modifyProjectPort.modifyProject(id, command);
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
