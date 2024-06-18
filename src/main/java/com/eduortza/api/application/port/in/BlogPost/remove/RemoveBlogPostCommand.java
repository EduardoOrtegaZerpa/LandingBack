package com.eduortza.api.application.port.in.BlogPost.remove;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveBlogPostCommand {
    private long id;
}
