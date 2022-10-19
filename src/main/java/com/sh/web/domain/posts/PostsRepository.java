package com.sh.web.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository를 상속받을 경우 @Repository를 적어줄 필요가 없다.
// 또한 자동으로 CRUD 메소드가 자동으로 생성된다.
public interface PostsRepository extends JpaRepository<Posts, Long> { // Entity 클래스와 PK의 자료형을 적어준다.

}
