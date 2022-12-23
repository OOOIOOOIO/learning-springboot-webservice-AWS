package com.sh.web.dto;

import com.sh.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String author;

    // 생성자 : DTO를 받아 저장(Entiy)는 이런 패턴
    public PostsResponseDTO(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
    
}
