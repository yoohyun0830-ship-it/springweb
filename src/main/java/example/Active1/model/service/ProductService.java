package example.Active1.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import example.Active1.model.dto.ProductDto;
import example.Active1.model.dto.ReviewDto;
import example.Active1.model.entity.CategoryEntity;
import example.Active1.model.entity.ProductEntity;
import example.Active1.model.repository.CategoryRepository;
import example.Active1.model.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    // 1. 제품 수정
    @Transactional
    public boolean update(ProductDto productDto) {

        Optional<ProductEntity> optional =
                productRepository.findById(productDto.getBno());

        if (optional.isPresent()) {

            ProductEntity productEntity = optional.get();

            productEntity.setName(productDto.getName());
            productEntity.setPrice(productDto.getPrice());

            CategoryEntity categoryEntity =
                    categoryRepository.findById(productDto.getCno())
                            .orElse(null);

            if (categoryEntity != null) {
                productEntity.setCategoryEntity(categoryEntity);
                return true;
            }
        }

        return false;
    }


    // 2. 제품 삭제
    public boolean delete(Integer bno) {

        ProductEntity productEntity =
                productRepository.findById(bno).orElse(null);

        if (productEntity != null) {
            productRepository.deleteById(bno);
            return true;
        }

        return false;
    }


    // 3. 제품 등록
    public boolean 제품등록(ProductDto productDto) {

        CategoryEntity categoryEntity =
                categoryRepository.findById(productDto.getCno())
                        .orElse(null);

        if (categoryEntity != null) {

            ProductEntity productEntity =
                    productDto.toEntity(categoryEntity);

            ProductEntity savedEntity =
                    productRepository.save(productEntity);

            if (savedEntity.getBno() >= 1) {
                return true;
            }
        }

        return false;
    }


    // 4. 제품 전체 조회
    public List<ProductDto> 제품전체조회() {

        List<ProductEntity> productEntities =
                productRepository.findAll();

        List<ProductDto> productDtos =
                new ArrayList<>();

        productEntities.forEach((productEntity) -> {

            ProductDto productDto =
                    ProductDto.from(productEntity);

            // 제품에 연결된 리뷰들을 DTO로 변환
            productEntity.getReviewEntities().forEach((reviewEntity) -> {

                ReviewDto reviewDto =
                        ReviewDto.from(reviewEntity);

                productDto.getReviewDtos().add(reviewDto);
            });

            productDtos.add(productDto);
        });

        return productDtos;
    }
}