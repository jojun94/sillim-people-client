package net.jojun.friends.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by JOJUN.
 * REST API Client의 CRUD 연습 Controller , restTemplate 연습
 * Date: 2020-07-07
 */
@RestController
public class UserController {

//    private static final Logger log = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    String baseUrl = "http://127.0.0.1:9080/";





    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String user_add(@RequestParam( value="user_name" , required=true) String name,
                           @RequestParam( value="user_age" , required=true) String age,
                           @RequestParam( value="user_locale" , required=true) String locale){
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        String body = null;
        /*Post 시 Map은 Json으로 전송됨*/
        Map<String, String> map = new HashMap<>();

        map.put("name",name);
        map.put("age", age);
        map.put("locale",locale);

        try {
            body= mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        HttpEntity entity = new HttpEntity(body, headers);


        ResponseEntity<String> response = restTemplate.postForEntity( baseUrl+"user", entity , String.class, name, age, locale );

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
    /* @RestController에 의해 @ResponseBody가 강제됨으로 View를 반환할 수 없음. MAV로 반환 */
    @GetMapping(value = "/user/list")
    public ModelAndView list()
    {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);

        /* 방법 1 : String 으로 반환 받기 */
        ResponseEntity<String> result = restTemplate.exchange(baseUrl+"user/list", HttpMethod.GET, entity, String.class);


        /* 방법 2 : Object 로 반환 받기 */
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(baseUrl+"user/list", Object[].class);
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

        /*  방법 2 - List - Map 으로 반환 */
        mav.addObject("list", list);
        mav.setViewName("user/list");
        return mav;
    }


    @DeleteMapping(value="/user")
    public String deleteUser(@RequestParam( value="id", required=true) int id){

        boolean isSuccess = false;
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Integer> map = new HashMap<>();
        map.put("id", id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map> entity = new HttpEntity<Map>(map, headers);

    //        ResponseEntity<String> response = restTemplate.delete(baseUrl+"user/{id}",map);
        /* delete는 반환값이 void 이다. Exchange를 이용하면? -> Status Code를 받아올 수 있음. */

    ResponseEntity<Map> response = restTemplate.exchange(baseUrl+"user/{id}", HttpMethod.DELETE, entity, Map.class, map);


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
}
