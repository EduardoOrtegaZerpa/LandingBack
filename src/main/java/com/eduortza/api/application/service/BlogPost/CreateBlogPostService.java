package com.eduortza.api.application.service.BlogPost;

import com.eduortza.api.application.exception.FileManagerException;
import com.eduortza.api.application.exception.StoreException;
import com.eduortza.api.application.port.in.BlogPost.create.CreateBlogPostCommand;
import com.eduortza.api.application.port.in.BlogPost.create.CreateBlogPostPort;
import com.eduortza.api.application.port.out.BlogPost.StoreBlogPostPort;
import com.eduortza.api.application.port.out.FilePort;
import com.eduortza.api.domain.BlogPost;
import jakarta.transaction.Transactional;

import java.util.Date;

import com.eduortza.api.common.UseCase;

@UseCase
public class CreateBlogPostService implements CreateBlogPostPort {

    private final StoreBlogPostPort storeBlogPostPort;
    private final FilePort filePort;


    public CreateBlogPostService(StoreBlogPostPort storeBlogPostPort, FilePort filePort) {
        this.storeBlogPostPort = storeBlogPostPort;
        this.filePort = filePort;
    }

    @Transactional
    @Override
    public BlogPost createBlogPost(CreateBlogPostCommand createBlogPostCommand) {

        BlogPost blogPost = new BlogPost();

        blogPost.setTitle(createBlogPostCommand.getTitle());
        blogPost.setContent(createBlogPostCommand.getContent());
        blogPost.setDescription(createBlogPostCommand.getDescription());
        blogPost.setMinutesToRead(createBlogPostCommand.getMinutesToRead());
        blogPost.setCreated(new Date());

        try{
            String fileName = filePort.saveFile(createBlogPostCommand.getImage(), "src/main/resources/static/images");
            blogPost.setImageUrl("http://localhost:8080/images/" + fileName);
        } catch (Exception e) {
            throw new FileManagerException("Error while trying to store image", e);
        }

        try {
            return storeBlogPostPort.store(blogPost);
        } catch (Exception e) {
            throw new StoreException("Error while trying to store in Database", e);
        }

    }
}
