package study.datajpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
// @GeneratedValue 안쓰고 싶을 때, 즉 ID를 직접 생성해야 할 때
public class Item implements Persistable<String> {

    //@Id
    //@GeneratedValue
    //private Long id;

    @Id
    private String id;

    @CreatedDate // persist가 되기 전에 호출됨. 따라서 해당 컬럼을 통해 isNew 판단 가능
    private LocalDateTime createdDate;

    public Item(String id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return createdDate == null ? true : false;
    }
}
