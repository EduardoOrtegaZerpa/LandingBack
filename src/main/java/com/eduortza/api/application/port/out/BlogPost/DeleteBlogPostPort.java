package com.eduortza.api.application.port.out.BlogPost;

public interface DeleteBlogPostPort {
    void delete(Long id) throws Exception;
    void deleteAll() throws Exception;
}
