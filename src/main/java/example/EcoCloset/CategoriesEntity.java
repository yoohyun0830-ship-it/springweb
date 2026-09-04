package example.EcoCloset;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity @Table(name = "categories")
@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder 
public class CategoriesEntity extends BaseTime {
    @Id
    private Integer cno;
    private String cname;

    // 양방향 참조
    @OneToMany(mappedBy = "categoriesEntity")
    @ToString.Exclude
    @Builder.Default
    private List<ClothesEntity>closetList = new ArrayList<>();
}
