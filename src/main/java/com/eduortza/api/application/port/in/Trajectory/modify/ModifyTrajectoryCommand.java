package com.eduortza.api.application.port.in.Trajectory.modify;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModifyTrajectoryCommand {
    private long id;
    private String content;
}
