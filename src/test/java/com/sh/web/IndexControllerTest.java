package com.sh.web;

import com.sh.SecurityComponentScanTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void 메인페이지_로딩(){
        //when
        String body = restTemplate.getForObject("/", String.class); // html body를 가져오나보다.
//        String body = this.restTemplate.getForObject("/", String.class); // 통과

        //then
        assertThat(body).contains("스프링");
    }

}
