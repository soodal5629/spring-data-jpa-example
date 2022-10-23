package study.datajpa.reopsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.Collection;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    /* 이름이없는 Named Qeury와 같다. */
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    /* Member 테이블의 특정 필드만 가져올 수 있음 */
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    /* new operation을 통해 dto로 반환 가능(JPQL이 제공하는 문법) */
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    /* List 말고도 여러가지 컬렉션 타입을 받을 수 있음 && 컬렉션 파라미터를 통해 in 절로 여러개 조회 가능 */
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);
}
