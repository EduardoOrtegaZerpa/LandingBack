package com.eduortza.api.application.service.BlogPost;

import com.eduortza.api.application.exception.DeleteException;
import com.eduortza.api.application.exception.FileManagerException;
import com.eduortza.api.application.port.in.BlogPost.remove.RemoveBlogPostPort;
import com.eduortza.api.application.port.out.BlogPost.DeleteBlogPostPort;
import com.eduortza.api.application.port.out.BlogPost.GetBlogPostPort;
import com.eduortza.api.application.port.out.FilePort;
import com.eduortza.api.common.UseCase;
import jakarta.transaction.Transactional;

@UseCase
public class RemoveBlogPostService implements RemoveBlogPostPort {

    private final DeleteBlogPostPort deleteBlogPostPort;
    private final FilePort filePort;
    private final GetBlogPostPort getBlogPostPort;

    public RemoveBlogPostService(DeleteBlogPostPort deleteBlogPostPort, FilePort filePort, GetBlogPostPort getBlogPostPort) {
        this.deleteBlogPostPort = deleteBlogPostPort;
        this.filePort = filePort;
        this.getBlogPostPort = getBlogPostPort;
    }

    @Transactional
    @Override
    public void removeBlogPost(Long id) {
        try {
            String imageUrl = getBlogPostPort.get(id).getImageUrl();
            filePort.deleteFile(imageUrl);
            deleteBlogPostPort.delete(id);
        } catch (Exception e) {
            throw new DeleteException("Error while trying to delete from Database", e);
        }
    }

    @Transactional
    @Override
    public void removeAllBlogPosts() {
        try {
            getBlogPostPort.getAll().forEach(blogPost -> {
                try {
                    filePort.deleteFile(blogPost.getImageUrl());
                } catch (Exception e) {
                    throw new FileManagerException("Error while trying to delete image from Database", e);
                }
            });
            deleteBlogPostPort.deleteAll();
        } catch (Exception e) {
            throw new DeleteException("Error while trying to delete all from Database", e);
        }
    }

}
