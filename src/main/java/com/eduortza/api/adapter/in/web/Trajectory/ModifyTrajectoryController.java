package com.eduortza.api.adapter.in.web.Trajectory;

import com.eduortza.api.application.port.in.Trajectory.modify.ModifyTrajectoryCommand;
import com.eduortza.api.application.port.in.Trajectory.modify.ModifyTrajectoryPort;
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

    public ModifyTrajectoryController(ModifyTrajectoryPort modifyTrajectoryPort) {
        this.modifyTrajectoryPort = modifyTrajectoryPort;
    }

    @PutMapping("/trajectory/{id}")
    public ResponseEntity<Object> modifyTrajectory(
            @PathVariable long id,
            @RequestParam(value = "content") String content
    ) {
        var response = new HashMap<String, String>();
        try {
            ModifyTrajectoryCommand modifyTrajectoryCommand = new ModifyTrajectoryCommand(id, content);
            modifyTrajectoryPort.modifyTrajectory(modifyTrajectoryCommand);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Exception(e.getMessage()));
        }
    }
}
