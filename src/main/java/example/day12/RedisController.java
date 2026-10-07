package example.day12;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController @RequestMapping("/api/redis") @RequiredArgsConstructor 
public class RedisController {
    // [1] 레디스 조작 객체 ( 문자열 기반의 자료 레디스에 삽입/조회/수정/삭제 )
    private final StringRedisTemplate stringRedisTemplate;
    // 1.
    @GetMapping("/test1")
    public Map<String,Object> test1(){

        // [2] 레디스에 자료 삽입, .opsForValue().set( KEY , VALUE ) , 문자열타입
        // key 중복이 안된다 , value 중복이 된다
        stringRedisTemplate.opsForValue().set("유재석", "90");
        stringRedisTemplate.opsForValue().set("강호동", "100");
        stringRedisTemplate.opsForValue().set("신동엽", "70");

        // [3] 레디스에 자료 조회 , keys("*") , 모든 키에 해당하는 자료 호출
        // 참고 : 컬렉션프레임워크( List , Map , Set )
        Set<String> keys= stringRedisTemplate.keys("*");
        Map<String , Object> map = new HashMap<>();
        for( String key : keys ){ // 모든 키들을 하나씩 반복하여
            String data = stringRedisTemplate.opsForValue().get(key); // 키 호출하여 값 호출
            map.put( key , data );
        }
        return map;
    }
    private final ObjectMapper objectMapper = new ObjectMapper();
    @PostMapping("/member")
    public boolean save (@RequestBody MemberDto memberDto )throws JsonProcessingException{
        // 1. 중복 없는 key 구성 
        String key = "member:" + memberDto.getMno();
        // 2. 문자열 템플릿에 DTO/자바 객체 대입 
        String str = objectMapper.writeValueAsString(memberDto);
        // 3. 레디스에 저장
        stringRedisTemplate.opsForValue().set(key, str);
        return true;
    }
    // [2] 전체조회
    @GetMapping("/member")
    public List<MemberDto> findAll()
            throws JsonMappingException, JsonProcessingException {

        // 1. member: 로 시작하는 모든 key 조회
        Set<String> keys = stringRedisTemplate.keys("member:*");

        // 2. 조회 결과를 저장할 리스트
        List<MemberDto> list = new ArrayList<>();

        // 3. 모든 key 반복
        for(String key : keys){

            // key에 해당하는 value 조회
            String value = stringRedisTemplate.opsForValue().get(key);

            // JSON 문자열 → MemberDto
            MemberDto memberDto =
                    objectMapper.readValue(value, MemberDto.class);

            // 리스트에 추가
            list.add(memberDto);
        }
        // 4. 전체조회 결과 반환
        return list;
    }

    // [3] 개별조회
    @GetMapping("/member/find")
    public MemberDto find(@RequestParam(name = "mno") Long mno)
            throws JsonMappingException, JsonProcessingException {

        // 1. 조회할 Redis Key 구성
        String findKey = "member:" + mno;

        // 2. Key를 이용해서 Redis에서 값 조회
        String value = stringRedisTemplate.opsForValue().get(findKey);

        // 3. 데이터가 없으면 null
        if(value == null) return null;

        // 4. JSON 문자열 → MemberDto 변환
        MemberDto memberDto =
                objectMapper.readValue(value, MemberDto.class);

        // 5. 조회 결과 반환
        return memberDto;
    }

    // [4] 삭제
    @DeleteMapping("/member")
    public boolean delete(@RequestParam(name = "mno")Long mno){
        String deleteKey = "member:"+mno;
        boolean result = stringRedisTemplate.delete(deleteKey);
        return result;
    }

    // [5] 수정
    @PutMapping ("/member")
    public boolean update(@RequestBody MemberDto memberDto)throws JsonProcessingException{
        String updateKey = "member:"+memberDto.getMno();
        if(updateKey == null) return false;
        String value = objectMapper.writeValueAsString(memberDto);
        stringRedisTemplate.opsForValue().set(updateKey, value);
        return true;
    }
}
