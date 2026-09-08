package example.Practice5.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.Practice5.model.dto.CommentDto;
import example.Practice5.model.entity.BoardEntity;
import example.Practice5.model.entity.CommentEntity;
import example.Practice5.model.repository.BoardRepository;
import example.Practice5.model.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired private CommentRepository commentRepository;
    @Autowired private BoardRepository boardRepository;
    // 1. 댓글 등록
    public boolean 댓글등록(CommentDto commentDto){
        // 1. DTO -> Entity
        CommentEntity commentEntity = commentDto.toEntity();
        // 2. DTO내 FK 값을 Entity로 변환
        Optional<BoardEntity> optional = boardRepository.findById(commentDto.getBoardId());
        // 3. 게시글이 존재하면
        if(optional.isPresent()){
            // 게시글 Entity 꺼내기
            BoardEntity boardEntity = optional.get();
            // 댓글 Entity에 게시글 Entity 대입
            commentEntity.setBoardEntity(boardEntity);
            // 4. 저장
            CommentEntity savedEntity = commentRepository.save(commentEntity);
            if(savedEntity.getId() >= 1) return true;}
        return false;
    }
    // 2. 댓글 삭제
    public boolean 댓글삭제(Integer commentId, String password){
        // 1. 댓글번호로 댓글 찾기
        Optional<CommentEntity> optional = commentRepository.findById(commentId);
        // 2. 댓글 존재하면
        if(optional.isPresent()){
            // 실제 댓글 Entity를 꺼낸다.
            CommentEntity commentEntity = optional.get();
            // 3. 댓글 비밀번호와 사용자가 입력한 비밀번호 비교
            if(commentEntity.getPassword().equals(password)){
                // 비밀번호가 같으면 댓글 삭제
                commentRepository.deleteById(commentId);
                return true;
            }
        }

        return false;
    }
}