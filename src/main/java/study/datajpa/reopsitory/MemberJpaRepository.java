package study.datajpa.reopsitory;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository // 스프링 빈으로 등록
public class MemberJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public Member save(Member member){
        em.persist(member);
        return member;
    }
    public void delete(Member member){
        em.remove(member);
    }
    public List<Member> findAll(){
        // JPQL
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    public Optional<Member> findById(Long id){
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }
    public long count(){
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }
    public Member find(Long id){
        return em.find(Member.class, id);
    }

    /* 페이징 쿼리 */
    public List<Member> findByPage(int age, int offset, int limit) {
        return em.createQuery("select m from Member m where m.age = :age order by m.username desc")
                .setParameter("age", age)
                .setFirstResult(offset) // 어디서부터 시작해서 가져올거야
                .setMaxResults(limit) // 개수를 몇개를 가져올거야
                .getResultList();
    }
    public long totalCount(int age) {
        return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
                .setParameter("age", age)
                .getSingleResult();
    }

    /* 벌크성 수정 쿼리 */
    public int bulkAgePlus(int age){
        return em.createQuery("update Member m set m.age = m.age + 1"
                                                + "where m.age>= :age")
                .setParameter("age", age)
                .executeUpdate(); // 수정된 개수 반환
    }
}
