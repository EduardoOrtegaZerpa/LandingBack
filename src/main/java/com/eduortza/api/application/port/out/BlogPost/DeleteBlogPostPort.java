package com.eduortza.api.application.port.out.BlogPost;

import com.eduortza.api.application.exception.DeleteException;

public interface DeleteBlogPostPort {
    void delete(long id);
    void deleteAll();
}
