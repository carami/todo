package carami.todo.service.impl;

import carami.todo.dao.MemberDao;
import carami.todo.domain.Member;
import carami.todo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberDao memberDao;

    @Override
    @Transactional(readOnly = true)
    public Member get(Long id) {
        return memberDao.selectById(id);
    }

    @Override
 //   @Transactional(readOnly = false)
    public Member addMember(Member member){
        Long insert = memberDao.insert(member);
        member.setId(insert);
        return member;
    }

    @Override
    public int delete(Long id) {
        return memberDao.delete(id);
    }

    @Override
    public int update(Member member) {
        return memberDao.update(member);
    }
}
