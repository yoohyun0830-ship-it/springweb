package example.EcoCloset;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
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

@Entity
@Table(name = "clothes")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClothesEntity extends BaseTime {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer clno;

    // 참조 FK 회원 테이블 회원번호
    @ManyToOne
    @JoinColumn(name = "mno" ) // 회원테이블( 회원 번호 )
    private UserEntity userEntity; // 회원 테이블 엔티티

    // 참조 카테고리테이블 카테고리번
    @ManyToOne
    @JoinColumn(name = "cno") // 카테고리테이블(카테고리 번호)
    private CategoriesEntity categoriesEntity;

    // 양방향 착용기록 테이블
    @OneToMany( mappedBy = "clothesEntity")
    @ToString.Exclude
    @Builder.Default    
    private List<WearLogEntity> wearLogEntity = new ArrayList<>(); 

    @Column( name = "clcolor", nullable = false, length = 30, unique = false)
    private String clcolor;
    @Column( name = "clname", nullable = true, length = 100, unique = false)
    private String clname;
    @Column( name = "retype", nullable = true, length = 30, unique = false)
    private String retype;
    
}
