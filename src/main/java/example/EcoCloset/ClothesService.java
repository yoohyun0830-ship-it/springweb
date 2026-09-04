package example.EcoCloset;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClothesService {
    @Autowired 
    private ClothesRepository clothesRepository;

     // 의류 등록
    public boolean clothesAdd(ClothesDto clothesDto, int mno){
        // 1. dto를 entity로 바꾸기
        ClothesEntity clothesEntity = clothesDto.toEntity();

        // 2. 변환한 엔티티를 저장하기
        ClothesEntity savedClothesEntity = clothesRepository.save(clothesEntity);

        // 3. 엔티티의 clno가 1 이상이라면?
        if (savedClothesEntity.getClno() >= 1) {
            return true;
        } else {
            return false;
        }
    }

    // 의류 전체조회
    public ArrayList<ClothesDto>clothesPrintAll(){
        
        // DB의 모든 의류 조회
        List<ClothesEntity> entityList = clothesRepository.findAll();

        // 반환할 DTO 리스트 생성
        ArrayList<ClothesDto>dtoList = new ArrayList<>();

        // Entity -> DTO 변환 
        for(ClothesEntity entity : entityList ){

            ClothesDto dto = ClothesDto.builder()
            .clno(entity.getClno())
            .mno(entity.getUserEntity().getMno())
            .cno(entity.getCategoriesEntity().getCno())
            .clname(entity.getClname())
            .clcolor(entity.getClcolor())
            .retype(entity.getRetype())
            .build();
        
            dtoList.add(dto);
        }

        // DTO 리스트 반환
        return dtoList;
    }
}
