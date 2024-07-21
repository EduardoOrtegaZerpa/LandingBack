package com.eduortza.api.adapter.in.web.BlogPost;


import com.eduortza.api.adapter.FyleSystemAdapter;
import com.eduortza.api.adapter.exception.AlreadyExistsException;
import com.eduortza.api.adapter.exception.BlogPostException;
import com.eduortza.api.adapter.exception.JwtAuthorizationException;
import com.eduortza.api.adapter.out.persistence.services.JwtService;
import com.eduortza.api.application.port.in.BlogPost.create.CreateBlogPostCommand;
import com.eduortza.api.application.port.in.BlogPost.create.CreateBlogPostPort;
import com.eduortza.api.domain.BlogPost;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@RestController
public class CreateBlogController {

    private final CreateBlogPostPort createBlogPostPort;
    private final JwtService jwtService;
    private final FyleSystemAdapter fyleSystemAdapter;

    public CreateBlogController(CreateBlogPostPort createBlogPostPort, JwtService jwtService, FyleSystemAdapter fyleSystemAdapter) {
        this.createBlogPostPort = createBlogPostPort;
        this.jwtService = jwtService;
        this.fyleSystemAdapter = fyleSystemAdapter;
    }

    @PostMapping("/blog")
    public ResponseEntity<Object> createBlogPost(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("content") String content,
            @RequestParam("minutesToRead") Number minutesToRead,
            @RequestParam("image") MultipartFile image
    ) {
        File imageFile;
        try {
            imageFile = fyleSystemAdapter.createTempFile(image);
            CreateBlogPostCommand createBlogPostCommand = new CreateBlogPostCommand(title, content, description, minutesToRead, imageFile);
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
