package study.datajpa.reopsitory;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    public void testSave(){
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);
        // Optional을 get 으로 가져오는 것은 좋은 방법은 아니지만 지금은 연습이니까..
        Member findMember = memberRepository.findById(savedMember.getId()).get();
        assertThat(findMember.getId()).isEqualTo(savedMember.getId());
        assertThat(findMember.getUsername()).isEqualTo(savedMember.getUsername());
        // 같은 트랜잭션 내에서는 동일성 보장 -> true
        // 즉, 트랜잭션 다르면 다른 객체가 조회됨
        assertThat(findMember).isEqualTo(member);
    }
}