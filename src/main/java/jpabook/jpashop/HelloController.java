package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        //model -> view에 넘긴다.
        model.addAttribute("data", "hello!!");
        return "hello"; // 화면이름 : resources/templates
    }
}