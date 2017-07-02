package carami.jdbc;

import carami.todo.config.DbConfig;
import carami.todo.config.RootApplicationContextConfig;
import carami.todo.dao.MemberDao;
import carami.todo.domain.Member;
import com.sun.tools.javac.comp.Todo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional  // Transactional이 있을 때와 없을 때 각각 실행해보고 그 때마다 msyql에서 결과를 select해본다.
public class SpringJdbcTest {
    @Autowired
    MemberDao memberDao;

    @Test
    public void shouldInsertAndSelect() {
        Member member = new Member("강경미", "carami@nate.com", "1234");
        Long memberPk = memberDao.insert(member);

        Member result = memberDao.selectById(memberPk);

        // http://sejong-wiki.appspot.com/assertThat
        assertThat(result.getName(), is("강경미")); // result의 name은 강경미 이다(is). 읽혀지는 코드로 테스트 코드가 작성된다.
        assertThat(result.getEmail(), is("carami@nate.com"));
        assertThat(result.getPasswd(), is("1234"));
    }


    @Test
    public void shouldDelete() {
        // given
        Member member = new Member("강경미", "carami@nate.com", "1234");
        Long memberPk = memberDao.insert(member);

        // when
        int deleteCount = memberDao.delete(memberPk);

        // then
        assertThat(deleteCount, is(1));
    }

    @Test
    public void shouldUpdate() {

        // given
        Member member = new Member("강경미", "carami@nate.com", "1234");
        Long memberPk = memberDao.insert(member);

        // when
        member.setId(memberPk);
        member.setName("강경미2");
        member.setEmail("carami2@nate.com");
        int updateCount = memberDao.update(member);

        // Then
        Member result = memberDao.selectById(memberPk);
        assertThat(result.getName(), is("강경미2"));
        assertThat(result.getEmail(), is("carami2@nate.com"));
    }
}
