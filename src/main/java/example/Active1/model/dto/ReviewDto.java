package example.Active1.model.dto;

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
}
