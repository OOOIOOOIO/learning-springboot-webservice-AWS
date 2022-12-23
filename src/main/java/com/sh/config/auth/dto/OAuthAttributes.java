package com.sh.config.auth.dto;

import com.sh.domain.user.Role;
import com.sh.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    /**
     *
     * @param registrationId
     * @param userNameAttributeName
     * @param attributes
     * @return OAuth2.0을 사용해 자동 로그인을 누가 하는지 판단
     */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        // Naver
        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }

        // Google
        return ofGoogle(userNameAttributeName, attributes);
    }

    /**
     *
     * @param userNameAttributeName
     * @param attributes
     * @return 구글 생성자, 구글로 로그인할 때 사용
     */
    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /**
     *
     * @param userNameAttributeName
     * @param attributes
     * @return 네이버 생성자, 네이버로 로그인할 때 사용
     */
    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> response = (Map<String, Object>) attributes.get("response"); // 네이버 로그인 시 response에 json형태로 정보가담겨져 있다.

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
//                .role(Role.GUEST) // 처음 가입할 때(로그인할 때) GUEST권한 부여
                .role(Role.USER)
                .build();
    }
}
