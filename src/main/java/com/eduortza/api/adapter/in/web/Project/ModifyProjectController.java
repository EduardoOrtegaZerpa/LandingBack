package com.eduortza.api.adapter.in.web.Project;

import com.eduortza.api.adapter.exception.ProjectException;
import com.eduortza.api.application.port.in.Project.modify.ModifyProjectCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.eduortza.api.application.port.in.Project.modify.ModifyProjectPort;

@RestController
public class ModifyProjectController {

    private final ModifyProjectPort modifyProjectPort;

    public ModifyProjectController(ModifyProjectPort modifyProjectPort) {
        this.modifyProjectPort = modifyProjectPort;
    }

    @PutMapping("/project/{id}")
    public ResponseEntity<Object> modifyProject(@PathVariable Long id, @RequestBody ModifyProjectCommand command) {
        try {
            modifyProjectPort.modifyProject(id, command);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProjectException(e.getMessage()));
        }
    }


}
