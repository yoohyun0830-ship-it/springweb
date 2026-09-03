package example.day05;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class TestService {
    private final AppStart appStart;
    @Autowired private TestRepository testRepository;

    TestService(AppStart appStart) {
        this.appStart = appStart;
    }

    // 1. 전체조회
    public List<TestDto>전체조회(){
        // 1. 모든 엔티티 조회
        List<TestEntity> entities = testRepository.findAll();

        // 2. 모든 엔티티 -> DTO 변환
        // 빈 리스트 생성
        List<TestDto>list = new ArrayList<>();
        // 모든 엔티티 반복하여 모든 DTO변환하여 새로운 리스트 저장
        // 리스트객체.forEach( (반복변수)-> { } );
        entities.forEach((entity)->{
            // - 리스트내 하나씩 entity(반복변수)에 대입 반복
            // - TestDto내 entity -> dto 변환함수 : from
            TestDto dto = TestDto.from(entity);
            // - 변환결과 새로운 리스트에 담기
            list.add(dto);
        });
        // 3. 반환
        return list;
    }

    // 2. 저장
    public boolean 저장( TestDto testDto ){
        // 1. dto ---> entity 변환함수 : toEntity 함수
        TestEntity testEntity = testDto.toEntity();
        // 2. entity save 저장
        TestEntity savedEntity = testRepository.save(testEntity);
        // 3. 저장결과 pk 여부 성공 판단
        if(savedEntity.getNo() >= 1){ return true; }
        return false;
    }

    // 3. 수정
    @Transactional
    public boolean 수정( TestDto testDto){
        // 1. 구정할 엔티티 찾는다(pk)
        Optional<TestEntity>optional
        =testRepository.findById(testDto.getNo());

        // 2. 찾은 엔티티 존재시
        if( optional.isPresent()){
            // 3. 엔티티 꺼낸다
            TestEntity entity = optional.get();
            // 4. setter 메소드 이용한 수정
            entity.setPrice( testDto.getPrice());
            entity.setDescri( testDto.getDescri());
            return true;
        }
        return false;
    }
}
