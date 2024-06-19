package com.eduortza.api.application.port.in.BlogPost.remove;

public interface RemoveBlogPostPort {
    void removeBlogPost(Long id);
    void removeAllBlogPosts();
}
