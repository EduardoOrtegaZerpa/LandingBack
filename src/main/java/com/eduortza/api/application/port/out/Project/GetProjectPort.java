package com.eduortza.api.application.port.out.Project;

import com.eduortza.api.application.exception.LoadingException;
import com.eduortza.api.domain.Project;

import java.util.List;

public interface GetProjectPort {
    Project get(long id);
    List<Project> getAll();
}
