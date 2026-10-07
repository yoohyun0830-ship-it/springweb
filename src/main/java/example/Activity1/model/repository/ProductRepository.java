package example.Activity1.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import example.Activity1.model.entity.ProductEntity;

@Repository 
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    
}
