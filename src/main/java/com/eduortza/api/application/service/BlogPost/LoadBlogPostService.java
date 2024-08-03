package com.eduortza.api.application.service.BlogPost;

import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.application.exception.LoadingException;
import com.eduortza.api.application.port.in.BlogPost.load.LoadBlogPostPort;
import com.eduortza.api.application.port.out.BlogPost.GetBlogPostPort;
import com.eduortza.api.common.UseCase;
import com.eduortza.api.domain.BlogPost;
import jakarta.transaction.Transactional;

@UseCase
public class LoadBlogPostService implements LoadBlogPostPort {

    private final GetBlogPostPort getBlogPostPort;

    public LoadBlogPostService(GetBlogPostPort getBlogPostPort) {
        this.getBlogPostPort = getBlogPostPort;
    }

    @Transactional
    @Override
    public BlogPost loadBlogPost(Long id) {
        try {
            return getBlogPostPort.get(id);
        } catch (NonExistsException e) {
            throw new NonExistsException("BlogPost with id " + id + " does not exist", e);
        } catch (Exception e) {
            throw new LoadingException("Error while trying to load from Database: " + e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public Iterable<BlogPost> loadAllBlogPosts() {
        try {
            return getBlogPostPort.getAll();
        } catch (Exception e) {
            throw new LoadingException("Error while trying to load from Database: " + e.getMessage(), e);
        }
    }
}
