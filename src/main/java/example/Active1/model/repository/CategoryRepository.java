package example.Active1.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import example.Active1.model.entity.CategoryEntity;

@Repository 
public interface CategoryRepository extends JpaRepository<CategoryEntity , Integer> {

    
} 