package example.EcoCloset;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteService {
    private final DeleteRepository deleteRepository;

    // 의류삭제
    public boolean delete(int clno){
        Optional<ClothesEntity> optional = deleteRepository.findById(clno);
        if( optional.isPresent()){
            deleteRepository.deleteById(clno);
            return true;
        }
        return false;
    }
    
}
