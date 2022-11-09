package study.datajpa.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class) // 이벤트를 기반으로 동작한다는 의미
@MappedSuperclass
@Getter
public class BaseTimeEntity {
    // 이런식으로 시간 | 등록자, 수정자 분리 가능
    // 웬만한 거의 모든 테이블들은 등록시간, 수정시간은 필요하지만
    // 등록자, 수정자가 필요없을 경우엔 해당 클래스만 extends 하면 됨
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}
