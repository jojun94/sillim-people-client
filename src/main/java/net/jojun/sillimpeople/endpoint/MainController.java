package net.jojun.sillimpeople.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/")
public class MainController {


    @GetMapping(value = "/")
    public ModelAndView home()
    {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("home");

        Map<String, Object> map = new HashMap<>();
        map.put("name", "jojun");
        map.put("date", LocalDateTime.now());

        mav.addObject("data", map);

        return mav;
    }
}
