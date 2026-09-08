package example.Practice5.model.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import example.Practice5.model.service.BoardService;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 엔티티 : 영구저장을 하는 자료의 실체( 데이터베이스 표 )
@Entity
// Table : 해당 Entity가 연결될 실제 DB테이블명 지정
@Table (name = "board")
// @NoArgsConstructor : 기본생성자 자동 생성
// JPA에서 Entity 객체 생성시 기본생성자 필요
@NoArgsConstructor 
// @AllArgsConstructor : 모든 멤버변수를 매개변수로 받는 생성자 자동 생성
@AllArgsConstructor 
// builder 패턴으로 객체를 생성할 수 있게 한다.
// ex) BoardEntity.builder().author("유재석").build();
@Builder 
// @Data : Getter + Setter + toString + equals/hashCode 등을 자동 생성
@Data 
public class BoardEntity extends BaseTime {
    // 게시글 실제 처리는 BoardService가 담당하기 때문에 BoardService를 주입받는다.
    @Autowired private BoardService boardService;
    
    @Id
    // 해당 필드를 기본키(PK)로 지정한다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK 값을 DB가 자동 증가(AUTO_INCREMENT)하도록 설정한다.
    private Integer id;
    @Column
    private String author;  // 게시글 작성자 DB의 author 컬럼과 연결된다.
    @Column 
    private String password;  // 게시글 삭제 시 확인할 비밀번호
    @Column 
    private String content;  // 게시글 내용
// ---- PK : 1:N 관계 (B(1) - C(N)) ----
@OneToMany
// 게시글 하나에는 댓글이 여러 개 달릴 수 있으므로 1:N 관계이다.

(mappedBy = "boardEntity" , cascade = CascadeType.ALL )
// mappedBy = "boardEntity": 자바에서 매핑할 FK멤버변수명
// cascade = CascadeType.ALL
// 게시글에 수행되는 작업을 댓글에도 같이 적용한다.

@ToString.Exclude
// BoardEntity와 CommentEntity가 서로 참조하기 때문에
// toString() 호출 시 무한 반복되는 순환참조를 방지한다.

@Builder.Default
// builder()로 객체를 생성하더라도
// comments의 기본값인 new ArrayList<>()를 유지하도록 한다.

private List<CommentEntity> commentEntities = new ArrayList<>();
// 하나의 게시글에는 댓글 여러 개가 존재할 수 있으므로 List 사용

}
