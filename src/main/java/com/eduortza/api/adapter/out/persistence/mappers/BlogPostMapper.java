package com.eduortza.api.adapter.out.persistence.mappers;

import com.eduortza.api.adapter.out.persistence.entities.BlogPostEntity;
import com.eduortza.api.domain.BlogPost;

public class BlogPostMapper {

    public static BlogPostEntity mapToEntity(BlogPost blogPost) {
        return new BlogPostEntity(
                blogPost.getId(),
                blogPost.getTitle(),
                blogPost.getContent(),
                blogPost.getDescription(),
                blogPost.getCreated(),
                blogPost.getMinutesToRead(),
                blogPost.getTags(),
                blogPost.getImageUrl()
        );
    }

    public static BlogPost mapToDomain(BlogPostEntity blogPostEntity) {
        return new BlogPost(
                blogPostEntity.getId(),
                blogPostEntity.getTitle(),
                blogPostEntity.getContent(),
                blogPostEntity.getDescription(),
                blogPostEntity.getCreated(),
                blogPostEntity.getMinutesToRead(),
                blogPostEntity.getTags(),
                blogPostEntity.getImageUrl()
        );
    }

}
