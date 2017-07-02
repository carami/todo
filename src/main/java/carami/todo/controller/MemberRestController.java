package carami.todo.controller;

import carami.todo.domain.Member;
import carami.todo.dto.MemberParam;
import carami.todo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberRestController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    public Member create(@RequestBody MemberParam memberParam){
        // Member클래스의 setMember에 @JsonIgnore를 설정했기 때문에
        // Member를 이용하여 passwd를 받아들일 수 없다. 그런 이유로
        // MemberParam을 사용하였다.
        Member member = new Member();
        member.setName(memberParam.getName());
        member.setEmail(memberParam.getEmail());
        member.setPasswd(memberParam.getPasswd());
        return memberService.addMember(member);
    }

    @GetMapping("/{id}")
    public Member getMember(@PathVariable Long id){
        Member member = memberService.get(id);
        member.setPasswd("");
        return member;
    }

    @PutMapping
    public int update(@RequestBody Member membrer){
        return memberService.update(membrer);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Long id){
        return memberService.delete(id);
    }
}
