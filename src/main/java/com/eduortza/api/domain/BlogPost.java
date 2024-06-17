package com.eduortza.api.domain;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogPost {

    private long id;
    private String title;
    private String content;
    private String description;
    private Date created;
    private Number minutesToRead;
    private List<String> tags;
    private String imageUrl;


    //Business logic

    public void addTag(String tag){
        tags.add(tag);
    }

    public void removeTag(String tag){
        tags.remove(tag);
    }

    public void clearTags(){
        tags.clear();
    }

}
