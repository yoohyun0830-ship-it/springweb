package example.Active1.model.dto;

import example.Active1.model.entity.ProductEntity;
import example.Active1.model.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor 
@AllArgsConstructor 
@Data @Builder 
public class ReviewDto {
    private int rno;
    private int bno;
    private String reviewer;
    private String content;
    private int rating;
    
    // Entity -> Dto 변환
    public static ReviewDto from(ReviewEntity reviewEntity) {

        return ReviewDto.builder()
                .rno(reviewEntity.getRno())
                .bno(reviewEntity.getProductEntity().getBno())
                .reviewer(reviewEntity.getReviewer())
                .content(reviewEntity.getContent())
                .rating(reviewEntity.getRating())
                .build();
    }

    // DTO -> Entity
    public ReviewEntity toEntity(ProductEntity productEntity) {
    return ReviewEntity.builder()
            .productEntity(productEntity)
            .reviewer(this.reviewer)
            .content(this.content)
            .rating(this.rating)
            .build();
}
}
