package com.eduortza.api.application.port.out.BlogPost;

public interface DeleteBlogPostPort {
    void delete(long id) throws Exception;
    void deleteAll() throws Exception;
}
