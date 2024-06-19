package com.eduortza.api.application.port.out.BlogPost;

import com.eduortza.api.domain.BlogPost;

import java.util.List;

public interface GetBlogPostPort {
    BlogPost get(long id);
    List<BlogPost> getAll();
}
