package study.datajpa.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class) // 이벤트를 기반으로 동작한다는 의미
@MappedSuperclass
@Getter
public class BaseEntity extends BaseTimeEntity {

    /* 빈으로 등록된 auditorProvider 호출하여 AuditorAware 자동으로 등록해줌 */
    @CreatedBy // 등록자
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy // 수정자
    private String lastModifiedBy;
}
