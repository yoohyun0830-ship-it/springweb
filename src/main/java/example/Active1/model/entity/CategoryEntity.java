package example.Active1.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity 
@Table(name = "category")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class CategoryEntity {
    @Id 
    @GeneratedValue 
    private Integer cno;

    // 카테고리 하나에 여러 제품이 연결되는 1:N 관계 매핑
    @OneToMany(mappedBy = "categoryEntity" , cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<CategoryEntity> categoryEntities = new ArrayList<>();
}
