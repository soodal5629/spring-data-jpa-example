package study.datajpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 얘를 넣어야 db에 table 생성할때도 자식 클래스가 부모 클래스 엔티티 필드 생성
public class JpaBaseEntity {
    // 해당 클래스를 상속받으면 등록일, 수정일을 공통으로 처리할 수 있음
    @Column(updatable = false) // 수정 불가
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist
    public void prePersist(){ // 저장하기 전에 발생
        LocalDateTime now = LocalDateTime.now();
        createdDate = now; // this 생략
        updatedDate = now; // this 생략
    }

    @PreUpdate
    public void preUpdate(){
        updatedDate = LocalDateTime.now();
    }

}
