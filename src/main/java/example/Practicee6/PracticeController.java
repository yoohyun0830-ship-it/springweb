package example.Practicee6;

import java.net.URLEncoder;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api")
@RequiredArgsConstructor 
public class PracticeController {
    // Redis를 사용하기 위한 객체
    private final StringRedisTemplate redisTemplate;

    // ==========================================
    //          1. 세션 (개인저장소)
    // ==========================================

    // [1] Session 데이터 저장
    // 요청 예시 : GET /api/session/add?data=apple
    @GetMapping("/session/add")
    public String addSessionDate(

        // URL 의 쿼리스트링 에서 data 값을 가져온다
        // 예) ?data=apple -> data 변수에 "apple" 저장
         @RequestParam("data") String data,

        // 현재 요청한 브라우저의 Session 객체를 가져온다
        // Session은 서버에 존재하는 브라우저별 개인 저장소
        HttpSession session){

        // [2] Session에 "SESSION_DATA"라는 이름으로 
        // 기존에 저장되어 있던 List를 가져온다.
        // 처음 요청 -> Null 
        List<String>list = (List<String>) session.getAttribute("SESSION_DATA");

        // [3] 기존에 저장된 List가 없을 때 -> 새로운 빈 List 생성
        if(list == null){
            list = new ArrayList<>();
        }

        // [4] 쿼리스트링으로 전달받은 data를 List에 추가
        list.add(data);

        // [5] data가 추가된 List -> session에 다시 저장
        session.setAttribute("SESSION_DATA", list);

        // [6] 저장이 완료되면 문자열 응답
        return "세션저장성공";
        }

    // [2] Session 전체 데이터 조회
    // 요청 예시 : GET /api/session/all
    @GetMapping("/session/all")
    public List<String> getAllSessiondata(
        // 현재 요청한 브라우저의 Session 객체를 가져온다
        HttpSession session ){ 

        // [1] Session에서 "SESSION_DATA"라는 이름으로
        // 저장되어 있는 List를 가져온다
        List<String> result = (List<String>)session.getAttribute("SESSION_DATA");

        // [2] Session에서 가져온 전체 데이터를 반환
        return result;
    }

    // ==========================================
    //          2. 쿠키 (개인저장소)
    // ==========================================

    //  Java 객체(List 등) -> JSON 문자열로 변환
    private final ObjectMapper objectMapper = new ObjectMapper();

    // [1] Coolie 데이터 저장
    // 요청 예시 : GET /api/cookie/add?data=dog
    @RequestMapping("/cookie/add")
    public String addCookieData(
        // [1] 쿼리스트링의 data 값을 받는다
        // 예) ?data=dog -> data = "dog"
        @RequestParam ("data") String data,

        // [2] 브라우저에 저장되어 있는 "COOKIE_DATA" 쿠키값을 가져옴
        // 처음 요청시 cookieData = null
        // required = false -> 쿠키가 없어도 오류를 발생시키지 않는다.

        @CookieValue(
                name = "COOKIE_DATA",
                required = false
        ) String cookieData,
        // [3] 서버가 브라우저에게 쿠키를 보내기 위한 응답 객체
        HttpServletResponse response) throws Exception{
        
        // [4] 기존 Cookie에 저장된 data를 List로 받아온다
        List<String> list = (cookieData == null)
                                                ? new ArrayList<>()
                                                : objectMapper.readValue(cookieData, List.class);
        
        // [5] 쿼리스트링으로 새로 받아온 데이터를 List에 추가
        list.add(data);

        // [6] List를 JSON으로 변환
        String json = objectMapper.writeValueAsString(list);

        // [7] JSON을 이용하여 Cookie 생성
        // URLEncoder.encode() -> JSON에 포함된 특수문자를 쿠키에 저장할 수 있도록 인코딩
        ResponseCookie cookie = ResponseCookie.from("COOKIE_DATA",
                                 URLEncoder.encode(json, StandardCharsets.UTF_8)
                            )          
                            .path("/")
                            .build();

        // [8] 응답 Header에 Cookie를 추가
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // [9] 결과 반환
        return "쿠키저장성공";
        }
    
    // [2] Cookie 전체 데이터 조회
    // 요청 예시 : GET /api/cookie/all
    @GetMapping("/cookie/all")
    public List<String> getAllCookieData(

        // [1] 브라우저의 "COOKIE_DATA" 쿠키 값을 가져온다
        // 쿠키가 없으면 cookieData = null
        @CookieValue(
                name = "COOKIE_DATA",
                required = false
        ) String cookieData) throws Exception{
        
        // [2] Cookie 값이 없으면 빈 List 반환
        if(cookieData == null){
            return Collections.emptyList();
        }

        // [3] Cookie의 저장된 JSON 문자열 -> JAVA의 List로 변환하여 반환
        return objectMapper.readValue(cookieData, List.class);
        }

        // ==========================================
        // 3. 레디스 (공유저장소 - opsForValue)
        // ==========================================

        // [1] Redis 데이터 저장
        // 요청 예시 : GET /api/redis/add?data=blue
        @GetMapping("/redis/add")
        public String addRedisData(
            // Redis를 사용하기 위한 객체

                // [1] Query String의 data 값을 받는다.
                // 예) ?data=blue → data = "blue"
                @RequestParam("data") String data) {

            // [2] Redis에 데이터를 저장한다.
        
            // opsForValue() => Redis에서 일반적인 String 값을 저장/조회할 때 사용
            // set(key, value) => Redis에 Key : Value 형태로 저장
            
            redisTemplate.opsForValue().set(data, data);

            // [3] 저장 완료 결과 반환
            return "레디스저장성공";
        }
        
        // [2] Redis 전체 데이터 조회
        // 요청 예시 : GET /api/redis/all
        @GetMapping("/redis/all")
        public List<String> getAllRedisData() {

            // [1] Redis에 저장되어 있는 모든 Key를 가져온다.
            // "*" : 모든 Key를 의미
            Set<String> keys = redisTemplate.keys("*");

            // [2] Redis에서 조회한 데이터를 담을 빈 List 생성
            List<String> list = new ArrayList<>();

            // [3] 가져온 Key들을 하나씩 반복
            for(String key : keys) {

                // [4] 현재 Key에 해당하는 Value를 Redis에서 가져온다.
                String data = redisTemplate.opsForValue().get(key);

                // [5] 가져온 데이터를 List에 추가
                list.add(data);
            }

            // [6] 전체 데이터 반환
            return list;
        }
    }
