package com.eduortza.api.application.port.out.Project;

public interface DeleteProjectPort {
    void delete(long id) throws Exception;
    void deleteAll() throws Exception;
}
