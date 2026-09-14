package example.Practice5.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.Practice5.model.dto.BoardDto;
import example.Practice5.model.dto.CommentDto;
import example.Practice5.model.entity.BoardEntity;
import example.Practice5.model.repository.BoardRepository;

@Service
public class BoardService {
    @Autowired private BoardRepository boardRepository;
    
    public boolean 게시글등록( BoardDto boardDto ){
        BoardEntity boardEntity = boardDto.toEntity();  
        BoardEntity savedEntity = boardRepository.save( boardEntity ); 
        if( savedEntity.getId() >= 1 ) return true; 
        return false;
    }
    
    public List<BoardDto> 게시글조회( ){
        List<BoardEntity> boardEntities = boardRepository.findAll(); 
        List<BoardDto> boardDtos = new ArrayList<>();

        boardEntities.forEach( (boardEntity) -> {  
            BoardDto boardDto = BoardDto.from(boardEntity); 
            // 달린댓글
            boardEntity.getCommentEntities().forEach((commentEntity) -> { 
                CommentDto commentDto = CommentDto.from( commentEntity );
                boardDto.getComments().add(commentDto);
            });
            boardDtos.add(boardDto);
        });
        return boardDtos; 
    }

    public boolean 게시글삭제( Integer id , String password ){ 
        BoardEntity boardEntity = boardRepository.findById( id ).orElse( null );
        if( boardEntity != null ){
            if( boardEntity.getPassword().equals( password ) ){
                boardRepository.deleteById( id );
                return true;
            }
        }
        return false;
    }
}