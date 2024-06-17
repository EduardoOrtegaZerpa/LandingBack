package com.eduortza.api.application.port.in.BlogPost.load;

import com.eduortza.api.domain.BlogPost;

public interface LoadBlogPostPort {
    BlogPost loadBlogPost(LoadBlogPostCommand command);
    Iterable<BlogPost> loadAllBlogPosts();
}
