package com.sh.web.domain.posts;

import com.sh.web.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor // 기본 생성자 생성
@Entity
public class Posts extends BaseTimeEntity {

    @Id // PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment를 해주기 위해서 필요
    private Long id;

    @Column(length = 500, nullable = false) // String의 기본 길이는 VARCHAR(255)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    // 생성자
    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    /*
        extends JpaRepository<Posts, Long>를 한 repository에 update 기능은 없네
        그래서 만들어줘야 하는구나!
     */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
