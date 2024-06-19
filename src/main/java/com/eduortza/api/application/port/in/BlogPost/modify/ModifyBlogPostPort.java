package com.eduortza.api.application.port.in.BlogPost.modify;

public interface ModifyBlogPostPort {
    void modifyBlogPost(long id, ModifyBlogPostCommand modifyBlogPostCommand);
}
