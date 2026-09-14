package example.Active1.model.entity;

import java.util.ArrayList;
import java.util.List;

import example.EcoCloset.CategoriesEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Table(name = "product")
@AllArgsConstructor 
@NoArgsConstructor 
@Builder 
@Data 
public class ProductEntity {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int bno;
    private String name;
    private Integer price;

    @ManyToOne 
    @JoinColumn(name = "cno")
    private CategoriesEntity categoriesEntity;

    @OneToMany(mappedBy = "productEntity")
    @ToString.Exclude
    @Builder.Default
    private List<ReviewEntity>reviewEntities = new ArrayList<>();
}
