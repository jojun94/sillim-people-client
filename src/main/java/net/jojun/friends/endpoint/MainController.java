package net.jojun.friends.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
