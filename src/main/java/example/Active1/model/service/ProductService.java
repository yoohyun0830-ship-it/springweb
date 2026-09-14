package example.Active1.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.Active1.model.dto.ProductDto;
import example.Active1.model.dto.ReviewDto;
import example.Active1.model.entity.ProductEntity;
import example.Active1.model.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    // 1. 제품 등록
    public boolean 제품등록(ProductDto productDto) {

        // DTO -> Entity
        ProductEntity productEntity = productDto.toEntity();

        // 제품 저장
        ProductEntity savedEntity =
                productRepository.save(productEntity);

        // 저장 성공 여부 반환
        if (savedEntity.getBno() >= 1) {
            return true;
        }

        return false;
    }


    // 2. 제품 전체 조회
    public List<ProductDto> 제품전체조회() {

        // 제품 Entity 전체 조회
        List<ProductEntity> productEntities =
                productRepository.findAll();

        // ProductDto 여러개 저장할 리스트
        List<ProductDto> productDtos =
                new ArrayList<>();


        // Entity -> DTO
        productEntities.forEach((productEntity) -> {

            // 제품 Entity -> ProductDto
            ProductDto productDto =
                    ProductDto.from(productEntity);


            // 해당 제품의 리뷰들 조회
            productEntity.getReviewEntities().forEach((reviewEntity) -> {

                // ReviewEntity -> ReviewDto
                ReviewDto reviewDto =
                        ReviewDto.from(reviewEntity);

                // ProductDto의 리뷰 리스트에 추가
                productDto.getReviewDtos().add(reviewDto);
            });


            // 제품 DTO 리스트에 추가
            productDtos.add(productDto);
        });


        return productDtos;
    }

}