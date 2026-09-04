package example.day06;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Table(name = "board")
@NoArgsConstructor @AllArgsConstructor @Builder @Data
public class BoardEntity {
    @Id
    private Integer bno;
    private String bname;

    // 단방향 참조(FK)
    @ManyToOne // 1:다 관계
    @JoinColumn(name = "cno") // FK이름 지정 , 주로 pk와 동일하게
    private CategoryEntity categoryEntity;

    // 양방향 참조
    @OneToMany( mappedBy = "boardEntity")
    @ToString.Exclude
    @Builder.Default
    private List<ReplyEntity> replyList = new ArrayList<>();
    
}
