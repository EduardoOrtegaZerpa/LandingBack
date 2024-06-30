package com.eduortza.api.application.port.in.BlogPost.modify;

import com.eduortza.api.domain.BlogPost;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.util.List;

@Data
@AllArgsConstructor
public class ModifyBlogPostCommand {
        private String title;
        private String content;
        private String description;
        private Number minutesToRead;
        private List<String> tags;
        private File image;
}
