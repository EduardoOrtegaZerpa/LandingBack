package com.eduortza.api.adapter.in.web.BlogPost;

import com.eduortza.api.adapter.exception.BlogPostException;
import com.eduortza.api.adapter.exception.NonExistsException;
import com.eduortza.api.application.port.in.BlogPost.modify.ModifyBlogPostCommand;
import com.eduortza.api.application.port.in.BlogPost.modify.ModifyBlogPostPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;


@RestController
public class ModifyBlogController {

    private final ModifyBlogPostPort modifyBlogPostPort;

    public ModifyBlogController(ModifyBlogPostPort modifyBlogPostPort) {
        this.modifyBlogPostPort = modifyBlogPostPort;
    }

    @PutMapping("/blog/{id}")
    public ResponseEntity<Object> modifyBlogPost(@PathVariable long id, @RequestBody ModifyBlogPostCommand modifyBlogPostCommand) {
        try {
            modifyBlogPostPort.modifyBlogPost(id, modifyBlogPostCommand);
            return ResponseEntity.status(HttpStatus.OK).body("Blog post modified");
        } catch (NonExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BlogPostException(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BlogPostException(e.getMessage()));
        }
    }
}
