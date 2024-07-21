package com.eduortza.api.adapter.in.web.Trajectory;

import com.eduortza.api.adapter.exception.BlogPostException;
import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.exception.TrajectoryException;
import com.eduortza.api.application.port.in.Trajectory.load.LoadTrajectoryPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoadTrajectoryController {

    private final LoadTrajectoryPort loadTrajectoryPort;

    public LoadTrajectoryController(LoadTrajectoryPort loadTrajectoryPort) {
        this.loadTrajectoryPort = loadTrajectoryPort;
    }

    @GetMapping("/trajectory")
    public ResponseEntity<Object> loadTrajectory() {
        try {
            var trajectory = loadTrajectoryPort.loadTrajectory();
            return ResponseEntity.ok(trajectory);
        }  catch (NonExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new TrajectoryException(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new TrajectoryException(e.getMessage()));
        }
    }
}
