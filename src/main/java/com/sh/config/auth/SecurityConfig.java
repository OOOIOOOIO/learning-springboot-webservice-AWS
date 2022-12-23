package com.sh.config.auth;

import com.sh.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}
/*
 headers().frameOptions().disable() : h2-console 화면을 사용하기 위해 해당 옵션들을 disable 한다.
 authorizeRequests() : URL별 권한 관리를 설정하는 옵션의 시작점. authorizeRequests가 선언되어야만 antMatchers 옵션 사용 가능
 antMatchers : 권한 관리르 설정하는 옵션의 시작점 URL, HTTP 메소드별로 관리가 가능하다.
            "/", "/css/**" 등 지정된 URL들은 접근 가능하도록 설정(permitAll())
            "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 접근 가능하도록 설정(hasRole(Role))
 anyRequest : 설정된 값들 이외 나머지 URL을 나타낸다. 여기서 authenticated()를 추가해 나머지 URL 들은 모두 "인증된 사용자"들에게만 허용하도록 한다.
            (인증된 사용자 == 로그인한 사용자)
 logout().logoutSuccessUrl("/") : 로그아웃 기능에 대한 여러 설정의 진입점, 로그아웃 성공시 "/"로 이동한다.
 oauth2Login : OAuth2 로그인 기능에 대한 여러 설정의 진입점
 userInfoEndpoint : OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당한다.
 userService(구현체 등록) : 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록한다.
                        리소스 서버(소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수도 있다.

 */