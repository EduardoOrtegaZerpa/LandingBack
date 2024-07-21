package com.eduortza.api.adapter.in.web.Trajectory;

import com.eduortza.api.adapter.exception.JwtAuthorizationException;
import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.exception.TrajectoryException;
import com.eduortza.api.adapter.out.persistence.services.JwtService;
import com.eduortza.api.application.port.in.Trajectory.modify.ModifyTrajectoryCommand;
import com.eduortza.api.application.port.in.Trajectory.modify.ModifyTrajectoryPort;
import com.eduortza.api.domain.Trajectory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class ModifyTrajectoryController {

    private final ModifyTrajectoryPort modifyTrajectoryPort;
    private final JwtService jwtService;

    public ModifyTrajectoryController(ModifyTrajectoryPort modifyTrajectoryPort, JwtService jwtService) {
        this.modifyTrajectoryPort = modifyTrajectoryPort;
        this.jwtService = jwtService;
    }

    @PutMapping("/trajectory/{id}")
    public ResponseEntity<Object> modifyTrajectory(
            @PathVariable long id,
            @RequestParam(value = "content") String content
    ) {
        var response = new HashMap<String, Object>();
        try {
            jwtService.authorizeAdminAccess();
            ModifyTrajectoryCommand modifyTrajectoryCommand = new ModifyTrajectoryCommand(id, content);
            Trajectory trajectory = modifyTrajectoryPort.modifyTrajectory(modifyTrajectoryCommand);
            response.put("message", "Trajectory modified successfully");
            response.put("trajectory", trajectory);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NonExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (JwtAuthorizationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TrajectoryException(e.getMessage()));
        }
    }
}
