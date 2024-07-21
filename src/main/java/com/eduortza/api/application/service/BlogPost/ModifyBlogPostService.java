package com.eduortza.api.application.service.BlogPost;

import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.in.web.User.LoadUserController;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@UseCase
public class ModifyBlogPostService implements ModifyBlogPostPort {

    private final UpdateBlogPostPort updateBlogPostPort;
    private final FilePort filePort;
    private final GetBlogPostPort getBlogPostPort;
    private static final Logger logger = LoggerFactory.getLogger(ModifyBlogPostService.class);

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
                filePort.deleteFile("src/main/resources/static/images/" + fileName);
                String fileNameCommand = filePort.saveFile(modifyBlogPostCommand.getImage(), "src/main/resources/static/images");
                blogPost.setImageUrl("http://localhost:8080/images/" + fileNameCommand);
            } catch (Exception e) {
                throw new FileManagerException("Error while trying to store image", e);
            }
        }


        try {
            updateBlogPostPort.update(blogPost);
        } catch (NonExistsException e) {
            throw new StoreException("Error while trying to update in Database", e);
        } catch (Exception e) {
            throw new StoreException("Error while trying to update in Database", e);
        }

    }

}
