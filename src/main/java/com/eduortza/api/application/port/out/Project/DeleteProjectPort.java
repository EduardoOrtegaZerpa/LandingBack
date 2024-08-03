package com.eduortza.api.application.port.out.Project;

import com.eduortza.api.application.exception.DeleteException;

public interface DeleteProjectPort {
    void delete(long id);
    void deleteAll();
}
