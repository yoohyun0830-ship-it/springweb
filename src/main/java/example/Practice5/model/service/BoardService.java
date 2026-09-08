package example.Practice5.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.Practice5.model.dto.BoardDto;
import example.Practice5.model.dto.CommentDto;
import example.Practice5.model.entity.BoardEntity;
import example.Practice5.model.repository.BoardRepository;

@Service
public class BoardService {
    @Autowired private BoardRepository boardRepository;
    // 1. 게시글 등록
    public boolean 게시글등록(BoardDto boardDto){
        // 1. DTO -> Entity
        BoardEntity boardEntity = boardDto.toEntity();
        // 2. DB 저장
        BoardEntity savedEntity = boardRepository.save(boardEntity);
        // 3. PK 존재하면 성공
        if(savedEntity.getId() >= 1){ return true;}
        return false;
    }
    // 2. 게시글 목록 조회
    public List<BoardDto> 게시글목록조회(){
        // 1. findAll 전체조회
        List<BoardEntity> boardEntities = boardRepository.findAll();
        // 2. Entity -> DTO 변환
        // 최종적으로 Controller에 반환할 BoardDto 목록을 생성한다.
        List<BoardDto> boardDtos = new ArrayList<>();
        // 3.  조회한 게시글 Entity들을 하나씩 반복
        boardEntities.forEach((boardEntity) -> {
            // 3-1. 현재 반복중인 BoardEntity 하나를 BoardDto로 변환한다.
            BoardDto boardDto = BoardDto.from(boardEntity);
            // 3-2. 현재 게시글에 연결되어 있는 댓글 목록을 하나씩 반복한다.
            boardEntity.getCommentEntities().forEach((commentEntity) -> {
                // 현재 댓글 Entity를 CommentDto로 변환한다.
                CommentDto commentDto = CommentDto.from(commentEntity);
                // 변환한 댓글 DTO를 현재 게시글 DTO의 comments 리스트에 추가
                boardDto.getComments().add(commentDto);
            });
            // 3-3. 댓글까지 모두 포함된 BoardDto를 전체 게시글 DTO 목록에 추가한다.
            boardDtos.add(boardDto);
        });
        // 4.  전체 게시글 목록을 Controller에 반환한다.
        return boardDtos;
    }
    // 3. 게시글 삭제
    public boolean 게시글삭제( Integer id, String password){
        // 1. 게시글번호 이용해서 게시글 찾기
        // findById() 결과는 Optional로 반환된다.
        Optional<BoardEntity> optional = boardRepository.findById(id);
        // 2. 게시글 존재하면
        if(optional.isPresent()){
            // 3. Optional 안에 들어있는 실제 BoardEntity 객체를 꺼낸다.
            BoardEntity boardEntity = optional.get();
            // 3. 비밀번호가 같으면
            if(boardEntity.getPassword().equals(password)){
                // 게시글 삭제
                boardRepository.deleteById(id); return true;}}
        return false;
    }
}