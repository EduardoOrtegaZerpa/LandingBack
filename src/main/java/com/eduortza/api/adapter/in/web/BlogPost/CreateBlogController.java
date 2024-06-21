package com.eduortza.api.adapter.in.web.BlogPost;


import com.eduortza.api.adapter.exception.AlreadyExistsException;
import com.eduortza.api.adapter.exception.BlogPostException;
import com.eduortza.api.adapter.exception.JwtAuthorizationException;
import com.eduortza.api.adapter.out.persistence.services.JwtService;
import com.eduortza.api.application.port.in.BlogPost.create.CreateBlogPostCommand;
import com.eduortza.api.application.port.in.BlogPost.create.CreateBlogPostPort;
import com.eduortza.api.domain.BlogPost;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CreateBlogController {

    private final CreateBlogPostPort createBlogPostPort;
    private final JwtService jwtService;

    public CreateBlogController(CreateBlogPostPort createBlogPostPort) {
        this.createBlogPostPort = createBlogPostPort;
        this.jwtService = new JwtService();
    }

    @PostMapping("/blog")
    public ResponseEntity<Object> createBlogPost(@RequestBody CreateBlogPostCommand createBlogPostCommand) {
        try {
            jwtService.authorizeAdminAccess();

            BlogPost blogPost= createBlogPostPort.createBlogPost(createBlogPostCommand);
            return ResponseEntity.status(HttpStatus.CREATED).body(blogPost);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new BlogPostException(e.getMessage()));
        } catch (JwtAuthorizationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new BlogPostException(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BlogPostException(e.getMessage()));
        }
    }
}
