package com.eduortza.api.application.service.BlogPost;

import com.eduortza.api.application.exception.DeleteException;
import com.eduortza.api.application.port.in.BlogPost.remove.RemoveBlogPostCommand;
import com.eduortza.api.application.port.in.BlogPost.remove.RemoveBlogPostPort;
import com.eduortza.api.application.port.out.BlogPost.DeleteBlogPostPort;
import jakarta.transaction.Transactional;

public class RemoveBlogPostService implements RemoveBlogPostPort {

    private final DeleteBlogPostPort deleteBlogPostPort;

    public RemoveBlogPostService(DeleteBlogPostPort deleteBlogPostPort) {
        this.deleteBlogPostPort = deleteBlogPostPort;
    }

    @Transactional
    @Override
    public void removeBlogPost(RemoveBlogPostCommand removeBlogPostCommand) {
        Long id = removeBlogPostCommand.getId();
        try {
            deleteBlogPostPort.delete(id);
        } catch (Exception e) {
            throw new DeleteException("Error while trying to delete from Database", e);
        }
    }

    @Transactional
    @Override
    public void removeAllBlogPosts() {
        try {
            deleteBlogPostPort.deleteAll();
        } catch (Exception e) {
            throw new DeleteException("Error while trying to delete all from Database", e);
        }
    }

}
