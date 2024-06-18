package com.eduortza.api.application.service.BlogPost;

import com.eduortza.api.application.exception.StoreException;
import com.eduortza.api.application.port.in.BlogPost.modify.ModifyBlogPostCommand;
import com.eduortza.api.application.port.in.BlogPost.modify.ModifyBlogPostPort;
import com.eduortza.api.application.port.out.BlogPost.UpdateBlogPostPort;
import com.eduortza.api.common.UseCase;
import com.eduortza.api.domain.BlogPost;
import jakarta.transaction.Transactional;


@UseCase
public class ModifyBlogPostService implements ModifyBlogPostPort {

    private final UpdateBlogPostPort updateBlogPostPort;

    public ModifyBlogPostService(UpdateBlogPostPort updateBlogPostPort) {
        this.updateBlogPostPort = updateBlogPostPort;

    }

    @Transactional
    @Override
    public void modifyBlogPost(ModifyBlogPostCommand modifyBlogPostCommand) {

        BlogPost blogPost = modifyBlogPostCommand.getOriginalPost();

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

        // TO DO: subir la imagen a un servidor y guardar la url


        try {
            updateBlogPostPort.update(blogPost);
        } catch (Exception e) {
            throw new StoreException("Error while trying to update in Database", e);
        }

    }

}
