package com.eduortza.api.application.port.in.BlogPost.remove;

public interface RemoveBlogPostPort {
    void removeBlogPost(RemoveBlogPostCommand removeBlogPostCommand);
    void removeAllBlogPosts();
}
