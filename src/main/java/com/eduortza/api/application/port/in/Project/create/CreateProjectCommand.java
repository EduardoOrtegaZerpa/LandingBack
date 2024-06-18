package com.eduortza.api.application.port.in.Project.create;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

@Data
@AllArgsConstructor
public class CreateProjectCommand {

        private String title;
        private String content;
        private String description;
        private File image;
        private String githubUrl;
}
