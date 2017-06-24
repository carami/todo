package carami.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by 강경미 on 2017. 6. 3..
 */
@Controller
public class HelloController {
    @GetMapping(path = "/")
    public String hello(Model model){
        return "hello";
    }

}
