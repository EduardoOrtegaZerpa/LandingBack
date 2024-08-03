package com.eduortza.api.application.service.BlogPost;

import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.application.exception.DeleteException;
import com.eduortza.api.application.exception.FileManagerException;
import com.eduortza.api.application.exception.LoadingException;
import com.eduortza.api.application.exception.StoreException;
import com.eduortza.api.application.port.in.BlogPost.modify.ModifyBlogPostCommand;
import com.eduortza.api.application.port.in.BlogPost.modify.ModifyBlogPostPort;
import com.eduortza.api.application.port.out.BlogPost.GetBlogPostPort;
import com.eduortza.api.application.port.out.BlogPost.UpdateBlogPostPort;
import com.eduortza.api.application.port.out.FilePort;
import com.eduortza.api.common.UseCase;
import com.eduortza.api.domain.BlogPost;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;


@UseCase
public class ModifyBlogPostService implements ModifyBlogPostPort {

    private final UpdateBlogPostPort updateBlogPostPort;
    private final FilePort filePort;
    private final GetBlogPostPort getBlogPostPort;

    @Value("${app.image.base.url}")
    private String imageBaseUrl;

    public ModifyBlogPostService(UpdateBlogPostPort updateBlogPostPort, FilePort filePort, GetBlogPostPort getBlogPostPort) {
        this.updateBlogPostPort = updateBlogPostPort;
        this.filePort = filePort;
        this.getBlogPostPort = getBlogPostPort;
    }

    @Transactional
    @Override
    public void modifyBlogPost(long id, ModifyBlogPostCommand modifyBlogPostCommand) {

        BlogPost blogPost;

        try {
            blogPost = getBlogPostPort.get(id);
        } catch (Exception e) {
            throw new LoadingException("Error while trying to get from Database", e);
        }

        if (blogPost == null) {
            throw new NullPointerException("BlogPost is null");
        }

        if (modifyBlogPostCommand.getTitle() != null) {
            blogPost.setTitle(modifyBlogPostCommand.getTitle());
        }

        if (modifyBlogPostCommand.getContent() != null) {
            blogPost.setContent(modifyBlogPostCommand.getContent());
        }

        if (modifyBlogPostCommand.getDescription() != null) {
            blogPost.setDescription(modifyBlogPostCommand.getDescription());
        }

        if (modifyBlogPostCommand.getMinutesToRead() != null) {
            blogPost.setMinutesToRead(modifyBlogPostCommand.getMinutesToRead());
        }

        if (modifyBlogPostCommand.getTags() != null) {
            blogPost.setTags(modifyBlogPostCommand.getTags());
        }

        if (modifyBlogPostCommand.getImage() != null) {
            try {
                String fileName = blogPost.getImageUrl().substring(blogPost.getImageUrl().lastIndexOf("/") + 1);
                filePort.deleteFile(fileName);
                String fileNameCommand = filePort.saveFile(modifyBlogPostCommand.getImage());
                String imageUrl = imageBaseUrl + fileNameCommand;
                blogPost.setImageUrl(imageUrl);
            } catch (FileManagerException e) {
                throw new FileManagerException("Error while trying to save/delete image " + e.getMessage(), e);
            } catch (Exception e) {
                throw new RuntimeException("An error has occurred: " + e.getMessage(), e);
            }

        }


        try {
            updateBlogPostPort.update(blogPost);
        } catch (Exception e) {
            throw new StoreException("Error while trying to store in database", e);
        }

    }

}
