package com.sh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// JPA Auditing 활성화
//@EnableJpaAuditing
/*
@SpringBootApplication가 붙은 클래스는 자동으로 모든 테스트들의 기본 설정이 적용된다.
따라서 위의 테스트 코드는 실행될 때 @SpringBootApplication가 붙은 나의 ~~~Application.java를 로딩하는데,
테스트 코드에 붙은 @WebMvcTest는 JPA 관련 bean 객체를 로딩하지 않아 @EnableJpaAuditing과 설정이 맞지 않는 문제가 발생한다.
쉽게 말해 @WebMvcTest는 Entity를 안불러오는데 @EnableJpaAuditing은 불러와야 해서 서로 충돌
그렇기 때문에 @EnableJpaAuditing을 따로 Config 패키에 선언한다.
 */
@SpringBootApplication
public class LearningAwsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningAwsApplication.class, args);
	}
}
