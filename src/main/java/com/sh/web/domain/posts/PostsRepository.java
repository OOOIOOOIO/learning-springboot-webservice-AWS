package com.sh.web.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// JpaRepository를 상속받을 경우 @Repository를 적어줄 필요가 없다.
// 또한 자동으로 CRUD 메소드가 자동으로 생성된다.
public interface PostsRepository extends JpaRepository<Posts, Long> { // Entity 클래스와 PK의 자료형을 적어준다.

    // SpringDataJpa에서 제공하지 않는 메소드는 아래처럼 쿼리로 작성할 수 있다.
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
