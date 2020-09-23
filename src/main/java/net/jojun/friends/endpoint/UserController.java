package net.jojun.friends.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

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

    @RequestMapping(value = "/AddUser", method = RequestMethod.POST)
    public String user_add(@ModelAttribute("user_name") String name,
                           @ModelAttribute("user_age") String age,
                           @ModelAttribute("user_locale") String locale){
        boolean isSuccess = false;
        String url = baseUrl + "AddUser";
        RestTemplate restTemplate = new RestTemplate();


//        Map headers_info = new HashMap<String, String>();
//        headers_info.put("Content-Type", "application/json");
//        headers.setAll(headers_info);

        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        System.out.println("########## : "+ headers.toString());

        map.add("name",name);
        map.add("age", age);
        map.add("locale",locale);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        System.out.println("################## : "+ request.toString());

        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );

        JsonObject jsonObject = new JsonObject();

        if(response.getStatusCode() == HttpStatus.OK){
            jsonObject.addProperty("success", "true");
            return jsonObject.toString();
        }
        else{
            jsonObject.addProperty("success", "false");
            return jsonObject.toString();
        }
    }

    @GetMapping(value = "/user/list")
//    @ResponseBody
    public ModelAndView list()
    {
        String url = baseUrl +"selectAllUser";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);

        /* 방법 1 : String 으로 반환 받기 */
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        log.info("Message Header : " + result.getHeaders());
        log.info("Message Body : " + result.getBody());
        log.info("Message Status Code : " + result.getStatusCode());

        //getForObject, postFor ... 사용한다.  공부하기
//        Object obj = restTemplate.getForObject(url, Object.class);
//        System.out.println(obj); JPA 연동후 오브젝트로 받기

        /* 방법 2 : Object 로 반환 받기 */
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(url, Object[].class);
        Object[] objects = responseEntity.getBody();

        /*Obj -> Map -> add List*/
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for(Object obj : objects){
            Map<String, String> map = new HashMap<String,String>();
            map = mapper.convertValue(obj, Map.class);
            list.add(map);
        }


        ModelAndView mav = new ModelAndView();
        /*방법 1 - json - string 으로 반환
        mav.addObject("data", result.getBody());

        */

//        방법 2 - List - Map 으로 반환

        mav.addObject("list", list);
        mav.setViewName("user/list");

        /* ModelAndView 와 model 선언후 attribute 차이 */
        return mav;
    }


    /* Map to Json -> common */
        /*JsonObject jsonObject = new JsonObject();
        for( Map.Entry<String, String> entry : result_code.entrySet() ) {
            String key = entry.getKey();
            String value = entry.getValue();
            jsonObject.addProperty(key, value);
        }*/
}
