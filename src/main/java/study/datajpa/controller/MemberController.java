package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.reopsitory.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    // 도메인 클래스 컨버터 기능 => 간단할때만 사용 가능, 조회용으로 써야 함(트랜잭션이 없는 상태에서 조회했으므로)
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<Member> list(@PageableDefault(size = 5, sort = "username") Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        // 실제로는 엔티티 말고 DTO 로 리턴해줘야 함!!!
        Page<MemberDto> result = page.map(MemberDto::new); // 생성자 이용한 코드
        return page;
    }

    @PostConstruct
    public void init() {
        memberRepository.save(new Member("userA"));
    }
}
