package carami.todo.dao;

import carami.todo.domain.Member;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MemberDao {
    private NamedParameterJdbcTemplate jdbc; // sql 을 실행하기 위해 사용되는 객체
    private SimpleJdbcInsert insertAction; // insert 를 편리하게 하기 위한 객체
    private RowMapper<Member> rowMapper = BeanPropertyRowMapper.newInstance(Member.class); // 칼럼 이름을 보통 user_name 과 같이 '_'를 활용하는데 자바는 낙타표기법을 사용한다 이것을 자동 맵핑한다.

    // Spring은 생성자를 통하여 주입을 하게 된다.
    public MemberDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource); // Datasource를 주입
        this.insertAction = new SimpleJdbcInsert(dataSource)  // Datasource를 주입
                .withTableName("member")   // table명을 지정
                .usingGeneratedKeyColumns("id"); // pk 칼럼을 지정
    }

    public Long insert(Member member){
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        return insertAction.executeAndReturnKey(params).longValue();
    }

    public Member selectById(long id){
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject(MemberSqls.SELECT_BY_ID,params,rowMapper);
    }

    public Member selectByEmail(String email){
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        return jdbc.queryForObject(MemberSqls.SELECT_BY_Email,params,rowMapper);
    }


    public int update(Member member){
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        return jdbc.update(MemberSqls.UPDATE_BY_ID, params);
    }

    public int delete(Long id){
        Map<String, ?> params = Collections.singletonMap("id", id);
        return jdbc.update(MemberSqls.DELETE_BY_ID, params);
    }
}
