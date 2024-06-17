package com.eduortza.api.application.port.in.BlogPost.create;

import com.eduortza.api.domain.BlogPost;

public interface CreateBlogPostPort {
    BlogPost createBlogPost(CreateBlogPostCommand createBlogPostCommand);
}
