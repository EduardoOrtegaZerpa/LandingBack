package com.eduortza.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private long id;
    private String title;
    private String content;
    private String description;
    private Date created;
    private String imageUrl;
    private String githubUrl;

    public boolean isValidGithubUrl() {
        return githubUrl.startsWith("https://github.com/");
    }
}
