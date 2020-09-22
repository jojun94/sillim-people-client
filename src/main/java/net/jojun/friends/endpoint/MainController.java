package net.jojun.friends.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    /*Thymeleaf MoV Init*/
    @GetMapping(value = "/")
    public ModelAndView home()
    {
        ModelAndView mav = new ModelAndView();

        /* resources/templates 내 html 이름*/
        mav.setViewName("home");

        Map<String, Object> map = new HashMap<>();
        map.put("name", "jojun");
        map.put("date", LocalDateTime.now());

        /*data라는 이름으로 View에 추가*/
        mav.addObject("data", map);

        return mav;
    }

    @GetMapping(value = "/user/list")
    public String list()
    {
        return "user/list";
    }

}
