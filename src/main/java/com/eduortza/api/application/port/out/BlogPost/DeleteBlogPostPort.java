package com.eduortza.api.application.port.out.BlogPost;

public interface DeleteBlogPostPort {
    void delete(Long id);
    void deleteAll();
}
