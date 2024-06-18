package com.eduortza.api.application.port.in.Project.load;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoadProjectCommand {
    private Long projectId;
}
