package com.eduortza.api.application.port.out.BlogPost;

import com.eduortza.api.application.exception.StoreException;
import com.eduortza.api.domain.BlogPost;

public interface StoreBlogPostPort {
    BlogPost store(BlogPost blogPost);
}
