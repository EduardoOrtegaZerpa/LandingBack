package com.eduortza.api.adapter;

import com.eduortza.api.adapter.exception.AlreadyExistsException;
import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.out.persistence.entities.BlogPostEntity;
import com.eduortza.api.adapter.out.persistence.mappers.BlogPostMapper;
import com.eduortza.api.adapter.out.persistence.repository.SpringBlogRepository;
import com.eduortza.api.application.exception.DeleteException;
import com.eduortza.api.application.exception.StoreException;
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
    public void delete(long id) throws NonExistsException, DeleteException {
        if (!springBlogRepository.existsById(id)) {
            throw new NonExistsException("BlogPost with id " + id + " does not exist");
        }

        try {
            springBlogRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeleteException("Error deleting BlogPost entity with id " + id);
        }
    }

    @Override
    public void deleteAll() throws DeleteException {
        try{
            springBlogRepository.deleteAll();
        } catch (Exception e) {
            throw new DeleteException("Error deleting all BlogPost entities");
        }
    }

    @Override
    public BlogPost get(long id) throws NonExistsException {
        return springBlogRepository.findById(id)
                .map(BlogPostMapper::mapToDomain)
                .orElseThrow(() -> new NonExistsException("BlogPost with id " + id + " does not exist"));
    }

    @Override
    public List<BlogPost> getAll() {
       return springBlogRepository.findAll().stream()
               .map(BlogPostMapper::mapToDomain)
               .toList();
    }

    @Override
    public BlogPost store(BlogPost blogPost) throws AlreadyExistsException, StoreException {

        if (blogPost == null) {
            throw new NullPointerException("BlogPost is null");
        }

        if (springBlogRepository.existsById(blogPost.getId())) {
            throw new AlreadyExistsException("BlogPost with id " + blogPost.getId() + " already exists");
        }

        BlogPostEntity blogPostEntity;
        try {
            blogPostEntity = springBlogRepository.save(BlogPostMapper.mapToEntity(blogPost));
        } catch (Exception e) {
            throw new StoreException("Error storing BlogPost");
        }
        return BlogPostMapper.mapToDomain(blogPostEntity);
    }

    @Override
    public void update(BlogPost blogPost) throws NonExistsException, StoreException {
        if (blogPost == null) {
            throw new NullPointerException("BlogPost is null");
        }

        if (!springBlogRepository.existsById(blogPost.getId())) {
            throw new NonExistsException("BlogPost with id " + blogPost.getId() + " does not exist");
        }

        try{
            springBlogRepository.save(BlogPostMapper.mapToEntity(blogPost));
        } catch (Exception e) {
            throw new StoreException("Error updating BlogPost");
        }
    }
}
