package carami.jdbc;

import carami.todo.config.RootApplicationContextConfig;
import carami.todo.dao.MemberDao;
import carami.todo.domain.Member;
import carami.todo.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Test
    public void shouldInsertAndSelect() {
        // given
        Member member = new Member("강경미", "carami@nate.com", "1234");
        Member saveMember = memberService.addMember(member);

        // when
        Member resultMember = memberService.get(saveMember.getId());

        // member에 hashCode, equals메소드를 오버라이딩하여 구현해야한다.
        assertThat(resultMember, is(saveMember));
    }


    @Test
    public void shouldDelete() {
        // given
        Member member = new Member("강경미", "carami@nate.com", "1234");
        Member saveMember = memberService.addMember(member);

        // when
        int deleteCount = memberService.delete(saveMember.getId());

        // then
        assertThat(deleteCount, is(1));
    }

    @Test
    public void shouldUpdate() {
        // given
        Member member = new Member("강경미", "carami@nate.com", "1234");
        Member saveMember = memberService.addMember(member);

        // when
        member.setId(saveMember.getId());
        member.setName("강경미2");
        member.setEmail("carami2@nate.com");
        int updateCount = memberService.update(member);

        // Then
        Member result = memberService.get(member.getId());
        assertThat(result.getName(), is("강경미2"));
        assertThat(result.getEmail(), is("carami2@nate.com"));
    }

}
