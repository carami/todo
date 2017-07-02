package carami.todo.controller;

import carami.todo.domain.Member;
import carami.todo.dto.MemberParam;
import carami.todo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping
    public String index(){
        return "index";
    }
}
