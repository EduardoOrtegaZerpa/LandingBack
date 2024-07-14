package com.eduortza.api.adapter.in.web.Trajectory;

import com.eduortza.api.application.port.in.Trajectory.load.LoadTrajectoryPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoadTrajectoryController {

    private final LoadTrajectoryPort loadTrajectoryPort;

    public LoadTrajectoryController(LoadTrajectoryPort loadTrajectoryPort) {
        this.loadTrajectoryPort = loadTrajectoryPort;
    }

    @GetMapping("/trajectory/{id}")
    public ResponseEntity<Object> loadTrajectory(@PathVariable long id) {
        try {
            var trajectory = loadTrajectoryPort.loadTrajectory(id);
            return ResponseEntity.ok(trajectory);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Exception(e.getMessage()));
        }
    }
}
