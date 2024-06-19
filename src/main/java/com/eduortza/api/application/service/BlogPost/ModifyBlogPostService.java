package com.eduortza.api.application.service.BlogPost;

import com.eduortza.api.application.exception.StoreException;
import com.eduortza.api.application.port.in.BlogPost.modify.ModifyBlogPostCommand;
import com.eduortza.api.application.port.in.BlogPost.modify.ModifyBlogPostPort;
import com.eduortza.api.application.port.out.BlogPost.GetBlogPostPort;
import com.eduortza.api.application.port.out.BlogPost.UpdateBlogPostPort;
import com.eduortza.api.application.port.out.FilePort;
import com.eduortza.api.common.UseCase;
import com.eduortza.api.domain.BlogPost;
import jakarta.transaction.Transactional;

import java.io.File;
import java.util.SplittableRandom;


@UseCase
public class ModifyBlogPostService implements ModifyBlogPostPort {

    private final UpdateBlogPostPort updateBlogPostPort;
    private final FilePort filePort;
    private final GetBlogPostPort getBlogPostPort;

    public ModifyBlogPostService(UpdateBlogPostPort updateBlogPostPort, FilePort filePort, GetBlogPostPort getBlogPostPort) {
        this.updateBlogPostPort = updateBlogPostPort;
        this.filePort = filePort;
        this.getBlogPostPort = getBlogPostPort;
    }

    @Transactional
    @Override
    public void modifyBlogPost(long id, ModifyBlogPostCommand modifyBlogPostCommand) {

        BlogPost blogPost = getBlogPostPort.get(id);

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

        if (modifyBlogPostCommand.getImage() != null) {
            try {
                filePort.deleteFile("src/main/resources/static/" + blogPost.getImageUrl());
                String fileName = filePort.saveFile(modifyBlogPostCommand.getImage(), "src/main/resources/static/images");
                blogPost.setImageUrl("images/" + fileName);
            } catch (Exception e) {
                throw new StoreException("Error while trying to store image", e);
            }
        }


        try {
            updateBlogPostPort.update(blogPost);
        } catch (Exception e) {
            throw new StoreException("Error while trying to update in Database", e);
        }

    }

}
