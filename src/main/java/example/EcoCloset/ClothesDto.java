package example.EcoCloset;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClothesDto {
    private Integer clno;
    private Integer mno;
    private Integer cno;
    private String clname;
    private String clcolor;
    private String retype;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

      // dto를 entity로
    public ClothesEntity toEntity(){
        return ClothesEntity.builder()
                            .clno(this.clno)
                            .clcolor(this.clcolor)
                            .clname(this.clname)
                            .retype(this.retype)
                            .build();
    }

    // entity를 dto로
    public ClothesDto from(ClothesEntity clothesEntity){
        return ClothesDto.builder()
                        .clno(clothesEntity.getClno())
                        .clcolor(clothesEntity.getClcolor())
                        .clname(clothesEntity.getClname())
                        .retype(clothesEntity.getRetype())
                        .createDate(clothesEntity.getCreateDate())
                        .updateDate(clothesEntity.getUpdateDate())
                        .build();
    }

}
