package example.Practice4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import example.Practice4.model.dto.StudentDto;
import example.Practice4.model.service.StudentService;

@RestController 
@RequestMapping  ("/api/course")
public class StudentController {
    @Autowired private StudentService studentService;

    @PostMapping ("")
    public boolean 학생등록(
        @RequestBody StudentDto studentDto){
            return studentService.학생등록(studentDto);
        }
        @DeleteMapping("")
        public boolean 학생삭제(
            @RequestParam (name = "studentId")Integer studentId){
                return studentService.학생삭제(studentId);
    }
}
