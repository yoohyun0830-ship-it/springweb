package example.Practice5.controller;

import example.Practice5.model.service.BoardService;

import java.security.Provider.Service;
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

@RestController
// HTTP 요청을 받을 수 있는 Controller 클래스
// 반환값을 JSON 형태로 응답한다.

@RequestMapping("/api/board")
public class BoardController {

    // 1. 게시글 등록(Post)
    @Autowired private BoardService boardService;

    @PostMapping("")
    public boolean 게시글등록(
        @RequestBody BoardDto boardDto){
        return boardService.게시글등록(boardDto);
    }

    // 2. 목록 조회(Get)
    @GetMapping ("")
    public List<BoardDto> 게시글목록조회(){
        return boardService.게시글목록조회();
    }

    // 3. 게시물 삭제 (Delete)
    @DeleteMapping("")
    public boolean 게시글삭제(
        @RequestParam (name = "boardId")Integer id,
        @RequestParam (name = "password") String password
    ){
            return boardService.게시글삭제(id , password);
        }
}
