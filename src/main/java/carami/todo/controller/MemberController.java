package carami.todo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import carami.todo.domain.Member;
import carami.todo.dto.MemberFormParam;
import carami.todo.service.MemberService;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public String members(){
        return "index";
    }


    // 회원 가입시 form에서 알맞은 값을 입력하였는지 프론트 javascript를 이용하여 검증을 해야겠지만,
    // 프론트를 무시하고 요청을 보낼 수도 있기 때문에 서버에서도 해당 값이 올바른지 검증하는 코드가 반드시 존재해야 한다.
    @PostMapping
    public String create(@ModelAttribute MemberFormParam memberFormParam) {
        if (memberFormParam.getName() == null || memberFormParam.getName().length() == 0 ||
                memberFormParam.getEmail() == null || memberFormParam.getEmail().length() == 0 ||
                memberFormParam.getPasswd1() == null || memberFormParam.getPasswd1().length() == 0 ||
                memberFormParam.getPasswd2() == null || memberFormParam.getPasswd2().length() == 0) {
            return "redirect:/"; // 이름, email, passwd1, passwd2 중에서 하나라도 입력하지 않은 것이 있을 경우 이동
        }else {
            if (memberFormParam.getPasswd1().equals(memberFormParam.getPasswd2())) { //사용자가 입력한 암호가 같을 경우
                Member member = new Member();
                member.setName(memberFormParam.getName());
                member.setEmail(memberFormParam.getEmail());
                member.setPasswd(memberFormParam.getPasswd1());
                Member resultMember = memberService.addMember(member);

                return "redirect:/"; // 회원 가입 후 봐야할 화면으로 redirect
            } else {
                return "redirect:/"; // 암호, 암호 가 일치하지 않음
            }
        }
    } // 회원 가입 끝

    // email을 로그인 id로 사용하려면 table 생성에서도 해당 값은 unique하게 설정되어 있어야 한다.
    // 그리고 null을 허용하면 안된다. email 칼럼에 대하여 unique, not null제약 조건을 추가한다.
    @PostMapping("/login")
    public String login(@RequestParam(name="email") String email, @RequestParam(name="passwd") String passwd, HttpServletRequest request){
        Member member = memberService.getByEmail(email);
        if(member != null && passwd.equals(member.getPasswd())){
            HttpSession session = request.getSession();
            session.setAttribute("loginInfo", member);
            return "redirect:/";
        }else{ // member가 없거나 passwd가 일치하지 않는다.
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String login(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("loginInfo");
        return "redirect:/";
    }

}
