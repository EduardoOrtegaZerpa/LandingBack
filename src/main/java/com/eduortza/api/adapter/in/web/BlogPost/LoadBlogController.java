package com.eduortza.api.adapter.in.web.BlogPost;

import com.eduortza.api.adapter.exception.BlogPostException;
import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.domain.BlogPost;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.eduortza.api.application.port.in.BlogPost.load.LoadBlogPostPort;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
public class LoadBlogController {

    private final LoadBlogPostPort loadBlogPostPort;

    public LoadBlogController(LoadBlogPostPort loadBlogPostPort) {
        this.loadBlogPostPort = loadBlogPostPort;
    }

    @GetMapping("/blog/{id}")
    public ResponseEntity<Object> loadBlogPost(@PathVariable Long id) {
        try {
            BlogPost blogPost= loadBlogPostPort.loadBlogPost(id);
            return ResponseEntity.status(HttpStatus.OK).body(blogPost);
        } catch (NonExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BlogPostException(e.getMessage()));
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BlogPostException(e.getMessage()));
        }
    }

    @GetMapping("/blog")
    public ResponseEntity<Object> loadAllBlogPosts() {
        try {
            Iterable<BlogPost> blogPostIterator = loadBlogPostPort.loadAllBlogPosts();
            List<BlogPost> blogPostList = StreamSupport.stream(blogPostIterator.spliterator(), false).toList();

            return ResponseEntity.status(HttpStatus.OK).body(blogPostList);
        } catch (NonExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BlogPostException(e.getMessage()));
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BlogPostException(e.getMessage()));
        }
    }


}
