package example.Practice5.model.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import example.Practice5.model.entity.BoardEntity;
import example.Practice5.model.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
@Data 
public class BoardDto {
    private Integer id;
    private String author;
    private String password;
    private String content;

    // +BaseTime
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    private List<CommentDto>comments = new ArrayList<>();

    public BoardEntity toEntity(){
        return BoardEntity.builder()
        .author(this.author)
        .password(this.password)
        .content(this.content)
        .build();
    }

    public static BoardDto from(BoardEntity entity){
        return BoardDto.builder()
        .id(entity.getId())
        .author(entity.getAuthor())
        .password(entity.getPassword())
        .content(entity.getContent())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .build();
    }
}