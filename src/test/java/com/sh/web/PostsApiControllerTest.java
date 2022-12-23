package com.sh.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.domain.posts.Posts;
import com.sh.domain.posts.PostsRepository;
import com.sh.web.dto.PostsSaveRequestDTO;
import com.sh.web.dto.PostsUpdateRequestDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 호스트가 사용하지 않는 랜덤 포트를 테스트에 사용하겠다는 것을 의미
public class PostsApiControllerTest {

    @LocalServerPort // 랜덤 로컬 port
    private int port;

    @Autowired // REST 방식으로 개발한 API Test를 최적화 하기 위해 만들어진 클래스
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    /* ==== SpringBootTest에서 MockMvc 사용하는 법 ==== */
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    // ======================================

    @AfterEach
    void tearDown(){
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER") // MockMvc에서만 작동하기 때문에 MockMvc로 테스트해야 한다. spring-security-test
    void posts_등록() throws Exception { // JsonProcessingException ( writeValueAsString() )

        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDTO requestDTO = PostsSaveRequestDTO.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";


        /* ==== SpringBootTest restTemplate ==== */

        //when
//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDTO, Long.class);

        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        // ===============================


        /* SpringBootTest에서 Mock 사용 */

        // when
        mvc.perform(post(url) // MockMvcRequestBuilders
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isOk()); // MockMvcResultMatchers



        // 공통 then

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

    }

    @Test
    @WithMockUser(roles = "USER") // MockMvc에서만 작동하기 때문에 MockMvc로 테스트해야 한다. spring-security-test
    void posts_수정() throws Exception { // JsonProcessingException ( writeValueAsString() )
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "wwww";
        String expectedContent = "qqqq";

        PostsUpdateRequestDTO requestDTO = PostsUpdateRequestDTO.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;


        /* ==== SpringBootTest restTemplate ==== */

        // body 생성, HttpEntity<>(T body)
//        HttpEntity<PostsUpdateRequestDTO> requestEntity = new HttpEntity<>(requestDTO);

        //when
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        // ===============================

        // SpringBootTest에서 MockMvc 사용
        mvc.perform(put(url) //MockMvcRequestBuilders
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isOk());


        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    @WithMockUser(roles = "USER") // MockMvc에서만 작동하기 때문에 MockMvc로 테스트해야 한다. spring-security-test
    void posts_삭제() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long deleteId = savedPosts.getId();
        Long expectedId = savedPosts.getId();

        String url = "http://localhost:" + port + "/api/v1/posts/" + deleteId;

        /* ==== SpringBootTest restTemplate ==== */
//        HttpHeaders httpHeaders = new HttpHeaders(); // 기본 헤더
//        HttpEntity entity = new HttpEntity(httpHeaders);

        //when
        // 이건 반환값이 없을 때, exchange, execute도 마찬가지
//        restTemplate.delete(url);

        // 반환값이 있을 경우
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, entity, Long.class);


        //then
//        assertThat(responseEntity.getBody()).isEqualTo(expectedId); // 1 예상

        // =================

        /* SpringBootTest MockMvc 사용 */

        mvc.perform(delete(url))
                .andExpect(status().isOk());

    }

}
