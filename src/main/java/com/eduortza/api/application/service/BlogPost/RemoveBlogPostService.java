package com.eduortza.api.application.service.BlogPost;

import com.eduortza.api.adapter.exception.NonExistsException;
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
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            filePort.deleteFile(fileName);
            deleteBlogPostPort.delete(id);
        } catch (NonExistsException e) {
            throw new NonExistsException("BlogPost with id " + id + " does not exist", e);
        } catch (DeleteException e) {
            throw new DeleteException("Error while trying to delete from Database", e);
        } catch (FileManagerException e) {
            throw new FileManagerException("Error while trying to delete image from Database", e);
        } catch (Exception e) {
            throw new RuntimeException("An error has occurred: " + e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public void removeAllBlogPosts() {
        try {
            getBlogPostPort.getAll().forEach(blogPost -> {
                try {
                    String imageUrl = blogPost.getImageUrl();
                    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                    filePort.deleteFile(fileName);
                } catch (FileManagerException e) {
                    throw new FileManagerException("Error while trying to delete image from Database", e);
                }
            });
            deleteBlogPostPort.deleteAll();
        } catch (Exception e) {
            throw new DeleteException("Error while trying to delete all from Database", e);
        }
    }

}
