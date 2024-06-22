package com.eduortza.api.adapter.in.web.BlogPost;

import com.eduortza.api.adapter.exception.JwtAuthorizationException;
import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.out.persistence.services.JwtService;
import com.eduortza.api.application.port.in.BlogPost.remove.RemoveBlogPostPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoveBlogController {

    private final RemoveBlogPostPort removeBlogPostPort;
    private final JwtService jwtService;

    public RemoveBlogController(RemoveBlogPostPort removeBlogPostPort, JwtService jwtService) {
        this.removeBlogPostPort = removeBlogPostPort;
        this.jwtService = jwtService;
    }

    @DeleteMapping("/blog/{id}")
    public ResponseEntity<Object> removeBlogPost(@PathVariable Long id) {
        try {
            jwtService.authorizeAdminAccess();
            removeBlogPostPort.removeBlogPost(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NonExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (JwtAuthorizationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/blog")
    public ResponseEntity<Object> removeBlogPost() {
        try {
            jwtService.authorizeAdminAccess();
            removeBlogPostPort.removeAllBlogPosts();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (JwtAuthorizationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (NonExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
