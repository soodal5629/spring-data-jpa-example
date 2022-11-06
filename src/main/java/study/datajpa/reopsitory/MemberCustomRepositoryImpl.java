package study.datajpa.reopsitory;

import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

/* 사용자 정의 리포지토리 구현 */
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository{
    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m where m.age = 10")
                .getResultList();
    }
}
