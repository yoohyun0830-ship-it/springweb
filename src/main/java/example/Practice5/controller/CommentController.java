package example.Practice5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import example.Practice5.model.dto.CommentDto;
import example.Practice5.model.service.CommentService;

@RestController 
@RequestMapping("/api/board/comments")
public class CommentController {
    @Autowired private CommentService commentService;

    @PostMapping("")
    public boolean 댓글등록( 
        @RequestBody CommentDto commentDto){
        return commentService.댓글등록(commentDto);
    }
    
    @DeleteMapping 
    public boolean 댓글삭제(
         @RequestParam(name = "commentId") Integer commentId ,
         @RequestParam (name = "password") String password ){
            return commentService.댓글삭제(
                commentId, password);
      }
}
