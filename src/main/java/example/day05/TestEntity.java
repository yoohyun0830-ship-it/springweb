package example.day05;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "test")
@NoArgsConstructor @AllArgsConstructor @Builder @Setter
@Getter @ToString
public class TestEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;
    @Column( name = "name" , nullable = false , length = 100, unique = true) // 제약조건설정
    private String name; // 이름

    @Column( columnDefinition = "varchar(100) not null default '제품설명'")
    private String descri; // 설명

    @Column( insertable = true , updatable = true)
    private Integer price; // 가격
    // + 제품 등록일 ++ 제품 수정일 ------> basetime 상속
}
/*
    - @Column( name = "필드명" ) : 생략시 자동으로 멤버변수지정
    - @Column( nullable = true/false ) : not null
    - @Column( length = "문자열길이~255") : 최대 255
    - @Column( unique = true/false) : 중복 가능/불가능
    - @Column( columnDefinition = "SQL 구문") 
    - @Column( insertable = insser여부 / updatable = update여부 )
*/
