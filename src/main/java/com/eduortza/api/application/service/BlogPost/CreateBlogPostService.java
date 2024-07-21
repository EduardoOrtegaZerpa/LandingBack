package com.eduortza.api.application.service.BlogPost;

import com.eduortza.api.adapter.exception.AlreadyExistsException;
import com.eduortza.api.adapter.in.web.User.LoadUserController;
import com.eduortza.api.application.exception.FileManagerException;
import com.eduortza.api.application.exception.StoreException;
import com.eduortza.api.application.port.in.BlogPost.create.CreateBlogPostCommand;
import com.eduortza.api.application.port.in.BlogPost.create.CreateBlogPostPort;
import com.eduortza.api.application.port.out.BlogPost.StoreBlogPostPort;
import com.eduortza.api.application.port.out.FilePort;
import com.eduortza.api.application.port.out.MailPort;
import com.eduortza.api.application.port.out.MailSuscriber.GetMailSubscriberPort;
import com.eduortza.api.domain.BlogPost;
import com.eduortza.api.domain.MailSuscriber;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;

import com.eduortza.api.common.UseCase;
import org.springframework.beans.factory.annotation.Value;

@UseCase
public class CreateBlogPostService implements CreateBlogPostPort {

    private final StoreBlogPostPort storeBlogPostPort;
    private final FilePort filePort;
    private final MailPort mailPort;
    private final GetMailSubscriberPort getMailSubscriberPort;

    @Value("${app.image.base.url}")
    private String imageBaseUrl;


    public CreateBlogPostService(
            StoreBlogPostPort storeBlogPostPort,
            FilePort filePort,
            GetMailSubscriberPort getMailSubscriberPort,
            MailPort mailPort) {
        this.storeBlogPostPort = storeBlogPostPort;
        this.filePort = filePort;
        this.getMailSubscriberPort = getMailSubscriberPort;
        this.mailPort = mailPort;
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
            String imageUrl = imageBaseUrl + fileName;
            blogPost.setImageUrl(imageUrl);
        } catch (Exception e) {
            throw new FileManagerException("Error while trying to store image", e);
        }

        try {
            BlogPost storedBlogPost = storeBlogPostPort.store(blogPost);
            List<MailSuscriber> mailSuscribers = getMailSubscriberPort.getAllMailSuscriber();
            mailPort.sendMailToAllSubscribers(mailSuscribers, "New Blog Post: " + storedBlogPost.getTitle());
            return storedBlogPost;
        } catch (AlreadyExistsException e) {
            throw new StoreException("Error while trying to store in Database", e);
        } catch (Exception e) {
            throw new StoreException("Error while trying to store in Database", e);
        }

    }
}
