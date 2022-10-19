package com.sh.web.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor // 기본 생성자 생성
@Entity
public class Posts {

    @Id // PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment를 해주기 위해서 필요
    private Long id;

    @Column(length = 500, nullable = false) // String의 기본 길이는 VARCHAR(255)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
