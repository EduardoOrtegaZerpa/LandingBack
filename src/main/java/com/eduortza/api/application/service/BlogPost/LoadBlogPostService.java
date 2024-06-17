package com.eduortza.api.application.service.BlogPost;

import com.eduortza.api.application.exception.LoadingException;
import com.eduortza.api.application.port.in.BlogPost.load.LoadBlogPostCommand;
import com.eduortza.api.application.port.in.BlogPost.load.LoadBlogPostPort;
import com.eduortza.api.application.port.out.BlogPost.GetBlogPostPort;
import com.eduortza.api.domain.BlogPost;
import jakarta.transaction.Transactional;

public class LoadBlogPostService implements LoadBlogPostPort {

    private final GetBlogPostPort getBlogPostPort;

    public LoadBlogPostService(GetBlogPostPort getBlogPostPort) {
        this.getBlogPostPort = getBlogPostPort;
    }

    @Transactional
    @Override
    public BlogPost loadBlogPost(LoadBlogPostCommand command) {
        try {
            return getBlogPostPort.get(command.getId());
        } catch (Exception e) {
            throw new LoadingException("Error while trying to load from Database", e);
        }
    }

    @Transactional
    @Override
    public Iterable<BlogPost> loadAllBlogPosts() {
        try {
            return getBlogPostPort.getAll();
        } catch (Exception e) {
            throw new LoadingException("Error while trying to load from Database", e);
        }
    }
}
