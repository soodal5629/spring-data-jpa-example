package study.datajpa.reopsitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

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

    /* 단건 Optional */
    Optional<Member> findOptionalByUsername(String username);

    /* 페이징 처리 - Page(total count 제공) */
    /*
    @Query(value = "select m from Member m left join m.team  t"
    , countQuery = "select count(m.username) from Member m") // 카운트 쿼리를 분리함으로써 성능 향상
    Page<Member> findByAge(int age, Pageable pageable);
    */
    /* 페이징 처리 - Slice(total count 제공x) */
    Slice<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true) // 얘 넣으면 영속성 컨텍스트 clear 자동으로 해줌!!
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m join fetch m.team") // fetch join
    List<Member> findMemberFetchJoin();

    /* 상위버젼을 오버라이드하고 @EntityGraph를 통해 fetch join 함 (jpql 작성할 필요 x) */
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    /* JPQL + @EntityGraph 같이 쓰는 것도 가능 */
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    /* Named 쿼리에서도 @EntityGraph 사용 가능*/
    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    /* 성능최적화 --> 변경 불가 */
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);
}
