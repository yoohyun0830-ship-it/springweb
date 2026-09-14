package example.Active1.model.entity;

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
import lombok.ToString;

@Entity @Table(name = "review")
@Data  @ToString @Builder 
@NoArgsConstructor 
@AllArgsConstructor 
public class ReviewEntity {
    // 리뷰번호 PK(rno)
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int rno;
    
    // 제품번호 FK(bno)
    @ManyToOne 
    @JoinColumn(name = "bno")
    private ProductEntity productEntity;

    // 작성자
    private String reviewer;
    // 리뷰내용
    private String content;
    // 평점
    private int rating;

}
