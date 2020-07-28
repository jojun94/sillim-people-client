package net.jojun.sillimpeople.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOJUN.
 * Date: 2020-07-07
 */
@RestController
public class UserController {

//    private static final Logger log = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    String baseUrl = "http://127.0.0.1:9080/";

    @GetMapping("/printAllUser")
    public String printAllUser(){
        String url = baseUrl +"selectAllUser";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);
//        List<Object> obj = new ArrayList<>();


        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

//        ResponseEntity<List<Object>> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        log.info("Message Header : " + result.getHeaders());
        log.info("Message Body : " + result.getBody());
        log.info("Message Status Code : " + result.getStatusCode());
        //getForObject, postFor ... 사용한다.  공부하기

//        Object obj = restTemplate.getForObject(url, Object.class);
//        System.out.println(obj); JPA 연동후 오브젝트로 받기

        return result.getBody();
    }
}
