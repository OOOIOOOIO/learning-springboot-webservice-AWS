package com.sh.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
    스프링 시큐리티에서는 권한 코드에 항상 ROLE_이 앞에 있어야만 한다.
    그래서 코드별 키 값을 ROLE_~~ 등으로 지정한다.
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;


}
/*
    Enum의 생성자는 private이여야 한다. public, protected는 에러가 발생한다. enum은 고정된 상수들의 집합으로써, 런타임, 컴파일타임에
    모든 값을 알고 있어야 한다. 즉 다른 패키지나 클래스에서 enum에 접근하면 안되며 어떤 값을 정해줄 수 없다. 따라서 컴파일 시에 타입안정성이
    보장된다.(해당 enum 내에서도 new 키워드로 인스턴스 생성 불가능, clone(), newInstnace() 메서드도 불가능) 이 때문에 생성자의 접근 제어자를
    private으로 설정해야 한다.
    Enum의 각 열거형 상수에 "추가 속성"을 부여하여 사용할 경우 "필드(private final)와 생성자 그리고 getter"를 생성해준다.
    ex) EYE("왼쪽", "오른쪽")
        private final String left;
        private final String right;
        + 생성자(@RequiredArgsConstructor, getter(@Getter)


    @RequiredArgsConstructor 을 사용해 final이 선언된 필드들은 컴파일이 아래와 같은 생성자로 생성된다.


    Role(String key, String title){
        this.key = key;
        this.title = title;
    }
 */