package example.day12;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // JPA 사용시 기본적인 CRUD 메소드 제공 , save findAll findByID deleteById 등등
    // 메소드쿼리(망명규칙) 또는 네이티브쿼리 추가 정의
    // findByXXX : XX에 필드명 넣어서 조회 추상매소드 만들기
    MemberEntity findByMid(String mid); // mid 일치하면 엔티티 조회
    Optional<MemberEntity>findByMname(String mname); // mname 일치하면 엔티티 조회
}