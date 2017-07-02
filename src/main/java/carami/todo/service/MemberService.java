package carami.todo.service;

import carami.todo.domain.Member;

public interface MemberService {
    public Member get(Long id);
    public Member addMember(Member member);
    public int delete(Long id);
    public int update(Member member);
}
