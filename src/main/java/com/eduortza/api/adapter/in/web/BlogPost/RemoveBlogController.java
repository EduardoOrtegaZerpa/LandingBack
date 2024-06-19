package com.eduortza.api.adapter.in.web.BlogPost;

import com.eduortza.api.application.port.in.BlogPost.remove.RemoveBlogPostPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoveBlogController {

    private final RemoveBlogPostPort removeBlogPostPort;

    public RemoveBlogController(RemoveBlogPostPort removeBlogPostPort) {
        this.removeBlogPostPort = removeBlogPostPort;
    }

    @DeleteMapping("/blog/{id}")
    public ResponseEntity<Object> removeBlogPost(@PathVariable Long id) {
        try {
            removeBlogPostPort.removeBlogPost(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/blog")
    public ResponseEntity<Object> removeBlogPost() {
        try {
            removeBlogPostPort.removeAllBlogPosts();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
