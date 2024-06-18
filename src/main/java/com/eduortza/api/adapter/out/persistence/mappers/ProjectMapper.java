package com.eduortza.api.adapter.out.persistence.mappers;

import com.eduortza.api.adapter.out.persistence.entities.ProjectEntity;
import com.eduortza.api.domain.Project;

public class ProjectMapper {

        public static ProjectEntity mapToEntity(Project project) {
            return new ProjectEntity(
                    project.getId(),
                    project.getTitle(),
                    project.getContent(),
                    project.getDescription(),
                    project.getCreated(),
                    project.getImageUrl(),
                    project.getGithubUrl()
            );
        }

        public static Project mapToDomain(ProjectEntity projectEntity) {
            return new Project(
                    projectEntity.getId(),
                    projectEntity.getTitle(),
                    projectEntity.getContent(),
                    projectEntity.getDescription(),
                    projectEntity.getCreated(),
                    projectEntity.getImageUrl(),
                    projectEntity.getGithubUrl()
            );
        }
}
