package com.sh.web;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//@SpringBootTest // @MockBean으로 정의된 Bean을 찾는다. 실제 Spring 환경을 구축한다.
//@AutoConfigureMockMvc // MockMvc를 사용할 때 SpringBootTest와 같이 사용한다. Service, Repository 등 사용가능

@ExtendWith(SpringExtension.class) // SpringBootTest에 내장되어 있다. 테스트 파일에 정의된 @Autowired를 로딩한다.
// 여기서는 Controller만 사용하기 때문에 선언.
@WebMvcTest // Spring MVC에 집중하여 테스트할 수 있는 어노테이션, Controller, ControllerAdvice를 사용할 수 있다.(Service, Repository는 사용불가), SpringBootTest와 같이 사용하지 못한다.
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc; // 웹 API를 테스트할 때 사용. Spring MVC 테스트의 시작점, HTTP GET, POST 등에 대한 API 테스트를 할 수 있다.

    @Test
    public void helloReturn() throws Exception {
        String hello = "hello";

        ResultActions hello1 = mvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("hello"));


    }

    @Test
    void helloDTOReturn() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(MockMvcRequestBuilders.get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(name)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount", Matchers.is(amount)));


    }




}


