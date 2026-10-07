package example.Activity1.model.dto;

import java.util.ArrayList;
import java.util.List;

import example.Activity1.model.entity.CategoryEntity;
import example.Activity1.model.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductDto {

    private Integer bno;
    private String name;
    private Integer price;
    private Integer cno;

    @Builder.Default
    private List<ReviewDto> reviewDtos = new ArrayList<>();

    // DTO -> Entity
    public ProductEntity toEntity(CategoryEntity categoryEntity) {
        return ProductEntity.builder()
                .name(this.name)
                .price(this.price)
                .categoryEntity(categoryEntity)
                .build();
    }

    // Entity -> DTO
    public static ProductDto from(ProductEntity entity) {
        return ProductDto.builder()
                .cno(entity.getCategoryEntity().getCno())
                .bno(entity.getBno())
                .name(entity.getName())
                .price(entity.getPrice())
                .build();
    }
}