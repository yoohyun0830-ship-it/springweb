package example.day06;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "reply")
@NoArgsConstructor @AllArgsConstructor @Builder @Data
public class ReplyEntity {
    @Id
    private Integer rno;
    private String rname;

    // 단방향참조
    @ManyToOne( cascade = CascadeType.ALL , fetch = FetchType.LAZY )
    @JoinColumn( name = "bno")
    private BoardEntity boardEntity;

}
/*  - 영속성 : 자바는 영구저장 불가능 -> DB 매핑/연결 하여 영속성(영구저장) 표현
        - repository.save( ) , repository.findAll( ) , repository.findById( ) 등과 연결
        - Entity 영속된 entity = repository.save( 비영속entity )
        
    - ManyToOne( cascade = 영속성제약조건 ,  fetch = 불러오기시기 )
        cascadeType.REMOVE : 부모엔티티 삭제 -> 자식엔티티 삭제
        cascadeType.MERGE : 부모엔티티 수정 -> 자식엔티티 수정 반영
        cascadeType.DETACH : 부모엔티티 영속(연결)해제 -> 자식엔티티 해제
        cascadeType.REFRESH : 부모엔티티 재호출(갱신) -> 자식엔티티 갱신
        cascadeType.PERSIST :  부모엔티티 저장 -> 자식엔티티 저장
        cascadeType.ALL : 위 속성들 모두 사용
    
    - fetch
        FetchType.LAZY : 해당 엔티티 조회시 자식(참조) 엔티티 불러오지 X
            - 초기로딩 빠르다 , 재사용성 느리다 , 필요한 정보만 불러옴<지연로딩>

        FetchType.EAGER : 해당 엔티티 조회시 자식(참조) 엔티티 (즉시) 불러옴. 부모조회시 자식도 포함
            - 기본값 , 초기 로딩 느리다 , 재사용성 빠르다 , 불필요한 정보까지 불러옴<성능저하>
*/
