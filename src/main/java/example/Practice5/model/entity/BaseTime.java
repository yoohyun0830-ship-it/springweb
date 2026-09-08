package example.Practice5.model.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor // 1. 자식클래스가 호출 할 수 있도록 (매개변수 X -> 기본생성자 자동 생성)
@MappedSuperclass // 2. 테이블이 아닌 상속용도 : BaseTime 자체를 테이블로 만들지는 말고, BaseTime의 필드들을 상속받은 Entity의 컬럼으로
@EntityListeners (AuditingEntityListener.class) // 3. 감시기능 : JPA가 Entity에 등록/수정 같은 일이 발생하는 걸 감지
public class BaseTime {
    @CreatedDate 
    private LocalDateTime createdAt;

    @LastModifiedDate 
    private LocalDateTime updatedAt;
   
}
