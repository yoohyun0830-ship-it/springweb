package example.Practice2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository 
        extends JpaRepository<TestEntity , Integer>{
    // 1. extends JpaRepository <조작할엔티티명 ,  조작할엔티티PK타입>
    // 2. 기본 CRUD 제공 받는다. SAVE() , findById() , findAll() , deleteById()
    // 3. 쿼리 제공받는다
} 