package example.EcoCloset;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mno;

    @Column(name="mid")
    private String mid;

    @Column(name="mpwd")
    private String mpwd;

     // 양방향 참조
    @OneToMany(mappedBy = "userEntity")
    @ToString.Exclude // 순환 참조 방지
    @Builder.Default // 빌더 사용시 초기 값 디폴트로 사용
    private List<ClothesEntity> list = new ArrayList<>();
}
