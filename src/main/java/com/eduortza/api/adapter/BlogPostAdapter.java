package com.eduortza.api.adapter;

import com.eduortza.api.adapter.exception.AlreadyExistsException;
import com.eduortza.api.adapter.out.persistence.entities.BlogPostEntity;
import com.eduortza.api.adapter.out.persistence.mappers.BlogPostMapper;
import com.eduortza.api.adapter.out.persistence.repository.SpringBlogRepository;
import com.eduortza.api.application.port.out.BlogPost.DeleteBlogPostPort;
import com.eduortza.api.application.port.out.BlogPost.GetBlogPostPort;
import com.eduortza.api.application.port.out.BlogPost.StoreBlogPostPort;
import com.eduortza.api.application.port.out.BlogPost.UpdateBlogPostPort;
import com.eduortza.api.domain.BlogPost;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlogPostAdapter implements StoreBlogPostPort, UpdateBlogPostPort, DeleteBlogPostPort, GetBlogPostPort {

    private final SpringBlogRepository springBlogRepository;

    public BlogPostAdapter(SpringBlogRepository springBlogRepository) {
        this.springBlogRepository = springBlogRepository;
    }
    
    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public BlogPost get(Long id) {
        return null;
    }

    @Override
    public List<BlogPost> getAll() {
        return List.of();
    }

    @Override
    public BlogPost store(BlogPost blogPost) {

        if (blogPost == null) {
            throw new RuntimeException("BlogPost is null");
        }

        if (springBlogRepository.existsById(blogPost.getId())) {
            throw new AlreadyExistsException("BlogPost with id " + blogPost.getId() + " already exists");
        }

        BlogPostEntity blogPostEntity = springBlogRepository.save(BlogPostMapper.mapToEntity(blogPost));
        return BlogPostMapper.mapToDomain(blogPostEntity);
    }

    @Override
    public void update(BlogPost blogPost) {

    }
}
