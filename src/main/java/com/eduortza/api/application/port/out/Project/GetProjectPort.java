package com.eduortza.api.application.port.out.Project;

import com.eduortza.api.domain.Project;

import java.util.List;

public interface GetProjectPort {
    Project get(long id) throws Exception;
    List<Project> getAll() throws Exception;
}
