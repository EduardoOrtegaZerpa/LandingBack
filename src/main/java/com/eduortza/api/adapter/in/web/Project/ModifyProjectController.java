package com.eduortza.api.adapter.in.web.Project;

import com.eduortza.api.adapter.FyleSystemAdapter;
import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.exception.ProjectException;
import com.eduortza.api.adapter.out.persistence.services.JwtService;
import com.eduortza.api.application.port.in.Project.modify.ModifyProjectCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.eduortza.api.application.port.in.Project.modify.ModifyProjectPort;
import com.eduortza.api.adapter.exception.JwtAuthorizationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;

@RestController
public class ModifyProjectController {

    private final ModifyProjectPort modifyProjectPort;
    private final JwtService jwtService;
    private final FyleSystemAdapter fyleSystemAdapter;

    public ModifyProjectController(ModifyProjectPort modifyProjectPort, JwtService jwtService, FyleSystemAdapter fyleSystemAdapter) {
        this.modifyProjectPort = modifyProjectPort;
        this.jwtService = jwtService;
        this.fyleSystemAdapter = fyleSystemAdapter;
    }

    @PutMapping("/project/{id}")
    public ResponseEntity<Object> modifyProject(
            @PathVariable Long id,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "githubUrl", required = false) String githubUrl,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        var response = new HashMap<String, String>();
        File imageFile;
        try {
            if (image != null) {
                imageFile = fyleSystemAdapter.createTempFile(image);
            } else {
                imageFile = null;
            }
            ModifyProjectCommand command = new ModifyProjectCommand(title, content, description, imageFile, githubUrl);
            jwtService.authorizeAdminAccess();
            modifyProjectPort.modifyProject(id, command);
            response.put("message", "Project modified successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NonExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (JwtAuthorizationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (ProjectException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
