package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 밑에 주석해놓은 것과 같음
@ToString(of = {"id", "username", "age"}) // team 넣으면 무한루프 돌 수 있음
public class Member extends JpaBaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY) // Member가 연관관계 주인
    @JoinColumn(name = "team_id")
    private Team team;
    // protected Member() {} // 아무데서나 호출 못하도록

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if(team != null){
            changeTeam(team);
        }
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    /* 멤버가 팀을 변경할 수 있는 메소드 */
    public void changeTeam(Team team){
        this.team = team;
        // 객체이기 때문에 둘다 변경해줘야 함
        team.getMembers().add(this);
    }
}
