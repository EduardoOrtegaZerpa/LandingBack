package com.eduortza.api.adapter.out.persistence.repository;

import com.eduortza.api.adapter.out.persistence.entities.BlogPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringBlogRepository extends JpaRepository<BlogPostEntity, Long> {
}
