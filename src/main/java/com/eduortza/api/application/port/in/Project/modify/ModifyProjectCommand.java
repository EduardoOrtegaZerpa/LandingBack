package com.eduortza.api.application.port.in.Project.modify;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModifyProjectCommand {
    private long id;
    private String title;
    private String content;
    private String description;
    private String imageUrl;
    private String githubUrl;

}
