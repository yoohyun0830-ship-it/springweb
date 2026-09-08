package example.Practice4.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import example.Practice4.model.dto.StudentDto;
import example.Practice4.model.entity.StudentEntity;
import example.Practice4.model.repository.StudentRepository;

public class StudentService {
    @Autowired private StudentRepository studentRepository;

    // 1. 학생등록
    public boolean 학생등록(StudentDto studentDto){
        StudentEntity studentEntity = studentDto.toEntity();
        StudentEntity savedEntity = studentRepository.save( studentEntity);
        if( savedEntity.getStudentId()>=1)return true; 
        return false;
    }

    // 2. 학생삭제
    public boolean 학생삭제(Integer studentId){
        // 1. 학생번호 이용한 학생엔티티 찾기
        Optional<StudentEntity>optional = studentRepository.findById(studentId);
        // 2. 만일 엔티티 존재하면
        if( optional.isPresent()){
            studentRepository.deleteById(studentId);
            return true;
        }
        return false;
    }
}
