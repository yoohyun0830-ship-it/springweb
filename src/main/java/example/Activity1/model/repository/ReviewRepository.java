package example.Activity1.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import example.Activity1.model.entity.ReviewEntity;

@Repository 
public interface ReviewRepository extends JpaRepository<ReviewEntity,Integer>{
    // 특정 제품의 리뷰 전체조회
    List<ReviewEntity> findByProductEntity_Bno(int bno);
} 