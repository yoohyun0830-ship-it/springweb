package example.EcoCloset;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DeleteController {
    private final DeleteService deleteService;

    // 의류 삭제
    @DeleteMapping("/smart_closet/{clno}")
    public boolean delete(@PathVariable(name = "clno") int clno){
        return deleteService.delete(clno);
    }

    
}
