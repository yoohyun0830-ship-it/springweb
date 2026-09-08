package example.Practice5.model.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import example.Practice5.model.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO는 DB 테이블 자체가 아니라 데이터를 전달하기 위한 객체이다.
// 프론트 <-> Controller <-> Service 사이에서 데이터를 전달할 때 사용

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BoardDto {
    private Integer id;
    private String author;
    private String password;
    private String content;

    // BaseTime에서 관리되는 생성시간을
    // 조회 응답으로 전달하기 위해 DTO에도 선언한다
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 게시글 전체조회 시
    // 게시글 정보와 함께 해당 게시글의 댓글 목록을 보여줘야 하므로
    // BoardDto 안에 CommentDto List를 둔다.
    @Builder.Default
    private List<CommentDto> comments = new ArrayList<>();

    // + toEntity : 게시글 등록 용도
    public BoardEntity toEntity(){

        // 프론트에서 전달받은 BoardDto 값을
        // DB에 저장하기 위한 BoardEntity로 변환
        return BoardEntity.builder()
                .author(this.author)
                .password(this.password)
                .content(this.content)
                .build();
    }

    // + from : 출력 용도
    public static BoardDto from(BoardEntity entity){

        return BoardDto.builder()
                .id(entity.getId())
                .author(entity.getAuthor())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                // 댓글목록은 Service에서
                .build();
    }
}