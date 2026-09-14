package example.Practice5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import example.Practice5.model.dto.BoardDto;
import example.Practice5.model.service.BoardService;

@RestController
@RequestMapping ("/api/board") 
public class BoardController {
@Autowired private BoardService boardService; 

// 1. 게시글 등록
@PostMapping("")
public boolean 게시글등록( @RequestBody BoardDto boardDto ){
    return boardService.게시글등록(boardDto);
    }

// 2. 게시글 목록 조회 
@GetMapping("")
public List<BoardDto>게시글조회(){
    return boardService.게시글조회();
    }

// 3. 게시글삭제
@DeleteMapping("")
public boolean 게시글삭제(
    @RequestParam (name = "id") Integer id, 
    @RequestParam (name = "password") String password){
        return boardService.게시글삭제(id,password);
    }
}
