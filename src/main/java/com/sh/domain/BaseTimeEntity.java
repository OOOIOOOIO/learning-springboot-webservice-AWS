package com.sh.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 상위 Entity 클래스, 상속관계. 공통적으로 들어가야할 정보들을 이런식으로 만든다.
@EntityListeners(AuditingEntityListener.class) // Auditing 기능 활성화
public abstract class BaseTimeEntity {

    @CreatedDate // Entity가 생성되어 저장될 때 자동으로 저장된다.
    private LocalDateTime createdDate;

    @LastModifiedDate // 조회한 Entity의 값을 변경할 때 시간이 자동으로 저장된다.
    private LocalDateTime modifiedDate;

}
