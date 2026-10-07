package example.Activity1.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import example.Activity1.model.dto.CategoryDto;
import example.Activity1.model.entity.CategoryEntity;
import example.Activity1.model.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    // 1. 카테고리 등록
    public CategoryDto createCategory(CategoryDto categoryDto) {

        // DTO -> Entity
        CategoryEntity categoryEntity =
                categoryDto.toEntity();

        // 저장
        CategoryEntity savedEntity =
                categoryRepository.save(categoryEntity);

        // Entity -> DTO
        return CategoryDto.from(savedEntity);
    }


    // 2. 카테고리 전체 조회
    public List<CategoryDto> getAllCategories() {

        // 카테고리 전체 조회
        List<CategoryEntity> categoryEntities =
                categoryRepository.findAll();

        // DTO 여러개 저장할 리스트
        List<CategoryDto> categoryDtos =
                new ArrayList<>();

        // Entity -> DTO
        for (CategoryEntity categoryEntity : categoryEntities) {

            CategoryDto categoryDto =
                    CategoryDto.from(categoryEntity);

            categoryDtos.add(categoryDto);
        }

        return categoryDtos;
    }


    // 3. 카테고리 삭제
    public boolean deleteCategory(Integer cno) {

        // 해당 카테고리가 존재하는지 확인
        if (categoryRepository.existsById(cno)) {

            // 카테고리 삭제
            categoryRepository.deleteById(cno);

            return true;
        }

        return false;
    }
}