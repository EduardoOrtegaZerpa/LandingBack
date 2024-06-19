package com.eduortza.api.application.port.in.Project.modify;

import com.eduortza.api.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

@Data
@AllArgsConstructor
public class ModifyProjectCommand {
    private String title;
    private String content;
    private String description;
    private File image;
    private String githubUrl;

}
