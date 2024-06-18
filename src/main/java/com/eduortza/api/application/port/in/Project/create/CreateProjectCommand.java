package com.eduortza.api.application.port.in.Project.create;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateProjectCommand {

        private String title;
        private String content;
        private String description;
        private String imageUrl;
        private String githubUrl;
}
