package example.Practice5.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "comment")
@NoArgsConstructor @AllArgsConstructor 
@Builder @Data 
public class CommentEntity extends BaseTime {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column
    private String author;
    @Column 
    private String password;
    @Column 
    private String content;

// -- FK : 게시물번호 ----
    // 각 댓글은 특정 게시글에 소속
    @JoinColumn(name = "board_id")
    @ManyToOne 
    private BoardEntity boardEntity;
}
