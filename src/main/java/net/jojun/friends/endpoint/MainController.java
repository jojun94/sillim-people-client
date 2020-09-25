package net.jojun.friends.endpoint;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Created by JOJUN.
 *
 * Date: 2020-07-07
 */
@Controller
public class MainController {
    String baseUrl = "http://127.0.0.1:9080/";

    @GetMapping(value = "/")
    public String home()
    {
        return "home";
    }


}
