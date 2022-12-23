package com.sh.web.dto;

import com.sh.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDTO {
    private String title;
    private String content;
    private String author;

    // 바로 값을 받는 경우 Builder 패턴
    @Builder
    public PostsSaveRequestDTO(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // DTO --> Entity 변환
    public Posts toEntity(){
        return Posts.builder() // 생성자 @Builder를 사용하면 이렇게 할 수 있구나.
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
