package com.eduortza.api.application.port.out.Project;

import com.eduortza.api.application.exception.StoreException;
import com.eduortza.api.domain.Project;

public interface StoreProjectPort {
    Project store(Project project);
}
