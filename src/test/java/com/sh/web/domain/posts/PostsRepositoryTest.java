package com.sh.web.domain.posts;

import com.sh.SecurityComponentScanTest;
import com.sh.domain.posts.Posts;
import com.sh.domain.posts.PostsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        //given
        String title = "테스트 제목";
        String content = "테스트 내용";

        Posts test = Posts.builder().title(title)
                .content(content)
                .author("aaaaaa")
                .build();
        postsRepository.save(test);

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts post = postsList.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);



    }

    @Test
    void BaseEntity_등록(){
        // given
        LocalDateTime now = LocalDateTime.of(2022, 10, 10, 5, 10, 10);

        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("autt")
                .build());
        // when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(posts.getCreatedDate());
        System.out.println(posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }

}
