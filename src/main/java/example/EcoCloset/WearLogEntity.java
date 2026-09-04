package example.EcoCloset;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wearlog")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WearLogEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wno;
    @Column( name = "wcontext", nullable = false, length = 200, unique = false)
    private String wcontext;

    // 의류테이블 참조 의류번호
    @ManyToOne
    @JoinColumn(name = "clno")
    private ClothesEntity clothesEntity;
    
}
