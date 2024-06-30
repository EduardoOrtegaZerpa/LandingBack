package com.eduortza.api.adapter.in.web.BlogPost;

import com.eduortza.api.adapter.FyleSystemAdapter;
import com.eduortza.api.adapter.exception.BlogPostException;
import com.eduortza.api.adapter.exception.JwtAuthorizationException;
import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.adapter.out.persistence.services.JwtService;
import com.eduortza.api.application.port.in.BlogPost.modify.ModifyBlogPostCommand;
import com.eduortza.api.application.port.in.BlogPost.modify.ModifyBlogPostPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;


@RestController
public class ModifyBlogController {

    private final ModifyBlogPostPort modifyBlogPostPort;
    private final JwtService jwtService;
    private final FyleSystemAdapter fyleSystemAdapter;

    public ModifyBlogController(ModifyBlogPostPort modifyBlogPostPort, JwtService jwtService, FyleSystemAdapter fyleSystemAdapter) {
        this.modifyBlogPostPort = modifyBlogPostPort;
        this.jwtService = jwtService;
        this.fyleSystemAdapter = fyleSystemAdapter;
    }

    @PutMapping("/blog/{id}")
    public ResponseEntity<Object> modifyBlogPost(
            @PathVariable long id,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "minutesToRead", required = false) Number minutesToRead,
            @RequestParam(value = "tags", required = false) List<String> tags,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        File imageFile;
        var response = new HashMap<String, String>();
        try {
            if (image != null) {
                imageFile = fyleSystemAdapter.createTempFile(image);
            } else {
                imageFile = null;
            }
            ModifyBlogPostCommand modifyBlogPostCommand = new ModifyBlogPostCommand(title, content, description, minutesToRead, tags, imageFile);
            jwtService.authorizeAdminAccess();
            modifyBlogPostPort.modifyBlogPost(id, modifyBlogPostCommand);
            response.put("message", "Blog post modified successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (JwtAuthorizationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new BlogPostException(e.getMessage()));
        } catch (NonExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BlogPostException(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BlogPostException(e.getMessage()));
        }
    }
}
